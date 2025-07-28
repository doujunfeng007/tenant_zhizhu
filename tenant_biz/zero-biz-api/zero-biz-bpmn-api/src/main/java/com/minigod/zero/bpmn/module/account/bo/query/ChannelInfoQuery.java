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
public class ChannelInfoQuery {
    private String channelId;
    private String channelName;
    private String remark;
}
