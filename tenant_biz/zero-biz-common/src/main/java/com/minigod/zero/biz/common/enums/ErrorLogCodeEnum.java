package com.minigod.zero.biz.common.enums;

public enum  ErrorLogCodeEnum {

	ZHITONG_IMPORTANT_SOURCE_ERROR(10000, "智通来源错误", "智通资讯要闻信息来源异常，请联系智通处理!"),
	;

	private int code;
	private String message;
	private String content;// 发送消息内容

	ErrorLogCodeEnum(int code, String message, String content) {
		this.code = code;
		this.message = message;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getContent() {
		return content;
	}
}
