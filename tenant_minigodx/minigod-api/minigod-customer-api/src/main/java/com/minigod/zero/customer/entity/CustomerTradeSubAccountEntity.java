package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 交易子账号信息 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_trade_sub_account")
@ApiModel(value = "CustomerTradeSubAccount对象", description = "交易子账号信息")
public class CustomerTradeSubAccountEntity  {

	@JsonSerialize(
		using = ToStringSerializer.class
	)
	@ApiModelProperty("主键id")
	@TableId(
		value = "id",
		type = IdType.ASSIGN_ID
	)
	private Long id;

    @ApiModelProperty("交易账号表的主键")
    private Long tradeAccountId;

    @ApiModelProperty("理财账户id")
    private String accountId;

    @ApiModelProperty("交易账号")
    private String tradeAccount;

    @ApiModelProperty("交易子账号")
    private String subAccount;

    @ApiModelProperty("市场类型")
    private String marketType;

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

	@ApiModelProperty("是否是主账号")
	private Integer isMaster;

    private static final long serialVersionUID = 1L;
}
