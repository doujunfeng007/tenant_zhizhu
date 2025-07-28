
package com.minigod.zero.flow.engine.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.flow.engine.entity.FlowExecution;
import com.minigod.zero.flow.engine.service.FlowEngineService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.annotation.PreAuth;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.RoleConstant;
import org.springframework.web.bind.annotation.*;

/**
 * 流程状态控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@RequestMapping("follow")
@AllArgsConstructor
@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
public class FlowFollowController {

	private final FlowEngineService flowEngineService;

	/**
	 * 流程状态列表
	 */
	@GetMapping("list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页", notes = "传入notice")
	public R<IPage<FlowExecution>> list(Query query, @ApiParam(value = "流程实例id") String processInstanceId, @ApiParam(value = "流程key") String processDefinitionKey) {
		IPage<FlowExecution> pages = flowEngineService.selectFollowPage(Condition.getPage(query), processInstanceId, processDefinitionKey);
		return R.data(pages);
	}

	/**
	 * 删除流程实例
	 */
	@PostMapping("delete-process-instance")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "删除", notes = "传入主键集合")
	public R deleteProcessInstance(@ApiParam(value = "流程实例id") @RequestParam String processInstanceId, @ApiParam(value = "删除原因") @RequestParam String deleteReason) {
		boolean temp = flowEngineService.deleteProcessInstance(processInstanceId, deleteReason);
		return R.status(temp);
	}

}
