package com.minigod.zero.trade.hs.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:30 PM
 */
public class GrmResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 返回码,默认成功
     */
    protected String errorId = "EM0512000000";
    /**
     * 错误消息
     */
    protected String errorMsg = "success";
    /**
     * 通知类消息
     */
    protected String notifyMsg = "";
    /**
     * String
     */
    protected Map<String,List<? extends Object>> result ;


    public String getErrorId() {
        return errorId;
    }

    public GrmResponseVO setErrorId(String errorId) {
        this.errorId = errorId;
        return  this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public GrmResponseVO setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return  this;
    }

    public String getNotifyMsg() {
        return notifyMsg;
    }

    public GrmResponseVO setNotifyMsg(String notifyMsg) {
        this.notifyMsg = notifyMsg;
        return this;
    }

    public Map<String,? extends Object> getResult() {
        return result;
    }


    public GrmResponseVO setResult(Map<String,List<? extends Object>> result) {
         this.result = result;
        return this;
    }


    public GrmResponseVO setResult(List<? extends Object> data){
        if(null == this.result){
            this.result = new HashMap<String,List<? extends Object>>();
        }
        this.result.put("data",data);
        return this;
    }

    public GrmResponseVO addResultData(Object data){
        if(null == this.result){
            this.result = new HashMap();
        }
        if(data instanceof List){
            this.result.put("data",(List)data);
        }else {
            List dataList = this.result.get("data");
            if (null == dataList) {
                dataList = new ArrayList();
            }
            dataList.add(data);
            this.result.put("data", dataList);
        }
        return this;
    }

    public List<? extends Object> resultData(){
        if(null != this.result){
            return this.result.get("data");
        }
        return null;
    }

    public GrmResponseVO(){}

    public static GrmResponseVO getInstance(){
        return new GrmResponseVO();
    }

    @Override
    public String toString() {
        return "GrmResponseVO{" +
                "errorId='" + errorId + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", notifyMsg='" + notifyMsg + '\'' +
                ", result=" + result +
                '}';
    }
}
