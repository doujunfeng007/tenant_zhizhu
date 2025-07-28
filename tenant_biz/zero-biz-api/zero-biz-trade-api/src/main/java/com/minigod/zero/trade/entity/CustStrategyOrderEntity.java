package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 条件单信息表 实体类
 *
 * @since 2023-05-18
 */
@Data
@TableName("cust_strategy_order")
@ApiModel(value = "CustStrategyOrder对象", description = "条件单信息表")
@EqualsAndHashCode(callSuper = true)
public class CustStrategyOrderEntity extends AppEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
	/**
	 * 资金帐号
	 */
	@ApiModelProperty(value = "资金帐号")
	private String capitalAccount;
	/**
	 * 资产ID
	 */
	@ApiModelProperty(value = "资产ID")
	private String assetId;
	/**
	 * 股票名称
	 */
	@ApiModelProperty(value = "股票名称")
	private String stockName;
    /**
     * 委托数量
     */
    @ApiModelProperty(value = "委托数量")
    private Integer entrustAmount;
    /**
     * 委托价格
     */
    @ApiModelProperty(value = "委托价格")
    private BigDecimal entrustPrice;

    @ApiModelProperty(value = "委托类型")
    private String entrustProp;
    /**
     * 买卖方向：1买入，2卖出
     */
    @ApiModelProperty(value = "买卖方向：B买入，S卖出")
    private String entrustBs;
    /**
	 * 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易）
	 * 盘前盘后market = US-美国市场 时有效，其他市场默认0
     */
    @ApiModelProperty(value = "交易阶段标识")
    private Integer sessionType;
    /**
     * 触发价格
     */
    @ApiModelProperty(value = "触发价格")
    private BigDecimal strategyPrice;
    /**
     * 1.上涨到触发价格触发,2.下跌到触发价格触发
     */
    @ApiModelProperty(value = "1.上涨到触发价格触发,2.下跌到触发价格触发")
    private Integer strategyAction;
    /**
     * 有效期 1-当日有效，2-撤单前有效
     */
    @ApiModelProperty(value = "有效期 1-当日有效，2-撤单前有效")
    private Integer deadlineDate;
	/**
	 * 过期时间
	 */
	@ApiModelProperty(value = "过期时间")
	private Date expirationTime;
	/**
	 * 是否允许盘前盘后触发：0否，1是
	 */
	@ApiModelProperty(value = "是否允许盘前盘后触发：0否，1是")
	private Integer discType;
	/**
	 * 订单状态：C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发,C4.已失败
	 */
	@ApiModelProperty(value = "订单状态：C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发,C4.已失败")
	private String entrustStatus;
    /**
     * 委托编号
     */
    @ApiModelProperty(value = "委托编号")
    private String entrustNo;
    /**
     * 委托时间
     */
    @ApiModelProperty(value = "委托时间")
    private Date entrustTime;
	/**
	 * 错误原因
	 */
	@ApiModelProperty(value = "错误原因")
	private String errorMessage;

	@ApiModelProperty(value = "市场")
	private String exchangeType;


}
