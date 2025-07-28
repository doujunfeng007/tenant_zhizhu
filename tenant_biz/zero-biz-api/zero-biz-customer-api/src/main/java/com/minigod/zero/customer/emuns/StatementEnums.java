package com.minigod.zero.customer.emuns;

import lombok.Getter;

public final class StatementEnums {
	/**
	 * 0未发送 1已发送 2发送失败
	 */
    @Getter
    public enum FileSendStatus {
        UN_SET(0, "未发送"),
        SEND_SUCCESS(1, "已发送"),
        SEND_FAIL(2, "发送失败"),
        SEND_ING(3, "发送中");


        private Integer code;
        private String value;

        private FileSendStatus(Integer code, String value) {
            this.code = code;
            this.value = value;
        }


    }


}
