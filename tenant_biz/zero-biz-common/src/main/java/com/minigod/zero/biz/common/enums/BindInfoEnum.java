package com.minigod.zero.biz.common.enums;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 11:26
 * @Description: 交易账号绑定状态
 */
public class BindInfoEnum {

	/**
	 * 交易账号绑定状态
	 */
	public enum BindAccEnum {
		/**
		 * 绑定成功
		 */
		BIND(0,"绑定成功"),
		/**
		 * 交易账号已存在，并且绑定了手机号，不能绑定
		 */
		BIND_PHONE(1,"该交易账号已被绑定，如是您本人账号，请直接使用该交易账号登录"),
		/**
		 * 交易账号已存在，未绑定手机号码，可以绑定，绑定后以交易账号数据为准
		 */
		BIND_NOT_PHONE(2,"该交易账号已被注册，若继续绑定，该交易账号用户数据将与当前账号的用户数据进行合并，是否继续？"),
		/**
		 * 交易账号不存在，可以绑定
		 */
		NOT_TRADE_ACCOUNT(3,"交易账号不存在");

		private final Integer code;
		private final String info;
		BindAccEnum(Integer code, String info) {
			this.code = code;
			this.info = info;
		}
		public Integer getCode() {
			return code;
		}
		public String getInfo() {
			return info;
		}
	}

	/**
	 * 手机号绑定状态
	 */
	public enum BindPhoneEnum {

		/**
		 * 该手机号未注册，绑定成功
		 */
		BIND(0,"绑定成功"),
		/**
		 * 手机号已注册但未绑定交易账号
		 */
		NOT_BIND_ACCT(1,"该手机号已被注册，若继续绑定，该手机号用户数据将与当前账号的用户数据进行合并，是否确认绑定？"),
		/**
		 * 该手机号已注册已绑定其他交易账号。
		 */
		BIND_ACCT(2,"该手机号已注册已绑定其他交易账号。"),
		/**
		 * 验证码错误
		 */
		CAPTCHA_ERROR(3,"验证码错误。"),
		/**
		 * 是当前用户的开户手机号
		 */
		OPEN_PHONE(4,"是当前用户的开户手机号"),
		/**
		 * 手机号已注册，不校验是否是开户手机号
		 */
		REGISTER(5,"手机号已注册，不校验是否是开户手机号");


		private final Integer code;
		private final String info;
		BindPhoneEnum(Integer code, String info) {
			this.code = code;
			this.info = info;
		}
		public Integer getCode() {
			return code;
		}
		public String getInfo() {
			return info;
		}
	}
}
