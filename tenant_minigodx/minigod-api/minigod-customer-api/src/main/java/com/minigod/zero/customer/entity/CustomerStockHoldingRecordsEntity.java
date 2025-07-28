package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户股票持仓表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_stock_holding_records")
@ApiModel(value = "CustomerStockHoldingRecords对象", description = "客户股票持仓表")
@EqualsAndHashCode(callSuper = true)
public class CustomerStockHoldingRecordsEntity extends BaseEntity {
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
     * 产品代码
     */
    @ApiModelProperty(value = "产品代码")
    private String productCode;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;
    /**
     * 市场代码
     */
    @ApiModelProperty(value = "市场代码")
    private String marktCode;
    /**
     * 买入时间
     */
    @ApiModelProperty(value = "买入时间")
    private Date buyTime;
    /**
     * 买入数量
     */
    @ApiModelProperty(value = "买入数量")
    private Integer buyNum;
    /**
     * 买入价格
     */
    @ApiModelProperty(value = "买入价格")
    private BigDecimal buyPrice;

}
