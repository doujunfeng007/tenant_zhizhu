package com.minigod.zero.biz.common.constant;

public class IpoConstant {
	/**ipo垫资分布式锁key*/
	public static final String IPO_LOAN_DISTRIBUTED_LOCK = "IPO_LOAN_DISTRIBUTED_LOCK";

	/**
	 * 认购状态
	 * -1：处理中，0:已提交，1:已受理 2:已拒绝,3:待公布，4:已中签，5:未中签, 6：已撤销 7：认购失败
	 */
	public static class ApplyStatus{
		public static final String PROCESSING = "-1";
		public static final String COMMIT = "0";
		public static final String ACCCEPTED = "1";
		public static final String REJECTED  = "2";
		public static final String WAITTING = "3";
		public static final String LUCKY = "4";
		public static final String UNLUCKY = "5";
		public static final String CANCEL = "6";
		public static final String FAILED = "7";
	}

	/**
	 * 垫资记录
	 * 事项类型：0:现金认购，1:融资认购，
	 */
	public static class ApplyType{
		public static final String CASH = "0";
		public static final String FINANCING = "1";
		public static final String ZERO_PRINCIPAL = "2";
	}

	/**
	 * 垫资记录
	 * 事项类型：0:现金认购，1:融资认购，2：垫资回滚
	 */
	public static class EventType{
		public static final int CASH = 0;
		public static final int FINANCING = 1;
		public static final int BACK  = 2;
	}

}
