package com.minigod.zero.biz.common.mkt.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 可认购新股缓存
 */
@Data
public class ApplyCanCache implements Serializable {

    private List<ApplyCanVO> canApplyList;

}
