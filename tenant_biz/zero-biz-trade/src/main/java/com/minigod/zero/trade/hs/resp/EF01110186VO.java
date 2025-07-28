package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:yanghu.luo
 * @create: 2023-05-16 15:10
 * @Description: 获取孖展比率
 */
@Data
public class EF01110186VO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 股票代码
	 */
	@JSONField(name="stock_code")
	private String stockCode;

	/**
	 * 孖展比例
	 */
	@JSONField(name="margin_ratio")
	private BigDecimal marginRatio;

	/**
	 * 起始日期
	 */
	@JSONField(name="init_date")
	private Integer initDate;

	/**
	 * 截止日期
	 */
	@JSONField(name="end_date")
	private Integer endDate;
}
