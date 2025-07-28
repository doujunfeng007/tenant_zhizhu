package com.minigod.zero.dbs.protocol;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 账户余额
 *
 * @author chenyu
 * @title AccountBalResponse
 * @date 2023-04-13 8:05
 * @description
 */
@Data
public class AccountBalResponse implements Serializable {

    private String accountCcy;
    private String accountName;
    private String accountNo;
    private String enqStatus;
    private BigDecimal clsLedgerBal;
    private BigDecimal clsAvailableBal;

    private List<AccountBalResponse> accountBalResponseDtl = new ArrayList<AccountBalResponse>();

}
