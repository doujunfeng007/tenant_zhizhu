package com.minigod.zero.customer.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.vo.HldStatementMonthDTO
 * @Description:  活力德 活利得月结单交易明细
 * @Author: linggr
 * @CreateDate: 2024/5/23 22:52
 * @Version: 1.0
 */
@Data
public class HldStatementMonthVO {

	/**
	 * 证券名称
	 */
	private String hldName;
	/**
	 * 证券代码
	 */
	private String hldCode;
	/**
	 * 交易日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Timestamp transactionDate;
	/**
	 * 交易种类/交易編号   买入卖出
	 */
	private String type;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 买入份额
	 */
	private BigDecimal quantity;
	/**
	 * 面值
	 */
	private BigDecimal nominalValue;
	/**
	 * 票面金额
	 */
	private BigDecimal faceAmount;
	/**
	 * 成交价格
	 */
	private BigDecimal settledAmount;

	/**
	 * 结算金额
	 */
	private BigDecimal amout;

}
