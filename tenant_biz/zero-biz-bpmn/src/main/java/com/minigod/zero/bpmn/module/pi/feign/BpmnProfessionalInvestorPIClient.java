package com.minigod.zero.bpmn.module.pi.feign;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.minigod.zero.biz.common.utils.GlobalExecutorService;
import com.minigod.zero.bpm.feign.IBpmSecuritiesInfoClient;
import com.minigod.zero.bpmn.module.account.enums.PIApplyPdfEnum;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.feign.IBpmnProfessionalInvestorPIClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import com.minigod.zero.bpmn.module.pi.enums.ApplicationStatusEnum;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIApplicationMapper;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIApplicationService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIInfoService;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIInfoVO;
import com.minigod.zero.bpmn.module.withdraw.constant.RedisKeyConstants;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.LanguageType;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: IBpmnProfessionalInvestorPIClient
 * @Description: 专业投资者PI认证模块
 * @Author eric
 * @Date 2024-05-10
 * @Version 1.0
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "专业投资者PI认证流程【同步】节点操作模块", tags = "专业投资者PI认证流程【同步】节点操作接口")
public class BpmnProfessionalInvestorPIClient implements IBpmnProfessionalInvestorPIClient {

	private final IProfessionalInvestorPIApplicationService iProfessionalInvestorPIApplicationService;
	private final IProfessionalInvestorPIInfoService iProfessionalInvestorPIInfoService;
	private final ProfessionalInvestorPIApplicationMapper professionalInvestorPIApplicationMapper;
	private final AccountOpenInfoMapper accountOpenInfoMapper;
	@Resource
	private final IBpmSecuritiesInfoClient securitiesInfoClient;
	private final ICustomerInfoClient customerInfoClient;
	private final IFlowClient iFlowClient;
	@Resource
	private final RedisLockClient redisLockClient;

	/**
	 * 定时器任务回调该方法更改客户级别为PI
	 *
	 * @return 操作结果
	 */
	@Override
	@PutMapping(PI_FLOW_CALLBACK)
	public R piFlowCallBack() {
		List<ProfessionalInvestorPIApplicationEntity> applicationList = iProfessionalInvestorPIApplicationService.queryListByNode("同步");
		return processApplications(applicationList);
	}

	/**
	 * 处理流程
	 *
	 * @param applicationList
	 * @return
	 */
	private R processApplications(List<ProfessionalInvestorPIApplicationEntity> applicationList) {
		for (ProfessionalInvestorPIApplicationEntity entity : applicationList) {
			this.processApplication(entity);
			this.sendProcessResult(entity, true);
		}
		return R.success();
	}

	/**
	 * 处理流程
	 *
	 * @param entity
	 */
	private void processApplication(ProfessionalInvestorPIApplicationEntity entity) {
		String lockKey = String.format(RedisKeyConstants.LOCK_PROFESSIONAL_INVESTOR_PI_KEY_PREFIX, entity.getApplicationId());
		Boolean lockStatus = false;
		try {
			lockStatus = redisLockClient.tryLock(lockKey, LockType.FAIR, RedisKeyConstants.MS_WAIT_EXPIRETIME, RedisKeyConstants.MS_JOB_EXPIRETIME, TimeUnit.MILLISECONDS);
			if (lockStatus) {
				ProfessionalInvestorPIInfoVO investorPIInfoVO = iProfessionalInvestorPIInfoService.queryByApplicationId(entity.getApplicationId());
				if (investorPIInfoVO == null) {
					log.warn("-->当前流程记录中,没有流程ID(application_id)是【" + entity.getApplicationId() + "】的记录!");
					return;
				}
				log.info(String.format("-->PI认证处理【%s】数据同步任务获取锁成功!", entity.getApplicationId()));
				updateSecuritiesInfoClientLevel(entity, investorPIInfoVO);
				updateCustomerInfoClientLevel(entity, investorPIInfoVO);
				updateApplicationStatus(entity);
				log.info(String.format("-->PI认证处理【%s】数据同步任务执行完毕!", entity.getApplicationId()));
			} else {
				log.error(String.format("-->PI认证处理【%s】数据同步任务获取锁失败!", entity.getApplicationId()));
			}
		} catch (InterruptedException e) {
			log.error(String.format("-->PI认证处理【%s】数据同步任务异常:", entity.getApplicationId()), e);
			commentOnTaskFailure(entity.getTaskId(), "同步客户级别异常:" + e.getMessage());
		} finally {
			if (lockStatus) {
				redisLockClient.unLock(lockKey, LockType.FAIR);
				log.info(String.format("-->PI认证处理【%s】数据同步任务释放锁成功!", entity.getApplicationId()));
			}
		}
	}

