package com.minigod.zero.trade.hs.constants;

import com.minigod.zero.core.tool.enums.EMarket;

import java.util.*;

/**
 * Created by sunline on 2016/4/9 16:03.
 *
 */
public class HsConstants {

	/** 常量 0 */
	public static final String ZERO = "0";

    public static String GRM_RESPONSE_OK = "EM0512000000";
    public static final String yyyyMMdd ="yyyyMMdd";
    public static final String CLIENT_LICENSE_STR = "3133226EFB89CACEDDCBF067DDEFACF57C74F9E227189A";
    public static final String BROKER_LICENSE_STR = "3133226EFB89CACEDDCBF067DDEFACF57C74F9E227199A";


    public static final String RESPONSE_FIELD_SEPARATOR = ",";
    public static final String RESPONSE_END_OF_LINE_SEPARATOR = ";";

    public static final String TAB="\t";//主分割标讄
    public static final String STOCK_INFO_FILE_APTH="HKMarketData\\HKStock.txt";//股票信息文件存储路径
    public static final String SPREAD_TABLE_FILE_APTH="HKMarketData\\SpreadTable.txt";//价差文件存储路径
    public static String SPREAD_TABLE_DEST_URL;//价差url
    public static String STOCK_INFO_DEST_URL;//股票信息url

    private static Map<EMarket,HSExchageType> grmToHs = new HashMap<>();
    private static Map<String , EMarket> hsToGrm = new HashMap<>();
    public static Map<EMarket, ERegion> marketERegionMap = new HashMap<>();

    public final  static Set<String> SECURTPARAMS = new HashSet();
    public final static Set<String> NULLABLEPARAMS = new HashSet<>();
    // 不检查方法的指定参数
    private static Map<String , Set<String>> NOT_CHECK_FUNID_VALUE = new HashMap<>();

    public static class Tables{
        public static final String TRD_STK_CALE = "hs_user.exchangedate";
        public static final String TRD_STK_CODE = "hs_secuhk.stkcode";
        public static final String  TRD_FUND_FUNDJOUR = "HS_FUND.FUNDJOUR";
        public static final String  TRD_SECUHK_STOCKJOUR = "HS_SECUHK.STOCKJOUR";
        public static final String  TRD_SECUHK_ENTRUST = "HS_SECUHK.ENTRUST";
        public static final String  TRD_SECUHK_REALTIME = "HS_SECUHK.REALTIME";
        public static final String  TRD_HIS_VHKPAPERRATE = "HS_HIS.VHKPAPERRATE";

    }

    public static class Dict{
        public static final String  FARE_DICT_TRADE_FEE="610003";
        //佣金
        public static final String  FARE_TYPE_COMMISSION="0";

    }

    static{
        marketERegionMap.put(EMarket.HK, ERegion.HK);
        marketERegionMap.put(EMarket.SH, ERegion.ML);
        marketERegionMap.put(EMarket.SZ, ERegion.ML);
        marketERegionMap.put(EMarket.US, ERegion.US);

        SECURTPARAMS.add(Constants.Fields.PASSWORD);
        SECURTPARAMS.add(Constants.Fields.NEW_PASSWORD);
        SECURTPARAMS.add(Constants.Fields.OP_PASSWORD);

        NULLABLEPARAMS.add(Constants.Fields.MONEY_TYPE);
        NULLABLEPARAMS.add(Constants.Fields.EXCHANGE_TYPE);
        NULLABLEPARAMS.add(Constants.Fields.POSITION_STR);
        NULLABLEPARAMS.add(Constants.Fields.STOCK_CODE);
        NULLABLEPARAMS.add(Constants.Fields.LANG);
        NULLABLEPARAMS.add(Constants.Fields.SESSION_TYPE);
        NULLABLEPARAMS.add(Constants.Fields.LOGIN_IP);
        NULLABLEPARAMS.add(Constants.Fields.FILTER_TYPE);// 过滤当日委托，历史委托
        NULLABLEPARAMS.add(Constants.Fields.REQUEST_NUM);
        NULLABLEPARAMS.add(Constants.Fields.ENTRUST_STATUS);// 状态
        NULLABLEPARAMS.add(Constants.Fields.BCAN);// bcan
        NULLABLEPARAMS.add(Constants.Fields.ISFILTER);

        NULLABLEPARAMS.add(Constants.Fields.ASSET_ID);
        NULLABLEPARAMS.add(Constants.Fields.USER_ID);
        NULLABLEPARAMS.add(Constants.Fields.DISC_TYPE);

        NOT_CHECK_FUNID_VALUE.put(GrmFunctions.CLIENT_GET_MAX_BUYABLE, new HashSet<>(Arrays.asList("entrustProp")));
        NOT_CHECK_FUNID_VALUE.put(GrmFunctions.BROKER_QUANTITY_MAX, new HashSet<>(Arrays.asList("entrustProp")));
        NOT_CHECK_FUNID_VALUE.put(GrmFunctions.CLIENT_MODIFY_PASSWORD, new HashSet<>(Arrays.asList("needLogin")));
    }

