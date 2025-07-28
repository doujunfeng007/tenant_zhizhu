package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据同步缓存
 */
@Data
public class SyncDataCache implements Serializable {

    private String type;//数据同步业务
    private Date last; //上次更新时间

}
