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
public class CustomerHistoryOrderVO {
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
     * 币种
	 */
	private String currency;
	/**
	 * 订单价格
	 */
	private BigDecimal price;
	/**
	 * 订单数量
	 */
	private Integer orderNum;
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;
	/**
     * 确认书地址
	 */
	private String url;

	private String buyType;

	public CustomerHistoryOrderVO(JSONObject data){
		this. id = data.getInteger("orderId");
		this.createTime = data.getString("settledDate");
		this.status = data.getString("status");
		this.productName = data.getString("fundName");
		this.productCode = data.getString("fundIsin");
		this.currency = data.getString("currency");
		this.price = data.getBigDecimal("transactionNav");
		this.orderNum = data.getInteger("quantity");
		this.orderAmount = data.getBigDecimal("amount");
		this.url = data.getString("");

		Integer type = data.getInteger("type");
		if (type == null){
			this.buyType = "未知";
		}else if(type == 1){
			this.buyType = "买入";
		}else if(type == 2){
			this.buyType = "卖出";
		}else if(type == 11){
			this.buyType = "IPO";
		}
	}


}
