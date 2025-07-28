package com.minigod.zero.bpm.service.impl.openAccount;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.minigod.zero.bpm.mapper.CustCapitalInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.constant.BpmConstants;
import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.BpmFundAcctRespDto;
import com.minigod.zero.bpm.dto.BpmSecuritiesRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.RiskEvaluationReqDto;
import com.minigod.zero.bpm.dto.acct.resp.BpmResponseDto;
import com.minigod.zero.bpm.dto.acct.resp.RiskEvaluationRespDto;
import com.minigod.zero.bpm.dto.acct.resp.RiskQuestionOptRespDto;
import com.minigod.zero.bpm.dto.acct.resp.RiskQuestionRespDto;
import com.minigod.zero.bpm.entity.*;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpm.mapper.BpmAccountInfoMapper;
import com.minigod.zero.bpm.mapper.BpmSecuritiesInfoMapper;
import com.minigod.zero.bpm.service.*;
import com.minigod.zero.bpm.service.api.IBpmOpenApiService;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.service.openAccount.IChangeAccountService;
import com.minigod.zero.bpm.vo.UserHkidrVo;
import com.minigod.zero.bpm.vo.request.YfundRiskTempVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpm.vo.response.SecuritiesUserInfoFullResp;
import com.minigod.zero.bpmn.module.account.dto.OpenAccountDTO;
import com.minigod.zero.bpmn.module.account.vo.FundAccountVO;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.feign.ICustOperationLogClient;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BpmAccountServiceImpl implements IBpmAccountService {

	@Resource
	private IBpmSecuritiesInfoService bpmSecuritiesInfoService;

	@Resource
	private BpmAccountInfoMapper bpmAccountInfoMapper;

	@Resource
	private IBpmFundAcctInfoService bpmFundAcctInfoService;

	@Resource
	private IAcctRiskEvaluationService acctRiskEvaluationService;

	@Resource
	private IAcctRiskQuestionService acctRiskQuestionService;

	@Resource
	private IAcctRiskQuestionOptionService acctRiskQuestionOptionService;

	@Resource
	private BpmSecuritiesInfoMapper bpmSecuritiesInfoMapper;

	@Resource
	private IChangeAccountService changeAccountService;
	@Resource
	private ICustOperationLogClient custOperationLogClient;
	@Resource
	private ICustTradeClient custTradeClient;

	@Resource
	private IBpmOpenApiService bpmOpenApiService;

	@Autowired
	private ICustomerInfoClient customerInfoClient;

	@Autowired
	private ZeroRedis zeroRedis;
	@Resource
	private IDictBizClient dictBizClient;

	@Resource
	private CustCapitalInfoMapper custCapitalInfoMapper;


	@Value("${fund.account.url:'http://10.9.68.161:16006'}")
	private String fundAccUrl;
	@Value("${bpm.api.url:'http://10.9.68.165:6426/bpm'}")
	private String bpmApiUrl;

	@Override
	public R<BpmAccountRespDto> getAccountRespDto(Long custId) {
		BpmAccountRespDto account = zeroRedis.protoGet(CacheNames.CUST_ACCOUNT_INFO.concat(custId.toString()), BpmAccountRespDto.class);
		boolean flag = false;
		if (null == account) {
			flag = true;
		} else {
			if (null == account.getUtime() || DateUtil.between(account.getUtime(), new Date()).getSeconds() >= 500L) {
				// 5分钟过期
				flag = true;
			}
		}
		if (flag) {
			BpmTradeAcctRespDto accountRespDto = getCurrentAcctInfo(custId);
			if (accountRespDto == null) {
				return R.fail("账户状态不正常，请核实！");
			}
			BpmSecuritiesRespDto securitiesRespDto = null;
			// 根据账号类型取数据
			if (accountRespDto.getAcctType().equals(OpenAccountEnum.BpmAcctType.ACCT_TYPE_1.value)) {
				// 个人户
				securitiesRespDto = getSecuritiesRespDto(custId);
			} else if (accountRespDto.getAcctType().equals(OpenAccountEnum.BpmAcctType.ACCT_TYPE_3.value)) {
				if (accountRespDto.getAuthorStatus().equals(CommonEnums.StatusEnum.NO.getCode())) {
					Jwt2Util.removeAccessToken(custId.toString(), AuthUtil.getToken(WebUtil.getRequest()));
					return R.fail(ResultCode.UN_AUTHORIZED, "公司授权人关联已失效！");
				}
				securitiesRespDto = getCompanyRespDto(custId, accountRespDto.getTradeAccount());
			}
			account = new BpmAccountRespDto();
			account.setCust(securitiesRespDto);
			account.setAcct(accountRespDto);
			account.setFund(getFundAcctRespDto(custId));
			account.setUtime(new Date());

			List<BpmCapitalInfoEntity> custCapitalInfos =
				custCapitalInfoMapper.selectList(Wrappers.<BpmCapitalInfoEntity>lambdaQuery().eq(BpmCapitalInfoEntity::getTradeAccount, accountRespDto.getTradeAccount()));
			account.setCapitalList(custCapitalInfos);
			zeroRedis.protoSet(CacheNames.CUST_ACCOUNT_INFO.concat(custId.toString()), account);
		}
		return R.data(account);
	}

	@Override
	public R<BpmAccountRespDto> getAccountRespDto(String tradeAccount, String fundAccount) {

		BpmTradeAcctRespDto accountRespDto = selectAcctInfo(tradeAccount, fundAccount);
		if (accountRespDto == null) {
			return R.fail("账户不存在！");
		}
		BpmSecuritiesRespDto securitiesRespDto = null;
		// 根据账号类型取数据
		if (accountRespDto.getAcctType().equals(OpenAccountEnum.BpmAcctType.ACCT_TYPE_1.value)) {
			// 个人户
			securitiesRespDto = getSecuritiesRespDto(accountRespDto.getCustId());
		}
		BpmAccountRespDto account = new BpmAccountRespDto();
		account.setCust(securitiesRespDto);
		account.setAcct(accountRespDto);
		account.setFund(getFundAcctRespDto(accountRespDto.getCustId()));
		account.setUtime(new Date());
		return R.data(account);
	}

	private BpmSecuritiesRespDto getCompanyRespDto(Long custId, String tradeAccount) {
		BpmSecuritiesRespDto securitiesRespDto = bpmAccountInfoMapper.getCompanyUserRespDto(custId, tradeAccount);
		if (null != securitiesRespDto) {
			// 公司户默认标准账户
			securitiesRespDto.setAccountLevel(3);
			// 公司户bankType = 1
			securitiesRespDto.setBankType(1);
			// 公司户不能APP申请开通中华通
			if (null != securitiesRespDto.getNorthTrade() && 1 == securitiesRespDto.getNorthTrade()) {
				securitiesRespDto.setNorthTrade(0);

				if (StringUtils.isNotBlank(securitiesRespDto.getBcanNo())
					&& StringUtils.isNotBlank(securitiesRespDto.getBcanStatus())
					&& BpmConstants.BcanStatusEnum.TYP_2.value.equals(securitiesRespDto.getBcanStatus()))
					securitiesRespDto.setNorthTrade(1);
			} else {
				securitiesRespDto.setNorthTrade(0);
			}
			securitiesRespDto.setNeedCA(Boolean.FALSE);
		}
		return securitiesRespDto;
	}

	@Override
	public BpmSecuritiesRespDto getSecuritiesRespDto(Long custId) {
		BpmSecuritiesRespDto securitiesRespDto = null;
		LambdaQueryWrapper<BpmSecuritiesInfoEntity> bpmUserWrapper = new LambdaQueryWrapper<>();
		bpmUserWrapper.eq(BpmSecuritiesInfoEntity::getCustId, custId);
		bpmUserWrapper.ne(BpmSecuritiesInfoEntity::getClientStatus, OpenAccountEnum.BpmClientStatus.STS_3.value);
		BpmSecuritiesInfoEntity bpmUserEntity = bpmSecuritiesInfoService.getOne(bpmUserWrapper);
		if (null != bpmUserEntity) {
			securitiesRespDto = new BpmSecuritiesRespDto();
			BeanUtil.copyProperties(bpmUserEntity, securitiesRespDto);
			if (StringUtils.isBlank(bpmUserEntity.getBcanStatus())) {
				//申请中华通状态同步太慢，通过接口获取状态
				SecuritiesUserInfoFullResp securities = changeAccountService.getSecuritiesFromBpm(custId);
				if (null != securities) {
					if (1 == securities.getNorthTrade() && !BpmConstants.CountryCodeEnum.CHN.value.equals(securities.getIssueCountry())) {
						securitiesRespDto.setBcanStatus(BpmConstants.BcanStatusEnum.TYP_1.value);
					} else {
						securitiesRespDto.setBcanStatus(BpmConstants.BcanStatusEnum.TYP_0.value);
					}
				} else {
					if (1 == bpmUserEntity.getNorthTrade() && !BpmConstants.CountryCodeEnum.CHN.value.equals(bpmUserEntity.getIssueCountry())) {
						securitiesRespDto.setBcanStatus(BpmConstants.BcanStatusEnum.TYP_1.value);
					} else {
						securitiesRespDto.setBcanStatus(BpmConstants.BcanStatusEnum.TYP_0.value);
					}
				}
			}
			if (null != bpmUserEntity.getOpenAccountTime() && null != bpmUserEntity.getAccountLevel() && null != bpmUserEntity.getIdKind()) {
				//判断是否需要补充CA认证
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				if (Integer.valueOf(sdf.format(bpmUserEntity.getOpenAccountTime())) < 20190101
					&& "1".equals(bpmUserEntity.getAccountLevel().toString())
					&& "1".equals(bpmUserEntity.getIdKind())) {
					securitiesRespDto.setNeedCA(true);
				}
			}
		}
		return securitiesRespDto;
	}

	@Override
	public BpmTradeAcctRespDto getCurrentAcctInfo(Long custId) {
		return bpmAccountInfoMapper.selectCurrentAcctInfo(custId);
	}

	@Override
	public BpmTradeAcctRespDto selectAcctInfo(String tradeAccount, String fundAccount) {
		return bpmAccountInfoMapper.selectAcctInfo(tradeAccount, fundAccount);
	}

	@Override
	public BpmFundAcctRespDto getFundAcctRespDto(Long custId) {
		BpmFundAcctRespDto fundAcctRespDto = null;
		BpmTradeAcctRespDto tradeAcct = this.getCurrentAcctInfo(custId);
		if (tradeAcct == null) {
			return fundAcctRespDto;
		}
		LambdaQueryWrapper<BpmFundAcctInfoEntity> bpmFundWrapper = new LambdaQueryWrapper<>();
		bpmFundWrapper.eq(BpmFundAcctInfoEntity::getTradeAccount, tradeAcct.getTradeAccount());
		bpmFundWrapper.eq(BpmFundAcctInfoEntity::getAccountStatus, 0);
		bpmFundWrapper.last("limit 1");
		BpmFundAcctInfoEntity bpmFundEntity = bpmFundAcctInfoService.getOne(bpmFundWrapper);
		if (null != bpmFundEntity) {
			fundAcctRespDto = new BpmFundAcctRespDto();
			BeanUtil.copyProperties(bpmFundEntity, fundAcctRespDto);
			Date nowDate = DateUtil.getTodayDate(0, 0, 0);
			LambdaQueryWrapper<AcctRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
			riskEvaluationWrapper.eq(AcctRiskEvaluationEntity::getCustId, custId);
			riskEvaluationWrapper.orderByDesc(AcctRiskEvaluationEntity::getCreateTime);
			riskEvaluationWrapper.last("limit 1");
			AcctRiskEvaluationEntity riskEvaluationEntity = acctRiskEvaluationService.getOne(riskEvaluationWrapper);
			if (null != riskEvaluationEntity) {
				fundAcctRespDto.setNowDate(nowDate);
				fundAcctRespDto.setEvaluationDate(riskEvaluationEntity.getCreateTime());
				fundAcctRespDto.setRiskType(riskEvaluationEntity.getRiskType());
				fundAcctRespDto.setExpiryDate(riskEvaluationEntity.getExpiryDate());
				fundAcctRespDto.setRetestSts(riskEvaluationEntity.getRetestSts());
			}
		}
		return fundAcctRespDto;
	}

	@Override
	public R<List<RiskQuestionRespDto>> riskQuestion(RiskEvaluationReqDto reqDto) {

		//WebUtil.getHeader("")
		if (null == reqDto.getQuestionType()) reqDto.setQuestionType(BpmConstants.QuestionTypeEnum.TYP_1.value);

		String lang = WebUtil.getHeader(CommonConstant.LANG);
		if (StringUtils.isBlank(lang)) lang = CommonConstant.ZH_CN;

		String key = "yfund_" + reqDto.getQuestionType() + "_" + lang;

		List<RiskQuestionRespDto> questions = null;
		String questionCache = zeroRedis.protoGet(key, String.class);
		if (StringUtils.isNotBlank(questionCache)) {
			JSONArray array = JSONUtil.parseArray(questionCache);
			questions = JSONUtil.toList(array, RiskQuestionRespDto.class);
		}
		if (null != questions && questions.size() > 0) {
			return R.data(questions);
		}

		LambdaQueryWrapper<AcctRiskQuestionEntity> riskQuestionWrapper = new LambdaQueryWrapper<>();
		riskQuestionWrapper.eq(AcctRiskQuestionEntity::getQuestionType, reqDto.getQuestionType());
		riskQuestionWrapper.eq(AcctRiskQuestionEntity::getLang, lang);
		riskQuestionWrapper.eq(AcctRiskQuestionEntity::getFlag, 0);
		riskQuestionWrapper.orderByAsc(AcctRiskQuestionEntity::getQuestionId);
		List<AcctRiskQuestionEntity> questionsEntityList = acctRiskQuestionService.list(riskQuestionWrapper);

		if (CollectionUtil.isNotEmpty(questionsEntityList)) {
			questions = BeanUtil.copyToList(questionsEntityList, RiskQuestionRespDto.class);
			for (RiskQuestionRespDto question : questions) {

				LambdaQueryWrapper<AcctRiskQuestionOptionEntity> ristOptionWrapper = new LambdaQueryWrapper<>();
				ristOptionWrapper.eq(AcctRiskQuestionOptionEntity::getQuestionId, question.getQuestionId());
				ristOptionWrapper.eq(AcctRiskQuestionOptionEntity::getLang, lang);
				ristOptionWrapper.eq(AcctRiskQuestionOptionEntity::getFlag, 0);
				ristOptionWrapper.orderByAsc(AcctRiskQuestionOptionEntity::getOptionId);
				List<AcctRiskQuestionOptionEntity> optionEntityList = acctRiskQuestionOptionService.list(ristOptionWrapper);

				if (CollectionUtil.isNotEmpty(optionEntityList)) {
					List<RiskQuestionOptRespDto> options = BeanUtil.copyToList(optionEntityList, RiskQuestionOptRespDto.class);
					question.setOptions(options);
				}
			}
		}

		if (CollectionUtil.isNotEmpty(questions)) {
			zeroRedis.protoSet(key, JSONUtil.toJsonStr(questions));
		}
		return R.data(questions);
	}

	@Override
	public R saveEvaluationTemp(RiskEvaluationReqDto reqDto) {
		if (null == reqDto || StringUtils.isBlank(reqDto.getTempData()))
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);

		Long custId = AuthUtil.getTenantCustId();

		YfundRiskTempVo yfundRiskTemp = new YfundRiskTempVo();
		yfundRiskTemp.setCustId(custId);
		yfundRiskTemp.setTempData(reqDto.getTempData());
		yfundRiskTemp.setTime(new Date());
		zeroRedis.protoSet(custId.toString().trim(), yfundRiskTemp);
		return R.success();
	}

	@Override
	public R<YfundRiskTempVo> evaluationTemp() {
		Long custId = AuthUtil.getTenantCustId();
		YfundRiskTempVo temp = zeroRedis.protoGet(custId.toString().trim(), YfundRiskTempVo.class);
		return R.data(temp);
	}

	@Override
	public R saveRiskEvaluation(RiskEvaluationReqDto reqDto) {
		if (null == reqDto || null == reqDto.getQuestionType() || null == reqDto.getRiskScore()
			|| null == reqDto.getRiskType()) return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		Long custId = AuthUtil.getTenantCustId();
		AcctRiskEvaluationEntity entity = new AcctRiskEvaluationEntity();
		entity.setRiskScore(reqDto.getRiskScore());
		entity.setRiskType(reqDto.getRiskType());
		entity.setCustId(custId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 365);
		entity.setExpiryDate(calendar.getTime());
		entity.setIsPush(0);
		entity.setOptionData(reqDto.getTempData());
		entity.setRetestSts(0);
		entity.setCreateTime(new Date());
		boolean flag = acctRiskEvaluationService.save(entity);
		FundAccountVO fundAccountVO = updateYfundInfo(entity, String.valueOf(custId));
		//中华通申请成功后清除accout redis
		if (flag) zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custId.toString()));
		if (flag) {
			// 用户操作记录
			try {
				R result = custTradeClient.custCurrentAccount(AuthUtil.getTenantCustId());
				if (!result.isSuccess()) {
					log.error("获取交易账号失败，custId：{}", AuthUtil.getTenantCustId());
				}
				CustAccountVO custAccount = (CustAccountVO) result.getData();
				CustOperationLogEntity eustOperationLog = new CustOperationLogEntity();
				eustOperationLog.setCapitalAccount(custAccount.getCapitalAccount());
				eustOperationLog.setTradeAccount(custAccount.getTradeAccount());
				eustOperationLog.setReqParams(JSON.toJSONString(reqDto));
				eustOperationLog.setCustId(AuthUtil.getTenantCustId());
				eustOperationLog.setIp(WebUtil.getIP());
				eustOperationLog.setDeviceCode(WebUtil.getHeader(TokenConstant.DEVICE_CODE));
				eustOperationLog.setReqTime(new Date());
				eustOperationLog.setOperationType(CommonEnums.CustOperationType.RISK_RESULT_SUBMIT.code);
				custOperationLogClient.operationLog(eustOperationLog);
			} catch (Exception e) {
				log.error("记录用户操作日志异常", e);
			}

			try {
				List<String> params = new ArrayList<>();
				R<List<DictBiz>> dictRiskType = dictBizClient.getList("risk_type");
				if (dictRiskType.isSuccess()) {
					List<DictBiz> dictBizData = dictRiskType.getData();
					if (dictBizData != null) {
						dictBizData.stream().forEach(d -> {
							if (d.getDictKey() != null && entity.getRiskType() != null && d.getDictKey().trim().toString().equals(entity.getRiskType().toString())) {
								log.info("风险类型：" + d.getDictValue());
								params.add(d.getDictValue());

							}
						});
					}
				}

				PushUtil.builder()
					.msgGroup("P")
					.custId(custId)
					.params(params)
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.templateCode(PushTemplate.RISK_ASSESSMENT.getCode())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();
			} catch (Exception e) {
				log.error("推送风险评估结果失败", e);
			}

		}

		return flag ? R.data(fundAccountVO) : R.fail();

	}

	/**
	 * 获取当前上下文用户风险测评结果
	 *
	 * @return
	 */
	@Override
	public R<RiskEvaluationRespDto> getRiskEvaluationInfo() {
		Long custId = AuthUtil.getTenantCustId();
		R<AcctRiskEvaluationEntity> result = acctRiskEvaluationService.getRiskEvaluationInfo(custId);
		List<AcctRiskEvaluationEntity> lastThreeMonthRecords = acctRiskEvaluationService.getLastThreeMonthRecords(custId);
		RiskEvaluationRespDto respDto = new RiskEvaluationRespDto();
		if (result.isSuccess()) {
			if (result.getData() != null) {
				BeanUtils.copyProperties(result.getData(), respDto);
				//三个月内有2条记录，不可重测
				if (lastThreeMonthRecords != null && lastThreeMonthRecords.size() >= 2) {
					respDto.setCanRetest(false);
				} else {
					respDto.setCanRetest(true);
				}
				//如果最新的记录已过期，可重测
				if (result.getData().getExpiryDate() != null && result.getData().getExpiryDate().before(new Date())) {
					respDto.setIsExpire(1);
					respDto.setCanRetest(true);
				} else {
					respDto.setIsExpire(0);
				}
				return R.data(respDto);
			} else {
				return R.success("风险测评结果为空!");
			}
		} else {
			return R.fail("风险测评结果查询失败!");
		}
	}

	@Override
	public R<BpmResponseDto> caCertification(CaCertificationDto reqDto) {
		Long custId = AuthUtil.getTenantCustId();
		BpmResponseDto res = null;
		try {
			if (StringUtils.isBlank(reqDto.getBankNo()) || StringUtils.isBlank(reqDto.getBankPhoneNumber()))
				return R.fail(ResultCode.PARAM_VALID_ERROR);
			reqDto.setUserId(custId.intValue());
			String server = bpmApiUrl + "/proxy/secUserInfoChange/doCaCertification";
			log.info("调用BPM-URL：" + server);
			HashMap<String, Object> hashMap = Maps.newHashMap();
			hashMap.put("params", reqDto);

			log.info("****************************补充CA认证调用BPM开始******************************");
			log.info(custId + "调用BPM传入补充CA认证参数：" + JSONObject.toJSONString(reqDto));
			String bpmResult = HttpUtil.post(server, JSONObject.toJSONString(hashMap), 20000);
			log.info("补充CA认证，返回结果：" + bpmResult);
			log.info("****************************补充CA认证调用BPM结束******************************");
			if (StringUtils.isNotBlank(bpmResult)) {
				res = JSONObject.parseObject(bpmResult, BpmResponseDto.class);
			} else {
				return R.fail("补充CA认证失败");
			}
		} catch (Exception e) {
			log.error("连接BPM服务器异常", e);
			return R.fail("补充CA认证异常");
		}
		return R.data(res);
	}

	@Override
	public R<BpmResponseDto> caCertificationStatus(CaCertificationDto reqDto) {
		Long custId = AuthUtil.getTenantCustId();
		if (null == reqDto) reqDto = new CaCertificationDto();
		reqDto.setUserId(custId.intValue());
		BpmResponseDto res = null;
		try {
			// 请求接口
			String bpmResult = null;
			String server = bpmApiUrl + "/proxy/secUserInfoChange/caCertificationStatus";
			log.info("调用BPM-URL：" + server);
			HashMap<String, Object> hashMap = Maps.newHashMap();
			hashMap.put("params", reqDto);

			log.info("****************************补充CA认证调用BPM开始******************************");
			log.info(custId + "调用BPM传入补充CA认证参数：" + JSONObject.toJSONString(bpmResult));
			bpmResult = HttpUtil.post(server, JSONObject.toJSONString(hashMap), 20000);
			log.info("补充CA认证，返回结果：" + bpmResult);
			log.info("****************************补充CA认证调用BPM结束******************************");
			if (StringUtils.isNotBlank(bpmResult)) {
				res = JSONObject.parseObject(bpmResult, BpmResponseDto.class);
			} else {
				log.error("连接BPM服务器异常,返回结果为null");
				return R.fail();

			}
		} catch (Exception e) {
			log.error("连接BPM服务器异常", e);
			return R.fail();
		}
		return R.data(res);
	}

	@Override
	public R bpmUpdateInfo(BpmSecuritiesRespDto dto) {
		BpmTradeAcctRespDto custAcctInfo = getCurrentAcctInfo(dto.getCustId());
		// 交易账号验证
		if (custAcctInfo == null) {
			log.warn("客户未绑定交易账号：" + dto.getCustId());
			return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "该客户未绑定交易账号");
		}
		Map<String, String> map = new HashMap<>();
		map.put("tradeAccount", custAcctInfo.getTradeAccount());
		if (StringUtils.isNotBlank(dto.getPhoneNumber())) map.put("phoneNumber", dto.getPhoneNumber());
		if (StringUtils.isNotBlank(dto.getEmail())) map.put("email", dto.getEmail());

		Map<String, Object> params = new HashMap<>();
		params.put("params", map);
		log.info("修改开户资料传参：" + JSONObject.toJSONString(params));
		String url = bpmApiUrl + "/crm_api/updateSecuritiesUserInfo";
		String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(params), Charset.forName("UTF-8"), true);
		log.info("修改开户资料号回参：" + result);
		ResponseVO responseVO = JSONObject.parseObject(result, ResponseVO.class);
		if (responseVO.getCode() == 0) {
			return R.success("修改开户资料成功");
		}
		return R.fail("修改开户资料失败");
	}

	/**
	 * 账户风险测评完成后调用大账户中心开户
	 *
	 * @param evaluation
	 * @param yfFundAccountMain
	 * @return
	 */
	private FundAccountVO updateYfundInfo(AcctRiskEvaluationEntity evaluation, String yfFundAccountMain) {
		try {
			if (StringUtils.isBlank(yfFundAccountMain) || null == evaluation) {
				return null;
			}
			R<CustomerAccountVO> result = customerInfoClient.customerAccountInfo(evaluation.getCustId());
			if (!result.isSuccess()) {
				log.error("风险测评,账号{}查询账号信息失败：{}", evaluation.getCustId(), result.getMsg());
			}
			CustomerAccountVO.TradeAccount tradeAccount = result.getData().getAcct();
			OpenAccountDTO openAccount = new OpenAccountDTO();
			openAccount.setAccountId(tradeAccount.getTradeAccount());
			openAccount.setCustId(evaluation.getCustId());
			openAccount.setStatus("active");
			openAccount.setExpiryDate(evaluation.getExpiryDate());
			openAccount.setRiskLevel(evaluation.getRiskType());
			log.info("风险测评,用户{}提交账户中心基金开户参数：{}", evaluation.getCustId(), openAccount);
			R<FundAccountVO> r = customerInfoClient.fundOpenAccount(openAccount);
			log.info("风险测评,用户{}提交账户中心基金开户返回参数：{}", evaluation.getCustId(), r);
			if (r.getCode() == ResultCode.SUCCESS.getCode()) {
				// 如果已经做过风险测评开过户，重新再提交风险测评不会再返回子账户信息。
				if (r.getData().getSubAccounts().size() < 1) {
					log.warn("风险测评,用户基金开户成功,但未获取到子账户信息, CustId=" + evaluation.getCustId() + ",如果已经做过风险测评开过户，重新再提交风险测评不会再返回子账户信息!");
				} else if (tradeAccount != null) {
					BpmFundAcctInfoEntity entity = new BpmFundAcctInfoEntity();
					entity.setTradeAccount(tradeAccount.getTradeAccount());
					entity.setFundAccountMain(r.getData().getAccountId());
					entity.setFundAccount(r.getData().getSubAccounts().get(0).getSubAccountId());
					entity.setFundAccountType(0);
					entity.setFundOperType(1);
					entity.setAccountStatus(0);
					entity.setCreateTime(new Date());
					entity.setUpdateTime(new Date());
					bpmFundAcctInfoService.save(entity);
				}
				log.info("风险测评,用户{}基金开户成功:", evaluation.getCustId());
			} else {
				log.error("风险测评,用户{}基金开户失败:", evaluation.getCustId());
			}

			return r.getData();
		} catch (Exception e) {
			log.error("风险测评,账户风险测评完成后调用大账户中心开户失败,原因:{} ", e.getMessage());
			return null;
		}
	}

	// 是否需要授权HKIDR
	@Override
	public R needGrantHkidr(Long custId) {
		boolean needGrantHkidr = false;
		boolean needFini = false;

		try {
			BpmSecuritiesInfoEntity securitiesUserInfoResp = bpmSecuritiesInfoMapper.selectOne(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery()
				.eq(BpmSecuritiesInfoEntity::getCustId, custId)
				.eq(BpmSecuritiesInfoEntity::getIsFundUnilateralAccount, 0).last(" limit 1"));
			if (securitiesUserInfoResp != null) {
				UserHkidrVo search = new UserHkidrVo();
				search.setUserId(custId.intValue());
				ResponseVO resp = bpmOpenApiService.needGrantHkidr(search);

				if (null != resp && resp.getCode() == 0) {
					needGrantHkidr = true;
					needFini = true;
					if (null != resp.getResult()) {
						UserHkidrVo hkidrResp = JSONObject.parseObject(resp.getResult().toString(), UserHkidrVo.class);
						if (null != hkidrResp) {
							if (null != hkidrResp.getHkidrStatus() && 1 == hkidrResp.getHkidrStatus())
								needGrantHkidr = false;
							if (null != hkidrResp.getFiniStatus() && 1 == hkidrResp.getFiniStatus()) needFini = false;
						}

					}
				}

			}

		} catch (Exception e) {
			log.error("needGrantHkidr error", e);
		}
		return R.data(Kv.create().set("needGrantHkidr", needGrantHkidr).set("needFini", needFini));
	}

	@Override
	public R grantHkidrEsop(UserHkidrVo vo) {
		if (null == vo || null == vo.getUserId()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		try {
			ResponseVO resp = bpmOpenApiService.needGrantHkidr(vo);
			if (null != resp && resp.getCode() == 0) {
				if (null != resp.getResult()) {
					UserHkidrVo hkidrResp = JSONObject.parseObject(resp.getResult().toString(), UserHkidrVo.class);
					if (null != hkidrResp && null != hkidrResp.getFiniStatus() && 1 == hkidrResp.getFiniStatus() && hkidrResp.getHkidrStatus() != null && hkidrResp.getHkidrStatus() == 1) {
						return R.success("已授权");
					}
				}
			}

			resp = bpmOpenApiService.grantHkidr(vo);
			if (null != resp && resp.getCode() == 0) {
				return R.success("授权成功");
			} else {
				return R.fail("授权失败");
			}

		} catch (Exception e) {
			log.error("grantHkidrEsop fail", e);
			return R.fail("授权失败,请稍后再试");
		}
	}

	// HKIDR授权
	@Override
	public R grantHkidr(UserHkidrVo vo) {
		if (null == vo || null == vo.getUserId()) return R.fail(ResultCode.PARAM_VALID_ERROR);

		try {
			ResponseVO resp = bpmOpenApiService.needGrantHkidr(vo);
			if (null != resp && resp.getCode() == 0) {
				if (null != resp.getResult()) {
					UserHkidrVo hkidrResp = JSONObject.parseObject(resp.getResult().toString(), UserHkidrVo.class);
					if (null != hkidrResp && null != hkidrResp.getFiniStatus() && 1 == hkidrResp.getFiniStatus() && hkidrResp.getHkidrStatus() != null && hkidrResp.getHkidrStatus() == 1) {
						return R.success("已授权");
					}
				}
			}

			if (null == vo.getHkidrStatusApproach()) {
				vo.setHkidrStatusApproach(1);
			}

			BpmSecuritiesInfoEntity securitiesUserInfoResp = bpmSecuritiesInfoMapper.selectOne(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery()
				.eq(BpmSecuritiesInfoEntity::getCustId, AuthUtil.getTenantCustId()).eq(BpmSecuritiesInfoEntity::getIsFundUnilateralAccount, 0));
			if (securitiesUserInfoResp != null) {
				resp = bpmOpenApiService.grantHkidr(vo);
				if (null != resp && resp.getCode() == 0) {
					return R.success("授权成功");
				} else {
					return R.fail("授权失败");
				}
			}
		} catch (Exception e) {
			log.error("grantHkidr fail", e);
			return R.fail("授权失败,请稍后再试");
		}

		return R.fail("授权失败");
	}

	@Override
	public R applyOpenZht() {
		Long custId = AuthUtil.getTenantCustId();
		try {

			SecuritiesUserInfoFullResp securities = changeAccountService.getSecuritiesFromBpm(custId);
			if (null != securities) {
				if (StringUtils.isNotBlank(securities.getIssueCountry())
					&& BpmConstants.CountryCodeEnum.CHN.value.equals(securities.getIssueCountry().toUpperCase()))
					return R.fail("申请失败,请联系客服");
				if (null != securities.getNorthTrade() && 1 == securities.getNorthTrade()) return R.fail("已提交申请");
				BpmTradeAcctRespDto accountRespDto = getCurrentAcctInfo(custId);
				if (null != accountRespDto) {
					Map<String, Object> openZhtParam = new HashMap();
					openZhtParam.put("tradeAccount", accountRespDto.getTradeAccount());
					openZhtParam.put("userId", custId);
					openZhtParam.put("northTrade", 1);
					Map<String, Object> params = new HashMap<>();
					params.put("params", openZhtParam);
					log.info("申请中华通开户传参：" + JSONObject.toJSONString(params));
					String url = bpmApiUrl + "/crm_api/updateUserInfoByBcan";
					String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(params), Charset.forName("UTF-8"), true);
					log.info("申请中华通开户回参：" + result);
					ResponseVO responseVO = JSONObject.parseObject(result, ResponseVO.class);
					if (responseVO.getCode() == 0) {
						//中华通申请成功后清除accout redis
						zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custId.toString()));
						return R.success();
					} else {
						return R.fail();
					}
				}
			}
		} catch (Exception e) {
			log.error("applyOpenZht error:", e);
			return R.fail();
		}
		return R.fail(ResultCode.NONE_DATA);
	}

	@Override
	public R<BpmTradeAcctRespDto> bpmTradeAcctInfo(Long custId) {
		return R.data(bpmAccountInfoMapper.selectCurrentAcctInfo(custId));
	}

}
