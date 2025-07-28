package com.minigod.zero.dbs.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: DbsTransaction
 * @Description:
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class DbsTransaction<T extends DbsTransactionInfo> implements Serializable {
    @JsonProperty("header")
    private DbsHeader header;

    @JsonProperty("txnInfo")
    private T txnInfo;
}
