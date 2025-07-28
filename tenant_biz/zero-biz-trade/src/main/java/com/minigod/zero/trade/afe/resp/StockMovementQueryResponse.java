package com.minigod.zero.trade.afe.resp;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockMovementQueryResponse.java
 * @Description TODO
 * @createTime 2024年04月28日 10:51:00
 */
@Data
public class StockMovementQueryResponse {

    @JSONField(name = "STOCK_MOVEMENT")
    private List<StockMovement> stockMovementList;

    @Data
    public static class StockMovement {

        @JSONField(name = "REF_NO")
        private String refNo;

        @JSONField(name = "TRANSACTION_DATE")
        private String transactionDate;

        @JSONField(name = "VALUE_DATE")
        private String valueDate;

        @JSONField(name = "MOVEMENT_TYPE")
        private String movementType;

        @JSONField(name = "EXCHANGE")
        private String exchange;

        @JSONField(name = "INSTRUMENT_NO")
        private String instrumentNo;

        @JSONField(name = "INSTRUMENT_NAME")
        private String instrumentName;

        @JSONField(name = "QTY")
        private String qty;

        @JSONField(name = "CUSTODIAN_REF")
        private String custodianRef;

        @JSONField(name = "INTERNAL_REF")
        private String internalRef;

        @JSONField(name = "NARRATIVE")
        private String narrative;

        @JSONField(name = "REMARK")
        private String remark;

        @JSONField(name = "STATUS")
        private String status;

        @JSONField(name = "Msg_ID")
        private String msgId;

    }
}
