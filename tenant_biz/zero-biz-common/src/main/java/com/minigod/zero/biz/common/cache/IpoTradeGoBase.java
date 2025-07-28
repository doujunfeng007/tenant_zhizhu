package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpoTradeGoBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private String assetId;
    private String response;
    private long updateMills;//更新时间
}
