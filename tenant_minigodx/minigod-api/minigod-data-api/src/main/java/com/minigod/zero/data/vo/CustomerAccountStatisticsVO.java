package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author eric
 * @desc 用户数转化率统计
 * @date 2024-10-28 13:40:05
 */
@Data
@ApiModel(value = "用户数转化率统计")
public class CustomerAccountStatisticsVO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 账户类型(0:个人户, 1:机构户)
	@ApiModelProperty(value = "账户类型")
	private String accountType;

	// 注册用户数(status=4)
	@ApiModelProperty(value = "注册用户数")
	private Integer registeredCount;

	// 开户用户数(status=5)
	@ApiModelProperty(value = "开户用户数")
	private Integer openedCount;

	// 入金用户数(status=0)
	@ApiModelProperty(value = "入金用户数")
	private Integer depositedCount;

	// 交易用户数(status=1)
	@ApiModelProperty(value = "交易用户数")
	private Integer tradingCount;

	// 开户转化率
	@ApiModelProperty(value = "开户转化率")
	private BigDecimal registerToOpenRate;

	// 入金转化率
	@ApiModelProperty(value = "入金转化率")
	private BigDecimal openToDepositRate;

	// 交易转化率
	@ApiModelProperty(value = "交易转化率")
	private BigDecimal tradingRate;

	// 总体转化率
	@ApiModelProperty(value = "总体转化率")
	private BigDecimal totalConversionRate;
}
