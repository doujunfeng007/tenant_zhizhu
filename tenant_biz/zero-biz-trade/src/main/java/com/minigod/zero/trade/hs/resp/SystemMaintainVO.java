package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 系统维护模式配置
 * @author sunline
 */
public class SystemMaintainVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**维护模式 0关闭 1开启*/
    private Integer maintainFlag;
    /**维护开始时间*/
    private String startTime;
    /**维护截止时间*/
    private String endTime;
    /**用户处于维护模式白名单*/
    private Boolean userInWhiteList;

    public Integer getMaintainFlag() {
        return maintainFlag;
    }

    public void setMaintainFlag(Integer maintainFlag) {
        this.maintainFlag = maintainFlag;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getUserInWhiteList() {
        return userInWhiteList;
    }

    public void setUserInWhiteList(Boolean userInWhiteList) {
        this.userInWhiteList = userInWhiteList;
    }
}
