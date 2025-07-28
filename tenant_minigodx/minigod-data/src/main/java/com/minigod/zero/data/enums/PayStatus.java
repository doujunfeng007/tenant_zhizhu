package com.minigod.zero.data.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/26 14:26
 * @description：
 */
public enum PayStatus {
	ADVANCE_CHARGE(0,"预付款"),
	PAYMENT_SUCCESSFUL(1,"付款成功"),
	CANCEL_PAYMENT(2,"取消付款"),
	PARTIAL_PAYMENT(3,"部分付款");

	private Integer code;
	private String desc;

	PayStatus(Integer code, String desc){
		this.code = code;
		this.desc = desc;
	}
	public Integer getCode() {
		return code;
	}
	public static Integer getDesc(Integer index) {
		for (PayStatus c : PayStatus.values()) {
			if (c.getCode().equals(index)) {
				return c.getCode();
			}
		}
		return null;
	}
}
