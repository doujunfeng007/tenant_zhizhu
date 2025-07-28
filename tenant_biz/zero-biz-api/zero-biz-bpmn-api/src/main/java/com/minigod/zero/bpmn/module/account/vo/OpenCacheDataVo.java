package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: OpenCacheDataVo
 * @Description:
 * @Author chenyu
 * @Date 2024/1/31
 * @Version 1.0
 */
@Data
public class OpenCacheDataVo {
    private Integer lastStep;
    private Map cacheInfos;
    private List<OpenImgVo> cacheImages;
}
