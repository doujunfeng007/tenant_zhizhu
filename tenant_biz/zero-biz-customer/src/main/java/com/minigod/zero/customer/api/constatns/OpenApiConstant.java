package com.minigod.zero.customer.api.constatns;

import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 外部服务接口配置（Http）
 */
@Component
public class OpenApiConstant {

    /**
     * 行情管理后台地址
     */
    public static String BIZ_SERVER_URL;
    @Value("${biz.server.dev.url:}")
    private String BIZ_SERVER_DEV_URL;
    @Value("${biz.server.k8s.url:}")
    private String BIZ_SERVER_K8S_URL;

    /**
     * CMS管理后台地址
     */
    public static String BIZ_CMS_URL;
    @Value("${biz.cms.dev.url:}")
    private String BIZ_CMS_DEV_URL;
    @Value("${biz.cms.k8s.url:}")
    private String BIZ_CMS_K8S_URL;

    /**
     * OMS管理后台地址
     */
    public static String BIZ_OMS_URL;
    @Value("${biz.oms.dev.url:}")
    private String BIZ_OMS_DEV_URL;
    @Value("${biz.oms.k8s.url:}")
    private String BIZ_OMS_K8S_URL;

    /**
     * 交易后台地址
     */
    public static String BIZ_TRADE_URL;
    @Value("${biz.trade.dev.url:}")
    private String BIZ_TRADE_DEV_URL;
    @Value("${biz.trade.k8s.url:}")
    private String BIZ_TRADE_K8S_URL;

    /**
     * 零号交易后台地址
     */
    public static String BPM_TRADE_URL;
    @Value("${bpm.trade.dev.url:}")
    private String BPM_TRADE_DEV_URL;
    @Value("${bpm.trade.k8s.url:}")
    private String BPM_TRADE_K8S_URL;

    /**
     * 行情服务地址
     */
    public static String MARKET_QUOT_URL;
    @Value("${market.quot.dev.url:}")
    private String MARKET_QUOT_DEV_URL;
    @Value("${market.quot.k8s.url:}")
    private String MARKET_QUOT_K8S_URL;

    /**
     * F10服务地址
     */
    public static String MARKET_INFO_URL;
    @Value("${market.info.dev.url:}")
    private String MARKET_INFO_DEV_URL;
    @Value("${market.info.k8s.url:}")
    private String MARKET_INFO_K8S_URL;

    /**
     * ICBC 地址
     */
    public static String ICBC_SYNC_URL;
    @Value("${icbc.sync.sit.url:}")
    private String ICBC_SYNC_SIT_URL;
    @Value("${icbc.sync.uat.url:}")
    private String ICBC_SYNC_UAT_URL;
    @Value("${icbc.sync.prd.url:}")
    private String ICBC_SYNC_PRD_URL;

    // 股票分页查询
    public static String SELECT_ASSET_INFO_LIST;
    public static String SYNC_ASSET_INFO;
    // 模糊查询股票
    public static String FUZZY_QUERY;

    // 根据主键ID查询行情用户信息
    public static String SELECT_USER_BY_MKT_USER_ID;
    // 根据租户号和用户号查询行情用户信息
    public static String SELECT_USER_BY_CUST_ID;
    // 下单处理行情权限
    public static String HANDEL_USER_DATA_INFOS;
    // 后台管理变更失效行情处理
    public static String HANDLE_FAIL_STATUS_PERMISSIONS;
    // 获取用户购买的行情权限
    public static String GET_QUOTE_PERMISSION;
    // 获取行情权限（点击剩余数量）
    public static String GET_SURPLUS_CLICK;
    // 行情刷新-外部
    public static String SWITCH_QUOT_LEVEL;
    // 获取ip地址
    public static String GET_IP_ADDRESS;
    // 更新行情用户美股专业声明
    public static String HANDEL_USER_PROFESSION;
    // 行情刷新-内部
    public static String SWITCH_QUOT_LEVEL_PROXY;
    // 行情过期通知
    public static String PUSH_MKT_EXPIRE_NOTICE;

