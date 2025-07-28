package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName TigerResponse.java
 * @Description TODO
 * @createTime 2025年02月18日 11:21:00
 */
@Data
public class TigerResponse implements Serializable {

    private String SUCCESS_CODE = "SUCCESS";

    private String errorCode;

    private Long serverTime;

    private String errorMessage;

    private Object data;

    public boolean isSuccess() {
        if (SUCCESS_CODE.equals(errorCode)) {
            return true;
        } else {
            return false;
        }
    }
}
