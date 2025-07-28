
package com.minigod.zero.user.feign;


import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.entity.UserInfo;
import com.minigod.zero.user.entity.UserOauth;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME
)
public interface IUserClient {

	String USER_INFO = AppConstant.FEIGN_API_PREFIX  + "/user-info";
	String USER_INFO_BY_TYPE = AppConstant.FEIGN_API_PREFIX  + "/user-info-by-type";
	String USER_INFO_BY_ID = AppConstant.FEIGN_API_PREFIX  + "/user-info-by-id";
	String USER_INFO_BY_CUSTID = AppConstant.FEIGN_API_PREFIX  + "/user-info-by-custid";
	String USER_INFO_BY_ACCOUNT = AppConstant.FEIGN_API_PREFIX  + "/user-info-by-account";
	String USER_AUTH_INFO = AppConstant.FEIGN_API_PREFIX  + "/user-auth-info";
	String SAVE_USER = AppConstant.FEIGN_API_PREFIX  + "/save-user";
	String REMOVE_USER = AppConstant.FEIGN_API_PREFIX  + "/remove-user";
	String USER_INFO_BY_IDS = AppConstant.FEIGN_API_PREFIX  + "/user-info-by-ids";
	String USER_INFO_BY_USER_NAME = AppConstant.FEIGN_API_PREFIX  + "/user-info-by-name";
	String USER_INFO_BY_USER_REAL_NAME = AppConstant.FEIGN_API_PREFIX  + "/user-info-by-real-name";
	String CHECK_OLD_LOGIN_PWD = AppConstant.FEIGN_API_PREFIX  + "/check-old-login-pwd";
	String UPDATE_PWD_BY_CUSTID = AppConstant.FEIGN_API_PREFIX  + "/update-pwd-by-custid";
	String SELECT_USER_PHONE = AppConstant.FEIGN_API_PREFIX  + "/select_user_by_phone";
	String SELECT_USER_EMAIL = AppConstant.FEIGN_API_PREFIX  + "/select_user_by_email";

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	@GetMapping(USER_INFO_BY_ID)
	R<User> userInfoById(@RequestParam("userId") Long userId);

	/**
	 * 通过custId获取用户信息
	 *
	 * @param custId 用户关联的custId
	 * @return
	 */
	@GetMapping(USER_INFO_BY_CUSTID)
	R<User> userInfoByCustId(@RequestParam("custId") Long custId);


	/**
	 * 通过custId更新user密码
	 *
	 * @param custId 用户关联的custId
	 * @return
	 */
	@PostMapping(UPDATE_PWD_BY_CUSTID)
	R updatePwdByCustId(@RequestParam("custId") Long custId,@RequestParam("password") String password);


	/**
	 * 根据账号获取用户信息
	 *
	 * @param tenantId 租户id
	 * @param account  账号
	 * @return
	 */
	@GetMapping(USER_INFO_BY_ACCOUNT)
	R<User> userByAccount(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account  账号
	 * @param userType 用户平台
	 * @return
	 */
	@GetMapping(USER_INFO_BY_TYPE)
	R<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account, @RequestParam("userType") String userType);

	/**
	 * 获取第三方平台信息
	 *
	 * @param userOauth 第三方授权用户信息
	 * @return UserInfo
	 */
	@PostMapping(USER_AUTH_INFO)
	R<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth);

	/**
	 * 新建用户
	 *
	 * @param user 用户实体
	 * @return
	 */
	@PostMapping(SAVE_USER)
	R<Boolean> saveUser(@RequestBody User user);

	/**
	 * 删除用户
	 *
	 * @param tenantIds 租户id集合
	 * @return
	 */
	@PostMapping(REMOVE_USER)
	R<Boolean> removeUser(@RequestParam("tenantIds") String tenantIds);

	/**
	 * 获取用户信息
	 *
	 * @param userIds 用户id组
	 * @return
	 */
	@GetMapping(USER_INFO_BY_IDS)
	List<User> userInfoByIds(@RequestParam("userIds") List<Long> userIds);

	/**
	 * 获取用户信息
	 *
	 * @param name 用户昵称
	 * @return
	 */
	@GetMapping(USER_INFO_BY_USER_NAME)
	List<User> userInfoByName(@RequestParam("name") String name);

	/**
	 * 获取用户信息
	 *
	 * @param realName 真名
	 * @return
	 */
	@GetMapping(USER_INFO_BY_USER_REAL_NAME)
	List<User> userInfoByRealName(@RequestParam("name") String realName);

	/**
	 * 校验用户旧密码
	 * @param account 用户ID
	 * @param password 登录密码
	 * @return
	 */
	@PostMapping(CHECK_OLD_LOGIN_PWD)
	R<Boolean> checkOldLoginPwd(@RequestParam("account") String account, @RequestParam("password") String password);


	@GetMapping(SELECT_USER_PHONE)
	R<UserInfo> selectByPhone(@RequestParam("tenantId") String tenantId, @RequestParam("phone") String phone, @RequestParam("areaCode") String areaCode);


	@GetMapping(SELECT_USER_EMAIL)
	R<UserInfo> selectByEmail(@RequestParam("tenantId") String tenantId, @RequestParam("email") String email);
}
