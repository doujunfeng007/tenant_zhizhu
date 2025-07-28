package com.minigod.zero.bpmn.module.account.enums;

/**
 * 机构开户审核结果
 *
 * @author eric
 * @since 2024-05-31 15:10:25
 */
public enum OrganizationOpenApproveEnum {
	/**
	 * 未提交开户资料
	 */
	NO_APPLY(-1, "未提交开户资料"),
	/**
	 * 待审核
	 */
	PENDING_AUDIT(0, "待审核"),
	/**
	 * 审核通过
	 */
	APPROVED(1, "审核通过"),
	/**
	 * 审核不通过
	 */
	REJECTED(2, "审核不通过");

	private final Integer code;
	private final String message;

	OrganizationOpenApproveEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static OrganizationOpenApproveEnum getCode(Integer code) {
		for (OrganizationOpenApproveEnum approveEnum : OrganizationOpenApproveEnum.values()) {
			if (approveEnum.getCode().equals(code)) {
				return approveEnum;
			}
		}
		return null;
	}
}
