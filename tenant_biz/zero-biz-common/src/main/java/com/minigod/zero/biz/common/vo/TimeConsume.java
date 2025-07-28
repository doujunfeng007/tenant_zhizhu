package com.minigod.zero.biz.common.vo;

import java.util.Date;

public class TimeConsume {
    private Date begin ;
    public Date startTimming(){
        begin = new Date();
        return begin;
    }

    public int endAndGetInterval(){
        return (int)(new Date().getTime() - begin.getTime());
    }

    private TimeConsume(){

    }

    public static TimeConsume getInstance(){
        return new TimeConsume();
    }
}