    static {
        grmToHs.put(EMarket.HK, HSExchageType.HK);
        grmToHs.put(EMarket.SH, HSExchageType.SH);
        grmToHs.put(EMarket.SZ, HSExchageType.SZ);
        grmToHs.put(EMarket.US, HSExchageType.US);
        grmToHs.put(EMarket.CSC, HSExchageType.CSC);


        hsToGrm.put(HSExchageType.HK.code, EMarket.HK);
        hsToGrm.put(HSExchageType.SH.code, EMarket.SH);
        hsToGrm.put(HSExchageType.SZ.code, EMarket.SZ);
        hsToGrm.put(HSExchageType.US.code, EMarket.US);
        hsToGrm.put(HSExchageType.CSC.code, EMarket.CSC);
    }

    public static String  getHsMktCode(EMarket eMarket){
        return grmToHs.get(eMarket).getCode();
    }

    public static EMarket getGrmMktCode(String hsExchangeType){
        return hsToGrm.get(hsExchangeType);
    }
    public static Set<String> getNotCheckFunidValue(String funid){
        return NOT_CHECK_FUNID_VALUE.get(funid);
    }

    public class InnerBrokerConfig{
        public static final String INNER_IPADDRESS = "127.0.0.1";
        public static final String INNER_OP_STATION = "127.0.0.1_68F728EE9B64";
        public static final String INNER_USER_TYPE = "1";
        public static final String INNER_OPERATOR_NO = "6789";
        public static final String INNER_OP_ENTRUST_WAY = "Z";
        public static final String INNER_OP_PASSWORD = "1";
        public static final String INNER_OP_BRANCHNO = "100";
        public static final String OP_BRANCHNO = "1001";
        public static final String CONDITION_SYS_NO = "1";
    }

    /**
     * 字段定义
     */
    public class Fields{
    	public static final String SESSION_TYPE = "session_type";
        public static final String BANK_TYPE = "bank_type";
        public static final String LICENSE_STR = "license_str";
        public static final String ERROR_NO = "error_no";
        public static final String SUCC = "0";
        public static final String DEFAULT_VALUE="";
        public static final String CLIENT_NAME="client_name";
        public static final String OPERATOR_NAME="operator_name";
        public static final String OP_BRANCH_NO = "op_branch_no";
        public static final String BRANCH_NO = "branch_no";
        public static final String OP_STATION = "op_station";
        public static final String OP_ENTRUST_WAY = "op_entrust_way";
        public static final String ENTRUST_WAY = "entrust_way";
        public static final String FUNCTION_ID = "function_id";
        public static final String INPUT_CONTENT = "input_content";
        public static final String CONTENT_TYPE = "content_type";
        public static final String ACCOUNT_CONTENT = "account_content";
        public static final String CLIENT_ID = "client_id";
        public static final String FUND_ACCOUNT = "fund_account";
        public static final String ASSET_PROP = "asset_prop";
        public static final String RESTRICTION = "restriction";
        public static final String OPERATOR_NO = "operator_no";
        public static final String USER_TYPE = "user_type";
        public static final String OP_PASSWORD = "op_password";
        public static final String MENU_ID = "menu_id";
        public static final String AUDIT_ACTION = "audit_action";
        public static final String MAIN_FLAG = "main_flag";
        public static final String MONEY_TYPE = "money_type"; //币种类别
        public static final String TO_MONEY_TYPE = "to_money_type";
        public static final String INTERNAL_CODE = "internal_code";
        public static final String SPELL_CODE = "spell_code";
        public static final String STOCK_CODE = "stock_code"; //证券代码
        public static final String stock_account = "stock_account";
        public static final String STOCK_NAME = "stock_name";
        public static final String PRICEED = "priceed";
        public static final String PRICEED_FLAG = "Priceed_flag";
        public static final String EDITION = "edition";
        public static final String PASSWORD = "password";
        public static final String EXCHANGE_TYPE = "exchange_type"; //交易类别(市场)
        public static final String ENTRUST_PRICE = "entrust_price";
        public static final String ENTRUST_PROP = "entrust_prop";
        public static final String ENTRUST_AMOUNT = "entrust_amount";
        public static final String REPORT_TIME = "report_time";
        public static final String QUERY_FLAG = "query_flag";
        public static final String BANK_NAME = "bank_name";
        public static final String AM_OPEN = "am_open";
        public static final String AM_CLOSE = "am_close";
        public static final String PM_OPEN = "pm_open";
        public static final String PM_CLOSE = "pm_close";
        public static final String BANKARG_FLAG = "bankarg_flag";
        public static final String BANK_STATUS = "bank_status";
        public static final String SEQUENCE_NO = "sequence_no";
        public static final String ALLOCATION_ID = "allocation_id";
        public static final String IDLE_START_BALANCE = "idle_start_balance";
        public static final String RESERVE_BALANCE = "reserve_balance";
        public static final String ENABLE_STATUS = "enable_status";
        public static final String SIGN_FLAG = "sign_flag";

