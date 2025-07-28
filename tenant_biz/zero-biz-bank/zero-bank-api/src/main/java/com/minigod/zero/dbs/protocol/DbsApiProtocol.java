package com.minigod.zero.dbs.protocol;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * todo
 *
 * @author chenyu
 * @title DbsApiProtocol
 * @date 2023-04-13 1:17
 * @description
 */
@Data
public class DbsApiProtocol {

    private Header header;
    private TxnInfo txnInfo;
    private AccountBalResponse accountBalResponse;
    private EnqInfo enqInfo ;
    private Error error;
    private EnqResponse enqResponse;
    private TxnResponse txnResponse;
    private List<TxnResponse> txnResponses;
    private TxnEnqResponse txnEnqResponse;

    @Data
    public class Error {
       private String status;
       private String code;
       private String description;
    }

    @Data
    public class EnqInfo{
        private String txnRefId;
        private String proxyType;
        private String proxyValue;
        private String respStatus;
        private String respStatusDescription;
        private String accountName;
        private String merchantProxy;
    }

    @Data
    public class EnqResponse {

        private String accountName;
        private String dispNameEn;
        private String dispNameZh;
        private String proxyType;
        private String proxyValue;
        private String respStatus;
        private String respStatusDescription;
        private String txnRefId;
    }

    @Data
    public class TxnResponse {
        private String customerReference;
        private String paymentReference;
        private String txnRefId;
        private String bankReference;
        private String txnType;
        private String txnStatus;
        private String txnRejectCode;
        private String txnStatusDescription;
        private BigDecimal txnSettlementAmt;
        private Date txnSettlementDt;

        private String responseType;
        private String msgRefId;

    }

}
