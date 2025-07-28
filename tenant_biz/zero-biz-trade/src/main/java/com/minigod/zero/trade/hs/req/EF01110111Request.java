package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class EF01110111Request extends GrmRequestVO implements Serializable {

    private String fundAccount;

    private String clientId;

    private String exchangeType;

    private String stockCode;

    private String requestNum;

	/**
	 * 查询模式（0 明细 1 汇总）
	 */
    private String queryMode;
    private String entrustNo;
}
