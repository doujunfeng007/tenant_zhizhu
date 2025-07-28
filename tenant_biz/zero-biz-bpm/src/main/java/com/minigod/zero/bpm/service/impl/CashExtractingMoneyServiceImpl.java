package com.minigod.zero.bpm.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.constant.CashConstant;
import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.entity.CashExtractingMoneyEntity;
import com.minigod.zero.bpm.mapper.CashExtractingMoneyMapper;
import com.minigod.zero.bpm.mq.product.CustOperationLogProducer;
import com.minigod.zero.bpm.mq.product.WithdrawalApplyProducer;
import com.minigod.zero.bpm.service.ICashAccessImageService;
import com.minigod.zero.bpm.service.ICashExtractingMoneyService;
import com.minigod.zero.bpm.utils.BpmRespCodeUtils;
import com.minigod.zero.bpm.vo.CashExtractingMoneyVO;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.bpm.vo.response.CashImgRespVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.cms.entity.oms.CashWithdrawalsBankEntity;
import com.minigod.zero.cms.feign.oms.ICashWithdrawalsBankClient;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.feign.ICustAccountClient;
import com.minigod.zero.trade.feign.ICustCashClient;
import com.minigod.zero.trade.vo.req.CashFrozenCancelVO;
import com.minigod.zero.trade.vo.req.CashFrozenVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 提取资金表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
@Slf4j
public class CashExtractingMoneyServiceImpl extends ServiceImpl<CashExtractingMoneyMapper, CashExtractingMoneyEntity> implements ICashExtractingMoneyService {

	@Resource
	private ICashAccessImageService cashAccessImageService;
	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private ICustAccountClient custAccountClient;
	@Resource
	private ICashWithdrawalsBankClient cashWithdrawalsBankClient;
	@Resource
	private CustOperationLogProducer custOperationLogProducer;
	@Resource
	private ICustCashClient custCashClient;
	@Resource
	private RedisLockClient redisLockClient;
	@Resource
	private WithdrawalApplyProducer withdrawalApplyProducer;
	@Resource
	private CashSendMsgCommonService cashSendMsgCommonService;

	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;
	@Value("${extracting.money.job.sender.email:noreply@zhizhu.cn}")
	private String extractingMoneyJobSenderEmail;
	@Value("${extracting.money.job.accept.email:noreply@zhizhu.cn}")
	private String extractingMoneyJobAcceptEmail;

	//出金解冻
	private String CLIENT_FUND_FROZEN_CANCEL = "";
	//出金申请
	private String CLIENT_FUND_WITHDRAW_APPLY = "";
	//恒生柜台状态
	private String HS_STATUS = "";

	@PostConstruct
	protected void initRestUrl() {
		cubpBaseUrl = cubpBaseUrl.trim();
		if (!cubpBaseUrl.endsWith("/")) {
			cubpBaseUrl += "/";
		}
		CLIENT_FUND_FROZEN_CANCEL = cubpBaseUrl + "/proxy/fund/fundWithdrawApplyCancel";
		CLIENT_FUND_WITHDRAW_APPLY = cubpBaseUrl + "/proxy/fund/clientFundWithdrawApply";
		HS_STATUS = cubpBaseUrl + "/crm_api/getHsStauts";
	}

	@Override
	public IPage<CashExtractingMoneyVO> selectCashExtractingMoneyPage(IPage<CashExtractingMoneyVO> page, CashExtractingMoneyVO cashExtractingMoney) {
		return page.setRecords(baseMapper.selectCashExtractingMoneyPage(page, cashExtractingMoney));
	}

