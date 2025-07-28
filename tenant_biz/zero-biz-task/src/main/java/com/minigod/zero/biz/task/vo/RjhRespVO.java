package com.minigod.zero.biz.task.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @Classname RjhRespVO
 * @Description TODO
 * @Date 2022/4/12 14:27
 */
@Data
public class RjhRespVO implements Serializable {

    private static final long serialVersionUID = -4487856107394380916L;

    private String SUCCESS_CODE ="200";

    private String code;

    private String msg;

    private Object body;

    public boolean isSuccess() {

        if(SUCCESS_CODE.equals(code)){
            return true;
        }else{
            return false;
        }
    }
}
