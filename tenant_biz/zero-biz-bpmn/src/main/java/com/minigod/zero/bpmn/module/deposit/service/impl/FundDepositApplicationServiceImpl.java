package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.enums.BankType;
import com.minigod.zero.biz.common.enums.DepositReviewStatus;
import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import com.minigod.zero.bpmn.module.bank.mapper.DepositBankBillFlowMapper;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.deposit.bo.FundDepositApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositApplicationEntity;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositInfoEntity;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositApplicationMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositImageMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositInfoMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositFundsMapper;
import com.minigod.zero.bpmn.module.deposit.service.IFundDepositApplicationService;
import com.minigod.zero.bpmn.module.deposit.vo.DepositRecordVO;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositApplicationVO;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositInfoVO;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.FinancingAccountAmount;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.utils.BusinessLock;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.entity.FlowNotify;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.service.FlowNotifyService;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户入金申请表 服务实现类
 *
 * @author taro
 * @since 2024-02-29
 */
@Service
@AllArgsConstructor
public class FundDepositApplicationServiceImpl extends ServiceImpl<FundDepositApplicationMapper, FundDepositApplicationEntity> implements IFundDepositApplicationService, FlowNotifyService {

	private final IFlowClient flowClient;
	private final IDictBizClient dictBizClient;
	private final RedisLockClient redisLockClient;
	private final ICustomerInfoClient iCustomerInfoClient;
	private final SecDepositFundsMapper secDepositFundsMapper;
	private final FundDepositInfoMapper fundDepositInfoMapper;
	private final FundDepositImageMapper fundDepositImageMapper;
	private final DepositBankBillFlowMapper depositBankBillFlowMapper;

	@Override
	public IPage<FundDepositApplicationVO> selectFundDepositApplicationPage(IPage<FundDepositApplicationVO> page, FundDepositApplicationQuery applicationQuery) {
		applicationQuery.setTenantId(AuthUtil.getTenantId());
		IPage<FundDepositApplicationVO> result = baseMapper.selectFundDepositApplicationPage(page, applicationQuery);
		result.getRecords().stream().forEach(record -> {
			if (StringUtils.isBlank(record.getAssignDrafter()) || record.getAssignDrafter().equals(String.valueOf(AuthUtil.getUserId()))) {
				record.setDealPermissions(1);
			} else {
				record.setDealPermissions(0);
			}
			record.setStatusName(DepositReviewStatus.getDesc(record.getStatus()));
			FundDepositInfoVO info = record.getInfo();
			if (info != null) {
				info.setBankTypeName(BankType.getDesc(info.getBankType()));
				info.setMoneyTypeName(CurrencyExcEnum.CurrencyType.getName(info.getMoneyType()));
				info.setRemittanceTypeName(SupportTypeEnum.getDesc(info.getRemittanceType()));
			}
		});
		return result;
	}

	/**
	 * 入金申请表分页查询
	 *
	 * @param page
	 * @param applicationQuery
	 * @return
	 */
	@Override
	public IPage<DepositRecordVO> queryPage(IPage<DepositRecordVO> page, FundDepositApplicationQuery applicationQuery) {

		List<DepositRecordVO> list = secDepositFundsMapper.queryPage(page, applicationQuery);

		if (!CollectionUtil.isEmpty(list)) {
			list.stream().forEach(record -> {
				record.setBankTypeName(BankType.getDesc(record.getBankType()));
				record.setStatusName(DepositReviewStatus.getDesc(record.getStatus()));
				record.setMoneyTypeName(CurrencyExcEnum.CurrencyType.getName(record.getMoneyType()));
				record.setRemittanceTypeName(SupportTypeEnum.getDesc(record.getRemittanceType()));
				if (record.getSettlementAmt() == null) {
					record.setSettlementAmt(BigDecimal.ZERO);
				}
				//fps 和网银 转账都有凭证
				if (Integer.valueOf(SupportTypeEnum.EDDA.getType()) != record.getRemittanceType()) {
					record.setImagesList(fundDepositImageMapper.queryByApplicationId(record.getApplicationId()));
				} else {
					record.setStatusName(DepositReviewStatus.getDesc(record.getStatus()));
				}
				if (StringUtils.isBlank(record.getAssignDrafter()) || record.getAssignDrafter().equals(String.valueOf(AuthUtil.getUserId()))) {
					record.setDealPermissions(1);
				} else {
					record.setDealPermissions(0);
				}
			});
		}
		return page.setRecords(list);
	}

