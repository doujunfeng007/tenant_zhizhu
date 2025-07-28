package com.minigod.zero.data.enums;

import lombok.Getter;

/**
 * @ClassName: com.minigod.zero.customer.enums.Statement
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/31 15:03
 * @Version: 1.0
 */
public enum StatementEnum {
	DAY(1, "日结单"),
	MONTH(2, "月结单");

	int code;
	String info;

	StatementEnum(int code, String info) {
		this.code = code;
		this.info = info;
	}

	public int getCode() {
		return code;
	}
	public String getInfo() {
		return info;
	}



	@Getter
	public enum FileSendStatus {
		UN_SET(0, "未发送"),
		SEND_SUCCESS(1, "已发送"),
		SEND_FAIL(2, "发送失败");

		private Integer code;
		private String value;

		private FileSendStatus(Integer code, String value) {
			this.code = code;
			this.value = value;
		}


	}

	@Getter
	public enum StatementType {
		FUND(0, "基金结单"),
		STOCK(1, "股票结单");

		private Integer type;
		private String value;

		private StatementType(Integer type, String value) {
			this.type = type;
			this.value = value;
		}
	}
}
