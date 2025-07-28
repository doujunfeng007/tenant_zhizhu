package com.minigod.zero.dbs.vo;

import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 账户余额请求实体
 * @author: chenyu
 * @date: 2021/04/21 10:11
 * @version: v1.0
 */
@ApiModel("账户余额请求实体")
@Data
public class AccountBalanceReponseVo extends DbsBaseRequestVO implements Serializable {
    private String accountNo;
    private String accountCcy;
    private BigDecimal clsLedgerBal;
    private BigDecimal clsAvailableBal;
    private String businessDate;

}
