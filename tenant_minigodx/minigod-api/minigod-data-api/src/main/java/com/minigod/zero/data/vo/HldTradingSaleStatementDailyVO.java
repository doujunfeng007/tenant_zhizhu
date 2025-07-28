package com.minigod.zero.data.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName: com.minigod.zero.customer.vo.HldStatementMonthDTO
 * @Description:  活利得日结单-活利得交易明细-活利得卖出确认单
 * @Author: linggr
 * @CreateDate: 2024/5/23 22:52
 * @Version: 1.0
 */
@Data
public class HldTradingSaleStatementDailyVO {

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
	private BigDecimal businessPrice;

	/**
	 * 应计利息
	 */
	private BigDecimal transactionGainLoss;


	/**
	 * 交收金额
	 */
	private BigDecimal amout;

	/**
	 * 总交收金额
	 */
	private BigDecimal totalSettlementAmount;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 订单类型  1:买;2:卖;3:交换买;4:交换卖;11 IPO 买入
	 */
	private Integer orderType;
	/**
	 * 下单日期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Timestamp submitDate;
	/**
	 * 汇率
	 */
	private BigDecimal rate;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
}
