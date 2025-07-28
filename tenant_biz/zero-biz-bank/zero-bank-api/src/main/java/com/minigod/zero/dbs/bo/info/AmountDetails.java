package com.minigod.zero.dbs.bo.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AmountDetails
 * @Description:
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class AmountDetails implements Serializable {

    /**
     * Transaction settlement amount with cents as 2 decimal points. E.g. $1,000.99 will be formatted as ‘1000.99’.
     * No Decimal for IDR and VND amounts, e.g. 100000
     */
    @JsonProperty("txnAmt")
    private String txnAmt;

    /**
     * 3 characters ISO Currency code.
     * SG/HK/TW: ISO Alphabetic Currency Code (Example: SGD/HKD/TWD)
     * ID/VN: ISO Numeric Currency Code (Example: 360/704)
     */
    @JsonProperty("txnCcy")
    private String txnCcy;
}
