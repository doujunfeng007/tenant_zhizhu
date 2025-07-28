package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询历史成交明细
 * 10150 服务_海外_取历史成交明细
 */

@Data
public class EF01110150VO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 委托编号
     */
    @JSONField(name="record_no")
    private String orderNo;
    @JSONField(name = "assetId")
    private String assetId;
    @JSONField(name = "stock_name")
    private String stockName;
    @JSONField(name = "stock_namegb")
    private String stockNameZhCN;
    @JSONField(name = "stock_namebig5")
    private String stockNameZhHK;
    @JSONField(name = "exchange_type")
    private String exchangeType;
    @JSONField(name = "stock_code")
    private String stockCode;
	private String orderStatus="8";

    /**
     * Bargain_id组成元素，流水号
     */
    @JSONField(name = "sequence_no")
    private String sequenceNo;

    /**
     * Bargain_id组成元素0代表原单，其他代表拆单。拆单生成方式为最小没有生成的序号。
     */
    @JSONField(name = "allocation_id")
    private String allocationId;

    /**
     * 成交日期
     */
    @JSONField(name = "init_date")
    private String businessDate;

    /**
     * 成交时间
     */
    @JSONField(name = "business_time")
    private String businessTime;

    /**
     * 成交量
     */
    @JSONField(name = "business_amount")
    private String qty;

    /**
     * 成交价格
     */
    @JSONField(name = "business_price")
    private String price;

    /**
     * 成交价格
     */
    @JSONField(name = "entrust_bs")
    private String bsFlag;

}
