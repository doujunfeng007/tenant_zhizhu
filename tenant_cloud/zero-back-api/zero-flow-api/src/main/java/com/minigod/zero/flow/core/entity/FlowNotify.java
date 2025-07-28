package com.minigod.zero.flow.core.entity;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName: FlowNotify
 * @Description: 通知回调实体类
 * @Author chenyu
 * @Date 2024/3/14
 * @Version 1.0
 */
@Data
public class FlowNotify {
    private String tenantId;
    private String node;
    private String businessKey;
    private String applicationId;
    private Map<String, Object> variables;
}