	@Override
	public R extractFund(Long custId, ExtractMoneyReqVo vo) {
		R<Map<String, String>> vfResp = validateFundAndFrozen(vo);
		if (!vfResp.isSuccess()) {
			return vfResp;
		}
		Map<String, String> frozenResp = vfResp.getData();
		String revertSerialNo = frozenResp.get("revertSerialNo");
		String initDate = frozenResp.get("initDate");

		CashExtractingMoneyEntity extractingMoney = new CashExtractingMoneyEntity();
		extractingMoney.setCustId(custId);
		extractingMoney.setExtAccount(vo.getExtAccount());
		extractingMoney.setExtAccountName(vo.getExtAccountName());
		extractingMoney.setTradeAccount(vo.getClientId());
		extractingMoney.setExtMethod(vo.getExtMethod());
		extractingMoney.setBankName(vo.getBankName());
		extractingMoney.setBankCode(vo.getBankCode());
		extractingMoney.setSwiftCode(vo.getSwiftCode());
		extractingMoney.setPayee(vo.getPayee());
		extractingMoney.setAddress(vo.getAddress());
		extractingMoney.setBankAccount(vo.getBankAccount());
		extractingMoney.setAvailableAmount(vo.getAvailableAmount());
		extractingMoney.setExtractionAmount(vo.getExtractionAmount());
		extractingMoney.setStatus(0);
		extractingMoney.setChargeMoney(vo.getChargeMoney());
		extractingMoney.setCreatedTime(new Date());
		extractingMoney.setModifyTime(new Date());
		extractingMoney.setCurrency(vo.getCurrency());
		extractingMoney.setBankId(vo.getBankId());
		extractingMoney.setBusType(vo.getBusType());
		if (StringUtils.isNotBlank(vo.getExtractMoneyImgIds())) {
			String ids = formatImgIds(vo.getExtractMoneyImgIds());
			extractingMoney.setExtractMoneyImgIds(ids);
		}
		extractingMoney.setRevertSerialNo(revertSerialNo);
		extractingMoney.setInitDate(initDate);
		baseMapper.insert(extractingMoney);

		//发送通知
		cashSendMsgCommonService.sendPushEmail(custId, WebUtil.getLanguage(), CommonTemplateCode.Push.WITHDRAWAL_INSTRUCTION_RECEIVED, CommonTemplateCode.Email.WITHDRAWAL_INSTRUCTION_RECEIVED);

		sendLogMsg(vo, custId, CommonEnums.CustOperationType.FUND_APPLY_OUT.code);
		return R.success();
	}

	private R<Map<String, String>> validateFundAndFrozen(ExtractMoneyReqVo vo) {
		String capitalAccount = vo.getExtAccount();

		//校验可取资金、冻结资金 两个步骤加锁，以防止多个出金请求同一时刻并发获取到一样的可取资金，导致冻结资金至负数
		try {
			boolean lock = redisLockClient.tryLock(CashConstant.EXTRACT_FUND_LOCK + capitalAccount, LockType.FAIR, CashConstant.EXTRACT_FUND_LOCK_WAIT_SECONDS, CashConstant.EXTRACT_FUND_LOCK_LEASE_SECONDS, TimeUnit.SECONDS);
			if (!lock) {
				log.info("资金账号：{} 提取资金获取分布式锁失败");
				return R.fail("操作过于频繁，请稍后再试");
			}
		} catch (Exception e) {
			log.error("资金账号：{} 提取资金获取分布式锁异常：{}", e.getMessage(), e);
			return R.fail(ResultCode.SOCKET_ERROR.getMessage());
		}

		try {
			String moneyType = null;
			if (vo.getCurrency() == 2) {
				moneyType = "1";
			} else if (vo.getCurrency() == 3) {
				moneyType = "0";
			} else {
				moneyType = "2";
			}
			//校验可取资金
			R<String> tradeResp = custAccountClient.getExtractableMoney(capitalAccount, moneyType);
			log.info("出金获取用户资金信息返回：{}", JSON.toJSONString(tradeResp));
			if (tradeResp.getCode() != ResultCode.SUCCESS.getCode()) {
				return R.fail("获取资金信息失败");
			}
			BigDecimal totalAmount = new BigDecimal(tradeResp.getData());
			if (totalAmount.compareTo(vo.getExtractionAmount()) < 0) {
				return R.fail("可扣减购买力不足");
			}
			//资金冻结
			CashFrozenVO cashFrozenVO = new CashFrozenVO();
			cashFrozenVO.setCapitalAccount(vo.getExtAccount());
			cashFrozenVO.setOccurBalance(vo.getExtractionAmount().toString());
			cashFrozenVO.setMoneyType(moneyType);
			cashFrozenVO.setFrozenReason("0");
			cashFrozenVO.setRemark("BATCH FROZEN");
			cashFrozenVO.setLocaleRemark("BATCH FROZEN");
			cashFrozenVO.setOverdraftForcedFlag("0");
			cashFrozenVO.setRevertFlag("0");
			R<Map<String, String>> cashFrozenResp = custCashClient.cashFrozen(cashFrozenVO);
			log.info("出金冻结资金返回：{}", JSON.toJSONString(cashFrozenResp));
			if (cashFrozenResp.getCode() != ResultCode.SUCCESS.getCode()) {
				return R.fail("资金冻结失败");
			}
			log.info("资金账号：{} 资金冻结成功", capitalAccount);
			return cashFrozenResp;
		} catch (Exception e) {
			log.error("资金账号：{} 出金校验可取资金&冻结资金异常:{}", capitalAccount, e.getMessage(), e);
			return R.fail(ResultCode.SOCKET_ERROR.getMessage());
		} finally {
			redisLockClient.unLock(CashConstant.EXTRACT_FUND_LOCK + capitalAccount, LockType.FAIR);
		}
	}