        // 302 A股
        public static final String BCAN = "bcan";

        public static final String LANG = "lang";
        public static final String ENABLE_AMOUNT = "enable_amount"; // 可卖数量
        public static final String CASH_AMOUNT = "cash_amount";
        public static final String POSITION_STR = "position_str";
        public static final String EXCHANGE_NAME = "exchange_name";
        public static final String HOLDER_STATUS = "holder_status";
        public static final String HOLDER_RIGHTS = "holder_rights";
        public static final String REGISTER = "register";
        public static final String SEAT_NO = "seat_no";
        public static final String BUY_UNIT = "buy_unit";
        public static final String SELL_UNIT = "sell_unit";
        public static final String PRICE_STEP = "price_step";
        public static final String STORE_UNIT = "store_unit";
        public static final String UP_PRICE = "up_price";
        public static final String DOWN_PRICE = "down_price";
        public static final String REAL_TYPE = "real_type";
        public static final String REAL_STATUS = "real_status";

        public static final String INCOME_BALANCE= "income_balance";
        public static final String KEEP_COST_PRICE= "keep_cost_price"; //保本价

        public static final String NOMINAL_PRICE = "nominal_price";
        public static final String NOMINAL_PRICE_TYPE = "nominal_price_type";
        public static final String BEST_BID_PRICE = "best_bid_price";

        public static final String ORDER_SHARE_BID1 = "order_share_bid1";
        public static final String ORDER_SHARE_BID2 = "order_share_bid2";
        public static final String ORDER_SHARE_BID3 = "order_share_bid3";
        public static final String ORDER_SHARE_BID4 = "order_share_bid4";
        public static final String ORDER_SHARE_BID5 = "order_share_bid5";
        public static final String ORDER_SHARE_BID6 = "order_share_bid6";
        public static final String ORDER_SHARE_BID7 = "order_share_bid7";
        public static final String ORDER_SHARE_BID8 = "order_share_bid8";
        public static final String ORDER_SHARE_BID9 = "order_share_bid9";
        public static final String ORDER_SHARE_BID10 = "order_share_bid10";

        public static final String BEST_ASK_PRICE = "best_ask_price";
        public static final String ORDER_SHARE_ASK1 = "order_share_ask1";
        public static final String ORDER_SHARE_ASK2 = "order_share_ask2";
        public static final String ORDER_SHARE_ASK3 = "order_share_ask3";
        public static final String ORDER_SHARE_ASK4 = "order_share_ask4";
        public static final String ORDER_SHARE_ASK5 = "order_share_ask5";
        public static final String ORDER_SHARE_ASK6 = "order_share_ask6";
        public static final String ORDER_SHARE_ASK7 = "order_share_ask7";
        public static final String ORDER_SHARE_ASK8 = "order_share_ask8";
        public static final String ORDER_SHARE_ASK9 = "order_share_ask9";
        public static final String ORDER_SHARE_ASK10 = "order_share_ask10";


        public static final String RECORD_NO = "record_no";
        public static final String ENTRUST_NO = "entrust_no"; //委托编号
        public static final String ENTRUST_TYPE = "entrust_type";

