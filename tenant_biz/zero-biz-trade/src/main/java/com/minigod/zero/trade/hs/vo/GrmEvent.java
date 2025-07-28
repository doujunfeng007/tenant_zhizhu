package com.minigod.zero.trade.hs.vo;


import java.util.Date;

/**
 * Created by sunline on 2016/4/11 15:08.
 * sunline
 */
public class GrmEvent {
    private EType eventType;
    private Date dateTime;
    private String funcitonId;
    private GrmDataVO extra;

    private GrmEvent(){};

    public static GrmEvent getInstance(){
        return new GrmEvent();
    }

    public EType getEventType() {
        return eventType;
    }

    public GrmEvent setEventType(EType eventType) {
        this.eventType = eventType;
        return  this;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public GrmEvent setDateTime(Date dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getFuncitonId() {
        return funcitonId;
    }

    public GrmEvent setFuncitonId(String funcitonId) {
        this.funcitonId = funcitonId;
        return this;
    }

    public GrmDataVO getExtra() {
        return extra;
    }

    public GrmEvent setExtra(GrmDataVO extra) {
        this.extra = extra;
        return this;
    }

    public static enum EType {
        ERROR_RETURN,
        SUCESSE_RETURN,
    }
}
