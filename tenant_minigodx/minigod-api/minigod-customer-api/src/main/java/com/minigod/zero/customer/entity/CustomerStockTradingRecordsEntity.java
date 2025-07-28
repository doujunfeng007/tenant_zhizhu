package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户交易流水汇总表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_stock_trading_records")
@ApiModel(value = "CustomerStockTransactionRecords对象", description = "客户交易流水汇总表")
@EqualsAndHashCode(callSuper = true)
public class CustomerStockTradingRecordsEntity extends BaseEntity {
	/**
	 * 租户id
	 */
	@ApiModelProperty(value = "租户id")
	private String tenantId;
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
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String fundAccount;
    /**
     * 交易日期
     */
    @ApiModelProperty(value = "交易日期")
    private LocalDate tradeDate;
    /**
     * 证券市场[SZ 深证，SH沪证，BZ 北证，K-港交所 P-美国市场]
     */
    @ApiModelProperty(value = "证券市场[SZ 深证，SH沪证，BZ 北证，K-港交所 P-美国市场]")
    private String exchangeType;
    /**
     * 证券代码
     */
    @ApiModelProperty(value = "证券代码")
    private String stockCode;
    /**
     * 交易类别[0B-买入 OS-卖出]
     */
    @ApiModelProperty(value = "交易类别[0B-买入 OS-卖出]")
    private String tradeKind;
    /**
     * 委托方式[7-网上委托 T-电话委托]
     */
    @ApiModelProperty(value = "委托方式[7-网上委托 T-电话委托]")
    private String entrustWay;
    /**
     * 币种代码[0-人名币 1-美元 2-港币]
     */
    @ApiModelProperty(value = "币种代码[0-人名币 1-美元 2-港币]")
    private String moneyType;
    /**
     * 成交数量
     */
    @ApiModelProperty(value = "成交数量")
    private Integer businessAmount;
    /**
     * 成交金额
     */
    @ApiModelProperty(value = "成交金额")
    private BigDecimal businessBalance;
    /**
     * 成交价格
     */
    @ApiModelProperty(value = "成交价格")
    private BigDecimal businessPrice;
    /**
     * 佣金
     */
    @ApiModelProperty(value = "佣金")
    private BigDecimal feeCount;
    /**
     * 净佣金
     */
    @ApiModelProperty(value = "净佣金")
    private BigDecimal profitFeeCount;
    /**
     * 其他费用
     */
    @ApiModelProperty(value = "其他费用")
    private BigDecimal otherFeeCount;
    /**
     * 资金发生数
     */
    @ApiModelProperty(value = "资金发生数")
    private BigDecimal occurBalance;
    /**
     * 交易流水号
     */
    @ApiModelProperty(value = "交易流水号")
    private Integer sequenceNo;

}
