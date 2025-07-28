package com.minigod.zero.bpmn.module.exchange.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName CurrencyExchangeRateInfoExcel.java
 * @Description TODO
 * @createTime 2024年03月16日 17:05:00
 */
@Data
@ColumnWidth(16)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class CurrencyExchangeRateInfoExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 目标币种名称
	 */
	@ExcelProperty(value = "目标币种名称")
	private String buyCcyName;

	/**
	 * 原始币种名称
	 */
	@ExcelProperty(value = "原始币种名称")
	private String sellCcyName;

	/**
	 * 兑换汇率
	 */
	@ExcelProperty(value = "兑换汇率")
	private BigDecimal exchangeRate;

	/**
	 * 汇率日期
	 */
	@ExcelProperty(value = "汇率日期")
	private String initDate;

	/**
	 * 默认数据[0-否 1-是]
	 */
	@ExcelProperty(value = "默认数据[0-否 1-是]")
	private Integer defaultConfig;
}
