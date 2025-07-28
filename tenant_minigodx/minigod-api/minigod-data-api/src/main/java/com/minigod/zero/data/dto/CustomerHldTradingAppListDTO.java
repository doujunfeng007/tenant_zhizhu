package com.minigod.zero.data.dto;

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
public class CustomerHldTradingAppListDTO {
	/**
	 * 主键id
	 */
	@ApiModelProperty(value="主键id")
	private String id;

	/**
	 * 交易日期(提交日期)
	 */
	@ApiModelProperty(value="submitDate交易日期(提交日期)")
	private Date submitDate;

	/**
	 * 清算日期
	 */
	@ApiModelProperty(value="settledDate清算日期")
	private Date settledDate;

	/**
	 * 确认日期
	 */
	@ApiModelProperty(value="confirmedDate确认日期")
	private Date confirmedDate;

	/**
	 * 拒绝日期
	 */
	@ApiModelProperty(value="rejectedDate拒绝日期")
	private Date rejectedDate;

	/**
	 * 取消日期
	 */
	@ApiModelProperty(value="canceledDate取消日期")
	private Date canceledDate;

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
	 * 基金代码
	 */
	@ApiModelProperty(value="债券代码")
	private String bondCode;

	/**
	 * 基金名称
	 */
	@ApiModelProperty(value="债券名称")
	private String bondName;

	/**
	 * 类型,1:买;2:卖;3:交换买;4:交换卖;
	 */
	@ApiModelProperty(value="type类型,1:买;2:卖;3:交换买;4:交换卖;")
	private Integer type;

	/**
	 * 申请金额
	 */
	@ApiModelProperty(value="amount申请金额")
	private BigDecimal amount;

	/**
	 * 成交数量
	 */
	@ApiModelProperty(value="businessAmount成交数量/实际成交份额")
	private BigDecimal businessAmount;

	/**
	 *
	 */
	@ApiModelProperty(value="时间")
	private Date time;
}
