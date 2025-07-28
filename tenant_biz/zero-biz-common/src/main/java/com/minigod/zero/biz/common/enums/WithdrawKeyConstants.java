package com.minigod.zero.biz.common.enums;

/**
 * 系统配置表 KEY
 *
 * @author zsdp
 */
public interface WithdrawKeyConstants {
    String DICT_KEY = "WITHDRAW_DICT_KEY";

    /**
     * 人民币限额 港币限额 KEY
     */
    String FUND_WITHDRAW_LIMIT_PREFIX = "FUND_WITHDRAW_LIMIT_";
    String FUND_WITHDRAW_LIMIT_CNY = "FUND_WITHDRAW_LIMIT_CNY";
    String FUND_WITHDRAW_LIMIT_HKD = "FUND_WITHDRAW_LIMIT_HKD";

    /**
     * 各币种金额大额通知设置 KEY
     */
    String FUND_WITHDRAW_NOTICE_CCY_PREFIX = "FUND_WITHDRAW_NOTICE_CCY_";

    /**
     * 系统通知邮件收件人
     */
    String NOTICE_RECEIVE_EMAILS = "NOTICE_RECEIVE_EMAILS";  // 系统通知邮件人员
    String NOTICE_LARGE_RECEIVE_EMAILS = "NOTICE_LARGE_RECEIVE_EMAILS"; // 客户渠道负责人邮件地址
    String NOTICE_APPLY_ACCEPT_EMAILS = "NOTICE_APPLY_ACCEPT_EMAILS"; // 财务邮件接收人
    String NOTICE_BALANCE_ACCEPT_EMAILS = "NOTICE_BALANCE_ACCEPT_EMAILS"; // 余额告警邮件接收人

    /**
     * 各币种账户余额告警金额 KEY
     */
    String DBS_ACCT_BALANCE_NOTICE_CCY_PREFIX = "DBS_ACCT_BALANCE_NOTICE_CCY_";

    /**
     * 取款全局手续费开关 all KEY
     */
    String FUND_WITHDRAW_FEE_SWITCH = "FUND_WITHDRAW_FEE_SWITCH";

    String BANK_FEE_CONFIG_ALL = "all";

    /**
     * Swift长度
     */
    int SWIFT_CODE_LENGTH_MIN = 8;
    int SWIFT_CODE_LENGTH_MAX = 11;

    /** 平台交易账号类型 */
    String PT_ACCT_TYPE_SEC = "SEC";					//证券
    String PT_ACCT_TYPE_FUT = "FET ";				//期货

}
