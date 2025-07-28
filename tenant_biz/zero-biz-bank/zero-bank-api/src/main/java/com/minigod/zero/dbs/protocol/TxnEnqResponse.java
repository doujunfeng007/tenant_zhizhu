package com.minigod.zero.dbs.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Tse Response
 *
 * @author chenyu
 * @title TxnEnqResponse
 * @date 2023-09-12 10:09
 * @description
 */
@Data
public class TxnEnqResponse implements Serializable {

    private String enqType;
    private String enqStatus;
    private String customerReference;
    private String customerTxnId;
    private String msgRefId;
    private String txnRefId;
    private String bankReference;
    private String refId;
    private String txnType;
    private String txnStatus;
    private String txnRejectCode;
    private String txnStatusDescription;
    private String txnCcy;
    private String txnAmount;
    private String debitAccountCcy;
    private String txnSettlementAmt;
    private String txnSettlementDt;
    private List<ClientReference> clientReferences;
    private String fxContractRef1;
    private String fxAmountUtilized1;
    private String fxContractRef2;
    private String fxAmountUtilized2;
    private String chargeBearer;
    private String debitAccountForBankCharges;
    private String chargesCcy;
    private String chargesAmount;
    private String  enqRejectCode;
    private String  enqStatusDescription;

    List<SenderParty> senderParty;
    ReceivingParty receivingParty;

    public class ClientReference {
        private String clientReference;
    }

    public class SenderParty {
        private String name;
    }

    public class ReceivingParty {
        private String name;
        private String accountNo;
        private String bankCtryCode;
        private String swiftBic;
        private String bankName;
        List<BeneficiaryAddresse> beneficiaryAddresses;
    }

    public class BeneficiaryAddresse {
        private String address;
    }


}
