package com.minigod.zero.trade.afe.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName LoginResponse.java
 * @Description 登录请求返回
 * @createTime 2024年04月17日 18:36:00
 */
@Data
public class LoginResponse {

	/**
	 * 最后一次登录时间
	 */
	@JSONField(name = "LAST_LOGIN_DATETIME")
	private String lastLoginDate;

	/**
	 * 密码过期时间
	 */
	@JSONField(name = "PASSWORD_EXPIRY_DATE")
	private String passwordExpiryDate;

	/**
	 * 密码修改时间
	 */
	@JSONField(name = "PASSWORD_CHANGE_DATETIME")
	private String passwordChangeDate;

	/**
	 * 最后一次登录失败时间
	 */
	@JSONField(name = "LAST_LOGIN_FAIL_DATETIME")
	private String lastLoginErrorDate;

	/**
	 * 登录状态 The value can be
	 * “NL”→Normal Login,
	 * “ST”→Session
	 * Timeout,
	 * “AD”→Access Denial,
	 * “KO”→Kick Out,
	 * “LO”→Logout
	 */
	@JSONField(name = "LOGIN_STATUS")
	private String loginStatus;

	/**
	 * The value can be null
	 * or any string.
	 */
	@JSONField(name = "INFO_PACKAGE_ID")
	private String infoPackageId;

	/**
	 * The value can be
	 * “Y”→Yes,
	 * “N”→No
	 */
	@JSONField(name = "BCAN_VALIDATED_FLAG")
	private String bcanValidatedFlag;

	/**
	 * fsec
	 */
	@JSONField(name = "FSEC")
	private String fsec;

	/**
	 */
	@JSONField(name = "SITE_ID")
	private Number siteId;

	/**
	 */
	@JSONField(name = "INFOBASE_PACKAGE_ID")
	private Number infobasePackageId;

	@JSONField(name = "INFOBASE_PARAM")
	private String infobaseParams;

	@JSONField(name = "SITE_NAME")
	private String siteName;

	/**
	 * 登录记录
	 */
	@JSONField(name = "LOGIN_RECORD")
	private LoginRecord loginRecord;

	@Data
	public static class LoginRecord {

		@JSONField(name = "LAST_LOGIN_FAIL_DATETIME")
		private String lastLoginErrorDate;

		@JSONField(name = "LAST_LOGIN_FAIL_CHANNEL")
		private String lastloginFailChannel;

		@JSONField(name = "LAST_LOGIN_DATETIME")
		private String lastLoginDate;

		@JSONField(name = "LAST_LOGIN_CHANNEL")
		private String lastLoginChannel;

		@JSONField(name = "LOGIN_STATUS")
		private String loginStatus;

	}
}
