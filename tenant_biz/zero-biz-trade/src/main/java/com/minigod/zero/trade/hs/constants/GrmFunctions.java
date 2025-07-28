package com.minigod.zero.trade.hs.constants;

/**
 * Created by sunline on 2016/4/20 10:48.
 * sunline
 */
public final class GrmFunctions {
    /**
     * EF011-10000以下的功能号，周边接口
     */
    public static final String SV_EXT_SYS_LOGIN = "EF01000100";
    public static final String GET_BRANCH_NO = "EF01000104";
    public static final String CLIENT_LOGIN = "EF01100200";
    public static final String CLIENT_PASSWD_VALIDATION = "EF01110018";
    public static final String CLIENT_PASSWORD_VALIDATION = "EF01281018";
    public static final String CLIENT_MODIFY_PASSWORD="EF01100201";
    public static final String CLIENT_GET_STOCK_AMOUNT="EF01100300";
    public static final String CLIENT_GET_MAX_BUYABLE="EF01100301";
    public static final String CLIENT_SET_COST_PRICE="EF01100309";
    public static final String CLIENT_GET_CANC_OR_MODIFY_ORDERS="EF01100303";
    public static final String CLIENT_MODIFY_ORDER="EF01100304";

    public static final String FAST_ORDER="EF01100316";
    public static final String STRATEGY_ORDER="EF01100341";
    public static final String STRATEGY_ORDER_MODIFY="EF01100342";
    public static final String STRATEGY_ORDER_CANCEL="EF01100343";
    public static final String STRATEGY_GET_OQ_UPDOWN_PRICE="EF01100344";
    public static final String STRATEGY_GET_ORDERBOOK="EF01100345";

    public static final String CLIENT_BUSINESS_TODAY="EF01100402";
    public static final String CLIENT_FUNDJOUR_TODAY="EF01100404";
    public static final String CLIENT_FUNDJOUR_HIS="EF01100417";
    public static final String CLIENT_FUNDRECORD_HIS="EF01100412";
    public static final String CLIENT_BUSINESS_HIS="EF01100411";
    public static final String CLIENT_HISTORY_ENTRUST = "EF01100421";
    public static final String CLIENT_SEC_BankInfo="EF01100802";
    public static final String CLIENT_SEC_CUSQRY="EF01100823";
    public static final String CLIENT_SEC_TO_BANK="EF01100824";
    public static final String CLIENT_SEC_BANK_ACTIVE="EF01100825";
    public static final String CLIENT_SEC_QRYREQ ="EF01100830";
    public static final String CLIENT_FUND_INNERTRANSFER="EF01100832";

    public static final String CLIENT_STOCKJOUR_TODAY="EF01100839";
    public static final String CLIENT_STOCKJOUR_HIS="EF01100840";
    public static final String CLIENT_STOCKJOUR_ALL = "EF01180840";
    public static final String CLIENT_FUNDJOUR_ALL = "EF01180417";

    public static final String CLIENT_GET_BUSINESS="EF01100806";
    public static final String CLIENT_GET_FUND_ACCT_INFO="EF01100808";
    public static final String CLIENT_GET_ENTRUST_DETAIL="EF01100809";
    public static final String CLIENT_GET_EXCH_RATE="EF01100814";
    public static final String CLIENT_GET_FUND_ACCT_TYPE="EF01100816";
    public static final String CLIENT_LOGIN_ALL_IN_ONE="EF01180200";
    public static final String CLIENT_GET_CLIENT_HOLDING = "EF01100403";
    public static final String CLIENT_ENTRUST_ENTER = "EF01100302";

    public static final String MARGIN_FUND_INFO = "EF01100817";
    public static final String MARGIN_FUND_TOTAL = "EF01100818";
    public static final String CLIENT_PASSWORD_CHECK = "EF01100883";
    public static final String GET_FUND_INFO = "EF01100942";
    public static final String FUND_INCOME_HIS_GET = "EF01100946";
    public static final String FUND_INCOME_SUM_GET = "EF01100947";
    public static final String YESTERDAY_FUND_INCOME_GET = "EF01100948";

    public static final String FUND_REDEEM_AMOUNT_GET = "EF01107430";
    public static final String FUND_BALANCE_GET = "EF01107431";
    public static final String FUND_ENTRUST = "EF01107432";
    public static final String FUND_CANCEL = "EF01107433";
    public static final String FUND_ORIGINAL_GET = "EF01107434";
    public static final String HIS_FUND_ORIGINAL_GET = "EF01107435";
    public static final String FUND_STK_CODE_GET = "EF01107437";