	@Override
	public DepositRecordVO depositRecordDetail(String applicationId) {
		DepositRecordVO record = secDepositFundsMapper.queryDepositRecordDetail(applicationId);
		if (record != null) {
			record.setBankTypeName(BankType.getDesc(record.getBankType()));
			record.setStatusName(DepositReviewStatus.getDesc(record.getStatus()));
			record.setMoneyTypeName(CurrencyExcEnum.CurrencyType.getName(record.getMoneyType()));
			record.setRemittanceTypeName(SupportTypeEnum.getDesc(record.getRemittanceType()));
			if (Integer.valueOf(SupportTypeEnum.EDDA.getType()) != record.getRemittanceType()) {
				record.setImagesList(fundDepositImageMapper.queryByApplicationId(record.getApplicationId()));
			}
		}
		return record;
	}

	@Override
	public String batchClaim(Integer applicationStatus, List<String> applicationIds) {
		StringBuilder stringBuilder = new StringBuilder("您申领的流程号:" + applicationIds + "<br/>");
		List<String> failClaim = new ArrayList();
		List<String> notFindClaim = new ArrayList();
		List<String> suceessClaim = new ArrayList<>();
		LambdaQueryWrapper<FundDepositApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(FundDepositApplicationEntity::getApplicationId, applicationIds);
		queryWrapper.eq(ObjectUtil.isNotEmpty(applicationStatus), FundDepositApplicationEntity::getApplicationStatus, applicationStatus);
		List<FundDepositApplicationEntity> applications = baseMapper.selectList(queryWrapper);
		if (applications.size() == 0) {
			throw new ServiceException("所选任务已全被申领");
		}

		for (FundDepositApplicationEntity application : applications) {
			if (application.getAssignDrafter() == null) {
				try {
					R r = flowClient.taskClaim(application.getTaskId());
					if (r.isSuccess()) {
						baseMapper.addAssignDrafter(AuthUtil.getUserId().toString(), application.getApplicationId());
						log.debug("成功认领:" + application.getApplicationId() + "<br/>" + "任务ID:" + application.getTaskId());
						suceessClaim.add(application.getApplicationId());
					} else {
						log.error(String.format("流程:%s 认领任务失败,原因: %s", application.getApplicationId(), r.getMsg()));
						failClaim.add(application.getApplicationId());
					}

				} catch (Exception e) {
					log.error(String.format("流程:%s 认领任务异常,原因: %s", application.getApplicationId(), e.getMessage()));
					failClaim.add(application.getApplicationId());
				}
			} else if (!application.getApplicationStatus().equals(applicationStatus)) {
				log.error(String.format("流程:%s 认领任务失败,原因: 任务状态和当前状态不匹配,任务状态：%s, 当前申领状态：%s", application.getApplicationId(), application.getApplicationStatus(), applicationStatus));
				notFindClaim.add(application.getApplicationId());
			} else {
				log.error(String.format("流程:%s 认领任务失败,原因: 不满足认领条件!", application.getApplicationId()));
				failClaim.add(application.getApplicationId());
			}
		}
		if (suceessClaim != null && suceessClaim.size() > 0) {
			stringBuilder.append("成功认领:" + suceessClaim + "<br/>");
		}
		if (failClaim != null && failClaim.size() > 0) {
			stringBuilder.append("失败认领:" + failClaim + "<br/>");
		}
		if (notFindClaim != null && notFindClaim.size() > 0) {
			stringBuilder.append("任务不存在:" + notFindClaim + "<br/>");
		}
		return stringBuilder.toString();
	}

