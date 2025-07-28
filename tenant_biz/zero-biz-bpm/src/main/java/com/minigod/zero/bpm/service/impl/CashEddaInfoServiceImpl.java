package com.minigod.zero.bpm.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minigod.zero.bpm.constant.CashConstant;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.entity.CashEddaInfoEntity;
import com.minigod.zero.bpm.mapper.CashEddaInfoMapper;
import com.minigod.zero.bpm.mq.product.CustOperationLogProducer;
import com.minigod.zero.bpm.service.HsbcEddaCommonService;
import com.minigod.zero.bpm.service.IBpmSecuritiesInfoService;
import com.minigod.zero.bpm.service.ICashEddaInfoService;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.utils.BpmRespCodeUtils;
import com.minigod.zero.bpm.vo.BpmSecuritiesInfoVO;
import com.minigod.zero.bpm.vo.CashEddaInfoVO;
import com.minigod.zero.bpm.vo.request.CashEddaInfoReqVo;
import com.minigod.zero.bpm.vo.request.EddaInfoApplyProtocol;
import com.minigod.zero.bpm.vo.request.EddaInfoCallProtocol;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpm.vo.response.SecEddaInfoVo;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * edda申请流水表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
@Slf4j
public class CashEddaInfoServiceImpl extends ServiceImpl<CashEddaInfoMapper, CashEddaInfoEntity> implements ICashEddaInfoService {

	@Resource
	@Lazy
	private HsbcEddaCommonService hsbcEddaCommonService;
	@Resource
	private IBpmSecuritiesInfoService bpmSecuritiesInfoService;
	@Resource
	private IBpmAccountService bpmAccountService;
	@Resource
	private ICustInfoClient custInfoClient;
	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private CustOperationLogProducer custOperationLogProducer;

	@Value("${hsbc.edda.accountIdentification:002021632001}")
	private String hsbcEddaAccountIdentification;
	@Value("${hsbc.edda.accountName:YUNFENG SECURITIES}")
	private String hsbcEddaAccountName;
	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;
	@Value("${cash.deposit.job.sender.email:noreply@zhizhu.cn}")
	private String cashDepositJobSenderEmail;
	@Value("${cash.deposit.job.accept.email:noreply@zhizhu.cn}")
	private String cashDepositJobAcceptEmail;

	private static String CUBP_HSBC_EDDA_INFO = "";

	@PostConstruct
	public void init() {
		cubpBaseUrl = cubpBaseUrl.trim();
		if (!cubpBaseUrl.endsWith("/")) {
			cubpBaseUrl += "/";
		}
		CUBP_HSBC_EDDA_INFO = cubpBaseUrl + "/proxy/eddaInfoSend";
	}

	@Override
	public IPage<CashEddaInfoVO> selectCashEddaInfoPage(IPage<CashEddaInfoVO> page, CashEddaInfoVO cashEddaInfo) {
		return page.setRecords(baseMapper.selectCashEddaInfoPage(page, cashEddaInfo));
	}

