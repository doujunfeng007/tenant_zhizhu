package com.minigod.zero.bpmn.module.pi.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIInfoEntity;
import com.minigod.zero.bpmn.module.pi.enums.ApplicationStatusEnum;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIApplicationMapper;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIInfoMapper;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.feign.IFlowClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.minigod.zero.flow.core.common.enums.FlowComment;

import java.util.Date;
import java.util.HashMap;

/**
 * 将事务方法抽取到单独的 Service 类中.
 * 在同一个类中直接调用带有 @Transactional 注解的方法时，事务不会生效。这是因为 Spring 的事务管理是通过 AOP 代理实现的，
 * 当在同一个类中调用方法时，调用的是原始对象的方法而不是代理对象的方法，导致事务切面无法被织入。
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProfessionalInvestorPIPassNodeService {
	// 注入需要的依赖
	private final ProfessionalInvestorPIApplicationMapper baseMapper;
	private final ProfessionalInvestorPIInfoMapper investorPIInfoMapper;
	private final IFlowClient flowClient;

	/**
	 * PI认证审批通过处理
	 *
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 */
	@Transactional(rollbackFor = Exception.class)
	public void doPassNode(String applicationId, String taskId, String reason) {
		// 认领任务
		R claimTask = flowClient.taskClaim(taskId);
		if (claimTask.isSuccess()) {
			baseMapper.addAssignDrafter(applicationId, AuthUtil.getUserId().toString());
			log.debug("PI认证成功申领:" + applicationId + "<br/>" + "任务ID:" + taskId);
		} else {
			log.error(String.format("PI认证流程:%s 认领任务失败,原因: %s", applicationId, claimTask.getMsg()));
		}
		// 完成任务
		R completeTask = flowClient.completeTask(taskId, reason, new HashMap<>());
		if (completeTask.isSuccess()) {
			//查询 PI认证申请记录表
			LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> queryApplicationWrapper = new LambdaQueryWrapper<>();
			queryApplicationWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, applicationId);
			ProfessionalInvestorPIApplicationEntity applicationEntity = baseMapper.selectOne(queryApplicationWrapper);

			// 查询 PI认证信息记录表
			LambdaQueryWrapper<ProfessionalInvestorPIInfoEntity> queryInfoWrapper = new LambdaQueryWrapper<>();
			queryInfoWrapper.eq(ProfessionalInvestorPIInfoEntity::getApplicationId, applicationId);
			ProfessionalInvestorPIInfoEntity infoEntity = investorPIInfoMapper.selectOne(queryInfoWrapper);

			if (ObjectUtil.isNotEmpty(applicationEntity) && ObjectUtil.isNotEmpty(infoEntity)) {
				// 更新 PI认证申请记录表
				LambdaUpdateWrapper<ProfessionalInvestorPIApplicationEntity> updateApplicationWrapper = new LambdaUpdateWrapper<>();
				updateApplicationWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, applicationId);
				// 获取当前审批状态,根据当前审批状态同步更新status字段
				ApplicationStatusEnum applicationStatusEnum = ApplicationStatusEnum.fromStatus(applicationEntity.getApplicationStatus());
				// 如果当前审批状态为结束状态,则更新状态为通过
				if (applicationStatusEnum != null && applicationStatusEnum.getStatus() == ApplicationStatusEnum.APPLY_END.getStatus()) {
					updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getApprovalOpinion, reason);
					updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getStatus, ApplicationStatusEnum.APPLY_SUCCESS.getStatus());
					updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getRemark, ApplicationStatusEnum.APPLY_SUCCESS.getDescription());
				} else {
					// 根据流程当前审批状态更新业务状态
					updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getApprovalOpinion, reason);
					updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getStatus, applicationStatusEnum.getStatus());
					updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getRemark, applicationStatusEnum.getDescription());
				}
				updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getLastApprovalTime, new Date());
				updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getUpdateTime, new Date());
				updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getUpdateUser, AuthUtil.getUserId());
				updateApplicationWrapper.set(ProfessionalInvestorPIApplicationEntity::getLastApprovalUser, AuthUtil.getUserName());

				int appRows = baseMapper.update(null, updateApplicationWrapper);

				// 更新PI信息表中的认定日期
				LambdaUpdateWrapper<ProfessionalInvestorPIInfoEntity> updateInfoWrapper = new LambdaUpdateWrapper<>();
				updateInfoWrapper.eq(ProfessionalInvestorPIInfoEntity::getApplicationId, applicationId);
				updateInfoWrapper.set(ProfessionalInvestorPIInfoEntity::getRecognitionDate, new Date());
				updateInfoWrapper.set(ProfessionalInvestorPIInfoEntity::getUpdateTime, new Date());
				updateInfoWrapper.set(ProfessionalInvestorPIInfoEntity::getUpdateUser, AuthUtil.getUserId());

				int infoRows = investorPIInfoMapper.update(null, updateInfoWrapper);
				if (appRows > 0 && infoRows > 0) {
					log.info("-->专业投资人PI申请记录表<审批通过>审批操作【更新成功】!");
				} else {
					log.error("-->专业投资人PI申请记录表<审批通过>审批操作【更新失败】!");
				}
			} else {
				log.warn("-->没有查询到流程ID:{}租户ID:{}的审批记录!", applicationId, AuthUtil.getTenantId());
			}
		} else {
			log.error("--><审批通过>提交异常,异常详情:" + completeTask.getMsg());
			throw new ServiceException("审批通过提交异常,异常详情:" + completeTask.getMsg());
		}
	}

	/**
	 * PI认证审批不通过处理
	 *
	 * @param applicationId
	 * @param instanceId
	 * @param reason
	 */
	@Transactional(rollbackFor = Exception.class)
	public void doUnPassNode(String applicationId, String instanceId, String reason) {
		LambdaQueryWrapper<ProfessionalInvestorPIApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, applicationId);
		ProfessionalInvestorPIApplicationEntity applicationEntity = baseMapper.selectOne(queryWrapper);
		if (ObjectUtil.isEmpty(applicationEntity)) {
			throw new ServiceException(String.format("申领专业投资人申请记录任务失败,未查询到申请记录,申请ID:%s", applicationId));
		}
		// 认领任务
		R claimTask = flowClient.taskClaim(applicationEntity.getTaskId());
		if (claimTask.isSuccess()) {
			baseMapper.addAssignDrafter(applicationId, AuthUtil.getUserId().toString());
			log.debug("PI认证成功申领:" + applicationId + "<br/>" + "任务ID:" + applicationEntity.getTaskId());
		} else {
			log.error(String.format("PI认证流程:%s 认领任务失败,原因: %s", applicationId, claimTask.getMsg()));
		}
		// 完成任务
		R r = flowClient.taskFinish(instanceId, FlowComment.REBACK.getType(), reason);
		if (r.isSuccess()) {
			LambdaUpdateWrapper<ProfessionalInvestorPIApplicationEntity> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(ProfessionalInvestorPIApplicationEntity::getApplicationId, applicationId);
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getStatus, ApplicationStatusEnum.APPLY_FAILED.getStatus());
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getRemark, ApplicationStatusEnum.APPLY_FAILED.getDescription());
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getApprovalOpinion, reason);
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getLastApprovalTime, new Date());
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getUpdateTime, new Date());
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getUpdateUser, AuthUtil.getUserId());
			updateWrapper.set(ProfessionalInvestorPIApplicationEntity::getLastApprovalUser, AuthUtil.getUserName());
			int rows = baseMapper.update(null, updateWrapper);
			if (rows > 0) {
				log.info("-->专业投资人PI申请记录表<审批不通过>审批操作【更新成功】!");
			} else {
				log.error("-->专业投资人PI申请记录表<审批不通过>审批操作【更新失败】!");
			}
		} else {
			throw new ServiceException("审批不通过提交异常,异常详情:" + r.getMsg());
		}
	}
}
