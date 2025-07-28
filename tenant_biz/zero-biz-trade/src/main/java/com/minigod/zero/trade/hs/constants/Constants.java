package com.minigod.zero.trade.hs.constants;


public class Constants {

    public static final String innerClientSideSid = "0232d1566c0638a0a7583c967254c717";

    public static final String USER_TYPE_CLIENT="0";
    public static final String USER_TYPE_OPERATOR="1";
    public static final String USER_TYPE_BROKER="2";

    public static final String FC_DS = "fc_DS";
    public static final String sunline_DS= "sunline_DS";
    public static final String TRD_DS= "trd_DS";
    public static final String MKT_DS="mktinfo_DS";
    public static final String GRM_DS="grm_DS";

    public static String APP_NAME="sunline-grm-core";//项目名称
    public static final String LOG_FILTER_FORMAT = "password";//日志文件的过滤格式
    public static final String LOG_PASSWORD_REPLACE_TXT = "***";//代替密码
    public static final String SYSTEM_ID_ACCOUNT_SYS = "zhxt";

    public static final String DES_ENCRYPT_KEY="EBSil-HK";
    public static final String CONTENT_TYPE_JSON="application/json";

    public static final String LANG_DEFAULT = "sc";
    public static final String LANG_SIMP_CHINESE = "sc";
    public static final String LANG_TRAD_CHINESE = "tc";
    // 英文
    public static final String EN = "en";
    // 简体(默认)
    public static final String ZH_HANS = "zh-hans";
    // 繁体
    public static final String ZH_HANT = "zh-hant";

    /**
     * 字段定义
     */
    public class Fields{
        public static final String FUNCTION_ID = "functionId";
        public static final String LANG="lang";
        public static final String SID = "sid";
        public static final String SIGN = "sign";
        public static final String PASSWORD = "password";
        public static final String NEW_PASSWORD = "newPassword";
        public static final String OP_PASSWORD = "opPassword";

        public static final String MONEY_TYPE="moneyType";
        public static final String EXCHANGE_TYPE="exchangeType";
        public static final String POSITION_STR="positionStr";
        public static final String STOCK_CODE="stockCode";

        public static final String FILTER_TYPE="filterType";


        public static final String SESSION_ID = "sessionId";
        public static final String SESSION_TYPE = "sessionType";
        public static final String LOGIN_IP = "loginIp";

        public static final String TOKEN = "token";
        public static final String OP_STATION= "opStation";
        public static final String REQUEST_NUM = "requestNum";
        public static final String  ENTRUST_STATUS="entrustStatus";
        public static final String BCAN = "bcan";
        public static final String ISFILTER = "isFilter";
        public static final String ENTRUSTTYPE="entrustType";
        public static final String ASSET_ID="assetId";
        public static final String USER_ID="userId";
        public static final String DISC_TYPE = "discType";
    }

    /**
     * 币种类型
     * @author sunline
     */
    public interface MoneyType {
        String USD = "1";
        String HKD = "2";
    }

    public interface FrozenReason {
        String COMMON_BLOCK = "0";
        String JUDICIAL_BLOCK = "1";
        String OTHER_BLOCK = "3";

    }

    public interface ExchageType{
        String EXCHANGE_TYPE_HK = "K";
        String EXCHANGE_TYPE_US = "P";
        String EXCHANGE_TYPE_ML = "M";
        // A股
        String EXCHANGE_TYPE_TV = "tv";
    }

    public enum IpoFinanceQueueStatus {
        STS_1(1,"排队中"),
        STS_2(2,"已完成"),
        STS_3(3,"已撤回"),
        STS_4(4,"额度不足"),
        STS_5(5,"柜台撤回") // 从柜台撤单后，更新排队队列，避免影响到排队队列里的额度统计
        ;
        public int value;
        public String description;

        IpoFinanceQueueStatus(int value,String description) {
            this.value = value;
            this.description = description;
        }
    }


    public enum DirectionEnum {
        UBY("1","买入"),
        SELL("2","卖出")
        ;
        private String code;
        private String desc;
        DirectionEnum(String code,String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode(){
            return code;
        }
    }

    public enum TradeAuthEnum {
        T1("T1","买港股"),
        T2("T2","卖港股"),
        T3("T3","买美股"),
        T4("T4","卖美股"),
        T5("T5","买A股通"),
        T6("T6","卖A股通"),
        T11("T11","买衍生品"),
        T12("T12","卖衍生品")
        ;
        private String code;
        private String desc;
        TradeAuthEnum(String code,String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode(){
            return code;
        }
    }

    public enum StockTypeEnum {
        WARRANT("90","窝轮"),
        CBBC0("100","牛熊证"),
        CBBC1("101","牛熊证"),
        CBBC2("102","牛熊证")
        ;
        private String code;
        private String desc;
        StockTypeEnum(String code,String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode(){
            return code;
        }
    }


}
