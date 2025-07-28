package com.minigod.zero.bpmn.module.pi.enums;

/**
 * 审批状态
 */
public enum ApplicationStatusEnum {
	NO_APPLY(-1, "未申请"),
	PENDING(0, "提交"),
	PRELIMINARY_REVIEW(1, "初审中"),
	UNDER_REVIEW(2, "复审中"),
	APPLY_SUCCESS(3, "等待同步数据中"),
	APPLY_PASS(4, "通过"),
	APPLY_EXPIRED(5, "即将过期"),
	APPLY_FAILED(6, "不通过"),
	APPLY_REVOKE(7, "已撤销"),
	APPLY_ERROR(8, "操作失败"),
	APPLY_END(99,"结束");

	private final int status;
	private final String description;

	ApplicationStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * 根据键获取枚举实例
	 *
	 * @param status
	 * @return
	 */
	public static ApplicationStatusEnum fromStatus(int status) {
		for (ApplicationStatusEnum statusEnum : ApplicationStatusEnum.values()) {
			if (statusEnum.getStatus() == status) {
				return statusEnum;
			}
		}
		return null;
	}
}
