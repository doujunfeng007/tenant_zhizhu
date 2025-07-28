package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 取款手续费对象 bank_fee_config
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Data
@TableName("bank_fee_config")
public class BankFeeConfig extends BaseEntity {

    /**
     * 自增ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 收款银行代码
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED, fill = FieldFill.UPDATE)
    private String bankCode;
    /**
     * 收款银行名称
     */
    private String bankName;
    /**
     * 转账方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账]
     */
    private Integer transferType;
    /**
     * 转账方式名称
     */
    private String transferTypeDesc;
    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    private String ccy;
    /**
     * 手续费
     */
    private BigDecimal chargeFee;
    /**
     * 是否可用[0-否 1-是]
     */
    private Integer active;
    private String tenantId;

}
