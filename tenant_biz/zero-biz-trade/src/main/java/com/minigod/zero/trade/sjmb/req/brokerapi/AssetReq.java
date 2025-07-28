package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName AssetReq.java
 * @Description TODO
 * @createTime 2024年01月11日 17:31:00
 */

@Data
public class AssetReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资产账号
     */
    private String accountId;
}
