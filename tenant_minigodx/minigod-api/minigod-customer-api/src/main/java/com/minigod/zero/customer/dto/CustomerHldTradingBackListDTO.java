package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.dto.CustomerFundTradingListDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/8 16:47
 * @Version: 1.0
 */
@Data
public class CustomerHldTradingBackListDTO {
	/**
	 * 主键id
	 */
	@ApiModelProperty(value="主键id")
	private String id;

	@ApiModelProperty(value="客户id")
	private String custId;

	/**
	 * 基金账号
	 */
	private String tradeAccount;

	/**
	 * 资金账号-基金子账号
	 */
	@ApiModelProperty(value = "资金账号")
	private String fundAccount;

	@ApiModelProperty(value="submitDate交易日期(提交日期)")
	private Date submitDate;

	@ApiModelProperty(value="证券Code代码")
	private String hldcode;

	@ApiModelProperty(value="type类型,1:买;2:卖;3:交换买;4:交换卖;")
	private Integer type;

	@ApiModelProperty(value="currency币种")
	private String currency;

	/**
	 * 申请金额
	 */
	@ApiModelProperty(value="amount申请金额")
	private BigDecimal amount;

	/**
	 * 成交数量
	 */
	@ApiModelProperty(value="businessAmount成交数量")
	private BigDecimal businessAmount;

	/**
	 * 成交金额
	 */
	@ApiModelProperty(value="businessBalance成交金额")
	private BigDecimal businessBalance;

	/**
	 * 成交价格
	 */
	@ApiModelProperty(value="businessPrice成交价格")
	private BigDecimal businessPrice;

	/**
	 * 状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;
	 */
	@ApiModelProperty(value="status状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;")
	private Integer status;

	/**
	 * 状态描述
	 */
	@ApiModelProperty(value="statusDesc状态描述")
	private String statusDesc;


	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="createTime创建时间")
	private Date createTime;
}
