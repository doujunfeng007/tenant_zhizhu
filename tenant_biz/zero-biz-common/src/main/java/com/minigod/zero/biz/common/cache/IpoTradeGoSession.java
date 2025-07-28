package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpoTradeGoSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uid;
    private String session;
}
