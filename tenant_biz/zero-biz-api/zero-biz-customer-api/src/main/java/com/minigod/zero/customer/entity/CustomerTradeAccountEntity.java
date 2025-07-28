package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 客户交易账户表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_trade_account")
@ApiModel(value = "CustomerTradeAccount对象", description = "客户交易账户表")
public class CustomerTradeAccountEntity  {

	@JsonSerialize(
		using = ToStringSerializer.class
	)
	@ApiModelProperty("主键id")
	@TableId(
		value = "id",
		type = IdType.ASSIGN_ID
	)
	private Long id;

    @ApiModelProperty("理财账户id")
    private String accountId;

    @ApiModelProperty("交易账号(在柜台体现为资金账号)")
    private String tradeAccount;

    @ApiModelProperty("业务类型")
    private String businessType;

    @ApiModelProperty("账户类型")
    private String accountType;

    @ApiModelProperty("操作类型")
    private Integer operType;

    @ApiModelProperty("账户状态")
    private Integer accountStatus;

    @ApiModelProperty("关联柜台交易账号")
    private String reletionTradeAccount;

    @ApiModelProperty("是否是当前选中账号")
    private Integer isCurrent;

    @ApiModelProperty("柜台类型")
    private Integer counterType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

	@TableLogic(
		value = "0",
		delval = "1"
	)
	@ApiModelProperty("是否已删除")
	private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}
