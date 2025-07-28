package com.minigod.zero.bpmn.module.account.bo.query;

import lombok.Data;

/**
 * @ClassName: ChannelInfoQuery
 * @Description:
 * @Author chenyu
 * @Date 2022/8/31
 * @Version 1.0
 */
@Data
public class ActivityManInfoQuery {
    private String activityCode;
    private String activityName;
    private String description;
    private String url;
    private String brokerageCategory;
}
