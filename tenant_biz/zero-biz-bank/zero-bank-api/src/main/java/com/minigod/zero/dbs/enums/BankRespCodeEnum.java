package com.minigod.zero.dbs.enums;

/**
 * 消息响应码定义
 *
 */
public class BankRespCodeEnum {

    /**
     * 公共模块相关
     */
    public enum ErrorCodeType {

        OK(0, "调用成功"),
        ERROR(1, "error"),
        NONE_DATA(201, "无满足条件的数据"),
        EXIST_ERROR(300, "有重复值存在"),
        PARAMETER_DISMATCH(301, "参数不匹配"),
        PARAMETER_ERROR(400, "参数错误"),
        PARAMS_PARAMETER_ERROR(401, "params参数错误"),
        SING_PARAMETER_ERROR(402, "签名参数SIGN错误"),
        KEY_PARAMETER_ERROR(403, "签名参数KEY错误"),
        SESSION_PARAMETER_ERROR(405, "参数SESSION_ID错误"),
        SOCKET_ERROR(404, "网络异常"),
        INTERNAL_ERROR(500, "请求异常，请重试"),
        UNBIND_WECHAT_ACC(600, "未绑定微信账号"),
        SIGN_ERROR(889, "签名错误"),
        ERROR_UNKNOWN(9999, "未知错误"),
        CONNECT_TIMEOUT(5001, "请求银行网关异常"),
        ERROR_DECRYPT(5002, "报文验签解密异常"),
        SYSTEM_ERROR(5003, "系统发生异常");

        private int code;
        private String message;

        ErrorCodeType(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getRetCode(String svc){
            return "ERR_" + svc + getCode();
        }

        public static ErrorCodeType getTypeEnum(int code) {
            for (ErrorCodeType typeEnum : ErrorCodeType.values()) {
                if (typeEnum.getCode() == code) {
                    return typeEnum;
                }
            }
            return null;
        }

    }

}