        public static final String CURR_TIME = "curr_time";
        public static final String ENTRUST_BS = "entrust_bs";
        public static final String BUSINESS_AMOUNT = "business_amount";
        public static final String OCCUR_AMOUNT = "occur_amount";

        public static final String BUSINESS_PRICE = "business_price";
        public static final String BUSINESS_NO = "business_no";
        public static final String PASSWORD_TYPE = "password_type";
        public static final String NEW_PASSWORD = "new_password";
        public static final String ERROR_ID = "error_id";
        public static final String ERROR_INFO = "error_info";
        public static final String ENTRUSY_TYPE = "entrust_type";
        public static final String BATCH_NO = "batch_no";
        public static final String BANK_NO = "bank_no";
        public static final String CLIENT_RIGHTS = "client_rights";
        public static final String SHORTSELL_TYPE = "shortsell_type";
        public static final String HEDGE_FLAG = "hedge_flag";
        public static final String ORIGIN_FLAG = "origin_flag";
        public static final String SYS_FLAG = "sys_flag";
        public static final String ACTION_STR = "action_str";
        public static final String INIT_DATE = "init_date"; // 市场交易日期
        public static final String ENABLE_BALANCE = "enable_balance"; //可用金额（购买力）
        public static final String FROZEN_BALANCE = "frozen_balance"; // 冻结金额
        public static final String FROZEN_REASON = "frozen_reason";
        public static final String VALID_DATE = "valid_date";
        public static final String REVERT_SERIAL_NO = "revert_serial_no";
        public static final String OCCUR_BALANCE = "occur_balance"; // 发生金额
        public static final String REMARK = "remark";
        public static final String JOUR_DATE = "jour_date";
        public static final String JOUR_SERIAL_NO = "jour_serial_no";
        public static final String CANCEL_BALANCE = "cancel_balance";
        public static final String MONITOR_TYPE = "monitor_type";
        public static final String MONITOR_VALUE_BEGIN = "monitor_value_begin";
        public static final String MONITOR_VALUE_END = "monitor_value_end";
        public static final String PAGE_NUM = "page_num";
        public static final String PAGE_SIZE = "page_size";
        public static final String MV_RATIO = "mv_ratio"; // 保证金比例


        public static final String MARKET_VALUE = "market_value";
        public static final String MARKET_CODE = "market_code";
        public static final String CURRENT_BALANCE = "current_balance";
        public static final String CURRENT_AMOUNT = "current_amount"; // 当前数量
        public static final String TRANSFER_BALANCE = "transfer_balance";
        public static final String ASSET = "asset";
        public static final String FETCH_CASH = "fetch_cash";
        public static final String GF_FETCH_BALANCE_T = "gf_fetch_balance_t"; // T日可取金额
        public static final String SPECIAL_FETCH_BALANCE = "special_fetch_balance";
        public static final String CACHE_ON_HOLD = "cash_on_hold";
        public static final String MAX_EXPOSURE = "max_exposure";
        public static final String CREDIT_LINE = "credit_line";
        public static final String MARGIN_VALUE = "margin_value";
        public static final String MARGIN_CALL = "margin_call"; // 追加保证金
        public static final String IPO_BALANCE = "ipo_balance";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String BUSINESS_FLAG = "business_flag"; //业务标志
        public static final String BUSINESS_TYPE = "business_type";
        public static final String BUSINESS_TIME = "business_time";
        public static final String BUSINESS_BALANCE = "business_balance";
        public static final String TRADEDAY_BALANCE = "tradeday_balance"; //账面结余

        public static final String ENTRUST_DATE = "entrust_date";
        public static final String ENTRUST_TIME = "entrust_time";
        public static final String ENTRUST_STATUS = "entrust_status";
        public static final String LOCATE_ENTRUST_NO = "locate_entrust_no";
        public static final String QUERY_DIRECTION = "query_direction";
        public static final String QUERY_MODE = "query_mode";
        public static final String SORT_DIRECTION = "sort_direction";
        public static final String REQUEST_NUM = "request_num";
        public static final String ALLOW_CANCEL = "allow_cancel";
        public static final String ALLOW_AMEND = "allow_amend";
        public static final String SERIAL_NO = "serial_no"; //流水序号
        public static final String LAST_PRICE = "last_price"; //最新价
        public static final String AV_BUY_PRICE = "av_buy_price"; //买入均价
        public static final String COST_PRICE = "cost_price";
        public static final String LOG_NO = "log_no";
        public static final String CURR_DATE = "curr_date";
        public static final String ERROR_INFO_OUT = "error_info_out";
        public static final String SYS_STATUS = "sys_status";
        public static final String SYS_STATUS_NAME = "sys_status_name";
        public static final String ENTRUST_NO_FIRST = "entrust_no_first";
        public static final String FFARE0_TIMES = "ffare0_times";
        public static final String FFARE1_TIMES = "ffare1_times";
        public static final String FFARE2_TIMES = "ffare2_times";
        public static final String FFARE3_TIMES = "ffare3_times";
        public static final String LOGIN_TIME = "login_time";
        public static final String LOGIN_IP = "login_ip";

