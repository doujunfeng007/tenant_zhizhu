package com.minigod.zero.biz.common.enums;

public enum EIpoFinanceQueueStatus {
	STS_1(1, "排队中"),
	STS_2(2, "已完成"),
	STS_3(3, "已撤回"),
	STS_4(4, "未抢到额度");
	public int value;
	public String description;

	EIpoFinanceQueueStatus(int value, String description) {
		this.value = value;
		this.description = description;
	}
}
