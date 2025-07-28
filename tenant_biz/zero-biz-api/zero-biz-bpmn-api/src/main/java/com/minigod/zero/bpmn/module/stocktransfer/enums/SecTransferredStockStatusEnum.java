package com.minigod.zero.bpmn.module.stocktransfer.enums;

/**
 * 股票转入状态枚举
 * 对应业务字典yfml_stock_roll_status
 *
 * @author: eric
 * @since 2024-09-13 13:21:14
 */
public enum SecTransferredStockStatusEnum {
	// 状态 0 已保存 1待同步(已提交") 2已受理 3 已退回 4已完成
	SAVE(0, "已保存"),
	SYNC(1, "已提交"),
	ACCEPT(2, "已受理"),
	BACK(3, "已退回"),
	FINISH(4, "已完成");

	private final int status;
	private final String description;

	public int getStatus() {
		return status;
	}

	SecTransferredStockStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public static SecTransferredStockStatusEnum fromStatus(int status) {
		for (SecTransferredStockStatusEnum item : values()) {
			if (item.getStatus() == status) {
				return item;
			}
		}
		return null;
	}

	public String getDescription() {
		return description;
	}

	public static String getDescription(int status) {
		for (SecTransferredStockStatusEnum item : values()) {
			if (item.getStatus() == status) {
				return item.getDescription();
			}
		}
		return null;
	}
}