    /**
     * EF011-10000以上的功能号，互联网接口
     */
    public static final String BROKER_GET_FUND_TOTAL="EF01180004"; // 查账号汇总
    public static final String BROKER_GET_BROKER_INFO="EF01110001";
    public static final String BROKER_GET_BRKCLT_B="EF01110002";
    public static final String BROKER_GET_CACHE_INFO="EF01110003";
    public static final String BROKER_GET_CACHE_SUM_INFO="EF01110004";
    public static final String BROKER_GET_CLIENT_HOLDING="EF01110005";
    public static final String BROKER_FUND_FROZEN="EF01110006";
    public static final String BROKER_FUND_UNFROZEN="EF01110007";
    public static final String BROKER_FUND_DEPOSIT="EF01110008";
    public static final String BROKER_FUND_FETCH="EF01110009";
    public static final String BROKER_STOCK_FROZEN="EF01110010";
    public static final String BROKER_STOCK_UNFROZEN="EF01110011";
    public static final String BROKER_STOCK_IN="EF01110012";
    public static final String BROKER_STOCK_OUT="EF01110013";
    public static final String BROKER_GET_CLIENT_FUND_ACCT_INFO="EF01110014";
    public static final String BROKER_GET_FUND_ACCT_TYPE="EF01110015";
    public static final String BROKE_GET_STK_ACCT_INFO="EF01110016";
    public static final String BROKER_CLIENT_CONFIRM="EF01110017";
    public static final String BROKER_QUANTITY_MAX="EF01110101";
    public static final String BROKER_AMOUNT_MAX="EF01110102";
    public static final String BROKER_ORDER_NEW="EF01110103";
	public static final String BROKER_ORDER_MODIFY="EF01110104";
    public static final String BROKER_ENTRUST_CURRFILL="EF01110111";
    public static final String BROKER_GET_BUSINESS="EF01110112";
    public static final String BROKER_FUNDJOUR_TODAY="EF01110113";
    public static final String BROKER_STOCKJOUR_TODAY="EF01110114";
    public static final String BROKER_LOGOUT="EF01110019";
    public static final String BROKER_GET_CLIENTINFO="EF01110125";
    public static final String BROKER_TRADEFARE_SET="EF01110126";
    public static final String BROKER_IPO_DEPO="EF01110127";
    public static final String BROKER_IPO_REFUND="EF01110128";

    public static final String OPEN_CLIENT_ACCOUNT="EF01110117";
    public static final String OPEN_FUND_ACCOUNT="EF01110119";

    public static final String BROKER_SET_CLIENT_FEE="EF01110135";
    public static final String BROKER_GET_CLIENT_FEE="EF01110136";
    public static final String BROKER_HISTORY_ENTRUST="EF01110148";
	public static final String BROKER_HISTORY_ENTRUST_DETAIL="EF01110181";
    public static final String BROKER_GET_HIS_BARGAIN_INFO="EF01110149";
    public static final String BROKER_GET_HIS_BARGAIN_INFO_DETAIL="EF01110150";
    public static final String BROKER_STOCKJOUR_HIS="EF01110172";
    public static final String BROKER_FUNDJOUR_HIS="EF01110173";
    public static final String BROKER_GET_IPODETAIL="EF01110184";
    public static final String BROKER_FUNDJOUR_ALL = "EF01180173";
    public static final String BROKER_IPO_APPLY ="EF01110230";
    public static final String BROKER_FUND_TOTAL ="EF01110109";//查询客户的汇总币种信息(单客户)
    public static final String BROKER_SECUHK_STOCK_ACCOUNT_OPEN ="EF01110122";//在指定客户资金帐户下开设证券交易帐户
    public static final String BROKER_FUND_PROTOCOL_SET ="EF01110216";//客户基金协议设置
    public static final String BROKER_FUND_PROTOCOL_GET ="EF01110217";//客户基金协议查询
    public static final String BROKER_FUND_PROTOCOL_CANCEL ="EF01110218";//客户基金协议取消
    public static final String BROKER_FUND_INFO ="EF01110108";
    public static final String BROKER_GET_MV_RATIO ="EF01110180"; //获取保证金
	public static final String BROKER_GET_MARGIN_RATIO ="EF01110186"; //获取孖展比率

    public static final String BROKE_LOGIN="EF01110000";
    public static final String BROKER_GET_FUND_ACCT_AND_CASH_LIST="EF01281001";
    public static final String BROKER_GET_FUND_ACCT_LIST="EF01281002";
    public static final String BROKER_TRADEFARE_SET_ALLINONE="EF01280126";

    public static final String QUERY_CONDITION_ORDER="EF01622307";
    public static final String ADD_CONDITION_ORDER="EF01622300";
    public static final String UPDATE_CONDITION_ORDER="EF01622316";
    public static final String CANNEL_CONDITION_ORDER="EF01622304";
    public static final String STOP_ACTIVE_CONDITION_ORDER="EF01622312";

    public static final String CLIENT_CHECK_PASSWORD_EXPIRED = "EF01100811";
    public static final String CLIENT_FUNDACCOUNT_GET = "EF01100810";

    public static final String CLIENT_GET_9F_DAILYFUNDINCOME="EF01118000";

    public static final String CLIENT_GET_BCAN_INFO="EF01100940";

    public static final String CLIENT_GET_BCAN_INFO2="EF01110229";

    public final class FC{
        public static final String GET_HQ_INFO = "EF0700001";
    }

    public final class DS{
        public static final String QUERY_TRD_CALE = "EF0501004";
    }
}
