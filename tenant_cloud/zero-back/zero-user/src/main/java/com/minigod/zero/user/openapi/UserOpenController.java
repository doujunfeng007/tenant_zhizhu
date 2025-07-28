package com.minigod.zero.user.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.service.IUserService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/wt/user")
@Api(value = "用户管理", tags = "用户管理接口")
public class UserOpenController {

	@Resource
	private IUserService userService;

	/**
	 * 根据用户号组获取用户信息
	 */
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据用户号组获取用户信息", notes = "传入ids")
	@PostMapping("/user-info-by-ids")
	public R<List<User>> userInfoByIds(@RequestBody List<Long> userIds) {
		return R.data(userService.listByIds(userIds));
	}

	/**
	 * 根据昵称获取用户信息
	 */
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "根据用户昵称获取用户信息", notes = "传入name")
	@PostMapping("/user-info-by-name")
	public R<List<User>> userInfoByName(@RequestBody String name) {
		return R.data(userService.userInfoByName(name));
	}

	/**
	 * 根据真实姓名获取用户信息
	 */
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "根据用户真是姓名获取用户信息", notes = "传入realName")
	@PostMapping("/user-info-by-real-name")
	public R<List<User>> userInfoByRealName(@RequestBody String realName) {
		return R.data(userService.userInfoByRealName(realName));
	}
}
