package com.minigod.zero.bpmn.module.exchange.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 兑换审核记录表
 */
@Data
public class CurrencyExchangeApplicationExcel implements Serializable {
	@ExcelProperty("客户账号")
	private String tradeAccount;

	@ExcelProperty("资金账号")
	private String fundAccount;

	@ExcelProperty("客户姓名")
	private String clientName;

	@ExcelProperty("英文名")
	private String clientNameSpell;

	@ExcelProperty("证券手机号")
	private String phoneNumber;

	@ExcelProperty("客户级别")
	private Integer vipLevel;

	@ExcelProperty("兑换方式名称")
	private String exchangeTypeName;

	@ExcelProperty(value = "预约流水号")
	private String applicationId;

	@ExcelProperty(value = "申请时间")
	private Date startTime;

	@ExcelProperty(value = "兑出金额")
	private BigDecimal amountOut;

	@ExcelProperty(value = "申请兑换汇率")
	private BigDecimal exchangeRate;

	@ExcelProperty(value = "申请兑入金额")
	private BigDecimal amountIn;

	@ExcelProperty(value = "兑换方向名称")
	private String exchangeDirectionName;

	@ExcelProperty(value = "柜台处理状态名称")
	private String processingStatusName;

	@ExcelProperty(value = "申请状态名称")
	private String appStatusName;
}