        public static final String DAY_TYPE = "DAY_TYPE";
        public static final String STKCODE_STATUS = "stkcode_status";


        public static final String COMPANY_NAME = "company_name";
        public static final String MAIN_BRANCH_NO = "main_branch_no";
        public static final String DEF_MONEY_TYPE = "def_money_type";

        public static final String BROKER_ACCOUNT = "broker_account";
        public static final String BROKER_NAME = "broker_name";
        public static final String BROKER_KIND = "broker_kind";
        public static final String ID_KIND = "id_kind";
        public static final String ID_NO = "id_no";
        public static final String SOURCE_FLAG = "source_flag";
        public static final String BKTRANS_STATUS = "bktrans_status";
        public static final String TRANS_TYPE = "trans_type";
        public static final String BUSIN_TYPE = "bisin_type";
        public static final String DEBIT_CUY = "debit_cuy";
        public static final String CREDIT_CUY = "credit_cuy";
        public static final String BKACCOUNT_KIND = "bkaccount_kind";


        public static final String BROKER_SEX = "broker_sex";
        public static final String ADDRESS = "address";
        public static final String PHONECODE = "phonecode";
        public static final String MOBILE_TEL = "mobile_tel";
        public static final String E_MAIL = "e_mail";
        public static final String BROKER_STATUS = "broker_status";
        public static final String BANK_ACCOUNT_CLI = "bank_account_cli";
        public static final String LEDGER_ID = "ledger_id";
        public static final String CHECK_NO = "check_no";
        public static final String BANK_ID = "bank_id";
        public static final String BANK_ACCOUNT = "bank_account";
        public static final String CLIENT_ACCOUNT = "client_account";
        public static final String BKACCOUNT_STATUS = "bkaccount_status";
        public static final String HOLDER_KIND = "holder_kind";
        public static final String HOLDER_NAME = "holder_name";
        public static final String HOLDER_LEVEL = "holder_level";
        public static final String REPORT_LEVEL = "report_level";
        public static final String REGFLAG = "regflag";
        public static final String BONDREG = "bondreg";
        public static final String HOLDER_STATUS_TEMP = "holder_status_temp";

        public static final String FOREIGN_FLAG = "foreign_flag";
        public static final String TRADE_SIGN = "trade_sign";
        public static final String TRADE_DATE = "trade_date";
        public static final String SETT_DATE = "sett_date";
        public static final String VALUE_DATE = "value_date";
        public static final String BUSINESS_FLAG_REAL = "business_flag_real";
        public static final String CHECK_VALID_DATE = "check_valid_date";
        public static final String LOCALE_REMARK = "locale_remark";
        public static final String FEE_MONEY_TYPE = "fee_money_type";
        public static final String FEE_OCCUR_BALANCE = "fee_occur_balance";
        public static final String THETHIRD = "theThird";
        public static final String EX_STATUS = "ex_status";
        public static final String FETCH_BALANCE_JUST = "fetch_balance_just";
        public static final String REMARK_FEE = "remark_fee";
        public static final String LOCALE_REMARK_FEE = "locale_remark_fee";
        public static final String TMP_JOIN_SERIALNO = "tmp_join_serialno";
        public static final String AUDIT_SERIAL_NO = "audit_serial_no";

