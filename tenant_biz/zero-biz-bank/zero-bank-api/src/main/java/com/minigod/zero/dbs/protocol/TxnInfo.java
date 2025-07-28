package com.minigod.zero.dbs.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * todo
 *
 * @author chenyu
 * @title TxnInfo
 * @date 2023-04-13 1:18
 * @description
 */
@Data
public class TxnInfo implements Serializable {

    private static final long serialVersionUID = -6016338488121317135L;

    private String txnType;
    private String customerReference;
    private String txnRefId;
    private String txnDate;
    private String valueDt;
    private ReceivingParty receivingParty;
    private AmtDtls amtDtls;
    private SenderParty senderParty;
    private RmtInf rmtInf;

    @Data
    public class ReceivingParty {
        private String name;
        private String accountNo;
        private String virtualAccountNo;
        private String virtualAccountName;
        private String proxyType;
        private String proxyValue;
    }

    @Data
    public class AmtDtls {
        private String txnCcy;
        private String txnAmt;
    }

    @Data
    public class SenderParty {
        private String name;
        private String accountNo;
        private String senderBankId;
        private String senderBankCode;
        private String senderBranchCode;
    }

    @Data
    public class RmtInf {
        private String paymentDetails;
        private String purposeCode;
        private String addtlInf;

    }

}
