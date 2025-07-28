package com.minigod.zero.platform.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 17:49
 * @description：
 */
@Data
public class CustomerOrderVO {
	private Integer id;
	/**
     * 订单时间
	 */
	private String createTime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品编号
	 */
	private String productCode;
	/**
	 * 订单价格
	 */
	private BigDecimal price;
	/**
	 * 订单数量
	 */
	private Integer orderNum;
	/**
	 * 成交量
	 */
	private Integer turnover;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 确认书
	 */
	private String ur;

	/**
	 * 订单金额
	 */
	private BigDecimal amount;

	/**
	 * 已成交金额
	 */
	private BigDecimal transactionAmount;

	public CustomerOrderVO(JSONObject data){
		this. id = data.getInteger("orderId");
		this.createTime= data.getString("createTime");
		this.status= data.getString("status");
		this.productName= data.getString("fundName");
		this.productCode= data.getString("fundIsin");
		this.price= data.getBigDecimal("price");
		this.orderNum= data.getInteger("quantity");
		this.turnover= data.getInteger("transactionQuantity");
		this.currency = data.getString("currency");
		this.amount= data.getBigDecimal("amount");
		this.transactionAmount= data.getBigDecimal("transactionAmount");
	}
}
