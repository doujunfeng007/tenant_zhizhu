package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * @author:yanghu.luo
 * @create: 2023-05-26 18:06
 * @Description: 历史委托
 */
@Data
public class HistoryOrdersVO extends BaseRequest {

	/**
	 * 资产ID
	 */
	private String assetId;

	/**
	 * 流水起始时间 起始日期和结束日期不能都为0，起始日期不能大于结束日期
	 */
	private String startDate;

	/**
	 * 流水结束时间起始日期和结束日期不能都为0，起始日期不能大于结束日期
	 */
	private String endDate;

	/**
	 * 时间范围 0-今天，1-一个月，3-三个月，7-一周
	 */
	private String timeFrame;

	/**
	 * 市场 ML A股，HK 香港交易所，US 美国市场
	 */
	private String market;

	/**
	 * 买卖方向[B-买入 S-卖出]
	 */
	private String bsFlag;
	/**
	 * I：竞价限价盘；A：竞价盘；L：限价盘；E：增强限价盘；S：特别限价盘；U:碎股盘
	 */
	private String orderType;

	/**
	 * 委托属性 默认：e，碎股盘：u
	 */
	private String entrustProp = "e";

	/**
	 * 'C0'待触发
	 * 'C1'已取消(撤单)
	 * 'C2'已失效
	 * 'C3'已触发
	 * '0' No Register(未报)
	 * '1' Wait to Register(待报)
	 * '2' Host Registered(已报)
	 * '3' Wait for Cancel(已报待撤)
	 * '4' Wait for Cancel Partially Matched
	 * '5' Partially Cancelled(部撤)
	 * '6' Cancelled(已撤)
	 * '7' Partially Filled(部成)
	 * '8' Filled(已成)
	 * '9' Host Reject(废单)
	 */
	private String entrustStatus;

	/**
	 * 页数，每次返回20条
	 */
	private String page;
}
