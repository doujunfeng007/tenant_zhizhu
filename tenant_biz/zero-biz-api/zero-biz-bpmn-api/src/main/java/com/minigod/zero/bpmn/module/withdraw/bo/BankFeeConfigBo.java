package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 取款手续费业务对象 bank_fee_config
 *
 * @author chenyu
 * @date 2023-04-06
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("取款手续费业务对象")
public class BankFeeConfigBo extends BaseEntity {
    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    @NotNull(message = "流水号不能为空", groups = { EditGroup.class})
    private Long id;

    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String bankCode;

    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String bankName;

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票 5-FPSID]
     */
    @ApiModelProperty(value = "取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票 5-FPSID]")
    @NotNull(message = "取款方式不能为空", groups = { AddGroup.class})
    private Integer transferType;

    /**
     * 转账方式名称
     */
    @ApiModelProperty(value = "转账方式名称")
    private String transferTypeDesc;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @ApiModelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
    @NotBlank(message = "币种代码不能为空", groups = { AddGroup.class})
    private String ccy;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal chargeFee;

    /**
     * 是否可用[0-否 1-是]
     */
    @ApiModelProperty(value = "是否可用[0-否 1-是]")
    private Integer active;

}
