package com.minigod.zero.bpmn.module.deposit.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.utils.ProtocolUtils;
import com.minigod.zero.bpmn.module.account.constants.DictTypeConstant;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.deposit.bo.SubmitDepositBo;
import com.minigod.zero.bpmn.module.deposit.entity.*;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositApplicationMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositImageMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositInfoMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositFundsMapper;
import com.minigod.zero.bpmn.module.deposit.service.BankCardInfoService;
import com.minigod.zero.bpmn.module.deposit.service.IFundDepositInfoService;
import com.minigod.zero.bpmn.module.deposit.service.ISecAccImgService;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositFundsService;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositInfoVO;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.dbs.util.DecimalUtils;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.trade.feign.ICounterFundClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 客户入金申请信息表 服务实现类
 *
 * @author taro
 * @since 2024-02-29
 */
@Service
@Slf4j
@AllArgsConstructor
public class FundDepositInfoServiceImpl extends ServiceImpl<FundDepositInfoMapper, FundDepositInfoEntity> implements IFundDepositInfoService {
	private final FundDepositApplicationMapper fundDepositApplicationMapper;
	private final FundDepositImageMapper fundDepositImageMapper;
	private final ISecAccImgService secAccImgService;
	private final IFlowClient iFlowClient;
	private final SecDepositFundsMapper secDepositFundsMapper;
	private final RedisLockClient redisLockClient;
	private final static String DEPOSIT_LOCK_KEY = "LOCK:DEPOSIT:%s:%s";
	private final ICustomerInfoClient iCustomerInfoClient;
	private final BankCardInfoService bankCardInfoService;
	private final ISecDepositFundsService iSecDepositFundsService;
	private final IDictBizClient dictBizClient;
	private final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<Runnable>());

	@Override
	public IPage<FundDepositInfoVO> selectFundDepositInfoPage(IPage<FundDepositInfoVO> page, FundDepositInfoVO fundDepositInfo) {
		return page.setRecords(baseMapper.selectFundDepositInfoPage(page, fundDepositInfo));
	}

	@Override
	public void submitDeposit(SecDepositFundsEntity secDepositFundsEntity) {
		try {
			String applicationId = determineApplicationId(secDepositFundsEntity);
			FundDepositApplicationEntity applicationEntity = createApplicationEntity(secDepositFundsEntity, applicationId);
			saveApplicationEntity(applicationEntity);
			saveImages(secDepositFundsEntity, applicationId);
			updateDepositInfo(secDepositFundsEntity, applicationId);
			submitToWorkflow(secDepositFundsEntity, applicationId);
		} catch (Exception e) {
			log.error("提交入金申请异常，参数：{}", secDepositFundsEntity, e);
		}
	}

	private String determineApplicationId(SecDepositFundsEntity entity) {
		if (StringUtil.isNotBlank(entity.getApplicationId())) {
			return entity.getApplicationId();
		} else {
			return ApplicationIdUtil.generateDepositId(entity.getTenantId());
		}
	}

	private FundDepositApplicationEntity createApplicationEntity(SecDepositFundsEntity entity, String applicationId) {
		FundDepositApplicationEntity applicationEntity = new FundDepositApplicationEntity();
		Date time = new Date();
		applicationEntity.setApplicationId(applicationId);
		applicationEntity.setCreateTime(time);
		applicationEntity.setIsDeleted(0);
		applicationEntity.setStartTime(time);

		String supportType = entity.getSupportType().toString();
		if (SupportTypeEnum.EDDA.getType().equals(supportType)) {
			applicationEntity.setStatus(BpmCommonEnum.FundDepositStatus.DEPOSIT_PENDING.getStatus());
		} else if (SupportTypeEnum.MANUAL.getType().equals(supportType)) {
			applicationEntity.setLastApprovalUser(entity.getBackPerson());
			applicationEntity.setStatus(BpmCommonEnum.FundDepositStatus.DEPOSIT_SUCCESS.getStatus());
		}

		applicationEntity.setApplicationTitle(String.format("[%s]入金申请", entity.getFundAccount()));
		applicationEntity.setTenantId(entity.getTenantId());

		return applicationEntity;
	}

	private void saveApplicationEntity(FundDepositApplicationEntity applicationEntity) {
		fundDepositApplicationMapper.delete(new LambdaQueryWrapper<FundDepositApplicationEntity>()
			.eq(FundDepositApplicationEntity::getApplicationId, applicationEntity.getApplicationId()));
		fundDepositApplicationMapper.insert(applicationEntity);
	}

	private void saveImages(SecDepositFundsEntity entity, String applicationId) {
		List<SecAccImgEntity> imgList = secAccImgService.findSecAccImg(entity.getId());
		List<FundDepositImage> imageList = imgList.stream()
			.map(img -> new FundDepositImage(applicationId, new Date(), img.getImageLocationType(), img.getAccImgPath(), img.getTenantId()))
			.collect(Collectors.toList());
		if (!imageList.isEmpty()) {
			fundDepositImageMapper.batchInsert(imageList);
		}
	}

	private void updateDepositInfo(SecDepositFundsEntity entity, String applicationId) {
		baseMapper.delete(new LambdaQueryWrapper<FundDepositInfoEntity>()
			.eq(FundDepositInfoEntity::getApplicationId, applicationId));
		baseMapper.insert(convertEntity(entity));
	}

	private void submitToWorkflow(SecDepositFundsEntity entity, String applicationId) {
		executorService.submit(() -> {
			try {
				Map<String, Object> variables = createWorkflowVariables(entity, applicationId);
				R r = iFlowClient.startProcessInstanceByKey(ProcessConstant.DEPOSIT_KEY, variables);
				if (r.isSuccess()) {
					handleWorkflowSuccess(entity, applicationId);
				} else {
					handleWorkflowFailure(entity, "工作流启动失败");
				}
			} catch (Exception e) {
				log.error("工作流提交异常，参数：{}", entity, e);
				handleWorkflowFailure(entity, "工作流提交异常");
			}
		});
	}

	private Map<String, Object> createWorkflowVariables(SecDepositFundsEntity entity, String applicationId) {
		Map<String, Object> variables = new HashMap<>();
		variables.put(TaskConstants.TENANT_ID, entity.getTenantId());
		variables.put(TaskConstants.APPLICATION_ID, applicationId);
		variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.DEPOSIT_KEY));
		variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.DEPOSIT_KEY));
		variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
		variables.put("skip", checkSkipNode(entity));
		return variables;
	}

	private void handleWorkflowSuccess(SecDepositFundsEntity entity, String applicationId) {
		entity.setPushrecved(1);
		entity.setState(DepositStatusEnum.SecDepositFundsStatus.ACCEPT.getCode());
		entity.setModifyTime(new Date());
		entity.setApplicationId(applicationId);
		sendDepositEmail(entity);
		if (SupportTypeEnum.MANUAL.getType().equals(entity.getSupportType().toString())) {
			entity.setSettlementDt(new Date());
			entity.setState(DepositStatusEnum.SecDepositFundsStatus.FINISH.getCode());
		}
		secDepositFundsMapper.updateById(entity);
	}

	private void handleWorkflowFailure(SecDepositFundsEntity entity, String reason) {
		entity.setErrCnt(entity.getErrCnt() + 1);
		entity.setPushrecved(1);
		entity.setState(DepositStatusEnum.SecDepositFundsStatus.RETURN.getCode());
		entity.setBackReason(reason);
		secDepositFundsMapper.updateById(entity);
	}

	private void sendDepositEmail(SecDepositFundsEntity secDepositFundsEntity) {

		try {

			R<CustomerAccountDetailVO> accountDetail = iCustomerInfoClient.selectCustomerDetailByAccountId(secDepositFundsEntity.getClientId());
			if (accountDetail.isSuccess()) {
				CustomerAccountDetailVO accountOpenInfoVO = accountDetail.getData();
				String now = DateUtil.now();
				String name = accountOpenInfoVO.getClientName();
				if (StringUtils.isEmpty(name)) {
					name = accountOpenInfoVO.getGivenNameSpell();
				}
				R<List<DictBiz>> emailAddress = dictBizClient.getListByTenantId(secDepositFundsEntity.getTenantId()
					, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
				//ops发送审核邮件
				List<DictBiz> data = emailAddress.getData();
				List<String> emailList = data.stream().map(DictBiz::getDictKey).collect(Collectors.toList());
				List<String> paramList = new ArrayList<>();
				paramList.add(now);
				paramList.add(name);
				paramList.add(secDepositFundsEntity.getRemittanceBankName());
				paramList.add(ProtocolUtils.bankCardFormatter(secDepositFundsEntity.getRemittanceBankAccount()));
				paramList.add(DecimalUtils.formatDecimal(secDepositFundsEntity.getDepositMoney()));
				paramList.add(CashConstant.PayCurrencyTypeDescEnum.getDesc(secDepositFundsEntity.getCurrency().toString()));

				List<String> titleList = new ArrayList<>();
				titleList.add(now);
				titleList.add(name);
				EmailUtil.builder()
					.titleParams(titleList)
					.paramList(paramList)
					.accepts(emailList)
					.templateCode(EmailTemplate.DEPOSIT_REQUEST_NOTIFICATION.getCode())
					.sendAsync();
			}


		} catch (Exception e) {
			log.error("发送邮件失败 {}", e.getMessage());
		}
	}

	/**
	 * 是否跳过凭证
	 *
	 * @param fundsEntity
	 * @return
	 */
	private Boolean checkSkipNode(SecDepositFundsEntity fundsEntity) {
		//添加判断跳过凭证的逻辑
		return false;
	}

	private Integer formatRemittanceType(String bankCode) {
		return "FPS".equals(bankCode) ? 1 : 2;
	}

	/**
	 * 入金实体类转换
	 * 同步入金info表 depositInfo
	 *
	 * @param entity
	 * @return
	 */
	private FundDepositInfoEntity convertEntity(SecDepositFundsEntity entity) {
		FundDepositInfoEntity depositInfo = new FundDepositInfoEntity();
		depositInfo.setBizId(entity.getId());
		depositInfo.setUserId(entity.getUserId());
		depositInfo.setClientId(entity.getClientId());
		depositInfo.setSwiftCode(entity.getSwiftCode());
		depositInfo.setBankType(entity.getBankType());
		depositInfo.setFundAccount(entity.getFundAccount());
		depositInfo.setApplicationId(entity.getApplicationId());
		//汇款信息
		depositInfo.setMoneyType(entity.getCurrency());
		depositInfo.setRemittanceType(entity.getSupportType());
		depositInfo.setDepositBalance(entity.getDepositMoney());
		depositInfo.setRemittanceBankId(entity.getRemittanceBankId());
		depositInfo.setRemittanceBankCode(entity.getRemittanceBankCode());
		depositInfo.setRemittanceBankName(entity.getRemittanceBankName());
		depositInfo.setRemittanceAccount(entity.getRemittanceBankAccount());
		String remittanceAccountName = entity.getRemittanceAccountNameEn();
		//兼容机构户没有英文名
		if (StringUtil.isBlank(remittanceAccountName)) {
			remittanceAccountName = entity.getFundAccountName();
		}
		depositInfo.setRemittanceAccountName(remittanceAccountName);
		//收款信息
		depositInfo.setReceivingAccount(entity.getReceivingAccount());
		depositInfo.setReceivingBankCode(entity.getReceivingBankCode());
		depositInfo.setReceivingBankName(entity.getRemittanceBankName());
		depositInfo.setReceivingAddress(entity.getReceivingBankAddress());
		depositInfo.setReceivingAccountName(entity.getReceivingBankNameCn());
		depositInfo.setHsDealStatus(0);
		Date now = new Date();
		depositInfo.setCreateTime(now);
		depositInfo.setUpdateTime(now);
		depositInfo.setApplicationTime(now);
		depositInfo.setTenantId(entity.getTenantId());
		return depositInfo;
	}

	@Override
	public FundDepositInfoVO queryByApplicationId(String applicationId) {
		LambdaQueryWrapper<FundDepositInfoEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(FundDepositInfoEntity::getApplicationId, applicationId);
		queryWrapper.last("limit 1");
		queryWrapper.orderByDesc(FundDepositInfoEntity::getApplicationTime);
		return (FundDepositInfoVO) baseMapper.selectOne(queryWrapper);
	}

	@Override
	public void depositToCounter(String tenantId, String applicationId, String taskId) {
		LambdaUpdateWrapper<FundDepositApplicationEntity> applicationEntityUpdateWrapper = new LambdaUpdateWrapper<>();
		applicationEntityUpdateWrapper.eq(FundDepositApplicationEntity::getApplicationId, applicationId);
		applicationEntityUpdateWrapper.set(FundDepositApplicationEntity::getUpdateTime, new Date());
		String lockKey = String.format(DEPOSIT_LOCK_KEY, tenantId, applicationId);
		Boolean isLock = false;
		LambdaUpdateWrapper<FundDepositInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(FundDepositInfoEntity::getApplicationId, applicationId);
		updateWrapper.eq(FundDepositInfoEntity::getTenantId, tenantId);
		updateWrapper.set(FundDepositInfoEntity::getUpdateTime, new Date());
		try {
			isLock = redisLockClient.tryLock(lockKey, LockType.FAIR, 1, 1, TimeUnit.SECONDS);
			if (isLock) {
				FundDepositInfoVO infoVO = baseMapper.queryByApplicationId(applicationId);
				if (infoVO.getHsDealStatus() == 1) {
					log.warn(applicationId + "已入账,请检查流程节点！");
					return;
				}
				//有可能包含edda和手工入金，不在这里处理
				String remittanceType = infoVO.getRemittanceType().toString();
				if (SupportTypeEnum.EDDA.getType().equals(remittanceType)
					|| SupportTypeEnum.MANUAL.getType().equals(remittanceType)) {
					return;
				}
				//账号中心入金
				Boolean flag = deposit(infoVO);
				if (!flag) {
					updateWrapper.set(FundDepositInfoEntity::getRetryCount, infoVO.getRetryCount() + 1);
					updateWrapper.set(FundDepositInfoEntity::getHsDealStatus, 2);
					baseMapper.update(null, updateWrapper);
					applicationEntityUpdateWrapper.set(FundDepositApplicationEntity::getStatus, BpmCommonEnum.FundDepositStatus.DEPOSIT_FAIL.getStatus());
					fundDepositApplicationMapper.update(null, applicationEntityUpdateWrapper);

					LambdaUpdateWrapper<SecDepositFundsEntity> fundsUpdateWrapper = new LambdaUpdateWrapper<>();
					fundsUpdateWrapper.eq(SecDepositFundsEntity::getApplicationId, applicationId);
					fundsUpdateWrapper.set(SecDepositFundsEntity::getBackPerson, AuthUtil.getUserName());
					fundsUpdateWrapper.set(SecDepositFundsEntity::getBackReason, "入金失败");
					fundsUpdateWrapper.set(SecDepositFundsEntity::getState, DepositStatusEnum.SecDepositFundsStatus.RETURN.getCode());
					secDepositFundsMapper.update(null, fundsUpdateWrapper);

					throw new ServiceException("入金失败:" + infoVO.getFundAccount());
				} else {
					updateWrapper.set(FundDepositInfoEntity::getHsDealStatus, 1);
					updateWrapper.set(FundDepositInfoEntity::getEntryTime, new Date());
					baseMapper.update(null, updateWrapper);
					applicationEntityUpdateWrapper.set(FundDepositApplicationEntity::getStatus, BpmCommonEnum.FundDepositStatus.DEPOSIT_SUCCESS.getStatus());
					fundDepositApplicationMapper.update(null, applicationEntityUpdateWrapper);

					LambdaUpdateWrapper<SecDepositFundsEntity> fundsUpdateWrapper = new LambdaUpdateWrapper<>();
					fundsUpdateWrapper.eq(SecDepositFundsEntity::getApplicationId, applicationId);
					fundsUpdateWrapper.set(SecDepositFundsEntity::getBackPerson, AuthUtil.getUserName());
					fundsUpdateWrapper.set(SecDepositFundsEntity::getModifyTime, new Date());
					fundsUpdateWrapper.set(SecDepositFundsEntity::getState, DepositStatusEnum.SecDepositFundsStatus.FINISH.getCode());
					fundsUpdateWrapper.set(SecDepositFundsEntity::getSettlementAmt, infoVO.getReceivingAmount());
					fundsUpdateWrapper.set(SecDepositFundsEntity::getSettlementDt, new Date());
					secDepositFundsMapper.update(null, fundsUpdateWrapper);

					R completeTaskR = iFlowClient.completeTask(taskId, "入金成功", new HashMap<>());
					if (!completeTaskR.isSuccess()) {
						throw new ServiceException(completeTaskR.getMsg());
					}

					try {
						SecDepositFundsEntity depositFunds = iSecDepositFundsService.selectByApplicationId(applicationId);
						bankCardInfoService.depositBankCardBinding(depositFunds);
					} catch (Exception e) {
						log.error("入金绑定银行卡失败：{}", e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isLock) {
				//取到锁才解锁，避免照成解锁其他线程的锁
				redisLockClient.unLock(lockKey, LockType.FAIR);
			}
		}
	}

	private boolean deposit(FundDepositInfoVO depositReq) {
		AmountDTO amount = new AmountDTO();
		amount.setBusinessId(depositReq.getApplicationId());
		amount.setAmount(depositReq.getReceivingAmount() != null ? depositReq.getReceivingAmount().setScale(2) : new BigDecimal(0));
		amount.setCurrency(CashConstant.MoneyTypeDescEnum.getDesc(depositReq.getMoneyType().toString()));
		amount.setAccountId(depositReq.getFundAccount());
		String remittanceType = String.valueOf(depositReq.getRemittanceType());
		if (SupportTypeEnum.FPS.getType().equals(remittanceType)) {
			amount.setThawingType(ThawingType.FPS_GOLD_DEPOSIT.getCode());
		} else if (SupportTypeEnum.WYZZ.getType().equals(remittanceType)) {
			amount.setThawingType(ThawingType.E_Bank_GOLD_DEPOSIT.getCode());
		} else if (SupportTypeEnum.EDDA.getType().equals(remittanceType)) {
			amount.setThawingType(ThawingType.EDDA_GOLD_DEPOSIT.getCode());
		} else if (SupportTypeEnum.OPEN_ACCOUNT.getType().equals(remittanceType)) {
			amount.setThawingType(ThawingType.OPEN_ACCOUNT_DEPOSIT.getCode());
		} else {
			amount.setThawingType(ThawingType.GOLD_DEPOSIT.getCode());
		}

		log.info("DBS 入金请求入账 res:{}", JSON.toJSONString(amount));
		R result = iCustomerInfoClient.goldDeposit(amount);

		log.info("DBS 入金请求入账 R:{}", JSON.toJSONString(result));
		if (result.isSuccess()) {
			try {
				List<String> params = new ArrayList<>();
				params.add(String.valueOf(amount.getAmount()));
				params.add(String.valueOf(depositReq.getClientId()));

				PushUtil.builder()
					.msgGroup("P")
					.custId(depositReq.getUserId())
					.params(params)
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.templateCode(PushTemplate.DEPOSIT_SUCCESS.getCode())
					.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("推送入金消息失败", e);
			}
		}
		return result != null && result.getCode() == ResultCode.SUCCESS.getCode();
	}

	@Override
	public void validateFirstDeposit(SubmitDepositBo params) {
		if ("EDDA".equals(params.getBankCode())) {
			LambdaQueryWrapper<FundDepositInfoEntity> queryWrapper = new LambdaQueryWrapper();
			queryWrapper.eq(FundDepositInfoEntity::getClientId, AuthUtil.getClientId());
			queryWrapper.eq(FundDepositInfoEntity::getTenantId, AuthUtil.getTenantId());
			queryWrapper.eq(FundDepositInfoEntity::getHsDealStatus, 1);
			queryWrapper.eq(FundDepositInfoEntity::getRemittanceAccount, params.getRemittanceBankAccount());
			queryWrapper.last("limit 1");
			if (ObjectUtil.isEmpty(baseMapper.selectOne(queryWrapper))) {
				if (params.getDepositMoney().compareTo(new BigDecimal("10000")) < 0) {
					throw new ServiceException("该银行账号为首次入金，需存入大于1万港币以满足证监会要求");
				}
			}
		}
	}
}