        public static final String FEE_JOIN_SERIALNO = "fee_join_serialno";
        public static final String OP_REMARK = "op_remark";
        public static final String ACTION_IN = "action_in";
        public static final String PAYEE = "payee";
        public static final String AUTO_PRINT = "auto_print";
        public static final String CHECK_SERIALNO = "check_serialno";
        public static final String STOCK_ACCOUNT = "stock_account";
        public static final String STKREVERTJOUR_NO = "stkrevertjour_no";
        public static final String CANCEL_AMOUNT = "cancel_amount";
        public static final String OCCUR_DATE = "occur_date";
        public static final String STOCK_PRICE = "stock_price";
        public static final String LOCAL_ID = "local_id";
        public static final String STOCK_MONEY_TYPE = "stock_money_type";
        public static final String CUSTODIAN_ID = "custodian_id";
        public static final String CUSTODIAN = "custodian";
        public static final String REMARK_FUND = "remark_fund";
        public static final String REMARK_FUND_LOCALE = "remark_fund_locale";
        public static final String OCCUR_AMOUNT_P1 = "occur_amount_p1";
        public static final String OCCUR_AMOUNT_P2 = "occur_amount_p2";
        public static final String OCCUR_AMOUNT_P3 = "occur_amount_p3";
        public static final String FARE_MONEY_TYPE = "fare_money_type";
        public static final String FUND_BUSINESS_FLAG = "fund_business_flag";
        public static final String SUM_BUY_AMOUNT = "sum_buy_amount";
        public static final String TRANSMIT_AMOUNT = "transmit_amount";
        public static final String BUSINESS_DATE = "business_date"; // 业务日期
        public static final String BUSINESS_NAME = "business_name"; //业务名称
        public static final String BEGIN_DATE = "begin_date";
        public static final String TO_FUND_ACCOUNT = "to_fund_account";
        public static final String INCOME_RATIO = "income_ratio";
        public static final String POST_BALANCE = "post_balance"; //后资金额
        public static final String POST_AMOUNT = "post_amount";
        public static final String FROM_MONEY_TYPE = "from_money_type";
        public static final String REVERSE_RATE = "reverse_rate";
        public static final String EXCH_RATE = "exch_rate";
        public static final String EXCH_RATE_REVERSE = "exch_rate_reverse";
        public static final String CLEAR_BALANCE = "clear_balance";
        public static final String BIRTHDAY = "birthday";
        public static final String ID_BEGINDATE = "id_begindate";
        public static final String ID_TERM = "id_term";
        public static final String FARE_KIND_STR = "fare_kind_str";
        public static final String OVERDRAFT_FORCED_FLAG = "overdraft_forced_flag";

        public static final String FARE_DICT = "fare_dict";
        public static final String FEE_TYPE = "fee_type";
        public static final String FEE_COUNT = "fee_count";
        public static final String FARE_TYPE = "fare_type";
        public static final String FARE_MODE = "fare_mode";
        public static final String PRECISION_FLAG = "precision_flag";
        public static final String PRECISION = "precision";
        public static final String MIN_FARE = "min_fare";
        public static final String MAX_FARE = "max_fare";
        public static final String STOCK_TYPE = "stock_type";

        public static final String FEE_COUNT_FIX = "fee_count_fix";
        public static final String FARE_STR = "fare_str";

        public static final String TOUCH_PRICE_TYPE = "touch_price_type";
        public static final String TOUCH_TYPE = "touch_type";
        public static final String TOUCH_PRICE = "touch_price";
        public static final String TILL_DATE = "till_date";
        public static final String TOUCH_COUNT = "touch_count";

        public static final String CONDITION_NO = "condition_no";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String PASSWD = "passwd";
        public static final String FUTU_EXCH_TYPE = "futu_exch_type";
        public static final String COMMODITY_TYPE = "commodity_type";
        public static final String CONTRACT_CODE = "contract_code";
        public static final String ENTRUST_DIRECTION = "entrust_direction";
        public static final String HEDGE_TYPE = "hedge_type";
        public static final String ENTRUST_PRICE_TYPE = "entrust_price_type";
        public static final String ENTRUST_FLOAT_PRICE = "entrust_float_price";
        public static final String CMD_TYPE = "cmd_type";
        public static final String CONDITION_SYS_NO = "condition_sys_no";
        public static final String FUTU_PRODUCT_TYPE = "futu_product_type";
        public static final String CONTRACT_NAME = "contract_name";

        // 811
        public static final String WARNING_DAYS = "warning_days";
        public static final String EXPIRY_DAYS = "expiry_days";
        public static final String EXPIRY_DATE = "expiry_date";
        public static final String WARNING_FLAG = "warning_flag";