	@Override
	public void batchUnClaim(List<String> applicationIds) {
		LambdaQueryWrapper<FundDepositApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(FundDepositApplicationEntity::getApplicationId, applicationIds);
		List<FundDepositApplicationEntity> applications = baseMapper.selectList(queryWrapper);
		for (FundDepositApplicationEntity application : applications) {
			baseMapper.clearAssignDrafter(application.getApplicationId());
			R r = flowClient.taskUnclaim(application.getTaskId());
			if (!r.isSuccess()) {
				throw new ServiceException(r.getMsg());
			}
		}
	}

	@Override
	public void rejectNode(String applicationId, String instanceId, String reason) {
		BusinessLock businessLock = new BusinessLock(true) {
			@Override
			public void business() {
				LambdaQueryWrapper<FundDepositApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(FundDepositApplicationEntity::getApplicationId, applicationId);
				queryWrapper.eq(FundDepositApplicationEntity::getTenantId, AuthUtil.getTenantId());
				FundDepositApplicationEntity applicationEntity = baseMapper.selectOne(queryWrapper);
				if (applicationEntity.getApplicationStatus() != 1) {
					throw new ServiceException("该任务已处理");
				}
				R r = flowClient.taskFinish(instanceId, FlowComment.REBACK.getType(), reason);
				if (r.isSuccess()) {
					LambdaUpdateWrapper<FundDepositApplicationEntity> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.eq(FundDepositApplicationEntity::getApplicationId, applicationId);
					updateWrapper.set(FundDepositApplicationEntity::getStatus, BpmCommonEnum.FundDepositStatus.REJECT.getStatus());
					updateWrapper.set(FundDepositApplicationEntity::getApprovalOpinion, reason);
					updateWrapper.set(FundDepositApplicationEntity::getLastApprovalUser, AuthUtil.getUserName());
					baseMapper.update(null, updateWrapper);
					LambdaUpdateWrapper<SecDepositFundsEntity> fundsUpdateWrapper = new LambdaUpdateWrapper<>();
					fundsUpdateWrapper.eq(SecDepositFundsEntity::getApplicationId, applicationId);
					fundsUpdateWrapper.set(SecDepositFundsEntity::getBackPerson, AuthUtil.getUserName());
					fundsUpdateWrapper.set(SecDepositFundsEntity::getBackReason, reason);
					fundsUpdateWrapper.set(SecDepositFundsEntity::getState, DepositStatusEnum.SecDepositFundsStatus.RETURN.getCode());
					secDepositFundsMapper.update(null, fundsUpdateWrapper);

					try {
						LambdaUpdateWrapper<SecDepositFundsEntity> fundsUpdateWrapper2 = new LambdaUpdateWrapper<>();
						fundsUpdateWrapper2.eq(SecDepositFundsEntity::getApplicationId, applicationId);
						SecDepositFundsEntity secDepositFundsEntity = secDepositFundsMapper.selectOne(fundsUpdateWrapper2);
						List<String> params = new ArrayList<>();
						params.add(applicationId);
						params.add(reason);
						params.add(dictBizClient.getValue("company", "CO_Phone_HK").getData());

						PushUtil.builder()
							.msgGroup("P")
							.custId(secDepositFundsEntity.getUserId())
							.params(params)
							.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
							.templateCode(PushTemplate.DEPOSIT_DOCUMENT_FAIL.getCode())
							.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
							.pushAsync();
					} catch (Exception e) {
						e.printStackTrace();
						log.error("推送入金消息失败", e);
					}
				} else {
					throw new ServiceException(r.getMsg());
				}
			}
		};
		String lockKey = "DEPOSIT_REJECT_NODE:" + applicationId;
		businessLock.lock(lockKey);

	}

