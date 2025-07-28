package com.minigod.zero.platform.enums;

public enum CodeType {
	SUCESS(5000, "请求成功"), //
	PARAMETER_ERROR(5001, "参数错误"), //
	NONE_DATA(5002, "无满足条件的数据"), //
	CAPTCHA_ERROR(5003, "手机验证码发送失败"), //
	PUSH_ERROR(5004, "消息推送失败"), //
	PARAMMATCH_ERROR(5005, "请求的参数与消息配置表配置的参数不一致"), //
	REQUESTBUSY_ERROR(5006, "请求过于频繁"), //
	SAVEFAILED_ERROR(5007, "短信信息保存失败"), //
	CUST_NO_ONLINE_ANDROID_DEVICE(5008, "请求的用户没有在线的android设备"), //
	UNKNOWN_ERROR(5499, "未知错误");

	private int errorCode; // 错误码
	private String errorMessage; // 错误信息

	private CodeType(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	// 获取错误码
	public int getErrorCode() {
		return errorCode;
	}

	// 获取错误信息
	public String getErrorMessage() {
		return errorMessage;
	}

	public static String getErrorMessage(Integer errorCode) {
		for (CodeType codeType : CodeType.values()) {
			if (codeType.getErrorCode() == (errorCode)) {
				return codeType.errorMessage;
			}
		}
		return null;
	}
}
