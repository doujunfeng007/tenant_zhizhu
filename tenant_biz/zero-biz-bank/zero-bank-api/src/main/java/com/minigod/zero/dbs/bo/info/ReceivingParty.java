package com.minigod.zero.dbs.bo.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ReceivingParty
 * @Description: 收款方信息
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class ReceivingParty implements Serializable {

    /**
     * Recipient account number/virtual account number. Hyphens will be excluded.
     */
    @JsonProperty("accountNo")
    private String accountNo;

    /**
     * Recipient account name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Virtual account number (if available). * Currently not used.
     */
    @JsonProperty("virtualAccountNo")
    private String virtualAccountNo;

    /**
     * Virtual account name (if available). * Currently not used.
     */
    @JsonProperty("virtualAccountName")
    private String virtualAccountName;
    /**
     * Proxy type to which the payment was sent (applicable for HK only)
     */
    @JsonProperty("proxyType")
    private String proxyType;

    /**
     * Proxy value to which the payment was sent (applicable for HK only)
     */
    @JsonProperty("proxyValue")
    private String proxyValue;
}
