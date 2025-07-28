package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 取款手续费视图对象 bank_fee_config
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Data
@ApiModel("取款手续费视图对象")
@ExcelIgnoreUnannotated
public class BankFeeConfigVo {

    /**
     * 自增ID
     */
    @ExcelProperty(value = "自增ID")
    @ApiModelProperty("自增ID")
    private Long id;

    /**
     * 收款银行代码
     */
    @ExcelProperty(value = "收款银行代码")
    @ApiModelProperty("收款银行代码")
    private String bankCode;

    /**
     * 收款银行名称
     */
    @ExcelProperty(value = "收款银行名称")
    @ApiModelProperty("收款银行名称")
    private String bankName;

    /**
     * 转账方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账]
     */
    @ExcelProperty(value = "转账方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账]")
    @ApiModelProperty("转账方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账]")
    private Integer transferType;

    /**
     * 转账方式名称
     */
    @ExcelProperty(value = "转账方式名称")
    @ApiModelProperty("转账方式名称")
    private String transferTypeDesc;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @ExcelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
    @ApiModelProperty("币种代码[CNY-人民币 USD-美元 HKD-港币]")
    private String ccy;

    /**
     * 手续费
     */
    @ExcelProperty(value = "手续费")
    @ApiModelProperty("手续费")
    private BigDecimal chargeFee;

    /**
     * 是否可用[0-否 1-是]
     */
    @ExcelProperty(value = "是否可用[0-否 1-是]")
    @ApiModelProperty("是否可用[0-否 1-是]")
    private Integer active;

}
