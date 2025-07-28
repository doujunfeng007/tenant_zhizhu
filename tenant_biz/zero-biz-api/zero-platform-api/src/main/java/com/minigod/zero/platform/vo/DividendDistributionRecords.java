package com.minigod.zero.platform.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 20:08
 * @description：
 */
@Data
public class DividendDistributionRecords {
	private Integer Id;
	/**
     * 派息时间
	 */
	private String dividendTime;
	/**
	 * 派息状态
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
	 * 派息金额
	 */
	private BigDecimal amount;


	public DividendDistributionRecords(JSONObject data){
		this.Id = data.getInteger("id");
		this.dividendTime = data.getString("exDividendDate");
		this.status = data.getString("");
		this.productName = data.getString("fundName");
		this.productCode = data.getString("productId");
		this.currency = data.getString("currency");
		this.amount = data.getBigDecimal("amount");
	}
}
