package com.minigod.zero.dbs.bo.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AddtionalData
 * @Description:
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class AddtionalData implements Serializable {
    /**
     * Bill Number could be provided by the merchant or could be an indication for the mobile application to prompt the consumer to input a BillNumber. Example the Billnumber may be presented when the QR Code is used for bill payment
     * *Applicable for ID only
     */
    @JsonProperty("billNumber")
    private String billNumber;
    /**
     * MobileNumber could be provided by the merchant or could be an indication for the mobile application to prompt the cosumers to input their mobile number. Example the mobile number can be used for prepare top-up and bill payment
     * *Applicable for ID only
     */
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    /**
     * StoreLabel could be provided by the merchant or could be an indication for the mobile application to prompt the consumers to input a store label. Example the store label may be displayed to the consumer on the mobile application identifying a specific store.
     * *Applicable for ID only
     */
    @JsonProperty("storeLabel")
    private String storeLabel;
    /**
     * Loyalty number could be provided by the merchant or could be an indication for the mobile application to prompt the consumer to input their LoyaltyNumber
     * *Applicable for ID only
     */
    @JsonProperty("loyaltyNumber")
    private String loyaltyNumber;
    /**
     * Any value defined by the merchant or acquirer in order to identify the transaction needs. This value could be provided by the merchant or could be an indication for the mobile application to prompt the consumer to input Reference Label from the transaction. Example, Reference Label can be used for transaction logging or transaction receipt *Applicable for ID only
     */
    @JsonProperty("referenceLabel")
    private String referenceLabel;
    /**
     * Any value that identifies a specific consumer. This value could be provided by the merchant or could be an indication for the mobile
     */
    @JsonProperty("customerLabel")
    private String customerLabel;
}