    // 获取同步新闻数据
    public static String GET_NEWS_INFO_SYNC;
    // 获取同步资讯公告数据
    public static String GET_NEWS_HK_NOTICE_SYNC;
    // 获取同步专题新闻数据
    public static String GET_NEWS_TPOIC_REL_SYNC;

    // 条件单触发
    public static String STRATEGY_ORDER_TRIGGER;
    // 条件单下单
    public static String STRATEGY_ORDER_PLACE;
    // 条件单改单
    public static String STRATEGY_ORDER_UPDATE;
    // 条件单撤单
    public static String STRATEGY_ORDER_CANCEL;
    // 持仓重算市值获取行情
    public static String GET_QUOTATION;
    // 获取日K数据
    public static String GET_STK_DAY;

    // 获取可认购股票信息
    public static String CAN_APPLY;
    // 获取待上市IPO列表
    public static String WAIT_LISTING;
    // 获取新股详情
    public static String STOCK_DETAIL;
    // 配售结果
    public static String PLACING_RESULT;
    // IPO信息
    public static String HK_IPO_INFO_URL;
    // IPO Map信息
    public static String HK_IPO_INFO_MAP_URL;
    // 交易日历
    public static String FIND_TRADE_CALE;
    // 同步股价提醒数据
    public static String SYNC_PRICE_REMINDER;

    // ipo股票详情
    public static String IPO_STOCK_DETAIL;
    // ipo申购
    public static String IPO_STOCK_APPLY;
    // 查询认购记录
    public static String IPO_APPLY_INFO;
    // 撤销申购
    public static String IPO_STOCK_CANCAL;
    // IPO股票列表
    public static String IPO_STOCK_LIST;
    // IPO结果录入标识
    public static String IPO_ZQ_IMPORT;
    // IPO 中签列表
    public static String IPO_ZQ_LIST;
    // IPO 未中签列表
    public static String IPO_N_ZQ_LIST;
    // 行情
    // 查询可认购列表
    public static String CAN_APPLY_MARKET;
    // 待上市列表
    public static String WAIT_LISTING_MARKET;
    // 获取新股详情
    public static String STOCK_DETAIL_MARKET;
    //配售结果
    public static String PLACING_RESULT_MARKET;

    // 获取港美股佣金
    public static String GET_COMMISSION_INFO;

    // 获取股票交易记录
    public static String GET_ORIGINAL;
    // 获取港美A股佣金
    public static String GET_COMMISSION_INFO_ALL;
    // 获取汇率
    public static String GET_EXCHANGE_RATE_LIST;

    // 添加自选
    public static String ICBC_ADD_OPTIONS;
    // 添加关注
    public static String ICBC_FOLLOW_OPTIONS;

