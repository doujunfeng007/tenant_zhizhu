package com.minigod.zero.trade.vo.resp.options;

import java.io.Serializable;

import lombok.Data;

/**
 * @author chen
 * @ClassName InquiryVO.java
 * @Description 询价返回
 * @createTime 2024年08月27日 11:40:00
 */
@Data
public class InquiryVO implements Serializable {

    private static final long serialVersionUID = -1L;

	/**
	 * CALL  PUT
	 */
	private String optionsType;

	/**
	 * 期权code
	 */
    private String optionsCode;

    /**
     * 成交额 序号10
     */
    private String amount;

    /**
     * 买卖一档卖价【序号:4】
     */
    private String askPrice;

    /**
     * 买卖一档卖档数量【序号:5】
     */
    private String askVol;

	private String bidPrice;

    /**
     * 买卖一档买档数量【序号:3】
     */
    private String bidVol;

    /**
     * 涨跌额【序号:7】
     */
    private String change;

    /**
     * number 涨跌幅【序号:8】
     */
    private String changeRate;

    /**
     * 希腊值delta【序号:17】
     */
    private String delta;

    /**
     * 希腊值gamma【序号:18】
     */
    private String gamma;

    /**
     * 最高价【序号:12】
     */
    private String high;

    /**
     * 内在价值【序号:23】
     */
    private String intrinsicValue;

    /**
     * 隐含波动率【序号:16】
     */
    private String iv;

    /**
     * 最新价【序号:9】
     */
    private String last;

    /**
     * 最低价
     */
    private String low;

    /**
     * 未平仓数【序号:15】
     */
    private String position;

    /**
     * 昨收价【序号:14】
     */
    private String preClose;

    /**
     * 溢价率【序号:11】
     */
    private String premium;

    /**
     * rho
     */
    private String rho;

    /**
     * 希腊值theta【序号:20】
     */
    private String theta;

	/**
	 * 时间价值【序号:22】
	 */
	private String timeValue;

	/**
	 * 希腊值vega
	 */
	private String vega;

	/**
	 * 成交量【序号:9】
	 */
	private String volume;

}
