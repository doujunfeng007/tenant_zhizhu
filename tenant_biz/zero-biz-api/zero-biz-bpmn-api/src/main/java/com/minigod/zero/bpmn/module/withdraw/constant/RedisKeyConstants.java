package com.minigod.zero.bpmn.module.withdraw.constant;

/**
 * Redis Key 定义
 *
 * @author chenyu
 * @title RedisKeyConstants
 * @date 2023-04-08 23:07
 * @description
 */
public class RedisKeyConstants {

   // 默认过期时间
   public static long MS_EXPIRETIME = 30000;

    // 获取锁等待时间
    public static long MS_WAIT_EXPIRETIME = 20000;

    // 默认过期时间
    public static long MS_JOB_EXPIRETIME = 70000;


    /**
     * #######################################################################
     * ####  锁定义KEY名称
     * #######################################################################
     */

    /** 取退款同步HS KEY */
    public static String LOCK_WITHDRAW_HS_KEY_PREFIX = "LOCK:WITHDRAW_HS:%s";

    /** DBS转账同步 KEY */
    public static String LOCK_WITHDRAW_DBS_KEY_PREFIX = "LOCK:WITHDRAW_DBS:";

    /** 取款申请互斥锁 KEY */
    public static String LOCK_WITHDRAW_APPLY_KEY_PREFIX = "LOCK:WITHDRAW_APPLY:";

    /** 银行卡登记申请互斥锁 KEY */
    public static String LOCK_BANKCARD_APPLY_KEY_PREFIX = "LOCK:BANKCARD_APPLY:";

    /** DBS 检测账户余额 KEY */
    public static String LOCK_DBS_ACCT_BALANCE_KEY_PREFIX = "LOCK:DBS_BALANCE:";

    /** DBS Remit回调同步 KEY */
    public static String LOCK_DBS_REMIT_ACK_KEY_PREFIX = "LOCK:DBS_REMIT_ACK:";

    /** 暂时取消不添加收款银行 KEY */
    public static String CANCEL_NONE_REG_BANKCARD_KEY_PREFIX = "BANKCARD:CANCEL_NONE_REG:";
    public static String CANCEL_NONE_REG_BANKCARD_MAP_KEY_PREFIX = "CUST:";

    /** DBS 检测账户余额 KEY */
    public static String LOCK_WITHDRAW_ACCPET_NOTICE_KEY = "LOCK:WITHDRAW_ACCEPT_NOTICE";

	/** 专业投资者认证互斥锁 KEY */
	public static String LOCK_PROFESSIONAL_INVESTOR_PI_KEY_PREFIX = "LOCK:PROFESSIONAL_INVESTOR_PI:%s";


}