    @PostConstruct
    void init() {
        String profile = System.getProperties().getProperty("spring.profiles.active");
        BIZ_SERVER_URL = SaasServerUrl(profile);
        BIZ_CMS_URL = SaasCmsUrl(profile);
        BIZ_OMS_URL = SaasOmsUrl(profile);
        MARKET_QUOT_URL = ZeroMktquotUrl(profile);
        MARKET_INFO_URL = ZeroMktinfoUrl(profile);
        BIZ_TRADE_URL = zeroTradeUrl(profile);
        BPM_TRADE_URL = zeroBpmTradeUrl(profile);
        ICBC_SYNC_URL = IcbcSyncUrl(profile);

        SELECT_ASSET_INFO_LIST = MARKET_QUOT_URL + "/open/wt/assetInfo/selectAssetInfoList";
        FUZZY_QUERY = MARKET_QUOT_URL + "/open/wt/assetInfo/fuzzyQuery";
        SYNC_PRICE_REMINDER = MARKET_QUOT_URL + "/open/wt/priceReminder/syncData";

        SELECT_USER_BY_MKT_USER_ID = BIZ_SERVER_URL + "/proxy/mktUser/selectUserByMktUserId";
        SELECT_USER_BY_CUST_ID = BIZ_SERVER_URL + "/proxy/mktUser/selectUserByCustId";
        HANDEL_USER_DATA_INFOS = BIZ_SERVER_URL + "/proxy/mktUser/handelUserDataInfos";
        HANDLE_FAIL_STATUS_PERMISSIONS = BIZ_SERVER_URL + "/proxy/mktUser/handleFailStatusUserPermissions";
        GET_QUOTE_PERMISSION = BIZ_SERVER_URL + "/proxy/userPermissions/getQuotePermission";
        GET_SURPLUS_CLICK = BIZ_SERVER_URL + "/proxy/mktUser/getSurplusClick";
        SWITCH_QUOT_LEVEL = BIZ_SERVER_URL + "/proxy/mktUser/switchQuotLevel";
        GET_IP_ADDRESS = BIZ_SERVER_URL + "/proxy/ipAddress/getIpAddress";
        HANDEL_USER_PROFESSION = BIZ_SERVER_URL + "/proxy/mktUser/handel_user_permission";
        SWITCH_QUOT_LEVEL_PROXY = BIZ_SERVER_URL + "/proxy/mktUser/switchQuotLevelProxy";
        PUSH_MKT_EXPIRE_NOTICE = BIZ_SERVER_URL + "/proxy/mktUser/pushExpireNotice";

        GET_NEWS_INFO_SYNC = BIZ_OMS_URL + "/proxy/newsInfo/getNewsInfoSync";
        GET_NEWS_HK_NOTICE_SYNC = BIZ_OMS_URL + "/proxy/newsInfo/getNewsHkNoticeSync";
        GET_NEWS_TPOIC_REL_SYNC = BIZ_OMS_URL + "/proxy/newsInfo/getNewsTpoicRelSync";

        STRATEGY_ORDER_PLACE = BIZ_SERVER_URL + "/proxy/trade/placeStrategyOrder";
        STRATEGY_ORDER_UPDATE = BIZ_SERVER_URL + "/proxy/trade/updateStrategyOrder";
        STRATEGY_ORDER_CANCEL = BIZ_SERVER_URL + "/proxy/trade/cancelStrategyOrder";
        GET_QUOTATION = BIZ_SERVER_URL + "/proxy/trade/getQuotation";
        GET_STK_DAY = BIZ_SERVER_URL + "/proxy/trade/getStkDay";

        WAIT_LISTING = BIZ_SERVER_URL + "/proxy/ipo_api/wait_listing";
        STOCK_DETAIL = BIZ_SERVER_URL + "/proxy/ipo_api/stock_detail";
        CAN_APPLY = BIZ_SERVER_URL + "/proxy/ipo_api/can_apply";

        HK_IPO_INFO_URL = BIZ_SERVER_URL + "/proxy/ipo_api/getIpoInfo";
        PLACING_RESULT = BIZ_SERVER_URL + "/proxy/ipo_api/find_placing_result";
        HK_IPO_INFO_MAP_URL = BIZ_SERVER_URL + "/proxy/ipo_api/getIpoInfoMap";
        FIND_TRADE_CALE = BIZ_SERVER_URL + "/proxy/stkTrdCale/findTradeCale";
        SYNC_ASSET_INFO = BIZ_SERVER_URL + "/proxy/assetInfo/findAssetInfoList";

        STRATEGY_ORDER_TRIGGER = BIZ_TRADE_URL + "/proxy/mktService/triggerStrategyOrder";

        IPO_STOCK_DETAIL = BPM_TRADE_URL + "/ipo/get";
        IPO_STOCK_APPLY = BPM_TRADE_URL + "/ipo/apply";
        IPO_APPLY_INFO = BPM_TRADE_URL + "/ipo/getMyApplies";
        IPO_STOCK_CANCAL = BPM_TRADE_URL + "/ipo/cancel";
        IPO_STOCK_LIST = BPM_TRADE_URL + "/ipo/list";
        IPO_ZQ_IMPORT = BPM_TRADE_URL + "/ipo/today/isResultImported";
        IPO_ZQ_LIST = BPM_TRADE_URL + "/ipo/today/queryAllottedApplies";
        IPO_N_ZQ_LIST = BPM_TRADE_URL + "/ipo/today/queryNotAllottedApplies";

        CAN_APPLY_MARKET = MARKET_INFO_URL + "/proxy/ipo_api/can_apply";
        WAIT_LISTING_MARKET = MARKET_INFO_URL + "/proxy/ipo_api/wait_listing";
        STOCK_DETAIL_MARKET = MARKET_INFO_URL + "/proxy/ipo_api/stock_detail";
        PLACING_RESULT_MARKET = MARKET_INFO_URL + "/proxy/ipo_api/findPlacingResult";

        GET_COMMISSION_INFO = BPM_TRADE_URL + "/customer/fare/queryConfig";
        GET_ORIGINAL = BPM_TRADE_URL + "/esop/rest/queryOriginal";
        GET_COMMISSION_INFO_ALL = BPM_TRADE_URL + "/customer/fare/queryConfigAll";
        GET_EXCHANGE_RATE_LIST = BPM_TRADE_URL + "/fund/getExchangeRateList";

        ICBC_ADD_OPTIONS = ICBC_SYNC_URL + "/open/wt/sync/syncAddOptions";
        ICBC_FOLLOW_OPTIONS = ICBC_SYNC_URL + "/open/wt/sync/syncFollowOptions";
    }

