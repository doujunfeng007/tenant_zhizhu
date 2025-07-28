package com.minigod.zero.dbs.bo.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: RemittanceInformation
 * @Description:
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class RemittanceInformation implements Serializable {
    /**
     * This shall contain the purpose code of the payment as selected by remitter *Applicable for HK only
     */
    @JsonProperty("purposeCode")
    private String purposeCode;
    /**
     * Details of payment. This will be made available if accompanying the original payment.
     */
    @JsonProperty("paymentDetails")
    private String paymentDetails;

    /**
     * Additional details. This will be made available if accompanying the original payment.
     */
    @JsonProperty("addtlInf")
    private String addtlInf;
}
