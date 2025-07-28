package com.minigod.zero.bpmn.module.account.bo;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName: OpenCacheInfoBo
 * @Description:
 * @Author chenyu
 * @Date 2024/1/31
 * @Version 1.0
 */
@Data
public class OpenCacheInfoBo {
    private Integer openType; // 开户类型
    private Integer step;
    private Map info;
}