    /**
     * 行情服务地址
     */
    String ZeroMktquotUrl(String profile) {
        if (profile.equals(AppConstant.DEV_CODE)) {
            return MARKET_QUOT_DEV_URL;
        } else {
            return MARKET_QUOT_K8S_URL;
        }
    }

    /**
     * F10服务地址
     */
    String ZeroMktinfoUrl(String profile) {
        if (profile.equals(AppConstant.DEV_CODE)) {
            return MARKET_INFO_DEV_URL;
        } else {
            return MARKET_INFO_K8S_URL;
        }
    }

    /**
     * 交易API
     *
     * @param profile
     * @return
     */
    String zeroTradeUrl(String profile) {
        if (profile.equals(AppConstant.DEV_CODE)) {
            return BIZ_TRADE_DEV_URL;
        } else {
            return BIZ_TRADE_K8S_URL;
        }
    }


    /**
     * 柜台交易API
     *
     * @param profile
     * @return
     */
    String zeroBpmTradeUrl(String profile) {
        if (profile.equals(AppConstant.DEV_CODE)) {
            return BPM_TRADE_DEV_URL;
        } else {
            return BPM_TRADE_K8S_URL;
        }
    }

    /**
     * saas管理后台地址
     */
    String SaasServerUrl(String profile) {
        if (profile.equals(AppConstant.DEV_CODE)) {
            return BIZ_SERVER_DEV_URL;
        } else {
            return BIZ_SERVER_K8S_URL;
        }
    }

    /**
     * saas-cms管理后台地址
     */
    String SaasCmsUrl(String profile) {
        if (profile.equals(AppConstant.DEV_CODE)) {
            return BIZ_CMS_DEV_URL;
        } else {
            return BIZ_CMS_K8S_URL;
        }
    }

    /**
     * saas-oms管理后台地址
     */
    String SaasOmsUrl(String profile) {
        if (profile.equals(AppConstant.DEV_CODE)) {
            return BIZ_OMS_DEV_URL;
        } else {
            return BIZ_OMS_K8S_URL;
        }
    }

    /**
     * ICBC 客户端
     *
     * @param profile
     * @return
     */
    String IcbcSyncUrl(String profile) {
        if (profile.equals(AppConstant.STG_CODE)) {
            return ICBC_SYNC_SIT_URL;
        } else if (profile.equals(AppConstant.UAT_CODE)) {
            return ICBC_SYNC_UAT_URL;
        } else {
            return ICBC_SYNC_PRD_URL;
        }
    }
}
