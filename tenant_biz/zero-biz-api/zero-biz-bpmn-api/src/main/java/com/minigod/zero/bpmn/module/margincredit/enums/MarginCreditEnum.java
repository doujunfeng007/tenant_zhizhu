/**
 * @program: sunline
 * @description: 货币兑换枚举
 * @author: LEN
 * @create: 2021-09-06 16:21
 **/
package com.minigod.zero.bpmn.module.margincredit.enums;

public class MarginCreditEnum {


	public enum MarginCreditApplicationStatus{
		SUBMIT(1, "提交"),
		PENDING(2, "初审"),
		REVIEW(3, "复审"),
		PASS(4, "已完成"),
		REJECT(5, "已退回"),
		ARCHIVE(6, "归档"),
		;
		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private MarginCreditApplicationStatus(int index, String value) {
			this.index = index;
			this.value = value;
		}
	}

	public enum MarginCreditAppStatus{
		SUBMIT(0, "已提交"),
		PENDING(1, "已受理"),
		PASS(2, "已成功"),
		REJECT(3, "已退回"),
		;
		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private MarginCreditAppStatus(int index, String value) {
			this.index = index;
			this.value = value;
		}
	}





}

