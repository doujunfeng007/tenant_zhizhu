package com.minigod.zero.biz.common;


import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

/**
 * @program: yff-mktmgr
 * @description: 公共参数
 * @author: BPM_support2
 * @create: 2021-12-22 16:23
 **/
public class CommonParams {

    private CommonParams() {

    }

    /**
     * 系统管理员账号
     */
    public static final String ADMIN_ACCOUNT = "admin";

    public static final Long ADMIN_ACCOUNT_ID = 1123598821738675201L;


    /**
     * 套餐周期单位
     */
    public static final String CYCLE_UNIT = "天";

    /**
     * 全部市场
     */
    public static final String ALL_MARKET = "all";

    /**
     * 大陆ip地址标识
     */
    public static final String MAINLAND_CODE = "CN";

    /**
     * 上海年度点击次数
     */
    public static final long SH_CLICK_COUNT = 300000L;

    /**
     * SZ_CAPPED顶格金额
     */
    public static final BigDecimal SZ_CAPPED_LIMIT_MONEY = new BigDecimal("5");


    /**
     * 导出最大限制
     */
    public static final long EXPORT_MAX_SIZE = 100000L;


    /**
     * 共有股票类型
     */
    public static final String COMMON_STOCK_TYPE = "020102";

    /**
     * 港股股票类型
     */
    public static final String[] HK_STOCK_TYPE ={"010104",COMMON_STOCK_TYPE,"020105","050100","060102","060103","060104","070102"};



    /**
     * 美股股票类型
     */
    public static final String[] US_STOCK_TYPE ={"010105",COMMON_STOCK_TYPE};


    /**
     * A股票类型
     */
    public static final String[] CN_STOCK_TYPE ={"010101","010102","010106","010107",COMMON_STOCK_TYPE,"070101"};

    /**
     * yf-ip-redis-lock
     */
    public static final String IP_CONFIGURATION = "IP_CONFIGURATION:";

	/**
	 * yf-ip-redis-lock server
	 */
	public static final String IP_CONFIGURATION_SERVER = "IP_CONFIGURATION:SERVER:";

    /**
     * 用户同步赠送套餐id
     */
    public static final long USER_SYNC_PACKAGE_ID = 112L;

    /**
     * 用户同步赠送天数
     */
    public static final Long USER_SYNC_PRESENTER_DAYS = 99999L;

    /**
     * 美股L1Code
     */
    public static final String NASDAQ_BASIC_LV1 = "Nasdaq_Basic_LV1";

    /**
     * ip未知地址
     */
    public static final String UNKNOWN_REAL_IP_ADDRESS = "UNKNOWN";

	/**
	 * yf-ip-redis-lock
	 */
	public static final String USER_READ_NEWS_ID = "USER_READ_NEWS_ID:";
}
