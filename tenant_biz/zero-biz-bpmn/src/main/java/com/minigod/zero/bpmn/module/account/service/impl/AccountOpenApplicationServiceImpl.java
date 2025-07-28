package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountCallbackBo;
import com.minigod.zero.bpmn.module.account.bo.query.ApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.BackApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.CABankVerifyQuery;
import com.minigod.zero.bpmn.module.account.entity.AccountDealLogEntity;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenApplicationMapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.entity.TaskBo;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


/**
 * 客户开户申请
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountOpenApplicationServiceImpl extends BaseServiceImpl<AccountOpenApplicationMapper, AccountOpenApplicationEntity> implements IAccountOpenApplicationService {
	private final IFlowClient flowClient;
	private final IAccountDealLogService iAccountDealLogService;
	private final IAccountBackReasonService accountBackReasonService;
	private final ICustomOpenInfoService customOpenInfoService;
	private final AccountOpenInfoMapper accountOpenInfoMapper;
	private final IDictBizClient dictBizClient;
	//开户流程状态字典
	private static final String DICT_OPEN_FLOW_STATUS = "customer_open_online_flow";
	//风险等级业务字典
	private static final String DICT_BIZ_RISK_TYPE = "risk_type";

	public IAccountOpenInfoService customerAccountOpenInfoService() {
		return SpringUtil.getBean(IAccountOpenInfoService.class);
	}

	/**
	 * 分页查询客户开户申请
	 *
	 * @param params
	 * @param pageQuery
	 * @return
	 */
	@Override
	public IPage<AccountOpenApplicationVO> queryPageList(ApplicationQuery params, Query pageQuery) {
		params.setTenantId(AuthUtil.getTenantId());
		params.setAssignDrafter(AuthUtil.getUserId().toString());
		IPage<AccountOpenApplicationVO> result = baseMapper.queryPageList(Condition.getPage(pageQuery), params);
		R<List<DictBiz>> dictApplicationStatus = dictBizClient.getList(DICT_OPEN_FLOW_STATUS);
		R<List<DictBiz>> dictRiskType = dictBizClient.getList(DICT_BIZ_RISK_TYPE);
		result.getRecords().stream().forEach(s -> {
			if (StringUtils.isBlank(s.getAssignDrafter())) {
				s.setDealPermissions(1);
			} else {
				if (s.getAssignDrafter().equals(String.valueOf(AuthUtil.getUserId()))) {
					s.setDealPermissions(1);
				} else {
					s.setDealPermissions(0);
				}
			}

			if (dictApplicationStatus.isSuccess()) {
				List<DictBiz> dictBizData = dictApplicationStatus.getData();
				if (dictBizData != null) {
					dictBizData.stream().forEach(d -> {
						if (d.getDictKey() != null && s.getApplicationStatus() != null && d.getDictKey().trim().toString().equals(s.getApplicationStatus().toString())) {
							s.setApplicationStatusName(d.getDictValue());
						}
					});
				}
			}

			if (dictRiskType.isSuccess()) {
				List<DictBiz> dictBizData = dictRiskType.getData();
				if (dictBizData != null) {
					dictBizData.stream().forEach(d -> {
						if (d.getDictKey() != null && s.getAcceptRisk() != null && d.getDictKey().trim().toString().equals(s.getAcceptRisk().toString())) {
							s.setAcceptRiskName(d.getDictValue());
						}
					});
				}
			}
			this.transferAccountOpenInfo(s);
		});
		return result;
	}

	/**
	 * 分页查询退回给客户的开户申请
	 *
	 * @param params
	 * @param pageQuery
	 * @return
	 */
	@Override
	public IPage<AccountBackInfoVO> queryBackPageList(BackApplicationQuery params, Query pageQuery) {
		params.setTenantId(AuthUtil.getTenantId());
		return baseMapper.queryBackPageList(Condition.getPage(pageQuery), params);
	}

	/**
	 * 分页查询CA银行四要素验证信息
	 *
	 * @param params
	 * @param pageQuery
	 * @return
	 */
	@Override
	public IPage<AccountCABankVerifyInfoVO> queryCABankVerifyInfoPageList(CABankVerifyQuery params, Query pageQuery) {
		params.setTenantId(AuthUtil.getTenantId());
		return baseMapper.queryCABankVerifyInfoPageList(Condition.getPage(pageQuery), params);
	}

	@Override
	public String batchClaim(Integer applicationStatus, List<String> applicationIds) {
		StringBuilder stringBuilder = new StringBuilder("您申领的流程号:" + applicationIds + "<br/>");
		List<String> failClaim = new ArrayList();
		List<String> notFindClaim = new ArrayList();
		List<String> suceessClaim = new ArrayList<>();
		LambdaQueryWrapper<AccountOpenApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(AccountOpenApplicationEntity::getApplicationId, applicationIds);
		queryWrapper.eq(ObjectUtil.isNotEmpty(applicationStatus), AccountOpenApplicationEntity::getApplicationStatus, applicationStatus);
		List<AccountOpenApplicationEntity> applications = baseMapper.selectList(queryWrapper);
		if (applications.size() == 0) {
			throw new ServiceException("所选任务已全被申领");
		}

		for (AccountOpenApplicationEntity application : applications) {
			if (application.getAssignDrafter() == null) {
				try {
					R r = flowClient.taskClaim(application.getTaskId());
					if (r.isSuccess()) {
						baseMapper.addAssignDrafter(application.getApplicationId(), AuthUtil.getUserId().toString());
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
		List<AccountOpenApplicationEntity> applications = baseMapper.selectApplicationIds(applicationIds, null);
		for (AccountOpenApplicationEntity application : applications) {
			application.setAssignDrafter(null);
			baseMapper.clearAssignDrafter(application.getApplicationId());
			R r = flowClient.taskUnclaim(application.getTaskId());
			if (!r.isSuccess()) {
				throw new ServiceException(r.getMsg());
			}
		}
	}

	@Override
	public void rejectNode(String applicationId, String taskId, String reason, Integer status) {
		AccountOpenApplicationVO application = baseMapper.queryByApplicationId(applicationId);
		AccountOpenInfoVO accountOpenInfo = accountOpenInfoMapper.queryByApplicationId(applicationId);
		if (application == null) {
			log.error("驳回/退回操作失败,申请编号(applicationId:" + applicationId + "),任务ID:" + taskId + ",开户申请不存在!");
			return;
		}
		if (accountOpenInfo == null) {
			log.error("驳回/退回操作失败,申请编号(applicationId:" + applicationId + "),任务ID:" + taskId + ",开户信息不存在!");
			return;
		}
		//不是初审节点
		if (application.getApplicationStatus() != 1) {
			application.setApprovalOpinion(reason);
			baseMapper.updateById(application);
			TaskBo bo = new TaskBo();
			bo.setInstanceId(application.getInstanceId());
			bo.setAssignee(application.getAssignDrafter());
			bo.setComment(reason);
			bo.setTaskId(application.getTaskId());
			bo.setTargetKey("node0");
			bo.setTaskName("初审");
			R r = flowClient.taskReturn(bo);
			if (!r.isSuccess()) {
				throw new ServiceException("流程处理失败:" + r.getMsg());
			}
			AccountDealLogEntity openAccountDealLog = new AccountDealLogEntity();
			openAccountDealLog.setApplicationId(applicationId);
			openAccountDealLog.setNode(application.getCurrentNode());
			openAccountDealLog.setUserId(AuthUtil.getUserId());
			openAccountDealLog.setResult(FlowComment.REBACK_0.getRemark());
			openAccountDealLog.setStatus(Integer.valueOf(FlowComment.REBACK_0.getType()));
			openAccountDealLog.setReason(reason);
			openAccountDealLog.setDealTime(new Date());
			iAccountDealLogService.saveOrUpdate(openAccountDealLog);
		} else {
			List<AccountBackReasonVO> reasons = accountBackReasonService.selectByApplicationId(applicationId);
			reasons = reasons.stream().filter(s -> StringUtils.isNotBlank(s.getContent())).collect(Collectors.toList());
			if (reasons.isEmpty()) {
				throw new ServiceException("请填写退回理由");
			}
			application.setIsBack(1);
			application.setApproveResult(status);
			application.setApprovalOpinion(reason);
			baseMapper.updateById(application);
			StringBuilder reasonBuilder = new StringBuilder(reason + "<br />");
			reasonBuilder.append("退回原因:<br />");
			reasons.forEach(s -> {
				reasonBuilder.append(s.getTitle() + ":" + s.getContent());
				reasonBuilder.append("<br />");
			});
			Map<String, Object> map = new HashMap<>();
			map.put(TaskConstants.IS_BACK, 1);
			R r = flowClient.taskJump(application.getInstanceId(), "退回", FlowComment.REBACK_0.getType(), reasonBuilder.toString());
			if (!r.isSuccess()) {
				throw new ServiceException("流程处理失败:" + r.getMsg());
			}
			//发送短信通知
			if (accountOpenInfo != null) {
				StringBuilder smsBuilder = new StringBuilder();
				reasons.forEach(s -> {
					smsBuilder.append(s.getTitle() + ":" + s.getContent());
					smsBuilder.append("<br />");
				});
				String name = accountOpenInfo.getClientName();
				if (StringUtils.isEmpty(name)) {
					name = accountOpenInfo.getClientNameSpell();
				}

				SmsUtil.builder()
					.param(name).param(smsBuilder.toString())
					.templateCode(SmsTemplate.OPEN_ACCOUNT_AUDIT_FAILED.getCode())
					.phoneNumber(accountOpenInfo.getPhoneNumber())
					.areaCode(accountOpenInfo.getPhoneArea())
					.sendAsync();


				List<String> pushParam = new ArrayList<>();
				pushParam.add(smsBuilder.toString());

				PushUtil.builder()
					.msgGroup("P")
					.custId(accountOpenInfo.getUserId())
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.params(pushParam)
					.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
					.templateCode(PushTemplate.ACCOUNT_OPEN_FAIL.getCode())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();
			}
			AccountDealLogEntity openAccountDealLog = new AccountDealLogEntity();
			openAccountDealLog.setApplicationId(applicationId);
			openAccountDealLog.setNode(application.getCurrentNode());
			openAccountDealLog.setUserId(AuthUtil.getUserId());
			openAccountDealLog.setResult(FlowComment.REBACK.getRemark());
			openAccountDealLog.setStatus(Integer.valueOf(FlowComment.REBACK.getType()));
			openAccountDealLog.setReason(reason);
			openAccountDealLog.setDealTime(new Date());
			iAccountDealLogService.saveOrUpdate(openAccountDealLog);
			AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoMapper.queryByApplicationId(applicationId);

			CustomOpenInfoEntity customOpenInfoEntity = customOpenInfoService.selectCustomOpenInfo(accountOpenInfoVO.getUserId());
			customOpenInfoEntity.setStatus(status);
			customOpenInfoEntity.setUpdateTime(new Date());
			customOpenInfoService.updateById(customOpenInfoEntity);
		}
	}

	final ExecutorService executorService = Executors.newSingleThreadExecutor();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void passNode(String applicationId, String taskId, String reason) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", 1);
		R r = flowClient.completeTask(taskId, reason, map);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		AccountOpenApplicationVO application = baseMapper.queryByApplicationId(applicationId);
		AccountOpenApplicationEntity entity = new AccountOpenApplicationEntity();
		entity.setId(application.getId());
		entity.setLastApprovalUser(AuthUtil.getUser().getNickName());
		entity.setLastApprovalTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setApprovalOpinion(reason);
		baseMapper.updateById(entity);
		AccountDealLogEntity openAccountDealLog = new AccountDealLogEntity();
		openAccountDealLog.setApplicationId(applicationId);
		openAccountDealLog.setNode(application.getCurrentNode());
		openAccountDealLog.setUserId(AuthUtil.getUserId());
		openAccountDealLog.setResult(FlowComment.NORMAL.getRemark());
		openAccountDealLog.setStatus(Integer.valueOf(FlowComment.NORMAL.getType()));
		openAccountDealLog.setReason(reason);
		openAccountDealLog.setDealTime(new Date());
		iAccountDealLogService.saveOrUpdate(openAccountDealLog);
		executorService.submit(() -> {
			try {
				AccountOpenInfoVO customerAccountOpenInfo = customerAccountOpenInfoService().queryByApplicationId(application.getApplicationId());
				customerAccountOpenInfoService().generatePdf(application.getApplicationId());
				customerAccountOpenInfoService().generateW8Pdf(application.getApplicationId());
				customerAccountOpenInfoService().generateSelfPdf(application.getApplicationId());
				//application.getApplicationStatus()
				if (customerAccountOpenInfo.getIsOpenUsaStockMarket() == 1) {
					//customerAccountOpenInfoService().generateW8Pdf(application.getApplicationId());
				}
				if (customerAccountOpenInfo.getFundAccountType() == 2) {
					customerAccountOpenInfoService().generateMarginPdf(application.getApplicationId());
				}
			} catch (Exception e) {
				log.error("归档失败", e);
			}
		});
	}

	/**
	 * 禁止/允许开户
	 *
	 * @param applicationId
	 * @param blacklist
	 * @param reason
	 * @return
	 */
	@Override
	public Boolean updateBlackListStatus(String applicationId, Integer blacklist, String reason) {
		AccountOpenApplicationVO application = baseMapper.queryByApplicationId(applicationId);
		if (application != null) {
			switch (blacklist) {
				case 0: {
					flowClient.taskComment(application.getTaskId(), FlowComment.ALLOW.getType(), reason);
					int rows = baseMapper.updateBlackListStatus(applicationId, blacklist, reason);
					if (rows > 0) {
						log.info("-->流程记录:{},允许开户(撤销禁止开户)操作成功!", applicationId);
					}
					break;
				}
				case 1: {
					flowClient.taskComment(application.getTaskId(), FlowComment.FORBID.getType(), reason);
					//清除任务处理人字段assign_drafter的值
					flowClient.taskUnclaim(application.getTaskId());
					int rows = baseMapper.updateBlackListStatus(applicationId, blacklist, reason);
					if (rows > 0) {
						AccountOpenInfoVO accountOpenInfo = accountOpenInfoMapper.queryByApplicationId(applicationId);
						if (accountOpenInfo != null) {
							//发送短信通知
							String name = accountOpenInfo.getClientName();
							if (StringUtils.isEmpty(name)) {
								name = accountOpenInfo.getFamilyName() + accountOpenInfo.getGivenName();
							}
							SmsUtil.builder()
								.param(name)
								.templateCode(SmsTemplate.ACCOUNT_OPENING_PROHIBITED.getCode())
								.phoneNumber(accountOpenInfo.getPhoneNumber())
								.areaCode(accountOpenInfo.getPhoneArea())
								.sendAsync();

						} else {
							log.error(String.format("发送短信失败,客户开户申请记录ID:%s,开户信息为空!", applicationId));
						}
					}
					break;
				}
				default: {
					throw new ServiceException(String.format("操作失败,参数错误:blacklist只能是1或0,当前为:%s", blacklist));
				}
			}
			return true;
		} else {
			throw new ServiceException(String.format("操作失败,流程号:%s 申请记录不存在！", applicationId));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void rejectPreNode(String applicationId, String taskId, String reason) {
		R r = flowClient.taskReject(taskId, reason);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		AccountOpenApplicationVO application = baseMapper.queryByApplicationId(applicationId);
		application.setApprovalOpinion(reason);
		baseMapper.updateById(application);
		AccountDealLogEntity openAccountDealLog = new AccountDealLogEntity();
		openAccountDealLog.setApplicationId(applicationId);
		openAccountDealLog.setNode(application.getCurrentNode());
		openAccountDealLog.setUserId(AuthUtil.getUserId());
		openAccountDealLog.setResult(FlowComment.REJECT.getRemark());
		openAccountDealLog.setStatus(Integer.valueOf(FlowComment.REJECT.getType()));
		openAccountDealLog.setReason(reason);
		openAccountDealLog.setDealTime(new Date());
		iAccountDealLogService.saveOrUpdate(openAccountDealLog);
	}

	@Override
	public void resetAml(String applicationId) {
		AccountOpenApplicationVO application = baseMapper.queryByApplicationId(applicationId);
		application.setRefKey(UUID.randomUUID().toString());
		application.setStatus(1);
		baseMapper.updateById(application);
	}

	@Override
	public AccountOpenApplicationVO queryByApplicationId(String applicationId) {
		return baseMapper.queryByApplicationId(applicationId);
	}

	@Override
	public List<AccountOpenApplicationEntity> queryListByNode(String currentNode) {
		return baseMapper.queryListByNode(currentNode);
	}

	@Override
	public List<AccountOpenApplicationEntity> queryAmlChecking() {
		return baseMapper.queryAmlChecking();
	}

	@Override
	public void updateRefKeyByApplicationId(String applicationId, String refKey, String status) {
		baseMapper.updateRefKeyByApplicationId(applicationId, refKey, status);
	}

	@Override
	public void callBackApplication(OpenAccountCallbackBo bo) {
		CustomOpenInfoEntity customOpenInfo = customOpenInfoService.selectCustomOpenInfo(bo.getUserId());
		if (customOpenInfo != null) {
			customOpenInfo.setRemoteId(bo.getApplicationId());
			customOpenInfo.setIsPushed(true);
			customOpenInfo.setPendingType(CustomOpenAccountEnum.PendingStatusType.APPROVE.getCode());
			customOpenInfo.setUpdateTime(new Date());
			customOpenInfo.setStatus(bo.getStatus());
			customOpenInfoService.updateById(customOpenInfo);
		} else {
			log.error("查询不到开户申请记录");
		}
	}

	/**
	 * 转换VO属性
	 *
	 * @param applicationVO
	 */
	private void transferAccountOpenInfo(AccountOpenApplicationVO applicationVO) {
		if (applicationVO == null) {
			return;
		}

		if (applicationVO.getSex() != null) {
			switch (applicationVO.getSex()) {
				case 0:
					applicationVO.setSexName(SystemCommonEnum.SexTypeEnum.BOY.getName());
					break;
				case 1:
					applicationVO.setSexName(SystemCommonEnum.SexTypeEnum.GIRL.getName());
					break;
			}
		}

		if (applicationVO.getIdKind() != null) {
			switch (applicationVO.getIdKind()) {
				case 1:
					applicationVO.setIdKindName(SystemCommonEnum.IdKindType.ID.getDesc());
					break;
				case 2:
					applicationVO.setIdKindName(SystemCommonEnum.IdKindType.HKID.getDesc());
					break;
				case 3:
					applicationVO.setIdKindName(SystemCommonEnum.IdKindType.PASSPORT.getDesc());
					break;
				case 4:
					applicationVO.setIdKindName(SystemCommonEnum.IdKindType.DRIVER_LICENSE.getDesc());
					break;
				case 5:
					applicationVO.setIdKindName(SystemCommonEnum.IdKindType.HK_MACAO_PASS.getDesc());
					break;
				case 6:
					applicationVO.setIdKindName(SystemCommonEnum.IdKindType.NATIONAL_ID_CARD.getDesc());
					break;
				case 7:
					applicationVO.setIdKindName(SystemCommonEnum.IdKindType.HK_NON_PERMANENT_ID.getDesc());
					break;
			}
		}

		if (applicationVO.getOpenAccountAccessWay() != null) {
			switch (applicationVO.getOpenAccountAccessWay()) {
				case 1:
					applicationVO.setOpenAccountAccessWayName(OpenAccountEnum.OpenAccountAccessWay.H5.getMessage());
					break;
				case 2:
					applicationVO.setOpenAccountAccessWayName(OpenAccountEnum.OpenAccountAccessWay.APP.getMessage());
					break;
				case 3:
					applicationVO.setOpenAccountAccessWayName(OpenAccountEnum.OpenAccountAccessWay.OFFLINE.getMessage());
					break;
			}
		}
	}
}
