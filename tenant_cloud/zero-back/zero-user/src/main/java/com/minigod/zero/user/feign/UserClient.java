
package com.minigod.zero.user.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.entity.UserInfo;
import com.minigod.zero.user.entity.UserOauth;
import com.minigod.zero.user.enums.SourceType;
import com.minigod.zero.user.service.IUserService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务Feign实现类
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

	private final IUserService service;

	@Override
	@GetMapping(USER_INFO_BY_ID)
	public R<User> userInfoById(Long userId) {
		return R.data(service.getById(userId));
	}

	@Override
	@GetMapping(USER_INFO_BY_CUSTID)
	public R<User> userInfoByCustId(Long custId) {
		return R.data(service.userInfoByCustId(custId));
	}

	@Override
	@PostMapping(UPDATE_PWD_BY_CUSTID)
	public R updatePwdByCustId(Long custId,String password) {
		service.updatePwdByCustId(custId,password);
		return R.success();
	}

	@Override
	@GetMapping(USER_INFO_BY_ACCOUNT)
	public R<User> userByAccount(String tenantId, String account) {
		return R.data(service.userByAccount(tenantId, account));
	}

	@Override
	@GetMapping(USER_INFO_BY_TYPE)
	public R<UserInfo> userInfo(String tenantId, String username, String userType) {
		return R.data(service.userInfo(tenantId, username, SourceType.of(userType)));
	}

	@Override
	@PostMapping(USER_AUTH_INFO)
	public R<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth) {
		return R.data(service.userInfo(userOauth));
	}

	@Override
	@PostMapping(SAVE_USER)
	public R<Boolean> saveUser(@RequestBody User user) {
		return R.data(service.submit(user));
	}

	@Override
	@PostMapping(REMOVE_USER)
	public R<Boolean> removeUser(String tenantIds) {
		return R.data(service.remove(Wrappers.<User>query().lambda().in(User::getTenantId, Func.toStrList(tenantIds))));
	}

	@Override
	@GetMapping(USER_INFO_BY_IDS)
	public List<User> userInfoByIds(List<Long> userIds) {
		return service.listByIds(userIds);
	}

	@Override
	@GetMapping(USER_INFO_BY_USER_NAME)
	public List<User> userInfoByName(String name) {
		return service.userInfoByName(name);
	}

	@Override
	@GetMapping(USER_INFO_BY_USER_REAL_NAME)
	public List<User> userInfoByRealName(String realName) {
		return service.userInfoByRealName(realName);
	}

	@Override
	@PostMapping(CHECK_OLD_LOGIN_PWD)
	public R<Boolean> checkOldLoginPwd(String account, String password) {
		return R.data(service.checkOldLoginPwd(account, password));
	}

	@Override
	public R<UserInfo> selectByPhone(String tenantId, String phone,String areaCode) {
		return R.data(service.selectUserByPhone(tenantId, phone, areaCode));
	}

	@Override
	public R<UserInfo> selectByEmail(String tenantId, String email) {
		return R.data(service.selectUserByEmail(tenantId, email));
	}
}
