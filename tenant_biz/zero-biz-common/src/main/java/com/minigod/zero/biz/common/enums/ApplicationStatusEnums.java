package com.minigod.zero.biz.common.enums;

public class ApplicationStatusEnums {

	public enum NewsPushApplyStatus{
		PENDING(1, "待处理"),
		CHECKING(2, "待审核"),
		REJECT(3, "审不过"),
		PREPARE_PUSH(4, "待推送"),
		PUSH(5, "已推送"),
		RESCINDED(6, "已撤销"),

		;
		private Integer value;
		private String desc;

		public Integer getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		NewsPushApplyStatus(Integer value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}
}
