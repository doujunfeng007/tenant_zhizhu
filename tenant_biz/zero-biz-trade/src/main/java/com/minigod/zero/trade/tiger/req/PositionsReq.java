package com.minigod.zero.trade.tiger.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName PositionsReq.java
 * @Description TODO
 * @createTime 2025年02月19日 16:35:00
 */
@Data
public class PositionsReq extends  TigerBaseRequest{

    private String accountId;

    /**
     * 股票等证券所属的国家地区(US、HK、SG)
     */
    private String market;
}
