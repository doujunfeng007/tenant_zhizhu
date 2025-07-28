package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 可认购新股缓存
 */
@Data
public class PredictApplyCanCache implements Serializable {

    private List<PredictApplyCanVO> predictApplyCanVOS;
}
