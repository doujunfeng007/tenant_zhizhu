package com.minigod.zero.trade.auth.vo.response;

import lombok.Data;

import java.io.Serializable;


@Data
public class ServerTypeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易柜台类型
     */
    private String serverType;
}
