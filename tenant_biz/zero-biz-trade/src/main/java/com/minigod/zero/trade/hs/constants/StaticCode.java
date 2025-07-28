package com.minigod.zero.trade.hs.constants;

/**
 * Created by sunline on 2016/4/26 11:14.
 * sunline
 */
public class StaticCode {
    /**重复下单校验分布式锁key*/
    public static final String TRADE_DISTRIBUTED_LOCK_KEY = "TRADE_DISTRIBUTED_LOCK_KEY:";

    public static final class ErrorCode{
        public static final String INVALID_SIGN_PARAM="EM0512000003";
        public static final String FUNCTON_NOT_EXIT ="EM0512000004";
        public static final String FUNCTION_INVOKE_FAILED="EM0512000005";
        public static final String TYPE_CAST_FAILED="EM0512000006";
        public static final String NULL_PARAMETER = "EM0512000001";
        public static final String CONNECT_TO_T2_FAILED = "EM0519000102";
        public static final String RECV_T2_MSG_FAILED = "EM0519000102";
        public static final String NULL_FUNCTION="EM0512000007";
        public static final String NULL_SID="EM0512000008";
        public static final String IP_INVALID="EM0512000009";
        public static final String FUNCTION_NOT_AUTHORIZED="EM0512000010";
        public static final String NULL_SIGN="EM0512000011";
        public static final String VALIDATE_SIGN_FAILED="EM0512000012";
        public static final String INVALID_REQUEST="EM0512000013";
        public static final String LOAD_Data_Failed ="EM0512000014";
        public static final String INVALID_SESSION_PARAM ="EM0512000015";
        public static final String SESSION_ERROR ="EM0512000016";
        public static final String INVALID_SID ="EM0512000017";
        public static final String NULL_SESSIONID="EM0512000018";
        public static final String SESSION_NOT_LOGIN="EM0512000019";
        public static final String INVALID_FUNDACCOUNT="EM0512000020";
        public static final String DECRYPTED_FAILED="EM0512000021";
        public static final String EXCEED_LICENSE_LIMIT="EM0512000022";
        public static final String TRADE_TEST_WITHOUT_TIME="EM0512000025";
        public static final String TRADE_TEST_WITH_TIME="EM0512000026";
        public static final String PASSWORD_CHECK_FAILED="EM0512000023";
        public static final String EMPTY_PARAMS="EM0512000024";
        public static final String NOT_IN_US_TRADE_TIME="EM0512000027"; // 不在美股交易段，不允许买卖
        public static final String NOT_SUPPORT_IPO_TRADE="EM0512000028"; // IPO阶段不支持交易

        public static final String FC_NOT_LOGIN="EM01900102";
        public static final String ERROR_SHOW_MSG = "EM000001"; // 接口执行失败，客户端直接显示服务端返回的MSG

        public static final String DISABLE_STRATEGY = "DISABLE_STRATEGY";// 禁用条件单提示code
        public static final String SERVER_BUSY="SERVER_BUSY";// 服务器忙提示code
        public static final String DUPLICATION_COMMIT="DUPLICATION_COMMIT";// 重复提交提示code
    }
}
