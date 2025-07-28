package com.minigod.zero.customer.api.constatns;

import com.minigod.zero.core.tool.api.IResultCode;

/**
 * @ClassName: com.minigod.zero.customer.api.constatns.ResCode
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/7 11:32
 * @Version: 1.0
 */
public enum ResCode implements IResultCode {
	PASSWORD_AGING_ERROR(9001,"尊敬的DigiFIN 财富管理用户/客户您好:您的账户登入密码已超过6个月未有更新，为了您的账户安全，请立即更改密码。更改账户登入密码方法: 我的->设置->账号与安全->修改登录密码。"),;




	final int code;
	final String message;


	private ResCode(final int code, final String message) {
		this.code = code;
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public int getCode() {
		return code;
	}
}