	@Override
	public void passNode(String applicationId, String taskId, String reason) {
		BusinessLock businessLock = new BusinessLock(true) {
			@Override
			public void business() {
				LambdaQueryWrapper<FundDepositApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(FundDepositApplicationEntity::getApplicationId, applicationId);
				queryWrapper.eq(FundDepositApplicationEntity::getTenantId, AuthUtil.getTenantId());
				FundDepositApplicationEntity applicationEntity = baseMapper.selectOne(queryWrapper);
				if (applicationEntity.getApplicationStatus() != 1) {
					throw new ServiceException("该任务已处理");
				}
				R r = flowClient.completeTask(taskId, reason, new HashMap<>());
				if (r.isSuccess()) {
					LambdaUpdateWrapper<FundDepositApplicationEntity> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.eq(FundDepositApplicationEntity::getApplicationId, applicationId);
					updateWrapper.set(FundDepositApplicationEntity::getApprovalOpinion, reason);
					updateWrapper.set(FundDepositApplicationEntity::getStatus, BpmCommonEnum.FundDepositStatus.COLLATE_AUDIT.getStatus());
					updateWrapper.set(FundDepositApplicationEntity::getLastApprovalUser, AuthUtil.getUserName());
					baseMapper.update(null, updateWrapper);
				} else {
					throw new ServiceException(r.getMsg());
				}
			}
		};
		String lockKey = "DEPOSIT_PASS_NODE:" + applicationId;
		businessLock.lock(lockKey);
	}

	@Override
	public FundDepositApplicationVO queryByApplicationId(String applicationId) {
		FundDepositApplicationVO vo = new FundDepositApplicationVO();
		LambdaQueryWrapper<FundDepositApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(FundDepositApplicationEntity::getApplicationId, applicationId);
		queryWrapper.eq(FundDepositApplicationEntity::getTenantId, AuthUtil.getTenantId());
		FundDepositApplicationEntity applicationEntity = baseMapper.selectOne(queryWrapper);
		if (ObjectUtil.isNotEmpty(applicationEntity)) {
			BeanUtils.copyProperties(applicationEntity, vo);
			FundDepositInfoVO info = fundDepositInfoMapper.queryByApplicationId(applicationId);
			vo.setInfo(info);
			vo.getInfo().setBankTypeName(BankType.getDesc(info.getBankType()));
			vo.getInfo().setMoneyTypeName(CurrencyExcEnum.CurrencyType.getName(info.getMoneyType()));
			vo.getInfo().setRemittanceTypeName(SupportTypeEnum.getDesc(info.getRemittanceType()));
			vo.setImagesList(fundDepositImageMapper.queryByApplicationId(applicationId));
		}
		return vo;
	}

