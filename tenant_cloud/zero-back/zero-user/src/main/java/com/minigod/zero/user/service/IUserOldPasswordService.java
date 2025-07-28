package com.minigod.zero.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.user.entity.UserOldPassword;

/**
 * 存量客户密码表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-20
 */
public interface IUserOldPasswordService extends IService<UserOldPassword> {


	/**
	 * 校验智珠存量用户登录密码，且通过后重新激活登录密码
	 * @param custId 客户ID UIN
	 * @param password 登录密码
	 */
	boolean checkOldLoginPwd(String custId, String password);

	/**
	 * 仅激活智珠存量用户登录密码
	 * @param userId 用户ID
	 */
	void activeLoginPwd(String userId);

}
