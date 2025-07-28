package com.minigod.zero.flow.core.service;

import com.minigod.zero.flow.core.entity.FlowNotify;

/**
 * @ClassName: FlowNotifyService
 * @Description: 所有通知业务实现这个类，并且通过字典配置 businessKey 对应的实现 beanName
 * @Author chenyu
 * @Date 2024/3/14
 * @Version 1.0
 */
public interface FlowNotifyService {
    /**
     * 通知回调
     * @param notify
     */
    void notify(FlowNotify notify);
}