	@Override
	public R saveEddaBank(Long custId, CashEddaInfoReqVo vo) {
		try {
			//如果是汇丰卡的汇丰edda授权，必须传银行预留手机号
			if (StringUtils.isNotBlank(vo.getBankId()) && CashConstant.HSBC_BANK_ID.equals(vo.getBankId())) {
				if (StringUtils.isBlank(vo.getBankPhone())) {
					return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "银行预留手机号为空，无法进行授权");
				}
			}

			BpmTradeAcctRespDto accountInfo = bpmAccountService.getCurrentAcctInfo(custId);
			if (accountInfo == null || !accountInfo.getTradeAccount().equals(vo.getClientId())
				|| !accountInfo.getCapitalAccount().equals(vo.getFundAccount())) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "抱歉，您当前账户信息有误，请重新登录操作。");
			}

			List<CashEddaInfoEntity> secEddaList = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashEddaInfoEntity::getTradeAccount, vo.getClientId())
				.list();
			//相同的银行账号每天最多请求授权5次，请求第6次时直接弹窗拦截提示：抱歉，您当前的银行账号授权申请次数已达上限，请于明日再做尝试。 （如果授权失败，客户重新提交授权申请，也计提交次数）
			int checkCount = 0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, new Date());
			Date nowDate = df.parse(date + " 00:00:00");
			for (CashEddaInfoEntity eddaInfoValid : secEddaList) {
				if (eddaInfoValid.getDepositAccount().equals(vo.getDepositAccount()) && eddaInfoValid.getCreateTime().after(nowDate)) {
					checkCount = checkCount + 1;
				}
			}
			if (checkCount >= 5) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "抱歉，您当前的银行账号授权申请次数已达上限，请于明日再做尝试。");
			}

			//如果存在短信验证未通过的，就更新删除
			new LambdaUpdateChainWrapper<>(baseMapper)
				.eq(CashEddaInfoEntity::getTradeAccount, vo.getClientId())
				.eq(CashEddaInfoEntity::getEddaState, CashConstant.EddaStateEnum.OPT_UNCONFIRMED.getCode())
				.set(CashEddaInfoEntity::getDataState, 2)
				.set(CashEddaInfoEntity::getDeleteTime, new Date())
				.update();

			//校验用户是否已经有存在的有效edda账户数据
			List<CashEddaInfoEntity> eddaInfoList = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashEddaInfoEntity::getTradeAccount, vo.getClientId())
				.eq(CashEddaInfoEntity::getDataState, 1)
				.list();
			for (CashEddaInfoEntity eddaInfoValid : eddaInfoList) {
				if (eddaInfoValid.getBankCode().equals(vo.getBankCode()) && eddaInfoValid.getDepositAccount().equals(vo.getDepositAccount())
					&& eddaInfoValid.getBankIdKind().equals(vo.getBankIdKind()) && eddaInfoValid.getBankIdNo().equals(vo.getBankIdNo())) {
					return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "您已提交过相同资料的授权申请，请先更改资料再做提交。");
				}
			}

			// 默认提交状态为0
			CashEddaInfoEntity eddaInfo = new CashEddaInfoEntity();
			vo.setDepositAccountName(vo.getDepositAccountName().trim());
			BeanUtils.copyProperties(vo, eddaInfo);
			eddaInfo.setTradeAccount(vo.getClientId());
			eddaInfo.setCustId(custId);

			//只对接汇丰edda，收款银行
			eddaInfo.setBenefitBankCore(CashConstant.EDDA_BENEFIT_BANK_CORE);
			eddaInfo.setBenefitNo(hsbcEddaAccountIdentification);
			eddaInfo.setBenefitAccount(hsbcEddaAccountName);

			eddaInfo.setEddaState(CashConstant.EddaStateEnum.AUTHORIZING.getCode());
			eddaInfo.setDataState(1);
			eddaInfo.setPushRecved(0);
			if (CashConstant.HSBC_BANK_ID.equals(vo.getBankId())) {
				//如果是汇丰edda并且申请的银行卡为汇丰卡，需要先短信验证。初始状态为短信验证未通过
				eddaInfo.setEddaState(CashConstant.EddaStateEnum.OPT_UNCONFIRMED.getCode());
				// 汇丰卡edda同步请求授权，不走Job了。直接把pushrecved设为1
				eddaInfo.setPushRecved(1);
				eddaInfo.setSmsConformCount(0);
				eddaInfo.setSmsRetryCount(0);
			}
			eddaInfo.setErrCnt(0);
			eddaInfo.setCreateTime(new Date());
			eddaInfo.setUpdateTime(new Date());
			baseMapper.insert(eddaInfo);
			if (CashConstant.HSBC_BANK_ID.equals(vo.getBankId())) {
				return hsbcEddaCommonService.hsbcBankEddaAuthorisations(eddaInfo);
			}

			sendLogMsg(vo, custId, CommonEnums.CustOperationType.FUND_APPLY_EDDA.code);
			return R.success();
		} catch (Exception e) {
			log.error("保存edda 数据异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	void sendLogMsg(CashEddaInfoReqVo vo, Long custId, int operationType){
		try {
			CustOperationLogEntity entity = new CustOperationLogEntity();
			entity.setCustId(custId);
			entity.setCapitalAccount(vo.getFundAccount());
			entity.setTradeAccount(vo.getClientId());
			entity.setIp(WebUtil.getIP());
			entity.setDeviceCode(WebUtil.getHeader(TokenConstant.DEVICE_CODE));
			entity.setReqParams(JSON.toJSONString(vo));
			entity.setReqTime(new Date());
			entity.setOperationType(operationType);
			AddMessageReq addMessageReq = new AddMessageReq();
			addMessageReq.setMessage(JSONUtil.toJsonStr(entity));
			addMessageReq.setTopic(MqTopics.CUST_OPERATION_LOG_MESSAGE);
			custOperationLogProducer.sendMsg(addMessageReq);
		} catch (Exception e) {
			log.error("记录用户操作日志异常", e);
		}
	}

	@Override
	public R eddaBankRetry(Long custId, CashEddaInfoReqVo vo) {
		CashEddaInfoEntity failedEddaInfo = baseMapper.selectById(vo.getKeyId());
		if (failedEddaInfo == null || failedEddaInfo.getEddaState().compareTo(2) != 0) {
			return R.fail(ResultCode.H5_DISPLAY_ERROR, "EDDA授权失败记录不存在");
		}
		failedEddaInfo.setEddaState(CashConstant.EddaStateEnum.AUTHORIZING.getCode());
		failedEddaInfo.setDataState(1);
		failedEddaInfo.setPushRecved(0);
		if (CashConstant.HSBC_BANK_ID.equals(failedEddaInfo.getBankId())) {
			//如果是汇丰edda并且申请的银行卡为汇丰卡，需要先短信验证。初始状态为短信验证未通过
			failedEddaInfo.setEddaState(CashConstant.EddaStateEnum.OPT_UNCONFIRMED.getCode());
			// 汇丰卡edda同步请求授权，不走Job了。直接把pushrecved设为1
			failedEddaInfo.setPushRecved(1);
			failedEddaInfo.setSmsConformCount(0);
			failedEddaInfo.setSmsRetryCount(0);
		}
		failedEddaInfo.setErrCnt(0);
		failedEddaInfo.setUpdateTime(new Date());
		failedEddaInfo.setApplicationId(null);
		failedEddaInfo.setMandateId(null);
		failedEddaInfo.setEddaFailReason(null);
		failedEddaInfo.setOtpId(null);
		failedEddaInfo.setMandateStatus(null);
		failedEddaInfo.setPhoneMask(null);
		updateEddaInfo(failedEddaInfo);

		// 发送短信通知 查询用户中文信息
		BpmSecuritiesInfoVO bpmSecuritiesInfoVO = new BpmSecuritiesInfoVO();
		bpmSecuritiesInfoVO.setCustId(custId);
		BpmSecuritiesInfoEntity securitiesUserInfo = bpmSecuritiesInfoService.selectBpmSecuritiesInfo(bpmSecuritiesInfoVO);
		if (securitiesUserInfo != null) {
			CustInfoEntity custInfoEntity = custInfoClient.userInfoByUserId(custId);
			String phone = null;
			if (custInfoEntity != null) {
				phone = custInfoEntity.getAreaCode() + "-" + custInfoEntity.getCellPhone();
			}
			if (StringUtils.isNotBlank(phone)) {
				// 发送edda 提交短信
				SendSmsDTO sendSmsDTO = new SendSmsDTO();
				sendSmsDTO.setPhone(phone);
				sendSmsDTO.setTemplateCode(2048);
				sendSmsDTO.setParams(Arrays.asList(securitiesUserInfo.getCustName()));
				platformMsgClient.sendSms(sendSmsDTO);
			}
		}
		if (CashConstant.HSBC_BANK_ID.equals(failedEddaInfo.getBankId())) {
			return hsbcEddaCommonService.hsbcBankEddaAuthorisations(failedEddaInfo);
		}
		return R.success();
	}

	@Override
	public R updateEddaBankInfo(Long custId, CashEddaInfoReqVo vo) {
		try {
			//如果是汇丰卡的汇丰edda授权，必须传银行预留手机号
			if (StringUtils.isNotBlank(vo.getBankId()) && CashConstant.HSBC_BANK_ID.equals(vo.getBankId())) {
				if (StringUtils.isBlank(vo.getBankPhone())) {
					return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "银行预留手机号为空，无法进行授权");
				}
			}

			// 获取sessionId对应的资金帐号，客户号，交易账号必须相同
			BpmSecuritiesInfoVO bpmSecuritiesInfoVO = new BpmSecuritiesInfoVO();
			bpmSecuritiesInfoVO.setCustId(custId);
			BpmTradeAcctRespDto accountInfo = bpmAccountService.getCurrentAcctInfo(custId);
			if (accountInfo == null || !accountInfo.getTradeAccount().equals(vo.getClientId())
				|| !accountInfo.getCapitalAccount().equals(vo.getFundAccount())) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "抱歉，您当前账户信息有误，请重新登录操作。");
			}

			List<CashEddaInfoEntity> secEddaList = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashEddaInfoEntity::getTradeAccount, vo.getClientId())
				.list();
			//相同的银行账号每天最多请求授权5次，请求第6次时直接弹窗拦截提示：抱歉，您当前的银行账号授权申请次数已达上限，请于明日再做尝试。 （如果授权失败，客户重新提交授权申请，也计提交次数）
			int checkCount = 0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, new Date());
			Date nowDate = df.parse(date + " 00:00:00");
			for (CashEddaInfoEntity eddaInfoValid : secEddaList) {
				if (eddaInfoValid.getDepositAccount().equals(vo.getDepositAccount()) && eddaInfoValid.getCreateTime().after(nowDate)) {
					checkCount = checkCount + 1;
				}
			}
			if (checkCount >= 5) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "抱歉，您当前的银行账号授权申请次数已达上限，请于明日再做尝试。");
			}

			//获取需要修改的原数据
			CashEddaInfoEntity oldEdda = baseMapper.selectById(vo.getKeyId());
			if (oldEdda == null || oldEdda.getId() == null || oldEdda.getId() < 1) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "不存在需要更新数据，更正授权信息失败");
			}
			if (oldEdda.getEddaState() != 2) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "数据授权状态错误，更正授权信息失败");
			}
			if (!vo.getClientId().equals(oldEdda.getTradeAccount())) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "操作用户异常，删除授权信息失败");
			}

			if (oldEdda.getEddaState() != 2) {
				//更新数据，校验用户是否已经有存在的有效edda账户数据,排除掉原需要修改的数据
				List<CashEddaInfoEntity> secEddaInfoList = new LambdaQueryChainWrapper<>(baseMapper)
					.eq(CashEddaInfoEntity::getTradeAccount, vo.getClientId())
					.eq(CashEddaInfoEntity::getDataState, 1)
					.list();
				for (CashEddaInfoEntity eddaInfoValid : secEddaInfoList) {
					if (eddaInfoValid.getBankCode().equals(vo.getBankCode()) && eddaInfoValid.getDepositAccount().equals(vo.getDepositAccount())
						&& eddaInfoValid.getBankIdKind().equals(vo.getBankIdKind()) && eddaInfoValid.getBankIdNo().equals(vo.getBankIdNo())) {
						if (!vo.getKeyId().equals(eddaInfoValid.getId())) {
							return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "您已提交过相同资料的授权申请，请先更改资料再做提交。");
						} else if (eddaInfoValid.getDepositAccountType().equals(vo.getDepositAccountType())) {
							return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "您已提交过相同资料的授权申请，请先更改资料再做提交。");
						}
					}
				}
			}

			//用户更新，先更新删除旧数据，再新增一笔
			oldEdda.setDeleteTime(new Date());
			oldEdda.setDataState(2);
			updateEddaInfo(oldEdda);

			// 默认提交状态为0
			CashEddaInfoEntity eddaInfo = new CashEddaInfoEntity();
			BeanUtils.copyProperties(vo, eddaInfo);
			eddaInfo.setTradeAccount(vo.getClientId());
			eddaInfo.setCustId(custId);
			//汇丰edda收款银行
			eddaInfo.setBenefitBankCore(CashConstant.EDDA_BENEFIT_BANK_CORE);
			eddaInfo.setBenefitNo(hsbcEddaAccountIdentification);
			eddaInfo.setBenefitAccount(hsbcEddaAccountName);
			eddaInfo.setEddaState(CashConstant.EddaStateEnum.AUTHORIZING.getCode());
			eddaInfo.setDataState(1);
			eddaInfo.setPushRecved(0);
			if (CashConstant.HSBC_BANK_ID.equals(vo.getBankId())) {
				//如果是汇丰edda并且申请的银行卡为汇丰卡，需要先短信验证。初始状态为短信验证未通过
				eddaInfo.setEddaState(CashConstant.EddaStateEnum.OPT_UNCONFIRMED.getCode());
				// 汇丰卡edda同步请求授权，不走Job了。直接把pushrecved设为1
				eddaInfo.setPushRecved(1);
				eddaInfo.setSmsConformCount(0);
				eddaInfo.setSmsRetryCount(0);
			}
			eddaInfo.setErrCnt(0);
			eddaInfo.setCreateTime(new Date());
			eddaInfo.setUpdateTime(new Date());
			getBaseMapper().insert(eddaInfo);
			if (CashConstant.HSBC_BANK_ID.equals(vo.getBankId())) {
				return hsbcEddaCommonService.hsbcBankEddaAuthorisations(eddaInfo);
			}
			return R.success();
		} catch (Exception e) {
			log.error("更新edda 授权数据", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	@Override
	public R deleteEddaBank(Long custId, CashEddaInfoReqVo vo) {
		try {
			//获取需要修改的原数据
			CashEddaInfoEntity oldEdda = baseMapper.selectById(vo.getKeyId());
			if (oldEdda == null || oldEdda.getId() == null || oldEdda.getId() < 1) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "不存在需要删除数据，删除授权信息失败");
			}
			if (oldEdda.getEddaState() != CashConstant.EddaStateEnum.FAIL.getCode()) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "数据授权状态错误，删除授权信息失败");
			}
			if (!vo.getClientId().equals(oldEdda.getTradeAccount())) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "操作用户异常，删除授权信息失败");
			}

			//更新数据为用户删除状态
			oldEdda.setDeleteTime(new Date());
			oldEdda.setDataState(0);
			updateEddaInfo(oldEdda);
			return R.success();
		} catch (Exception e) {
			log.error("删除edda 授权数据异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	@Override
	public R getEddaBankInfo(Long custId, CashEddaInfoReqVo vo) {
		List<CashEddaInfoEntity> eddaInfoList = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashEddaInfoEntity::getTradeAccount, vo.getClientId())
			.eq(CashEddaInfoEntity::getDataState, 1)
			.eq(CashEddaInfoEntity::getBankCode, vo.getBankCode())
			.eq(CashEddaInfoEntity::getBankType, vo.getBankType())
			.orderByDesc(CashEddaInfoEntity::getEddaState)
			.list();
		eddaInfoList.removeIf(o -> o.getEddaState() == CashConstant.EddaStateEnum.OPT_UNCONFIRMED.getCode());
		List<SecEddaInfoVo> respVoList = Lists.newArrayList();
		for (CashEddaInfoEntity eddaInfo : eddaInfoList) {
			SecEddaInfoVo respVo = new SecEddaInfoVo();
			//数据Id
			respVo.setKeyId(eddaInfo.getId());
			//交易账户
			respVo.setClientId(eddaInfo.getTradeAccount());
			//银行 1大陆 2香港 3其他
			respVo.setBankType(eddaInfo.getBankType());
			//资金账号
			respVo.setFundAccount(eddaInfo.getFundAccount());
			//银行名称
			respVo.setBankName(eddaInfo.getBankName());
			//银行代码
			respVo.setBankCode(eddaInfo.getBankCode());
			//银行bankid
			respVo.setBankId(eddaInfo.getBankId());
			//存入账户
			respVo.setDepositAccount(eddaInfo.getDepositAccount());
			//存入账户名称
			respVo.setDepositAccountName(eddaInfo.getDepositAccountName());
			//存入账户类型:1 港币账户; 2 综合多币种账户
			respVo.setDepositAccountType(eddaInfo.getDepositAccountType());
			//银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
			respVo.setBankIdKind(eddaInfo.getBankIdKind());
			//银行开户证件号码
			respVo.setBankIdNo(eddaInfo.getBankIdNo());
			//状态 0未授权 1授权中 2授权失败 3授权成功 4解除授权
			respVo.setEddaState(eddaInfo.getEddaState());
			//失败原因
			respVo.setEddaFailReason(eddaInfo.getEddaFailReason());
			respVo.setBankQuota(eddaInfo.getBankQuota());
			respVo.setIcon(eddaInfo.getIcon());
			respVoList.add(respVo);
		}

		return R.data(respVoList);
	}

	@Override
	public ResponseVO updateEddaStatus(EddaInfoCallProtocol protocol) {
		String applicationId = protocol.getApplicationId();
		Integer eddaStatus = protocol.getEddaState();
		if (StringUtils.isBlank(applicationId)) {
			return BpmRespCodeUtils.getErrorMsg(-1, "预约号不能为空");
		}
		if (eddaStatus == null) {
			return BpmRespCodeUtils.getErrorMsg(-1, "eDDA状态不能为空");
		}
		CashEddaInfoEntity eddaInfo = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashEddaInfoEntity::getApplicationId, applicationId)
			.one();
		if (eddaInfo == null) {
			return BpmRespCodeUtils.getErrorMsg(-1, "eDDA记录不存在");
		}
		eddaInfo.setEddaState(eddaStatus);
		if (CashConstant.EddaStateEnum.RELIEVE.getCode() == eddaStatus) {
			eddaInfo.setDataState(2);
		}
		eddaInfo.setPushRecved(1);
		eddaInfo.setEddaFailReason(protocol.getEddaFailReason());
		eddaInfo.setBankQuota(protocol.getBankQuota());
		eddaInfo.setUpdateTime(new Date());
		updateEddaInfo(eddaInfo);
		return BpmRespCodeUtils.getSuccessMsg(new ResponseVO());
	}

	@Override
	public int updateEddaInfo(CashEddaInfoEntity eddaInfo) {
		LambdaUpdateWrapper<CashEddaInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(CashEddaInfoEntity::getId, eddaInfo.getId());
		updateWrapper.set(CashEddaInfoEntity::getEddaState, eddaInfo.getEddaState());
		updateWrapper.set(CashEddaInfoEntity::getDataState, eddaInfo.getDataState());
		updateWrapper.set(CashEddaInfoEntity::getPushRecved, eddaInfo.getPushRecved());
		updateWrapper.set(CashEddaInfoEntity::getSmsConformCount, eddaInfo.getSmsConformCount());
		updateWrapper.set(CashEddaInfoEntity::getSmsRetryCount, eddaInfo.getSmsRetryCount());
		updateWrapper.set(CashEddaInfoEntity::getErrCnt, eddaInfo.getErrCnt());
		updateWrapper.set(CashEddaInfoEntity::getUpdateTime, eddaInfo.getUpdateTime());
		updateWrapper.set(CashEddaInfoEntity::getApplicationId, eddaInfo.getApplicationId());
		updateWrapper.set(CashEddaInfoEntity::getMandateId, eddaInfo.getMandateId());
		updateWrapper.set(CashEddaInfoEntity::getEddaFailReason, eddaInfo.getEddaFailReason());
		updateWrapper.set(CashEddaInfoEntity::getOtpId, eddaInfo.getOtpId());
		updateWrapper.set(CashEddaInfoEntity::getMandateStatus, eddaInfo.getMandateStatus());
		updateWrapper.set(CashEddaInfoEntity::getPhoneMask, eddaInfo.getPhoneMask());
		updateWrapper.set(CashEddaInfoEntity::getBankQuota, eddaInfo.getBankQuota());
		updateWrapper.set(CashEddaInfoEntity::getDeleteTime, eddaInfo.getDeleteTime());
		return baseMapper.update(null, updateWrapper);
	}

	@Override
	public R executeClientEddaInfoJob() {
		List<CashEddaInfoEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashEddaInfoEntity::getPushRecved, 0)
			.list();
		for (CashEddaInfoEntity eddaInfo : list) {
			try {
				if (eddaInfo.getErrCnt() > 3) {
					continue;
				}
				EddaInfoApplyProtocol protocol = new EddaInfoApplyProtocol();
				protocol.setBankPhone(eddaInfo.getBankPhone());
				protocol.setClientId(eddaInfo.getTradeAccount());
				protocol.setFundAccount(eddaInfo.getFundAccount());
				protocol.setBankName(eddaInfo.getBankName());
				protocol.setBankCode(eddaInfo.getBankCode());
				protocol.setBankId(eddaInfo.getBankId());
				protocol.setDepositAccount(eddaInfo.getDepositAccount());
				protocol.setDepositAccountName(eddaInfo.getDepositAccountName());
				protocol.setDepositAccountType(eddaInfo.getDepositAccountType());
				protocol.setBankIdKind(eddaInfo.getBankIdKind());
				protocol.setBankIdNo(eddaInfo.getBankIdNo());
				protocol.setBenefitAccount(eddaInfo.getBenefitAccount());
				protocol.setBenefitBankCore(eddaInfo.getBenefitBankCore());
				protocol.setBenefitNo(eddaInfo.getBenefitNo());
				//info 银行 1大陆 2香港 3其他
				//protocol 银行 1大陆 0香港 2其他
				if (eddaInfo.getBankType() == 2) {
					protocol.setBankType(0);
				} else if (eddaInfo.getBankType() == 1) {
					protocol.setBankType(1);
				} else if (eddaInfo.getBankType() == 3) {
					protocol.setBankType(2);
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("params", protocol);
				log.info(eddaInfo.getCustId() + "EddaInfo send params：" + JSON.toJSONString(map));
				String result = HttpClientUtils.postJson(CUBP_HSBC_EDDA_INFO, JSONObject.toJSONString(map), true);
				log.info(eddaInfo.getCustId() + "EddaInfo send return：" + result);
				if (StringUtils.isBlank(result)) {
					log.info(eddaInfo.getCustId() + "Edda授权数据上送失败，下游返回NULL");
					continue;
				}
				ResponseVO responseVO = JSONObject.parseObject(result, ResponseVO.class);
				if (responseVO.getCode() == 0) {
					String applicationId = JSONObject.parseObject(responseVO.getResult().toString()).get("applicationId").toString();
					eddaInfo.setApplicationId(applicationId);
					eddaInfo.setPushRecved(1);
					eddaInfo.setUpdateTime(new Date());
					updateEddaInfo(eddaInfo);
					log.info(eddaInfo.getCustId() + "Edda授权数据上送成功");
				} else {
					eddaInfo.setErrCnt(eddaInfo.getErrCnt() + 1);
					log.info(eddaInfo.getCustId() + "Edda授权数据上送失败");
					updateEddaInfo(eddaInfo);
					saveErrorInfo(eddaInfo, "Edda授权数据发送失败：" + result, "Edda授权数据发送失败");
				}
			} catch (Exception e) {
				log.error("汇丰Edda数据上送异常", e);
				eddaInfo.setErrCnt(eddaInfo.getErrCnt() + 1);
				updateEddaInfo(eddaInfo);
				saveErrorInfo(eddaInfo, "Edda授权数据上送异常：" + e, "Edda授权数据上送异常");
			}
		}
		return R.success();
	}

	private void saveErrorInfo(CashEddaInfoEntity info, String errorMsg, String body) {
		if (info.getErrCnt() <= 1) {
			String title = "用户号：" + info.getCustId() + "Edda授权数据上送任务异常";
			errorMsg = errorMsg + "【" + body + "】";
			SendEmailDTO sendEmailDTO = new SendEmailDTO();
			sendEmailDTO.setContent(errorMsg);
			sendEmailDTO.setTitle(title);
			sendEmailDTO.setSender(cashDepositJobSenderEmail);
			sendEmailDTO.setAccepts(Arrays.asList(cashDepositJobAcceptEmail));
			platformMsgClient.sendEmail(sendEmailDTO);
		}
	}
}
