package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查询当日股票流水
 */
@Data
public class EF01110114VO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 发生日期
	 */
	@JSONField(name="init_date")
	private String initDate;
	/**
	 * 交易类别 K-香港，1-上海A，D-上海B，2-深圳A，H-深证B，P-美股，t-沪股通，v-深港通
	 */
	@JSONField(name="exchange_type")
	private String exchangeType;
	/**
	 * 业务标志
	 */
	@JSONField(name="business_flag")
	private String businessFlag;
	/**
	 * 业务名称
	 */
	@JSONField(name="business_name")
	private String businessName;

	/**
	 * 证券代码
	 */
	@JSONField(name="stock_code")
	private String stockCode;
	/**
	 * 发生数量
	 */
	@JSONField(name="occur_amount")
	private String occurAmount;
	/**
	 * 后证券额(当前数量)
	 */
	@JSONField(name="post_amount")
	private String postAmount;
	/**
	 * 证券名称
	 */
	private String stockName;

}
