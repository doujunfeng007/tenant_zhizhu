package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户资产流水汇总表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_stock_asset_records")
@ApiModel(value = "CustomerStockAssetRecords对象", description = "客户资产流水汇总表")
@EqualsAndHashCode(callSuper = true)
public class CustomerStockAssetRecordsEntity extends BaseEntity {
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
     * 证券名称
     */
    @ApiModelProperty(value = "证券名称")
    private String stockName;
    /**
     * 币种代码[0-人名币 1-美元 2-港币]
     */
    @ApiModelProperty(value = "币种代码[0-人名币 1-美元 2-港币]")
    private String moneyType;
    /**
     * 期初数量
     */
    @ApiModelProperty(value = "期初数量")
    private Long beginAmount;
    /**
     * 当前数量
     */
    @ApiModelProperty(value = "当前数量")
    private Long currentAmount;
    /**
     * 冻结数量
     */
    @ApiModelProperty(value = "冻结数量")
    private Long frozenAmount;
    /**
     * 解冻数量
     */
    @ApiModelProperty(value = "解冻数量")
    private Long unfrozenAmount;
    /**
     * 买入均价
     */
    @ApiModelProperty(value = "买入均价")
    private BigDecimal costPrice;
    /**
     * 证券市值
     */
    @ApiModelProperty(value = "证券市值")
    private BigDecimal stockMktValue;
    /**
     * 参考盈亏
     */
    @ApiModelProperty(value = "参考盈亏")
    private BigDecimal referProfitCost;

}
