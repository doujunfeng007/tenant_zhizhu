package com.minigod.zero.biz.common.constant;


/**
 * <code>QuotConstants</code>
 *
 * @author sunline
 * @date 2015-7-29 下午7:28:25
 * @version v1.0
 */

public class QuotConstants {

	//xiongpan 2015-09-17 分时增加集合竞价的分时点
	public static final String PUSH_TIMESHARING_START_TIME_AM_BID = "09:26:00";
	public static final String PUSH_TIMESHARING_START_TIME_AM_BID_ = "09:27:00";

	// 上午分时起始数据
	public static final String PUSH_TIMESHARING_START_TIME_AM = "09:30:00";
	// 上午分时结束时间
	public static final String PUSH_TIMESHARING_END_TIME_AM = "11:30:00";
	// 下午分时开始时间
	public static final String PUSH_TIMESHARING_START_TIME_PM = "13:00:00";
	// 下午分时结束时间
	public static final String PUSH_TIMESHARING_END_TIME_PM = "15:01:00";

	// 上午行情开始刷新时间
	public static final String PUSH_QUOT_FLESH_START_TIME_AM = "09:15:00";
	// 上午行情结束刷新时间
	public static final String PUSH_QUOT_FLESH_END_TIME_AM = PUSH_TIMESHARING_END_TIME_AM;
	// 下午行情开始刷新时间
	public static final String PUSH_QUOT_FLESH_START_TIME_PM = PUSH_TIMESHARING_START_TIME_PM;
	// 下午行情结束刷新时间
	public static final String PUSH_QUOT_FLESH_END_TIME_PM = PUSH_TIMESHARING_END_TIME_PM;


	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String QUOT_END_TIME = "15:00:00";
	public static final String OPEN_WORK_TIME = "09:10:00";

	//sunline 2016-04-01 增加香港市场节点

	// 上午分时起始数据
		public static final String HKEX_PUSH_TIMESHARING_START_TIME_AM = "09:30:00";
	// 上午分时结束时间
	public static final String HKEX_PUSH_TIMESHARING_END_TIME_AM = "12:00:00";
	// 下午分时开始时间
	public static final String HKEX_PUSH_TIMESHARING_START_TIME_PM = "13:00:00";
	// 下午分时结束时间
	public static final String HKEX_PUSH_TIMESHARING_END_TIME_PM = "16:01:00";

	// 上午行情开始刷新时间
	public static final String HKEX_PUSH_QUOT_FLESH_START_TIME_AM = "09:00:00";
	// 上午行情结束刷新时间
	public static final String HKEX_PUSH_QUOT_FLESH_END_TIME_AM = HKEX_PUSH_TIMESHARING_END_TIME_AM;
	// 下午行情开始刷新时间
	public static final String HKEX_PUSH_QUOT_FLESH_START_TIME_PM = HKEX_PUSH_TIMESHARING_START_TIME_PM;
	// 下午行情结束刷新时间
	public static final String HKEX_PUSH_QUOT_FLESH_END_TIME_PM = HKEX_PUSH_TIMESHARING_END_TIME_PM;

	public static final String HKEX_QUOT_END_TIME = "16:00:00";

	public static final String HKEX_OPEN_WORK_TIME = "09:00:00";

	//暗盘开始时间
	public static final String PM_START_WORK_TIME = "16:15:00";
	//暗盘结束时间
	public static final String PM_END_WORK_TIME = "18:30:00";

}