	@Override
	public R getWithdrawImg(Long custId, String imgIds) {
		if (StringUtils.isBlank(imgIds)) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		String imgIdsArr[] = imgIds.split(",");
		List<CashImgRespVo> respVos = new ArrayList<>();
		for (String imgId : imgIdsArr) {
			CashImgRespVo respVo = new CashImgRespVo();
			respVo.setImgId(Long.valueOf(imgId.trim()));
			respVo.setImgPath("");
			CashAccessImageEntity accImg = cashAccessImageService.getBaseMapper().selectById(Long.valueOf(imgId.trim()));
			if (null != accImg && StringUtils.isNotBlank(accImg.getAccImgPath()) && custId.intValue() == accImg.getCustId().intValue()) {
				respVo.setImgPath(accImg.getAccImgPath());
			}
			respVos.add(respVo);
		}
		return R.data(respVos);
	}

	@Override
	public R getExtractableMoney(AccountFundReqVo vo) {
		try {
			String moneyType = vo.getMoneyType();
			String capitalAccount = vo.getCapitalAccount();
			if (StringUtils.isBlank(moneyType) || StringUtils.isBlank(capitalAccount)) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}

			R<String> tradeResp = custAccountClient.getExtractableMoney(capitalAccount, moneyType);
			if (tradeResp.getCode() == ResultCode.SUCCESS.getCode()) {
				Map map = new HashMap(1);
				map.put("totalAmount", tradeResp.getData());
				return R.data(map);
			}
			log.error("获取用户资金信息失败，返回：{}", JSON.toJSONString(tradeResp));
			return R.fail(ResultCode.INTERNAL_ERROR);
		} catch (Exception e) {
			log.error("获取用户资金信息异常 ", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	@Override
	public ResponseVO updateFundWithdrawStatus(ClientFundWithdrawCallbackProtocol vo) {
		ResponseVO rt = new ResponseVO();
		Long bizId = vo.getBizId();
		String applicationId = vo.getApplicationId();
		Integer status = vo.getWithdrawStatus();

		if ((StringUtils.isBlank(applicationId) && bizId == null) || status == null) {
			return BpmRespCodeUtils.getErrorParameMsg(rt);
		}

		CashExtractingMoneyEntity extractingMoney = null;
		if (bizId != null) {
			extractingMoney = baseMapper.selectById(bizId);
			if (extractingMoney == null) {
				return BpmRespCodeUtils.getErrorMsg(-1, "中台不存在该出金记录id：" + bizId);
			}
			if (StringUtils.isBlank(extractingMoney.getApplicationId()) && StringUtils.isNotBlank(applicationId)) {
				extractingMoney.setApplicationId(applicationId);
			}
		} else {
			extractingMoney = new LambdaQueryChainWrapper<>(baseMapper).eq(CashExtractingMoneyEntity::getApplicationId, applicationId).one();
			if (extractingMoney == null) {
				return BpmRespCodeUtils.getErrorMsg(-1, "该预约号不存在：" + applicationId);
			}
		}
		if (status == 0) {
			extractingMoney.setPushRecved(2);
		}
		extractingMoney.setBackReason(vo.getBackReason());
		extractingMoney.setStatus(status);

		//BPM后台校验不通过自动拒绝的，需进行解冻资金
		if (StringUtils.isBlank(applicationId) && status == 2) {
			CashFrozenCancelVO cashFrozenCancelVO = new CashFrozenCancelVO();
			cashFrozenCancelVO.setJourDate(extractingMoney.getInitDate());
			cashFrozenCancelVO.setJourSerialNo(extractingMoney.getRevertSerialNo());
			cashFrozenCancelVO.setCancelBalance(extractingMoney.getExtractionAmount().toString());
			cashFrozenCancelVO.setOccurBalance(extractingMoney.getExtractionAmount().toString());
			R<Map<String, String>> cashFrozenCancelResp = custCashClient.cashFrozenCancel(cashFrozenCancelVO);
			log.info("出金解冻资金返回:{}", JSON.toJSONString(cashFrozenCancelResp));
			if (cashFrozenCancelResp.getCode() != ResultCode.SUCCESS.getCode()) {
				log.info("资金账号:{} 解冻资金失败", extractingMoney.getExtAccount());
				return BpmRespCodeUtils.getErrorMsg(-1, "解冻资金失败");
			}
			log.info("资金账号:{} 解冻资金成功", extractingMoney.getExtAccount());
		}

		baseMapper.updateById(extractingMoney);
		return BpmRespCodeUtils.getSuccessMsg(rt);
	}

	@Override
	public R cancelClientWithdraw(ExtractMoneyReqVo vo) {
		// 判断恒生状态
		String result1 = HttpClientUtils.get(cubpBaseUrl + "/crm_api/getHsStauts", null, Charset.forName("UTF-8"), true);
		if (StringUtils.isNotBlank(result1)) {
			ResponseVO responseVO = JSONObject.parseObject(result1, ResponseVO.class);
			if (responseVO.getCode() == 0) {
				String sysStauts = JSONObject.parseObject(responseVO.getResult().toString()).getString("sysStatus");
				String bankStatus = JSONObject.parseObject(responseVO.getResult().toString()).getString("bankStatus");
				if ("6".equals(sysStauts) || "0".equals(bankStatus)) {
					return R.fail(ResultCode.SESSION_INVALID.getCode(), "柜台清算期间不允许此操作");
				}
			}
		}

		// 调用CUBP接口取消入金
		CashExtractingMoneyEntity extractingMoney = baseMapper.selectById(vo.getId());
		if (CashConstant.CashExtractingMoneyStatus.CANCEL.getCode() == extractingMoney.getStatus()) {
			return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "抱歉，仅支持取消“待审核”状态下的出金申请。");
		}

		extractingMoney.setStatus(CashConstant.CashExtractingMoneyStatus.CANCEL.getCode());
		String applicationId = extractingMoney.getApplicationId();
		if (StringUtils.isBlank(applicationId)) {
			// return AppUtil.getErrorMsg(-1 , "预约号不存在，请稍后再试");
			extractingMoney.setPushRecved(-1);
			baseMapper.updateById(extractingMoney);
			return R.success();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("applicationId", applicationId);
		String result = HttpClientUtils.postJson(CLIENT_FUND_FROZEN_CANCEL, getRequestJson(map), true);
		JSONObject jsonObject = JSONObject.parseObject(result);
		String code = jsonObject.get("code").toString();
		if (code.equals("0")) {
			baseMapper.updateById(extractingMoney);
		}
		if (code.equals("-1001")) {
			return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "取消失败");
		}
		if (code.equals("-1")) {
			return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "预约号不存在");
		}
		if (code.equals("-1002")) {
			return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "抱歉，仅支持取消“待审核”状态下的出金申请。");
		}

