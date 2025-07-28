package com.minigod.zero.bpmn.module.paycategory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.minigod.zero.bpmn.module.paycategory.enums.PayeeCategoryStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收款人类别管理表/线上支付记录表
 * @TableName payee_category
 */
@TableName(value ="payee_category")
@Data

public class PayeeCategoryEntity implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易流水号
     */
    @TableField(value = "sequence_no")
    private String sequenceNo;
    /**
     * 柜台交易订单流水号
     */
    @TableField(value = "order_sequence_no")
    private String orderSequenceNo;

    /**
     * 用户id
     */
    @TableField(value = "cust_id")
    private Long custId;

    /**
     * 理财账号
     */
    @TableField(value = "account_id")
    private String accountId;

    /**
     * 支付方式  1 现金  2活利得
     */
    @TableField(value = "pay_way")
    private Integer payWay;

    /**
     * 收款人类别 1本人  2非本人
     */
    @TableField(value = "payee_category")
    private Integer payeeCategory;

    /**
     * 币种代码
     */
    @TableField(value = "currency")
    private String currency;

    /**
     * 可用现金
     */
    @TableField(value = "cash")
	@JsonSerialize(nullsUsing = NullSerializer.class)
    private BigDecimal cash;

    /**
     * 商品名称 活利得名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 商品代码
     */
    @TableField(value = "product_code")
    private String productCode;

    /**
     * 卖出金额
     */
    @TableField(value = "selling_amount")
	@JsonSerialize(nullsUsing = NullSerializer.class)
    private BigDecimal sellingAmount;

    /**
     * 收款人名字
     */
    @TableField(value = "payee_name")
    private String payeeName;

    /**
     * 收款金额
     */
    @TableField(value = "receivable_amount")
	@JsonSerialize(nullsUsing = NullSerializer.class)
    private BigDecimal receivableAmount;

    /**
     * 订单状态 1未提交  2已提交 PayeeCategoryStatusEnum.OrderStatus
	 * {@link PayeeCategoryStatusEnum.OrderStatus}
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    /**
     * 支付状态 1未支付 2已支付
	 * 	 {@link PayeeCategoryStatusEnum.PayStatus}
     */
    @TableField(value = "pay_status")
    private Integer payStatus;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    private String payTime;

    /**
     * 订单备注
     */
    @TableField(value = "order_remark")
    private String orderRemark;

    /**
     *
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;

	/**
	 * 收款银行名称
	 */
	@ApiModelProperty(value = "收款银行名称")
	@TableField(value = "benefit_bank_name")
	private String benefitBankName;
	/**
	 * 收款账号
	 */
	@ApiModelProperty(value = "收款账号")
	@TableField(value = "benefit_no")
	private String benefitNo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
