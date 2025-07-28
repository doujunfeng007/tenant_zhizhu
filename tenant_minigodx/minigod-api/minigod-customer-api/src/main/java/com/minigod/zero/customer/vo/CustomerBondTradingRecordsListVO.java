package com.minigod.zero.customer.vo;

import com.minigod.zero.core.mp.support.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Date;

/**
 * 客户债券交易流水vo
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerBondTradingRecordsListVO extends Query implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 证券代码
	 */
	@ApiModelProperty(value = "证券代码")
	private String bondCode;

	@ApiModelProperty(value = "交易开始日期")
	private Date tradeStartDate;

	@ApiModelProperty(value = "交易结束日期")
	private Date tradeEndDate;



}
