package com.minigod.zero.dbs.bo.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: SenderParty
 * @Description: 付款方信息
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class SenderParty implements Serializable {
    /**
     * Name of the sender party.
     */
    @JsonProperty("name")
    private String name;

    /**
     * Account number of sender.
     */
    @JsonProperty("accountNo")
    private String accountNo;

    /**
     * Sender Bank BIC or Sender Bank Local Clearing Code. If both are provided sender Bank BIC will be used.
     * For ID QRIS, field value will be sender Bank Id / Issuer Id.
     */
    @JsonProperty("senderBankId")
    private String senderBankId;

    /**
     * Sender Bank Name / Issuer Name
     * Applicable for ID QRIS Only
     */
    @JsonProperty("senderBankName")
    private String senderBankName;

    /**
     * Sender Bank Local Clearing Code (applicable for HK only) * Currently not in used.
     */
    @JsonProperty("senderBankCode")
    private String senderBankCode;

    /**
     * Sender Branch Code (applicable for HK only) * Currently not in used.
     */
    @JsonProperty("senderBranchCode")
    private String senderBranchCode;
}