        // 341
        public static final String CONDITION_TYPE = "condition_type";
        public static final String TOUCH_PRICE_UP="touch_price_up";
        public static final String TOUCH_PRICE_DOWN ="touch_price_down";
        public static final String STRATEGY_TYPE = "strategy_type";
        public static final String STRATEGY_ENDDATE="strategy_enddate";
        public static final String STRATEGY_STATUS="strategy_status";
        // 342
        public static final String TOUCH_PRICE2="touch_price2";
        public static final String TOUCH_PRICE_UP2="touch_price_up2";
        public static final String TOUCH_PRICE_DOWN2 ="touch_price_down2";

        // 344
        public static final String SPREAD_CODE ="spread_code";

        // 999
        public static final String ASSET_PRICE ="asset_price";
        public static final String BEGIN_AMOUNT ="begin_amount";
        public static final String CLOSE_PRICE ="close_price";
        public static final String REAL_BUY_BALANCE ="real_buy_balance";
        public static final String REAL_SELL_BALANCE ="real_sell_balance";
        public static final String REAL_SELL_AMOUNT ="real_sell_amount"; //回报卖出数量
        public static final String DEPOSIT_AMOUNT ="deposit_amount";
        public static final String WITHDRAW_AMOUNT ="withdraw_amount";
        public static final String DEPOSIT_VALUE ="deposit_value";
        public static final String WITHDRAW_VALUE ="withdraw_value";
        public static final String QUANTITY_ALLOTTED ="quantity_allotted";
        public static final String FINAL_PRICE ="final_price";
        public static final String REDUCE_BALANCE ="reduce_balance";
        public static final String TOTAL_RIGHTS_FARE ="total_rights_fare";
        public static final String ACCRUED_INTEREST ="accrued_interest";

        public static final String QUANTITY_APPLY ="quantity_apply";
        public static final String DEPOSIT_RATE ="deposit_rate";
        public static final String OVER_FLAG ="over_flag";

        public static final String MANUAL_IPO_INTRATE = "manual_ipo_intrate";
        public static final String MANUAL_HANDLING_FEE = "manual_handling_fee";
        public static final String HANDLING_FEE_ABLE = "handling_fee_able";
        public static final String QUANTITY_FILTER = "quantity_filter";
        public static final String BALANCE_FILTER = "balance_filter";

        public static final String ORDER_TYPE = "order_type";
        public static final String FARE0 = "fare0";
        public static final String FARE1 = "fare1";
        public static final String FARE2 = "fare2";
        public static final String FARE3 = "fare3";
        public static final String FAREX = "farex";
        public static final String FX_MONEY_TYPE = "fx_money_type";
        public static final String FX_RATE = "fx_rate";
        public static final String SETT_METHOD = "sett_method";
        public static final String BROKER_NO = "broker_no";

        public static final String IPO_APPLYING_BALANCE = "ipo_applying_balance";
        public static final String FETCH_BALANCE = "fetch_balance";
        public static final String CREDIT_VALUE = "credit_value";
        public static final String LOAN_VALUE = "loan_value";


    }

	/**
	 * 恒生交易类别
	 * D	SHANG HAI B
	 * H	SHEN ZHEN B
	 * K	SEHK
	 * P	USA
	 * m	MUTUAL FUND
	 * t	SHANGHAI CSC
	 * v	SHENZHEN CSC
	 */
    public enum HSExchageType{
        HK("K"),SH("t"),SZ("v"),US("P"),CSC("t"), FUND("m"),SHB("D"),SZB("H");
        private String code;
        HSExchageType(String code){
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    //委托单过滤，过滤当日委托，历史委托
    public enum EntrustFilterType{
        ALL("0"), //全部订单
        NORMAL("1"), //普通单
        STRATEGY("2"); //条件单
        private String code;
        EntrustFilterType(String code){
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum HsMoneyType{
        HKD("2"),USD("1"),CNY("0"),OTH("3");
        private String moneyType;
        HsMoneyType(String moneyType){
            this.moneyType = moneyType;
        }

        public String getMoneyType() {
            return moneyType;
        }



    }

	/**
	 * 交易市场
	 */
	public enum MarketTypeEnum {
		CN("0", "A股"),
		US("1", "美股"),
		HK("2","港股"),
		OTH("3","其他");
		private final String code;
		private final String name;
		MarketTypeEnum(String code, String info) {
			this.code = code;
			this.name = info;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
}
