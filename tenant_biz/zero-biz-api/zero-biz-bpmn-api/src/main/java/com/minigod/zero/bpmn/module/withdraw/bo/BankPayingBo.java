package com.minigod.zero.bpmn.module.withdraw.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公司付款银行信息业务对象 bank_paying
 *
 * @author chenyu
 * @date 2023-04-06
 */

@Data
@ApiModel("公司付款银行信息业务对象")
public class BankPayingBo  {
    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;

    /**
     * 付款银行代码
     */
    @ApiModelProperty(value = "付款银行代码")
    private String bankCode;

    /**
     * 付款银行名称
     */
    @ApiModelProperty(value = "付款银行名称")
    private String bankName;

    /**
     * 付款银行英文名称
     */
    @ApiModelProperty(value = "付款银行英文名称")
    private String bankEname;

    /**
     * 付款银行账户名称
     */
    @ApiModelProperty(value = "付款银行账户名称")
    private String bankAcctName;

    /**
     * 付款银行账户
     */
    @ApiModelProperty(value = "付款银行账户")
    private String bankAcct;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @ApiModelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
    private String ccy;

    /**
     * 银行账户类型
     */
    @ApiModelProperty(value = "银行账户类型")
    private String bankAcctType;

    /**
     * 服务类别[SEC-证券 FET-期货]
     */
    @ApiModelProperty(value = "服务类别[SEC-证券 FET-期货]")
    private String serviceType;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String glCode;

    /**
     * 柜台退款公司银行代码
     */
    @ApiModelProperty(value = "柜台退款公司银行代码")
    private String gtInBankCode;

    /**
     * 柜台退款付款银行账户
     */
    @ApiModelProperty(value = "柜台退款付款银行账户")
    private String gtInBankAcct;

    /**
     * 柜台退款手续费公司银行代码
     */
    @ApiModelProperty("柜台退款手续费公司银行代码")
    private String gtFeeInBankCode;

    /**
     * 柜台退款手续费付款银行账户
     */
    @ApiModelProperty("柜台退款手续费付款银行账户")
    private String gtFeeInBankAcct;

    /**
     * 柜台取款公司银行代码
     */
    @ApiModelProperty(value = "柜台取款公司银行代码")
    private String gtOutBankCode;

    /**
     * 柜台取款付款银行账户
     */
    @ApiModelProperty(value = "柜台取款付款银行账户")
    private String gtOutBankAcct;

    /**
     * 是否可用[0-否 1-是]
     */
    @ApiModelProperty(value = "是否可用[0-否 1-是]")
    private Integer active;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
}