	@Override
	public void collateNode(String applicationId, String taskId, String refId, String reason) {
		//银行流水锁
		String refLockKey = String.format("LOCK:REFERENCE_NO:%s", refId);
		//入金流水锁
		String applicationLockKey = String.format("LOCK:DEPOSIT_APPLICTION:%s", applicationId);
		Boolean applicationLockStatus = false;
		Boolean refLockStatus = false;
		try {
			//加锁等待避免重复
			applicationLockStatus = redisLockClient.tryLock(applicationLockKey, LockType.REENTRANT, 1, 1, TimeUnit.SECONDS);
			if (!applicationLockStatus) {
				throw new ServiceException("该银行流水正被其他人处理,请稍后重试!");
			}
			applicationLockStatus = redisLockClient.tryLock(refLockKey, LockType.REENTRANT, 1, 1, TimeUnit.SECONDS);
			if (!applicationLockStatus) {
				throw new ServiceException("该入金流水正被其他人处理,请稍后重试!");
			}
			DepositBankBillFlow depositBankBillFlow = depositBankBillFlowMapper.selectBankBillByRefId(refId);
			if (ObjectUtil.isEmpty(depositBankBillFlow)) {
				throw new ServiceException("未找到此银行流水");
			} else {
				if (!AuthUtil.getTenantId().equals(depositBankBillFlow.getTenantId())) {
					throw new ServiceException("该银行流水不属于此租户，请联系管理员");
				}
				if (depositBankBillFlow.getCheckStatus() == 1) {
					throw new ServiceException("该银行流水已核对!");
				}

			}
			R r = flowClient.completeTask(taskId, reason, new HashMap<>());
			if (r.isSuccess()) {
				FundDepositInfoVO fundDepositInfoVO = fundDepositInfoMapper.queryByApplicationId(applicationId);

				LambdaUpdateWrapper<FundDepositInfoEntity> updateInfoWrapper = new LambdaUpdateWrapper<>();
				//修改流水实际到账金额
				updateInfoWrapper.set(FundDepositInfoEntity::getReceivingAmount, depositBankBillFlow.getActualMoney());
				updateInfoWrapper.set(FundDepositInfoEntity::getUpdateTime, new Date());

				updateInfoWrapper.eq(FundDepositInfoEntity::getApplicationId, applicationId);
				int updateNum = fundDepositInfoMapper.update(null, updateInfoWrapper);

				//修改流程表核对人信息
				LambdaUpdateWrapper<FundDepositApplicationEntity> updateWrapper = new LambdaUpdateWrapper<>();
				updateWrapper.eq(FundDepositApplicationEntity::getApplicationId, applicationId);
				updateWrapper.set(FundDepositApplicationEntity::getApprovalOpinion, reason);
				updateWrapper.set(FundDepositApplicationEntity::getLastApprovalUser, AuthUtil.getUserName());
				updateWrapper.set(FundDepositApplicationEntity::getStatus, BpmCommonEnum.FundDepositStatus.DEPOSIT_PENDING.getStatus());
				updateWrapper.set(FundDepositApplicationEntity::getUpdateTime, new Date());
				baseMapper.update(null, updateWrapper);

				//银行流水关联业务流水
				LambdaUpdateWrapper<DepositBankBillFlow> updateFlowWrapper = new LambdaUpdateWrapper<>();
				updateFlowWrapper.set(StringUtil.isNotBlank(applicationId), DepositBankBillFlow::getApplicationId, applicationId);
				updateFlowWrapper.set(DepositBankBillFlow::getCheckStatus, 1);
				updateFlowWrapper.set(DepositBankBillFlow::getCreditMount, fundDepositInfoVO.getDepositBalance());
				updateFlowWrapper.set(ObjectUtil.isNotEmpty(AuthUtil.getUserId()), DepositBankBillFlow::getAssignDrafter, AuthUtil.getUserId());
				updateFlowWrapper.eq(DepositBankBillFlow::getReferenceNo, refId);
				R<FinancingAccountAmount> financingAccountAmountR = iCustomerInfoClient.accountAmount(fundDepositInfoVO.getFundAccount(), CashConstant.MoneyTypeDescEnum.getDesc(fundDepositInfoVO.getMoneyType().toString()));
				if (financingAccountAmountR.isSuccess()) {
					updateFlowWrapper.set(DepositBankBillFlow::getAccountBalance, financingAccountAmountR.getData().getAvailableAmount());
				}
				depositBankBillFlowMapper.update(null, updateFlowWrapper);
			} else {
				throw new ServiceException(r.getMsg());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (refLockStatus) {
				redisLockClient.unLock(refLockKey, LockType.REENTRANT);
			}
			if (applicationLockStatus) {
				redisLockClient.unLock(applicationLockKey, LockType.REENTRANT);
			}
		}
	}

	@Override
	public List<FundDepositApplicationEntity> queryListByNode(String currentNodeName) {
		LambdaQueryWrapper<FundDepositApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(FundDepositApplicationEntity::getCurrentNode, currentNodeName);
		queryWrapper.eq(FundDepositApplicationEntity::getIsDeleted, 0);
		queryWrapper.orderByDesc(FundDepositApplicationEntity::getLastApprovalTime);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public void notify(FlowNotify notify) {
		if ("入账".equalsIgnoreCase(notify.getNode())) {
			//发送入账通知
			System.out.println("发送入账通知");
		}
	}
}
