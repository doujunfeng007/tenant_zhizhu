package com.minigod.zero.biz.common.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/12 17:34
 * @description：edda授权状态
 */
public enum EddaAuthStatus {
	AUDIT(0,"待审核"),
	AUDITED(1,"已审核"),
	AUTHORIZE(2,"授权中"),
	AUTHORIZE_FAIL(3,"授权失败"),
	AUTHORIZE_SUCCESS(4,"已授权"),
	REVOKE(5,"撤销授权");
	private Integer code;
	private String desc;


	EddaAuthStatus(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}
}
