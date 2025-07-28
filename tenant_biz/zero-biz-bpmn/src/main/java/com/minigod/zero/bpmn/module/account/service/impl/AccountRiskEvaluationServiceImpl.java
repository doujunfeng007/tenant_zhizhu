package com.minigod.zero.bpmn.module.account.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskEvaluationDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskEvaluationSubmitDTO;
import com.minigod.zero.bpmn.module.account.dto.OpenAccountDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskEvaluationEntity;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireRatingEntity;
import com.minigod.zero.bpmn.module.account.entity.BpmnFundAcctInfoEntity;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.OrganizationOpenApproveEnum;
import com.minigod.zero.bpmn.module.account.mapper.AccountRiskEvaluationMapper;
import com.minigod.zero.bpmn.module.account.mapper.BpmFundAcctInfoMapper;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.constant.RiskMessageConstant;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.feign.ICustOperationLogClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 风险测评结果服务接口
 *
 * @author eric
 * @since 2024-08-20 14:19:08
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountRiskEvaluationServiceImpl extends BaseServiceImpl<AccountRiskEvaluationMapper, AccountRiskEvaluationEntity>
	implements IAccountRiskEvaluationService {
	private final ICustOperationLogClient custOperationLogClient;
	private final ICustomerInfoClient customerInfoClient;
	private final ICustTradeClient custTradeClient;
	private final ZeroRedis zeroRedis;
	private final BpmFundAcctInfoMapper bpmFundAcctInfoMapper;
	private final IAccountRiskQuestionnaireService accountRiskQuestionnaireService;
	private final IAccountRiskQuestionnaireRatingService accountRiskQuestionnaireRatingService;
	private final IAccountOpenApplicationService iAccountOpenApplicationService;
	private final IAccountOpenInfoService iAccountOpenInfoService;
	private final IOrganizationOpenAccountOnlineService iOrganizationOpenAccountOnlineService;

	/**
	 * 风险评测记录分页列表
	 *
	 * @param page
	 * @param acctRiskEvaluation
	 * @return
	 */
	@Override
	public IPage<AccountRiskEvaluationVO> selectAcctRiskEvaluationPage(IPage<AccountRiskEvaluationVO> page, AccountRiskEvaluationDTO acctRiskEvaluation) {
		List<AccountRiskEvaluationVO> acctRiskEvaluationVOs = new ArrayList<>();
		List<AccountRiskEvaluationEntity> acctRiskEvaluationEntities = this.baseMapper.selectAcctRiskEvaluationPage(page, acctRiskEvaluation);
		List<AccountRiskQuestionnaireRatingEntity> ratingEntities = accountRiskQuestionnaireRatingService.list();
		for (AccountRiskEvaluationEntity acctRiskEvaluationEntity : acctRiskEvaluationEntities) {
			AccountRiskEvaluationVO acctRiskEvaluationVO = new AccountRiskEvaluationVO();
			if (ratingEntities != null && ratingEntities.size() > 0) {
				List<AccountRiskQuestionnaireRatingEntity> currentRatingEntities = ratingEntities.stream().filter(ratingEntity -> ratingEntity.getQuestionnaireId().equals(acctRiskEvaluationEntity.getQuestionnaireId())).collect(Collectors.toList());
				if (currentRatingEntities != null) {
					for (AccountRiskQuestionnaireRatingEntity ratingEntity : currentRatingEntities) {
						if (acctRiskEvaluationEntity.getRiskType().equals(ratingEntity.getRating())) {
							acctRiskEvaluationVO.setRiskTypeName(ratingEntity.getRatingName());
							break;
						}
					}
				}
			}
			acctRiskEvaluationVO.setOptionData(acctRiskEvaluationEntity.getOptionData());
			acctRiskEvaluationVO.setIsPush(acctRiskEvaluationEntity.getIsPush());
			acctRiskEvaluationVO.setRetestSts(acctRiskEvaluationEntity.getRetestSts());
			acctRiskEvaluationVO.setRiskType(acctRiskEvaluationEntity.getRiskType());
			acctRiskEvaluationVO.setSubjectiveScore(acctRiskEvaluationEntity.getSubjectiveScore());
			acctRiskEvaluationVO.setCreateTime(acctRiskEvaluationEntity.getCreateTime());
			acctRiskEvaluationVO.setObjectiveScore(acctRiskEvaluationEntity.getObjectiveScore());
			acctRiskEvaluationVO.setRiskScore(acctRiskEvaluationEntity.getRiskScore());
			acctRiskEvaluationVO.setCustId(acctRiskEvaluationEntity.getCustId());
			acctRiskEvaluationVO.setQuestionnaireId(acctRiskEvaluationEntity.getQuestionnaireId());
			acctRiskEvaluationVO.setExpiryDate(acctRiskEvaluationEntity.getExpiryDate());
			acctRiskEvaluationVO.setId(acctRiskEvaluationEntity.getId());

			if (acctRiskEvaluationEntity.getExpiryDate() != null && acctRiskEvaluationEntity.getExpiryDate().before(new Date())) {
				acctRiskEvaluationVO.setIsExpire(1);
			} else {
				acctRiskEvaluationVO.setIsExpire(0);
			}

			AccountRiskQuestionnaireVO accountRiskQuestionnaireVO = accountRiskQuestionnaireService.getAcctRiskQuestionnaireById(acctRiskEvaluationVO.getQuestionnaireId());
			if (null != accountRiskQuestionnaireVO) {
				acctRiskEvaluationVO.setQuestionnaireName(accountRiskQuestionnaireVO.getQuestionnaireName());
				acctRiskEvaluationVO.setQuestionnaireType(accountRiskQuestionnaireVO.getQuestionnaireType());
				acctRiskEvaluationVO.setLang(accountRiskQuestionnaireVO.getLang());
			}

			acctRiskEvaluationVOs.add(acctRiskEvaluationVO);
		}
		return page.setRecords(acctRiskEvaluationVOs);
	}

	/**
	 * 获取风险评测记录详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AccountRiskEvaluationVO getRiskEvaluationInfoById(Long id) {
		AccountRiskEvaluationVO riskEvaluationVO = new AccountRiskEvaluationVO();
		AccountRiskEvaluationEntity riskEvaluation = this.baseMapper.selectById(id);
		if (null != riskEvaluation) {
			List<AccountRiskQuestionnaireRatingVO> ratingVOS = accountRiskQuestionnaireRatingService.getByQuestionnaireIds(riskEvaluation.getQuestionnaireId());
			if (ratingVOS != null) {
				for (AccountRiskQuestionnaireRatingVO ratingVO : ratingVOS) {
					if (riskEvaluation.getRiskType().equals(ratingVO.getRating())) {
						riskEvaluationVO.setRiskTypeName(ratingVO.getRatingName());
						break;
					}
				}
			}
			riskEvaluationVO.setOptionData(riskEvaluation.getOptionData());
			riskEvaluationVO.setIsPush(riskEvaluation.getIsPush());
			riskEvaluationVO.setRetestSts(riskEvaluation.getRetestSts());
			riskEvaluationVO.setRiskType(riskEvaluation.getRiskType());
			riskEvaluationVO.setSubjectiveScore(riskEvaluation.getSubjectiveScore());
			riskEvaluationVO.setCreateTime(riskEvaluation.getCreateTime());
			riskEvaluationVO.setObjectiveScore(riskEvaluation.getObjectiveScore());
			riskEvaluationVO.setRiskScore(riskEvaluation.getRiskScore());
			riskEvaluationVO.setCustId(riskEvaluation.getCustId());
			riskEvaluationVO.setQuestionnaireId(riskEvaluation.getQuestionnaireId());
			riskEvaluationVO.setExpiryDate(riskEvaluation.getExpiryDate());
			riskEvaluationVO.setId(riskEvaluation.getId());
			if (riskEvaluation.getExpiryDate() != null && riskEvaluation.getExpiryDate().before(new Date())) {
				riskEvaluationVO.setIsExpire(1);
			} else {
				riskEvaluationVO.setIsExpire(0);
			}
		}

		AccountRiskQuestionnaireVO accountRiskQuestionnaireVO = accountRiskQuestionnaireService.getAcctRiskQuestionnaireById(riskEvaluationVO.getQuestionnaireId());
		if (null != accountRiskQuestionnaireVO) {
			riskEvaluationVO.setQuestionnaireName(accountRiskQuestionnaireVO.getQuestionnaireName());
			riskEvaluationVO.setQuestionnaireType(accountRiskQuestionnaireVO.getQuestionnaireType());
			riskEvaluationVO.setLang(accountRiskQuestionnaireVO.getLang());
		}
		return riskEvaluationVO;
	}

	/**
	 * 暂存风险测评结果
	 *
	 * @param accRiskEvaluationSubmit
	 * @return
	 */
	@Override
	public R saveEvaluationTemp(AccountRiskEvaluationSubmitDTO accRiskEvaluationSubmit) {
		if (null == accRiskEvaluationSubmit || StringUtils.isBlank(accRiskEvaluationSubmit.getTempData()))
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		Long custId = AuthUtil.getTenantCustId();
		Long questionnaireId = accRiskEvaluationSubmit.getQuestionnaireId();
		AccountRiskEvaluationTempVO riskEvaluationTempVO = new AccountRiskEvaluationTempVO();
		riskEvaluationTempVO.setCustId(custId);
		riskEvaluationTempVO.setQuestionnaireId(questionnaireId);
		riskEvaluationTempVO.setTempData(accRiskEvaluationSubmit.getTempData());
		riskEvaluationTempVO.setTime(new Date());
		zeroRedis.protoSet(custId.toString().trim(), riskEvaluationTempVO);
		return R.success();
	}

	/**
	 * 获取当前用户风险测评暂存记录
	 *
	 * @return
	 */
	@Override
	public R<AccountRiskEvaluationTempVO> evaluationTemp() {
		Long custId = AuthUtil.getTenantCustId();
		AccountRiskEvaluationTempVO temp = zeroRedis.protoGet(custId.toString().trim(), AccountRiskEvaluationTempVO.class);
		return R.data(temp);
	}

	/**
	 * 提交风险评测结果
	 *
	 * @param accRiskEvaluationSubmit
	 * @return
	 */
	@Override
	public R saveRiskEvaluation(AccountRiskEvaluationSubmitDTO accRiskEvaluationSubmit) {
		if (null == accRiskEvaluationSubmit || null == accRiskEvaluationSubmit.getRiskScore() || null == accRiskEvaluationSubmit.getRiskType())
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);

		//查询个人开户状态
		AccountOpenInfoVO accountOpenInfoVO = iAccountOpenInfoService.queryByUserId(AuthUtil.getUserId());
		//查询机构开户状态
		OrganizationOpenInfoEntity organizationOpenInfoEntity = iOrganizationOpenAccountOnlineService.queryOrganizationOpenAccountByCustId(AuthUtil.getUserId());
		//如果个人开户和机构开户都查不到该用户的资料说明还没有提交开户申请
		if (accountOpenInfoVO == null && organizationOpenInfoEntity == null) {
			log.warn("客户号:【" + AuthUtil.getTenantCustId() + "】开户资料不存在!");
			return R.fail(String.format(I18nUtil.getMessage(RiskMessageConstant.RISK_CUSTOMER_OPEN_ACCOUNT_DATA_NOT_EXIST_NOTICE), AuthUtil.getTenantCustId()));
		}
		//如果是个人开户
		if (accountOpenInfoVO != null) {
			AccountOpenApplicationVO accountOpenApplicationVO = iAccountOpenApplicationService.queryByApplicationId(accountOpenInfoVO.getApplicationId());
			if (accountOpenApplicationVO == null) {
				log.warn(("客户号:【" + AuthUtil.getTenantCustId() + "】开户记录不存在,请先开户再做风险测评!"));
				return R.fail(String.format(I18nUtil.getMessage(RiskMessageConstant.RISK_CUSTOMER_OPEN_ACCOUNT_RECORD_NOT_EXIST_NOTICE), AuthUtil.getTenantCustId()));
			}
			//判断开户申请是否开户已经完成，字典配置customer_open_online_flow中，结束状态为8
			if (!accountOpenApplicationVO.getApplicationStatus().equals(8)) {
				return R.fail(String.format(I18nUtil.getMessage(RiskMessageConstant.RISK_CUSTOMER_OPEN_ACCOUNT_RECORD_UNFINISHED_NOTICE), AuthUtil.getTenantCustId()));
			}
		}
		//如果是机构开户
		if (organizationOpenInfoEntity != null) {
			if (!organizationOpenInfoEntity.getApproveStatus().equals(OrganizationOpenApproveEnum.APPROVED.getCode())) {
				return R.fail(String.format(I18nUtil.getMessage(RiskMessageConstant.RISK_ORGANIZATION_OPEN_ACCOUNT_RECORD_UNFINISHED_NOTICE), AuthUtil.getTenantCustId()));
			}
		}

		Long custId = AuthUtil.getTenantCustId();
		Long questionnaireId = accRiskEvaluationSubmit.getQuestionnaireId();

		AccountRiskQuestionnaireVO accountRiskQuestionnaireVO = accountRiskQuestionnaireService.getAcctRiskQuestionnaireById(questionnaireId);
		if (null == accountRiskQuestionnaireVO) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTIONNAIRE_NOT_EXIST_NOTICE));
		}
		Float riskScore = Float.valueOf(accRiskEvaluationSubmit.getRiskScore());
		AccountRiskQuestionnaireRatingVO riskQuestionnaireRatingVO = accountRiskQuestionnaireRatingService.getRiskQuestionnaireByQuestionnaireIdAndScore(questionnaireId, riskScore);
		if (null == riskQuestionnaireRatingVO) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTIONNAIRE_SCORE_NOT_CONFIGURED_NOTICE));
		}
		AccountRiskEvaluationEntity riskEvaluation = new AccountRiskEvaluationEntity();
		riskEvaluation.setRiskScore(accRiskEvaluationSubmit.getRiskScore());
		riskEvaluation.setRiskType(riskQuestionnaireRatingVO.getRating());
		riskEvaluation.setCustId(custId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 365);
		riskEvaluation.setExpiryDate(calendar.getTime());
		riskEvaluation.setIsPush(0);
		riskEvaluation.setOptionData(accRiskEvaluationSubmit.getTempData());
		riskEvaluation.setRetestSts(0);
		riskEvaluation.setCreateTime(new Date());
		boolean flag = this.save(riskEvaluation);
		FundAccountVO fundAccountVO = syncYFundInfo(riskEvaluation, String.valueOf(custId));
		if (flag) {
			// 清空风险测评暂存结果缓存
			zeroRedis.hDel(AccountRiskEvaluationTempVO.class, custId.toString().trim());
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
				eustOperationLog.setReqParams(JSON.toJSONString(accRiskEvaluationSubmit));
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
				params.add(riskQuestionnaireRatingVO.getRatingName());

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
	 * 获取客户基金风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public R<AccountRiskEvaluationVO> getRiskEvaluationInfo(Long custId) {
		LambdaQueryWrapper<AccountRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AccountRiskEvaluationEntity::getCustId, custId);
		riskEvaluationWrapper.orderByDesc(AccountRiskEvaluationEntity::getCreateTime);
		riskEvaluationWrapper.last("limit 1");
		AccountRiskEvaluationEntity riskEvaluation = getOne(riskEvaluationWrapper);

		AccountRiskEvaluationVO riskEvaluationVO = new AccountRiskEvaluationVO();
		if (null != riskEvaluation) {
			List<AccountRiskQuestionnaireRatingVO> ratingVOS = accountRiskQuestionnaireRatingService.getByQuestionnaireIds(riskEvaluation.getQuestionnaireId());
			if (ratingVOS != null) {
				for (AccountRiskQuestionnaireRatingVO ratingVO : ratingVOS) {
					if (riskEvaluation.getRiskType().equals(ratingVO.getRating())) {
						riskEvaluationVO.setRiskTypeName(ratingVO.getRatingName());
						break;
					}
				}
			}
			riskEvaluationVO.setObjectiveScore(riskEvaluation.getObjectiveScore());
			riskEvaluationVO.setSubjectiveScore(riskEvaluation.getSubjectiveScore());
			riskEvaluationVO.setRiskScore(riskEvaluation.getRiskScore());
			riskEvaluationVO.setRiskType(riskEvaluation.getRiskType());
			riskEvaluationVO.setCreateTime(riskEvaluation.getCreateTime());
			riskEvaluationVO.setExpiryDate(riskEvaluation.getExpiryDate());
			riskEvaluationVO.setRetestSts(riskEvaluation.getRetestSts());
			riskEvaluationVO.setOptionData(riskEvaluation.getOptionData());
			riskEvaluationVO.setIsPush(riskEvaluation.getIsPush());
			riskEvaluationVO.setCustId(riskEvaluation.getCustId());
			riskEvaluationVO.setId(riskEvaluation.getId());
			List<AccountRiskEvaluationEntity> lastThreeMonthRecords = this.getLastThreeMonthRecords(custId);
			//三个月内有2条记录，不可重测
			if (lastThreeMonthRecords != null && lastThreeMonthRecords.size() >= 2) {
				riskEvaluationVO.setCanRetest(false);
			} else {
				riskEvaluationVO.setCanRetest(true);
			}
			//如果最新的记录已过期，可重测
			if (riskEvaluation.getExpiryDate() != null && riskEvaluation.getExpiryDate().before(new Date())) {
				riskEvaluationVO.setIsExpire(1);
				riskEvaluationVO.setCanRetest(true);
			} else {
				riskEvaluationVO.setIsExpire(0);
			}
		}
		return R.data(riskEvaluationVO);
	}

	/**
	 * 获取客户风险评测记录列表
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public R<List<AccountRiskEvaluationVO>> getRiskEvaluationList(Long custId) {
		LambdaQueryWrapper<AccountRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AccountRiskEvaluationEntity::getCustId, custId);
		riskEvaluationWrapper.orderByDesc(AccountRiskEvaluationEntity::getCreateTime);
		List<AccountRiskEvaluationEntity> riskEvaluations = this.list(riskEvaluationWrapper);
		List<AccountRiskEvaluationVO> riskEvaluationVOS = new ArrayList<>();
		if (riskEvaluations != null && riskEvaluations.size() > 0) {
			riskEvaluations.forEach(riskEvaluation -> {
				AccountRiskEvaluationVO riskEvaluationVO = new AccountRiskEvaluationVO();
				List<AccountRiskQuestionnaireRatingVO> ratingVOS = accountRiskQuestionnaireRatingService.getByQuestionnaireIds(riskEvaluation.getQuestionnaireId());
				if (ratingVOS != null) {
					for (AccountRiskQuestionnaireRatingVO ratingVO : ratingVOS) {
						if (riskEvaluation.getRiskType().equals(ratingVO.getRating())) {
							riskEvaluationVO.setRiskTypeName(ratingVO.getRatingName());
							break;
						}
					}
				}
				riskEvaluationVO.setObjectiveScore(riskEvaluation.getObjectiveScore());
				riskEvaluationVO.setSubjectiveScore(riskEvaluation.getSubjectiveScore());
				riskEvaluationVO.setRiskScore(riskEvaluation.getRiskScore());
				riskEvaluationVO.setRiskType(riskEvaluation.getRiskType());
				riskEvaluationVO.setCreateTime(riskEvaluation.getCreateTime());
				riskEvaluationVO.setExpiryDate(riskEvaluation.getExpiryDate());
				riskEvaluationVO.setRetestSts(riskEvaluation.getRetestSts());
				riskEvaluationVO.setOptionData(riskEvaluation.getOptionData());
				riskEvaluationVO.setIsPush(riskEvaluation.getIsPush());
				riskEvaluationVO.setCustId(riskEvaluation.getCustId());
				riskEvaluationVO.setId(riskEvaluation.getId());

				List<AccountRiskEvaluationEntity> lastThreeMonthRecords = this.getLastThreeMonthRecords(custId);
				//三个月内有2条记录，不可重测
				if (lastThreeMonthRecords != null && lastThreeMonthRecords.size() >= 2) {
					riskEvaluationVO.setCanRetest(false);
				} else {
					riskEvaluationVO.setCanRetest(true);
				}
				//如果最新的记录已过期，可重测
				if (riskEvaluation.getExpiryDate() != null && riskEvaluation.getExpiryDate().before(new Date())) {
					riskEvaluationVO.setIsExpire(1);
					riskEvaluationVO.setCanRetest(true);
				} else {
					riskEvaluationVO.setIsExpire(0);
				}
				riskEvaluationVOS.add(riskEvaluationVO);
			});
		}
		return R.data(riskEvaluationVOS);
	}

	/**
	 * 获取客户过去3个月内的基金风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public List<AccountRiskEvaluationEntity> getLastThreeMonthRecords(Long custId) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -3);
		Date lastThreeMonthTime = calendar.getTime();
		LambdaQueryWrapper<AccountRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AccountRiskEvaluationEntity::getCustId, custId);
		riskEvaluationWrapper.between(AccountRiskEvaluationEntity::getCreateTime, lastThreeMonthTime, new Date());
		riskEvaluationWrapper.orderByDesc(AccountRiskEvaluationEntity::getCreateTime);
		List<AccountRiskEvaluationEntity> riskEvaluations = baseMapper.selectList(riskEvaluationWrapper);
		return riskEvaluations;
	}


	/**
	 * 获取客户基金风险评测记录
	 *
	 * @param accRiskEvaluation
	 * @return
	 */
	@Override
	public R<AccountRiskEvaluationVO> getRiskEvaluationInfo(AccountRiskEvaluationDTO accRiskEvaluation) {
		if (null == accRiskEvaluation.getCustId())
			return R.fail(ResultCode.PARAM_VALID_ERROR);

		LambdaQueryWrapper<AccountRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AccountRiskEvaluationEntity::getCustId, accRiskEvaluation.getCustId());
		riskEvaluationWrapper.orderByDesc(AccountRiskEvaluationEntity::getCreateTime);
		riskEvaluationWrapper.last("limit 1");
		AccountRiskEvaluationEntity riskEvaluation = getOne(riskEvaluationWrapper);
		AccountRiskEvaluationVO riskEvaluationVO = new AccountRiskEvaluationVO();
		if (null != riskEvaluation) {
			List<AccountRiskQuestionnaireRatingVO> ratingVOS = accountRiskQuestionnaireRatingService.getByQuestionnaireIds(riskEvaluation.getQuestionnaireId());
			if (ratingVOS != null) {
				for (AccountRiskQuestionnaireRatingVO ratingVO : ratingVOS) {
					if (riskEvaluation.getRiskType().equals(ratingVO.getRating())) {
						riskEvaluationVO.setRiskTypeName(ratingVO.getRatingName());
						break;
					}
				}
			}
			riskEvaluationVO.setObjectiveScore(riskEvaluation.getObjectiveScore());
			riskEvaluationVO.setSubjectiveScore(riskEvaluation.getSubjectiveScore());
			riskEvaluationVO.setRiskScore(riskEvaluation.getRiskScore());
			riskEvaluationVO.setRiskType(riskEvaluation.getRiskType());
			riskEvaluationVO.setCreateTime(riskEvaluation.getCreateTime());
			riskEvaluationVO.setExpiryDate(riskEvaluation.getExpiryDate());
			riskEvaluationVO.setRetestSts(riskEvaluation.getRetestSts());
			riskEvaluationVO.setOptionData(riskEvaluation.getOptionData());
			riskEvaluationVO.setIsPush(riskEvaluation.getIsPush());
			riskEvaluationVO.setCustId(riskEvaluation.getCustId());
			riskEvaluationVO.setId(riskEvaluation.getId());
			List<AccountRiskEvaluationEntity> lastThreeMonthRecords = this.getLastThreeMonthRecords(accRiskEvaluation.getCustId());
			//三个月内有2条记录，不可重测
			if (lastThreeMonthRecords != null && lastThreeMonthRecords.size() >= 2) {
				riskEvaluationVO.setCanRetest(false);
			} else {
				riskEvaluationVO.setCanRetest(true);
			}
			//如果最新的记录已过期，可重测
			if (riskEvaluation.getExpiryDate() != null && riskEvaluation.getExpiryDate().before(new Date())) {
				riskEvaluationVO.setIsExpire(1);
				riskEvaluationVO.setCanRetest(true);
			} else {
				riskEvaluationVO.setIsExpire(0);
			}
		}
		return R.data(riskEvaluationVO);
	}

	/**
	 * 获取客户基金风险评测记录
	 *
	 * @return
	 */
	@Override
	public R<AccountRiskEvaluationVO> getRiskEvaluationInfo() {
		LambdaQueryWrapper<AccountRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AccountRiskEvaluationEntity::getCustId, AuthUtil.getTenantCustId());
		riskEvaluationWrapper.orderByDesc(AccountRiskEvaluationEntity::getCreateTime);
		riskEvaluationWrapper.last("limit 1");
		AccountRiskEvaluationEntity riskEvaluation = getOne(riskEvaluationWrapper);
		AccountRiskEvaluationVO riskEvaluationVO = new AccountRiskEvaluationVO();
		if (null != riskEvaluation) {
			List<AccountRiskQuestionnaireRatingVO> ratingVOS = accountRiskQuestionnaireRatingService.getByQuestionnaireIds(riskEvaluation.getQuestionnaireId());
			if (ratingVOS != null) {
				for (AccountRiskQuestionnaireRatingVO ratingVO : ratingVOS) {
					if (riskEvaluation.getRiskType().equals(ratingVO.getRating())) {
						riskEvaluationVO.setRiskTypeName(ratingVO.getRatingName());
						break;
					}
				}
			}
			riskEvaluationVO.setObjectiveScore(riskEvaluation.getObjectiveScore());
			riskEvaluationVO.setSubjectiveScore(riskEvaluation.getSubjectiveScore());
			riskEvaluationVO.setRiskScore(riskEvaluation.getRiskScore());
			riskEvaluationVO.setRiskType(riskEvaluation.getRiskType());
			riskEvaluationVO.setCreateTime(riskEvaluation.getCreateTime());
			riskEvaluationVO.setExpiryDate(riskEvaluation.getExpiryDate());
			riskEvaluationVO.setRetestSts(riskEvaluation.getRetestSts());
			riskEvaluationVO.setOptionData(riskEvaluation.getOptionData());
			riskEvaluationVO.setIsPush(riskEvaluation.getIsPush());
			riskEvaluationVO.setCustId(riskEvaluation.getCustId());
			riskEvaluationVO.setId(riskEvaluation.getId());
			List<AccountRiskEvaluationEntity> lastThreeMonthRecords = this.getLastThreeMonthRecords(AuthUtil.getTenantCustId());
			//三个月内有2条记录，不可重测
			if (lastThreeMonthRecords != null && lastThreeMonthRecords.size() >= 2) {
				riskEvaluationVO.setCanRetest(false);
			} else {
				riskEvaluationVO.setCanRetest(true);
			}
			//如果最新的记录已过期，可重测
			if (riskEvaluation.getExpiryDate() != null && riskEvaluation.getExpiryDate().before(new Date())) {
				riskEvaluationVO.setIsExpire(1);
				riskEvaluationVO.setCanRetest(true);
			} else {
				riskEvaluationVO.setIsExpire(0);
			}
			return R.data(riskEvaluationVO);
		}
		return R.success();
	}

	/**
	 * 重置风险评测
	 *
	 * @param accRiskEvaluation
	 * @return
	 */
	@Override
	public R resetRiskEvaluation(AccountRiskEvaluationDTO accRiskEvaluation) {
		if (null == accRiskEvaluation.getId()) return
			R.fail(ResultCode.PARAM_VALID_ERROR);

		AccountRiskEvaluationEntity resetRisk = new AccountRiskEvaluationEntity();
		resetRisk.setId(accRiskEvaluation.getId());
		resetRisk.setRetestSts(1);
		this.updateById(resetRisk);

		return R.success();
	}

	/**
	 * 更新大账户中心用户风险等级
	 *
	 * @param evaluation
	 * @param yfFundAccountMain
	 * @return
	 */
	private FundAccountVO syncYFundInfo(AccountRiskEvaluationEntity evaluation, String yfFundAccountMain) {
		try {
			if (StringUtils.isBlank(yfFundAccountMain) || null == evaluation) {
				return null;
			}
			R<CustomerAccountVO> result = customerInfoClient.customerAccountInfo(evaluation.getCustId());
			if (!result.isSuccess()) {
				log.error("风险测评,账号{}风险测评查询账号信息失败：{}", evaluation.getCustId(), result.getMsg());
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
					BpmnFundAcctInfoEntity entity = new BpmnFundAcctInfoEntity();
					entity.setTradeAccount(tradeAccount.getTradeAccount());
					entity.setFundAccountMain(r.getData().getAccountId());
					entity.setFundAccount(r.getData().getSubAccounts().get(0).getSubAccountId());
					entity.setFundAccountType(0);
					entity.setFundOperType(1);
					entity.setAccountStatus(0);
					entity.setCreateTime(new Date());
					entity.setUpdateTime(new Date());
					// 占时插入基金账户信息表到zero_cust库,后续业务迁移到bpmn来以后再插入到zero_bpmn库
					int rows = bpmFundAcctInfoMapper.insert(entity);
					if (rows > 0) {
						log.info("风险测评,用户{}基金开户成功,插入bpm_fund_acct_info表成功!", evaluation.getCustId());
					} else {
						log.error("风险测评,用户{}基金开户成功,插入bpm_fund_acct_info表失败!", evaluation.getCustId());
					}
				}
			}
			log.info("风险测评,用户{}基金开户成功!", evaluation.getCustId());
			return r.getData();
		} catch (Exception e) {
			log.error("风险测评,更新大账户中心用户风险等级异常:" + e.getMessage());
			return null;
		}
	}
}
