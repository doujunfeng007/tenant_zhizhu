package com.minigod.zero.biz.common.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/18 16:31
 * @description：
 */
public enum DepositReviewStatus {
	PENDING_PROCESSING(1,"待凭证处理"),
	PENDING_CHECK(2,"待核对"),
	PENDING_ENTRY(3,"待入账"),
	ENTRY_SUCCESS(4,"入账成功"),
	ENTRY_FAIL(5,"入账失败"),
	REFUSE(6,"拒绝"),
	CANCEL(7,"取消");

	private Integer code;
	private String desc;

	DepositReviewStatus(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc(){
		return desc;
	}

	public static String getDesc(Integer index) {
		for (DepositReviewStatus c : DepositReviewStatus.values()) {
			if (c.getCode().equals(index)) {
				return c.getDesc();
			}
		}
		return null;
	}

}