		sendLogMsg(vo, AuthUtil.getCustId(), CommonEnums.CustOperationType.FUND_APPLY_OUT_EDIT.code);
		return R.success();
	}

	@Override
	public R withdrawSuccessBank(Long custId, Integer extMethod) {
		LambdaQueryWrapper<CashExtractingMoneyEntity> wrapper = new LambdaQueryWrapper<>();
		if (extMethod != null) {
			wrapper.eq(CashExtractingMoneyEntity::getExtMethod, extMethod);
		}
		wrapper.eq(CashExtractingMoneyEntity::getCustId, custId).eq(CashExtractingMoneyEntity::getStatus, 3)
			.orderByDesc(CashExtractingMoneyEntity::getModifyTime)
			.last("limit 1");
		CashExtractingMoneyEntity extractingMoney = baseMapper.selectOne(wrapper);

		Map<String, String> map = new HashMap<>(7);
		String bankName = "";
		String bankCode = "";
		String bankAccount = "";
		String swiftCode = "";
		String address = "";
		String bankId = "";
		String bankIds = "";
		if (extractingMoney != null) {
			bankName = extractingMoney.getBankName();
			bankCode = extractingMoney.getBankCode();
			bankAccount = extractingMoney.getBankAccount();
			swiftCode = extractingMoney.getSwiftCode();
			address = extractingMoney.getAddress();
			bankId = extractingMoney.getBankId();
		}
		map.put("bankName", bankName);
		map.put("bankCode", bankCode);
		map.put("bankAccount", bankAccount);
		map.put("swiftCode", swiftCode);
		map.put("address", address);
		map.put("bankId", bankId);
		map.put("bankIds", bankIds);
		return R.data(map);
	}

	@Override
	public R withdrawSuccessBankList(Long custId) {
		List<Map<String, Object>> maps = new ArrayList<>();
		List<CashExtractingMoneyEntity> secExtractingMoney = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashExtractingMoneyEntity::getCustId, custId)
			.eq(CashExtractingMoneyEntity::getStatus, 3)
			.groupBy(CashExtractingMoneyEntity::getBankAccount)
			.orderByDesc(CashExtractingMoneyEntity::getModifyTime)
			.list();
		for (CashExtractingMoneyEntity withdraw : secExtractingMoney) {
			Map<String, Object> map = new HashMap<>(9);
			map.put("receiptBankCode", withdraw.getBankCode());
			map.put("receiptBankName", withdraw.getBankName());
			map.put("bankType", withdraw.getExtMethod());
			map.put("bankAccount", withdraw.getBankAccount());
			map.put("swiftCode", withdraw.getSwiftCode());

			CashWithdrawalsBankEntity secWithdrawalsBank = cashWithdrawalsBankClient.likeReceiptBankName(withdraw.getBankName());

			if (secWithdrawalsBank != null) {
				map.put("appBanklogo", secWithdrawalsBank.getAppIcon());
				map.put("pcBanklogo", secWithdrawalsBank.getPcIcon());
				map.put("timeArrival", secWithdrawalsBank.getTimeArrival());
				map.put("chargeMoney", secWithdrawalsBank.getChargeMoney());
			} else {
				map.put("appBanklogo", null);
				map.put("pcBanklogo", null);
				map.put("timeArrival", "1-2个工作日");
				map.put("chargeMoney", "请咨询银行");
			}
			maps.add(map);
		}
		return R.data(maps);
	}

	@Override
	public List<CashExtractingMoneyEntity> findExtratingMoney(Long custId, HistoryRecordReqVo historyRecord) {
		LambdaQueryWrapper<CashExtractingMoneyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(CashExtractingMoneyEntity::getIsFind, 1);
		if (custId != null) {
			queryWrapper.eq(CashExtractingMoneyEntity::getCustId, custId);
		}
		if (historyRecord.getState() != 99) {
			queryWrapper.eq(CashExtractingMoneyEntity::getStatus, historyRecord.getState());
		}
		if (historyRecord.getCurrency() != null && historyRecord.getCurrency() != 0) {
			queryWrapper.eq(CashExtractingMoneyEntity::getCurrency, historyRecord.getCurrency());
		}
		queryWrapper.orderByDesc(CashExtractingMoneyEntity::getId);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public Map<String, BigDecimal> allExtractingMoney(Long custId) {
		Map<String, BigDecimal> map = new HashMap<>();
		try {
			BigDecimal hk = baseMapper.selectExtractingMoneySumByCurrency(1, custId);
			BigDecimal dollar = baseMapper.selectExtractingMoneySumByCurrency(2, custId);
			map.put("hk", hk);
			map.put("dollar", dollar);
		} catch (Exception e) {
			log.error("查询提取资金异常" + e.getMessage());
		}
		return map;
	}

	@Override
	public R executeClientFundWithdrawApplyJob() {
		List<CashExtractingMoneyEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashExtractingMoneyEntity::getPushRecved, 0)
			.list();
		for (CashExtractingMoneyEntity vo : list) {
			try {
				if (vo.getStatus() == 2) {
					// 办理异常，不用回调
					continue;
				}

				// 判断恒生状态
				String hsResult = HttpClientUtils.get(HS_STATUS, null, Charset.forName("UTF-8"), true);
				if (StringUtils.isNotBlank(hsResult)) {
					ResponseVO responseVO = JSONObject.parseObject(hsResult, ResponseVO.class);
					if (responseVO.getCode() == 0) {
						String sysStauts = JSONObject.parseObject(responseVO.getResult().toString()).getString("sysStatus");
						String bankStatus = JSONObject.parseObject(responseVO.getResult().toString()).getString("bankStatus");
						if ("6".equals(sysStauts) || "0".equals(bankStatus)) {
							log.info("清算时间段内，无法出金");
							continue;
						}
					}
				}

				String clientId = vo.getTradeAccount();
				Long custId = vo.getCustId();
				String fundAccount = vo.getExtAccount();
				String clientNameSpell = vo.getPayee();
				Integer withdrawType = null;
				if (vo.getExtMethod() != null && vo.getExtMethod() == 1) {
					//1大陆银行
					withdrawType = 1;
				} else if (vo.getExtMethod() != null && vo.getExtMethod() == 2) {
					//2香港银行
					withdrawType = 0;
				} else if (vo.getExtMethod() != null && vo.getExtMethod() == 3) {
					//其他银行
					withdrawType = 2;
				}

				String bankName = vo.getBankName();
				String bankNo = vo.getBankAccount();
				String swiftCode = vo.getSwiftCode();
				String contactAddress = vo.getAddress();
				String bankId = vo.getBankId();
				String moneyType = null;
				if (vo.getCurrency() == 1) {
					//2-港币
					moneyType = "2";
				} else if (vo.getCurrency() == 2) {
					//1-美元
					moneyType = "1";
				} else if (vo.getCurrency() == 3) {
					//0-人民币
					moneyType = "0";
				}
				BigDecimal occurBalance = vo.getExtractionAmount();
				BigDecimal frozenBalance = vo.getExtractionAmount();

				ClientFundWithdrawApplyProtocol clientFundWithdrawApplyVo = new ClientFundWithdrawApplyProtocol();
				clientFundWithdrawApplyVo.setClientId(clientId);
				clientFundWithdrawApplyVo.setCustId(custId);
				clientFundWithdrawApplyVo.setFundAccount(fundAccount);
				clientFundWithdrawApplyVo.setClientNameSpell(clientNameSpell);
				clientFundWithdrawApplyVo.setWithdrawType(withdrawType);
				clientFundWithdrawApplyVo.setBankName(bankName);
				clientFundWithdrawApplyVo.setBankNo(bankNo);
				clientFundWithdrawApplyVo.setSwiftCode(swiftCode);
				clientFundWithdrawApplyVo.setContactAddress(contactAddress);
				clientFundWithdrawApplyVo.setMoneyType(moneyType);
				clientFundWithdrawApplyVo.setOccurBalance(occurBalance);
				clientFundWithdrawApplyVo.setFrozenBalance(frozenBalance);
				clientFundWithdrawApplyVo.setBankId(bankId);
				clientFundWithdrawApplyVo.setBankCode(vo.getBankCode());
				BigDecimal chargeMoney = new BigDecimal(0);
				if (vo.getChargeMoney() != null && vo.getChargeMoney().compareTo(chargeMoney) > 0) {
					chargeMoney = vo.getChargeMoney();
				}
				clientFundWithdrawApplyVo.setChargeMoney(chargeMoney);

				// 出金凭证
				List<String> picList = Lists.newArrayList();
				String extractMoneyImgIds = vo.getExtractMoneyImgIds();

				if (extractMoneyImgIds != null && !("".equals(extractMoneyImgIds))) {
					List<String> imgIdList = Arrays.asList(extractMoneyImgIds.split(","));
					for (String s : imgIdList) {
						if (StringUtils.isNotBlank(s)) {
							CashAccessImageEntity secAccImg = cashAccessImageService.getBaseMapper().selectById(Long.valueOf(s));
							if (secAccImg != null) {
								picList.add(secAccImg.getAccImgPath());
							}
						}
					}
				}

				clientFundWithdrawApplyVo.setWithdrawImage(picList);
				clientFundWithdrawApplyVo.setBusType(vo.getBusType());
				clientFundWithdrawApplyVo.setBizId(vo.getId());
				clientFundWithdrawApplyVo.setRevertSerialNo(vo.getRevertSerialNo());
				clientFundWithdrawApplyVo.setInitDate(vo.getInitDate());
				log.info(clientId + " Withdraw apply params：" + JSONObject.toJSONString(clientFundWithdrawApplyVo));
				AddMessageReq message = new AddMessageReq();
				message.setMessage(JSONUtil.toJsonStr(clientFundWithdrawApplyVo));
				boolean sendMsg = withdrawalApplyProducer.sendMsg(message);
				if (sendMsg) {
					CashExtractingMoneyEntity update = new CashExtractingMoneyEntity();
					update.setId(vo.getId());
					update.setPushRecved(1);
					baseMapper.updateById(update);
				}
			} catch (Exception e) {
				log.error("出金申请任务异常", e);
				saveErrorInfo(vo, "出金申请任务异常：" + e, "出金申请任务异常");
			}
		}
		return R.success();
	}

	void sendLogMsg(ExtractMoneyReqVo vo, Long custId, int operationType){
		try {
			CustOperationLogEntity entity = new CustOperationLogEntity();
			entity.setCustId(custId);
			entity.setCapitalAccount(vo.getExtAccount());
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

	private void saveErrorInfo(CashExtractingMoneyEntity secExtractingMoney, String errorMsg, String body) {
		String title = "用户号：" + secExtractingMoney.getCustId() + " 出金申请任务异常";
		errorMsg = errorMsg + "【" + body + "】";
		SendEmailDTO sendEmailDTO = new SendEmailDTO();
		sendEmailDTO.setSender(extractingMoneyJobSenderEmail);
		sendEmailDTO.setAccepts(Arrays.asList(extractingMoneyJobAcceptEmail));
		sendEmailDTO.setTitle(title);
		sendEmailDTO.setContent(errorMsg);
		platformMsgClient.sendEmail(sendEmailDTO);
	}

	private String formatImgIds(String ids) {
		boolean i1 = ids.indexOf(",", ids.length() - 1) == ids.length() - 1;
		if (ids.indexOf(",") == 0 || i1) {
			String[] split = ids.split(",");
			List<String> list = Lists.newArrayList();
			for (String s : split) {
				if (StringUtils.isNotBlank(s)) {
					list.add(s);
				}
			}
			String[] array = list.toArray(new String[list.size()]);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < array.length; i++) {
				if (i < array.length - 1) {
					sb.append(array[i] + ",");
				} else {
					sb.append(array[i]);
				}
			}

			return sb.toString();
		}

		return ids;
	}

	public static String getRequestJson(Map<String, String> requestMap) {
		Map<String, Object> finalMap = new HashMap<>();
		finalMap.put("params", requestMap);
		return JSONUtils.toJSONString(finalMap);
	}
}
