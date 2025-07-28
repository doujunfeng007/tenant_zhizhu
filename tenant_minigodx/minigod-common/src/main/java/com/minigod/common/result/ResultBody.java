package com.minigod.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBody<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;
    /**
     * 响应编码
     */
    private int code = 200;
    /**
     * 相应状态
     */
    private boolean success;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 响应时间
     */
    private long timestamp = System.currentTimeMillis();

    private ResultBody(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }

    public static<T> ResultBody success(T data){
        return new ResultBody(ResultCode.SUCCESS.getCode(), data, ResultCode.SUCCESS.getMessage());
    }

    public static<T> ResultBody success(){
        return new ResultBody(ResultCode.SUCCESS.getCode(), null, ResultCode.SUCCESS.getMessage());
    }

    public static ResultBody fail(String errorMessage){
        return new ResultBody(ResultCode.FAIL.getCode(),null,errorMessage);
    }

    public static ResultBody fail(){
        return new ResultBody(ResultCode.FAIL.getCode(),null,ResultCode.FAIL.getMessage());
    }
}
