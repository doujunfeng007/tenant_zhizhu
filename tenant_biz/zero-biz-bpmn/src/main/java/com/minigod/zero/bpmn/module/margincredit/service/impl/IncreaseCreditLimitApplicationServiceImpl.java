package com.minigod.zero.bpmn.module.margincredit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitApplicationEntity;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitEntity;
import com.minigod.zero.bpmn.module.margincredit.enums.MarginCreditEnum;
import com.minigod.zero.bpmn.module.margincredit.mapper.IncreaseCreditLimitApplicationMapper;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitApplicationService;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitService;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitApplicationVO;
import com.minigod.zero.bpmn.module.margincredit.vo.req.IncreaseCreditQuery;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.flow.core.feign.IFlowClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName IncreaseCreditLimitApplicationServiceImpl.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */
@Service
@Slf4j
@AllArgsConstructor
public class IncreaseCreditLimitApplicationServiceImpl extends BaseServiceImpl<IncreaseCreditLimitApplicationMapper, IncreaseCreditLimitApplicationEntity> implements IIncreaseCreditLimitApplicationService {

	@Autowired
	@Lazy
	private IIncreaseCreditLimitService increaseCreditLimitService;

	private final IFlowClient flowClient;



	@Override
	public IPage<IncreaseCreditLimitApplicationVO> queryPageList(IncreaseCreditQuery query, Query pageQuery) {
		query.setTenantId(AuthUtil.getTenantId());
		IPage<IncreaseCreditLimitApplicationVO> result = baseMapper.queryPageList(Condition.getPage(pageQuery), query);
		result.getRecords().stream().forEach(s -> {
			if (s.getAssignDrafter() == null) {
				s.setDealPermissions(1);
			} else {
				if (s.getAssignDrafter().equals(String.valueOf(query.getUserId()))) {
					s.setDealPermissions(1);
				} else {
					s.setDealPermissions(0);
				}
			}
		});
		return result;
	}

	@Override
	public String batchClaim(Integer applicationStatus, List<String> applicationIds) {
		StringBuilder stringBuilder = new StringBuilder("您申领的流程号:" + applicationIds + "<br/>");
		List<String> failClaim = new ArrayList();
		List<String> notFindClaim = new ArrayList();
		List<String> suceessClaim = new ArrayList<>();
		LambdaQueryWrapper<IncreaseCreditLimitApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(IncreaseCreditLimitApplicationEntity::getApplicationId, applicationIds);
		queryWrapper.eq(ObjectUtil.isNotEmpty(applicationStatus), IncreaseCreditLimitApplicationEntity::getApplicationStatus, applicationStatus);
		List<IncreaseCreditLimitApplicationEntity> applications = baseMapper.selectList(queryWrapper);
		if (applications.size() == 0) {
			throw new ServiceException("所选任务已全被申领");
		}

		for (IncreaseCreditLimitApplicationEntity application : applications) {
			if (application.getAssignDrafter() == null) {
				try {
					R r = flowClient.taskClaim(application.getTaskId());
					if (r.isSuccess()) {
						baseMapper.addAssignDrafter(application.getApplicationId(), AuthUtil.getUserId().toString());
						suceessClaim.add(application.getApplicationId());
						log.info("成功认领:" + application.getApplicationId() + "<br/>" + "任务ID:" + application.getTaskId());
					} else {
						log.error(String.format("流程:%s 认领任务失败,原因: %s", application.getApplicationId(), r.getMsg()));
						failClaim.add(application.getApplicationId());
					}

				} catch (Exception e) {
					log.error(String.format("流程:%s 认领任务异常,原因: %s", application.getApplicationId(), e.getMessage()), e);
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

		LambdaQueryWrapper<IncreaseCreditLimitApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(IncreaseCreditLimitApplicationEntity::getApplicationId, applicationIds);
		List<IncreaseCreditLimitApplicationEntity> applications = baseMapper.selectList(queryWrapper);

		for (IncreaseCreditLimitApplicationEntity application : applications) {

			baseMapper.clearAssignDrafter(application.getApplicationId());
			R r = flowClient.taskUnclaim(application.getTaskId());
			if (!r.isSuccess()) {
				throw new ServiceException(r.getMsg());
			}
		}

	}

	@Override
//	@Transactional(rollbackFor = Exception.class)
	public void rejectNode(String applicationId, String taskId, String reason, Integer status) {

		IncreaseCreditLimitApplicationEntity application = baseMapper.queryByApplicationId(applicationId);
		R r = flowClient.taskFinish(application.getInstanceId(),null, reason);
		if (!r.isSuccess()) {
			throw new ServiceException("流程处理失败:" + r.getMsg());
		}
		application.setApprovalOpinion(reason);
		application.setApplicationStatus(MarginCreditEnum.MarginCreditApplicationStatus.REJECT.getIndex());
		baseMapper.updateById(application);
		IncreaseCreditLimitEntity increaseCreditLimit= increaseCreditLimitService.queryByApplicationId(applicationId);
		increaseCreditLimit.setStatus(MarginCreditEnum.MarginCreditAppStatus.REJECT.getIndex());
		increaseCreditLimit.setUpdateTime(new Date());
		increaseCreditLimitService.updateById(increaseCreditLimit);

	}

	@Override
	public void rejectPreNode(String applicationId, String taskId, String reason) {
		R r = flowClient.taskReject(taskId, reason);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		IncreaseCreditLimitApplicationEntity application = baseMapper.queryByApplicationId(applicationId);
		application.setApprovalOpinion(reason);
		baseMapper.updateById(application);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void passNode(String applicationId, String taskId, String reason) {

		Map<String, Object> map = new HashMap<>();
		map.put("status", 1);
		R r = flowClient.completeTask(taskId, reason, map);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		IncreaseCreditLimitApplicationEntity application = baseMapper.queryByApplicationId(applicationId);
		IncreaseCreditLimitApplicationEntity update = new IncreaseCreditLimitApplicationEntity();
		update.setId(application.getId());
		update.setLastApprovalUser(AuthUtil.getUser().getNickName());
		update.setLastApprovalTime(new Date());
		update.setUpdateTime(new Date());
		update.setApprovalOpinion(reason);
		baseMapper.updateById(update);

	}
}
