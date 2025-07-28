
package com.minigod.zero.user.service.impl;

import com.minigod.zero.user.entity.ProcessLeave;
import com.minigod.zero.user.mapper.LeaveMapper;
import com.minigod.zero.user.service.ILeaveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.entity.ZeroFlow;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class LeaveServiceImpl extends BaseServiceImpl<LeaveMapper, ProcessLeave> implements ILeaveService {

	private final IFlowClient flowClient;

	@Override
	@Transactional(rollbackFor = Exception.class)
	// @GlobalTransactional
	public boolean startProcess(ProcessLeave leave) {
		String businessTable = FlowUtil.getBusinessTable(ProcessConstant.LEAVE_KEY);
		if (Func.isEmpty(leave.getId())) {
			// 保存leave
			leave.setApplyTime(DateUtil.now());
			save(leave);
			// 启动流程
			Kv variables = Kv.create()
				.set(ProcessConstant.TASK_VARIABLE_CREATE_USER, AuthUtil.getUserName())
				.set("taskUser", TaskUtil.getTaskUser(leave.getTaskUser()))
				.set("days", DateUtil.between(leave.getStartTime(), leave.getEndTime()).toDays());
			R<ZeroFlow> result = flowClient.startProcessInstanceById(leave.getProcessDefinitionId(), variables);
			if (result.isSuccess()) {
				log.debug("流程已启动,流程ID:" + result.getData().getProcessInstanceId());
				// 返回流程id写入leave
				leave.setProcessInstanceId(result.getData().getProcessInstanceId());
				updateById(leave);
			} else {
				throw new ServiceException("开启流程失败");
			}
		} else {

			updateById(leave);
		}
		return true;
	}

}
