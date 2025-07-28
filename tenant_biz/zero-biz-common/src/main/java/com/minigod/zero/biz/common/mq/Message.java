package com.minigod.zero.biz.common.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 消息体
 * @Author: BPM_support2
 * @Date: 2021-12-21
 * @Version: 1.0.0
 */
@Data
public class Message<T> implements Serializable {
    private String id;
    private T content;
}
