package com.minigod.zero.biz.common.mkt.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StkTrdCale implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long caleId;
	/**
	 * 自然日期
	 */
	private Date normalDate;
	/**
	 * 地区
	 */
	private String regionCode;
	/**
	 * 当天是否交易日
	 */
	private Boolean isTradeDay;
	/**
	 * 上一个交易日
	 */
	private Date lastTrd;
	/**
	 * 下一个交易日
	 */
	private Date nextTrd;
	/**
	 * 是否本周最后一个交易日
	 */
	private Boolean isWeekEnd;
	/**
	 * 是否本月最后一个交易日
	 */
	private Boolean isMonthEnd;
	/**
	 * 是否本年最后一个交易日
	 */
	private Boolean isYearEnd;
	/**
	 * 上周最后一个交易日
	 */
	private Date lastWeekTrd;
	/**
	 * 上月最后一个交易日
	 */
	private Date lastMonthTrd;
	/**
	 * 上年最后一个交易日
	 */
	private Date lastYearTrd;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改人
	 */
	private Long updateUser;
	/**
	 * 外部系统时间
	 */
	private Date extTime;
	/**
	 * 备注
	 */
	private String remark;
}
