package com.minigod.zero.biz.common.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * @author huangwei
 * @Description 交易类型
 * @mail h549866023@qq.com
 * @date 2022/11/24 14:54
 **/
public enum ETradeType {
    //无成类型
    NONE(0, ""),
    //竞价成交
    PRE_OPEN_TRANSACTION_(4, "P"),
    //非自动对盘或特别买卖单位的非两边客成交
    NON_DIRECT_TRANSACTION(22, "M"),
    //自动对盘的两边客成交
    AUTOMATCH_DIRECT_TRANSACTION(100, "Y"),
    //非自动对盘或特别买卖单位的两边客成交
    DIRECT_TRANSACTION(101, "X"),
    //碎股成交
    ODD_LOT_TRANSACTION(102, "D"),
    //开市前成交（指交易在开市前已达成，其中包括在上个交易日收市前达成而未及输入系统的成交
    AUCTION_TRANSACTION(103, "U");
    //自动对盘的非两边客成交
    //AUTOMATCH_NON_DIRECT_TRANSACTION(103, " ");
    //成交已遭反驳/取消
    //REJECTED_OR_CANCELLED_TRANSACTION("*");


    private static Map<Integer, String> eTradeTypeMap = new HashMap<>();
    private int trdTypeKey;
    private String trdTypeVal;

    ETradeType(int trdTypeKey, String trdTypeVal) {
        this.trdTypeKey = trdTypeKey;
        this.trdTypeVal = trdTypeVal;
    }

    static {
        ETradeType[] eTradeTypeVals = ETradeType.values();
        for (ETradeType eTradeType : eTradeTypeVals) {
            eTradeTypeMap.put(eTradeType.getTrdTypeKey(), eTradeType.getTrdTypeVal());
        }
    }

    public int getTrdTypeKey() {
        return trdTypeKey;
    }

    public String getTrdTypeVal() {
        return trdTypeVal;
    }

    public static String getETradeTypeByTrdTypeKey(Integer trdTypeKey) {
        return eTradeTypeMap.get(trdTypeKey);
    }
}
