package com.minigod.zero.biz.common.utils;

import lombok.Data;

import java.util.Date;

/**
 * 时区
 *
 * @author rdp
 */
@Data
public class TimeZoneVO {
    /**
     * 夏令时开始时间
     */
    private Date summerStartTime;
    /**
     * 冬令时开始时间
     */
    private Date summerEndTime;

}
