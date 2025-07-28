package com.minigod.zero.dbs.bo.icn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minigod.zero.dbs.bo.DbsTransactionInfo;
import com.minigod.zero.dbs.bo.info.*;
import lombok.Data;

/**
 * @ClassName: IcnDbsTransactionInfo
 * @Description: 汇款通知信息
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class IcnInfo extends DbsTransactionInfo {

    /**
     * This block contains details on the received amount.
     */
    @JsonProperty("amtDtls")
    private AmountDetails amtDtls;

    /**
     * From the request message. TW: customerReference to prefix with value “DICN” to receive ICN for CASA credit.
     * Mandatory for SG/HK/TW/ID. Not Applicable for VN.
     */
    @JsonProperty("customerReference")
    private String customerReference;

    /**
     * Transaction settlement amount with cents as 2 decimal points. E.g. $1,000.99 will be formatted as ‘1000.99’.
     * No Decimal for IDR and VND amounts, e.g. 100000
     */
    @JsonProperty("senderParty")
    private SenderParty senderParty;

    /**
     * Unique transaction reference by the bank
     */
    @JsonProperty("txnRefId")
    private String txnRefId;

    /**
     * This block contains the recipient details
     */
    @JsonProperty("receivingParty")
    private ReceivingParty receivingParty;


    /**
     * Payment type through which account was credit. See APPENDIX A: TRANSACTION CODE
     */
    @JsonProperty("txnType")
    private String txnType;

    /**
     * Date when transaction is received.
     * Format: YYYY-MM-DD
     * E.g. 2018-01-25
     */
    @JsonProperty("txnDate")
    private String txnDate;

    /**
     * Date when incoming credit transaction is received.
     * Format: YYYY-MM-DD
     * E.g. 2018-01-25
     */
    @JsonProperty("valueDt")
    private String valueDt;

    /**
     * This block contains the payment details. This block will be available only if one of the information is present for the payment type.
     */
    @JsonProperty("rmtInf")
    private RemittanceInformation rmtInf;

    /**
     * This block contains the additional payment details.
     * *Applicable for ID QRIS only
     */
    @JsonProperty("addtionlData")
    private AddtionalData addtionalData;
}
