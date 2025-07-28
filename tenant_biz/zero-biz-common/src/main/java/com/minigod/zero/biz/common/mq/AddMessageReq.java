package com.minigod.zero.biz.common.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @author BPM_support2
 * @date 2021-12-21
 * @description 发送RocketMQ消息请求实体类
 */
@Data
public class AddMessageReq implements Serializable {

    private String topic;

    private String tag;

    private String message;

}
