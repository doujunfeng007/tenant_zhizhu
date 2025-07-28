package com.minigod.zero.trade.afe.common;

/**
 * @author chen
 * @ClassName MessageCode.java
 * @Description AFE 柜台返回的code
 */
public enum AfeMessageCode {


    SUCCESS("OK", "成功", "成功", "成功"),
	NEED_LOGIN("SEE505", "请交易解锁", "请交易解锁", "please login"),
	SESSION_INVALID("SEE520", "您的帳戶已被重覆登入", "您的帳戶已被重覆登入", "please login"),



    PARAMS_ERROR("400", "参数错误", "参数错误", "参数错误");

    private final String code;
    private final String simplifiedChinese;
    private final String traditionalChinese;
    private final String english;

    AfeMessageCode(String code, String simplifiedChinese, String traditionalChinese, String english) {
        this.code = code;
        this.simplifiedChinese = simplifiedChinese;
        this.traditionalChinese = traditionalChinese;
        this.english = english;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        // 默认简体
        return simplifiedChinese;
    }
}