	/**
	 * 状态处理完成后发送邮件通知客户和系统消息
	 *
	 * @param entity
	 */
	private void sendProcessResult(ProfessionalInvestorPIApplicationEntity entity, Boolean isApproved) {
		GlobalExecutorService.getExecutor().execute(() -> {
			try {
				ProfessionalInvestorPIInfoVO investorPIInfoVO = iProfessionalInvestorPIInfoService.queryByApplicationId(entity.getApplicationId());
				if (investorPIInfoVO == null) {
					log.warn("-->当前流程记录中,没有流程ID(application_id)是【" + entity.getApplicationId() + "】的记录!");
					return;
				}
				log.info(String.format("-->PI认证处理【%s】数据同步任务更新流程状态成功,准备发送邮件通知客户!", entity.getApplicationId()));
				iProfessionalInvestorPIApplicationService.sendEmail(entity.getApplicationId(), PIApplyPdfEnum.NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS, isApproved);
				log.info(String.format("-->PI认证处理【%s】数据同步任务发送邮件通知客户成功!", entity.getApplicationId()));
				log.info(String.format("-->PI认证处理【%s】数据同步任务准备发送系统消息,接收者(cust_ID):%s!", entity.getApplicationId(), investorPIInfoVO.getClientId()));
				PushUtil.builder()
					.msgGroup("P")
					.lang(LanguageType.ZH_HANS.getCode())
					.custId(investorPIInfoVO.getClientId())
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.templateCode(PushTemplate.PI_CERTIFICATION.getCode())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();
				log.info(String.format("-->PI认证处理【%s】数据同步任务发送系统消息成功!", entity.getApplicationId()));
			} catch (Exception exception) {
				log.error(String.format("-->PI认证处理【%s】数据同步任务异常,状态处理完成后发送邮件通知客户和系统消息异常,异常详情->", entity.getApplicationId()), exception);
			}
		});
	}

	/**
	 * 更新后台客户资料的客户级别
	 *
	 * @param entity
	 * @param investorPIInfoVO
	 */
	private void updateSecuritiesInfoClientLevel(ProfessionalInvestorPIApplicationEntity
													 entity, ProfessionalInvestorPIInfoVO investorPIInfoVO) {
		R securitiesInfoClientLevelUpdate = securitiesInfoClient.securitiesInfoClientLevelUpdate(4, investorPIInfoVO.getClientId());
		if (!securitiesInfoClientLevelUpdate.isSuccess()) {
			log.warn("-->流程(ApplicationID)编号:{},同步SecuritiesInfo客户级别为【4-PI】失败!", entity.getApplicationId());
			commentOnTaskFailure(entity.getTaskId(), "同步客户级别失败:" + securitiesInfoClientLevelUpdate.getMsg());
		} else {
			log.info("-->流程(ApplicationID)编号:{},同步SecuritiesInfo客户级别为【4-PI】成功!", entity.getApplicationId());
		}
	}

	/**
	 * 更新账号中心的客户级别
	 *
	 * @param entity
	 * @param investorPIInfoVO
	 */
	private void updateCustomerInfoClientLevel(ProfessionalInvestorPIApplicationEntity
												   entity, ProfessionalInvestorPIInfoVO investorPIInfoVO) {
		R customerInfoClientLevelUpdate = customerInfoClient.updatePiLevel(4, investorPIInfoVO.getClientId());
		if (!customerInfoClientLevelUpdate.isSuccess()) {
			log.warn("-->调用minigod-customer服务,更改流程(ApplicationID)编号:{},客户级别为【4-PI】失败,失败原因:{}", entity.getApplicationId(), customerInfoClientLevelUpdate.getMsg());
		}
	}

	/**
	 * 更新流程状态
	 *
	 * @param entity
	 */
	private void updateApplicationStatus(ProfessionalInvestorPIApplicationEntity entity) {
		LambdaUpdateWrapper<ProfessionalInvestorPIApplicationEntity> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, entity.getApplicationId())
			.eq(ProfessionalInvestorPIApplicationEntity::getTenantId, entity.getTenantId())
			.eq(ProfessionalInvestorPIApplicationEntity::getIsDeleted, 0);

		R completeTask = iFlowClient.completeTask(entity.getTaskId(), "同步客户级别成功!", new HashMap<>());
		if (completeTask.isSuccess()) {
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getStatus, ApplicationStatusEnum.APPLY_PASS.getStatus())
				.set(ProfessionalInvestorPIApplicationEntity::getRemark, ApplicationStatusEnum.APPLY_PASS.getDescription())
				.set(ProfessionalInvestorPIApplicationEntity::getUpdateTime, new Date());
			log.info("-->流程(ApplicationID)编号:{}同步节点定时任务调用链成功!", entity.getApplicationId());
		} else {
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getStatus, ApplicationStatusEnum.APPLY_ERROR.getStatus())
				.set(ProfessionalInvestorPIApplicationEntity::getRemark, ApplicationStatusEnum.APPLY_ERROR.getDescription())
				.set(ProfessionalInvestorPIApplicationEntity::getUpdateTime, new Date());
			log.error("-->流程(ApplicationID)编号:{},同步客户级别为【4-PI】completeTask任务提交失败,失败原因:{}", entity.getApplicationId(), completeTask.getMsg());
			commentOnTaskFailure(entity.getTaskId(), "同同步客户级别为【4-PI】completeTask任务提交失败：" + completeTask.getMsg());
		}

		int counts = professionalInvestorPIApplicationMapper.update(null, updateWrapper);
		if (counts <= 0) {
			log.error("-->更新流程信息ApplicationID:{},操作失败!", entity.getApplicationId());
		} else {
			log.info("-->更新流程信息ApplicationID:{},操作成功!", entity.getApplicationId());
		}
	}

	private void commentOnTaskFailure(String taskId, String message) {
		iFlowClient.taskComment(taskId, FlowComment.EXCEPTION.getType(), message);
	}
}
