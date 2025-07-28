package com.minigod.zero.bpmn.module.fundTrans.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*@ClassName: ClientFundTransInfo
*@Description: ${description}
*@Author chenyu
*@Date 2024/12/11
*@Version 1.0
*
*/

/**
 * 资金调拨申请记录
 */
@Data
@ApiModel(description = "资金调拨申请记录")
public class ClientFundTransInfo implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String applicationId;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 客户账号
     */
    @ApiModelProperty(value = "客户账号")
    private String clientId;

    /**
     * 客户中文名
     */
    @ApiModelProperty(value = "客户中文名")
    private String clientName;

    /**
     * 客户英文名
     */
    @ApiModelProperty(value = "客户英文名")
    private String clientNameSpell;

    /**
     * 入金账号
     */
    @ApiModelProperty(value = "入金账号")
    private String depositAccount;
    @ApiModelProperty(value = "入金账号类型")
    private String depositBusinessType;

    /**
     * 出金账号
     */
    @ApiModelProperty(value = "出金账号")
    private String withdrawAccount;
    @ApiModelProperty(value = "出金账号类型")
    private String withdrawBusinessType;
    @ApiModelProperty(value = "币种")
    private String currency;

    /**
     * 申请金额
     */
    @ApiModelProperty(value = "申请金额")
    private BigDecimal amount;

    /**
     * 入金金额
     */
    @ApiModelProperty(value = "入金金额")
    private BigDecimal depositAmount;

    /**
     * 出金金额
     */
    @ApiModelProperty(value = "出金金额")
    private BigDecimal withdrawAmount;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 失败原因
     */
    @ApiModelProperty(value = "失败原因")
    private String failReason;

    private static final long serialVersionUID = 1L;


}
