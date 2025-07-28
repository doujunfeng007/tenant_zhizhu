package com.minigod.zero.trade.sjmb.common;

/**
 * @author chen
 * @ClassName MessageCode.java
 * @Description 随机漫步柜台code
 */
public enum MessageCode {
    SUCCESS("0000", "成功", "成功", "成功"),
	NEED_LOGIN("310801", "请交易解锁", "请交易解锁", "please login"),



    PARAMS_ERROR("400", "参数错误", "参数错误", "参数错误");

    private final String code;
    private final String simplifiedChinese;
    private final String traditionalChinese;
    private final String english;

    MessageCode(String code, String simplifiedChinese, String traditionalChinese, String english) {
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

