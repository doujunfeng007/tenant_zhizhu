package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;

/**
 * 公司付款银行信息对象 bank_paying
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Data
@TableName("bank_paying")
public class BankPaying extends TenantEntity {

    private static final long serialVersionUID=1L;

    /**
     * 付款银行代码
     */
    private String bankCode;
    /**
     * 付款银行名称
     */
    private String bankName;
    /**
     * 付款银行英文名称
     */
    private String bankEname;
    /**
     * 付款银行账户名称
     */
    private String bankAcctName;
    /**
     * 付款银行账户
     */
    private String bankAcct;
    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    private String ccy;
    /**
     * 银行账户类型
     */
    private String bankAcctType;
    /**
     * 服务类别[SEC-证券 FET-期货]
     */
    private String serviceType;
    /**
     * 编码
     */
    private String glCode;
    /**
     * 柜台退款公司银行代码
     */
    private String gtInBankCode;
    /**
     * 柜台退款付款银行账户
     */
    private String gtInBankAcct;

    /**
     * 柜台退款手续费公司银行代码
     */
    private String gtFeeInBankCode;

    /**
     * 柜台退款手续费付款银行账户
     */
    private String gtFeeInBankAcct;

    /**
     * 柜台取款公司银行代码
     */
    private String gtOutBankCode;
    /**
     * 柜台取款付款银行账户
     */
    private String gtOutBankAcct;
    /**
     * 是否可用[0-否 1-是]
     */
    private Integer active;

}
