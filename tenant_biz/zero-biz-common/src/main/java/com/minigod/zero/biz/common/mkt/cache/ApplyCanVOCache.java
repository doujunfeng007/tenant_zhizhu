package com.minigod.zero.biz.common.mkt.cache;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 可认购新股缓存
 */

@Data
public class ApplyCanVOCache implements Serializable {

    private List<ApplyCanVO> canApplyList;


}
