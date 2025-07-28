package com.minigod.zero.user.controller;

import com.minigod.zero.user.entity.ProcessLeave;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.user.service.ILeaveService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.user.cache.UserCache;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@ApiIgnore
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/process/leave")
@AllArgsConstructor
public class LeaveController extends ZeroController {

	private final ILeaveService leaveService;

	/**
	 * 详情
	 *
	 * @param businessId 主键
	 */
	@GetMapping("detail")
	public R<ProcessLeave> detail(Long businessId) {
		ProcessLeave detail = leaveService.getById(businessId);
		detail.getFlow().setAssigneeName(UserCache.getUser(detail.getCreateUser()).getName());
		return R.data(detail);
	}

	/**
	 * 新增或修改
	 *
	 * @param leave 请假信息
	 */
	@PostMapping("start-process")
	public R startProcess(@RequestBody ProcessLeave leave) {
		return R.status(leaveService.startProcess(leave));
	}

}
