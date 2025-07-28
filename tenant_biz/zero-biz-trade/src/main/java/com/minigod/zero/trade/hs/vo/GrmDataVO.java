package com.minigod.zero.trade.hs.vo;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.trade.hs.constants.ErrorMsgHandler;
import com.minigod.zero.trade.hs.constants.GrmConstants;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:30 PM
 */
public class GrmDataVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 返回码
     */
    private String errorId = GrmConstants.GRM_RESPONSE_OK;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 通知类消息
     */
    private String notifyMsg;
    /**
     * String
     */
    private List<Map<String,String>> result;

    public String getErrorId() {
        return errorId;
    }

    public GrmDataVO setErrorId(String errorId) {
        this.errorId = errorId;
        return  this;
    }

    public GrmDataVO setError(String errorId,String lang) {
        this.errorId = errorId;
        this.errorMsg = ErrorMsgHandler.getErrorMsg(errorId,lang);
        return  this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public GrmDataVO setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return  this;
    }

    public String getNotifyMsg() {
        return notifyMsg;
    }

    public GrmDataVO setNotifyMsg(String notifyMsgId,String lang,String[] params) {
        String msg = ErrorMsgHandler.getErrorMsg(notifyMsgId,lang);
        this.notifyMsg = notifyMsg;
        return this;
    }

    public List<Map<String,String>> getResult() {
        return result;
    }

    public GrmDataVO setResult(List<Map<String,String>> result) {
        this.result = result;
        return this;
    }

    private GrmDataVO(){
        setError(GrmConstants.GRM_RESPONSE_OK,GrmConstants.GRM_RESPONSE_OK);
    };

    public static GrmDataVO getInstance(){
        return new GrmDataVO();
    }

    @Override
    public String toString() {
        return JSONObject.toJSON(this).toString();
    }
}
