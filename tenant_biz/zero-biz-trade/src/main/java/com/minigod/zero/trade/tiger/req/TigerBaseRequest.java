package com.minigod.zero.trade.tiger.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName TigerBaseRequest.java
 * @Description TODO
 * @createTime 2025年02月18日 16:23:00
 */
@Data
public class TigerBaseRequest {

    /**
     * 请求唯一标识 uuid
     */
    private String nonce;

    /**
     * 当前时间毫秒数
     */
    private String ts;

}
