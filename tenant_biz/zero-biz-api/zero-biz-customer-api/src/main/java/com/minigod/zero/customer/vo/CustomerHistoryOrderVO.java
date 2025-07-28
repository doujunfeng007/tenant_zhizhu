package com.minigod.zero.customer.vo;

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
}
