
package com.minigod.zero.flow.core.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.entity.TaskBo;
import com.minigod.zero.flow.core.entity.ZeroFlow;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class IFlowClientFallback implements IFlowClient {

	@Override
	public R<ZeroFlow> startProcessInstanceById(String processDefinitionId,  Map<String, Object> variables) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<ZeroFlow> startProcessInstanceByKey( String businessKey, Map<String, Object> variables) {
		return R.fail("远程调用失败");
	}

	@Override
	public R completeTask(String taskId, String comment, Map<String, Object> variables) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<Object> taskVariable(String taskId, String variableName) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<Map<String, Object>> taskVariables(String taskId) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<String> taskClaim(String taskId) {
		return R.fail("远程调用失败");
	}

	@Override
	public R taskUnclaim(String taskId) {
		return R.fail("远程调用失败");
	}

	@Override
	public R taskReturn(TaskBo taskBo) {
		return R.fail("远程调用失败");
	}

	@Override
	public R taskComment(String taskId, String type, String comment) {
		return R.fail("远程调用失败");
	}

	@Override
	public R taskReject(String taskId, String comment) {
		return R.fail("远程调用失败");
	}

	@Override
	public R taskFinish(String instanceId,String type, String comment) {
		return R.fail("远程调用失败");
	}

	@Override
	public R taskJump(String instanceId, String taskName,String type, String comment) {
		return R.fail("远程调用失败");
	}
}
