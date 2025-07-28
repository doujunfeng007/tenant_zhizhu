package com.minigod.zero.biz.common.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class TouristMessage implements Serializable {

	private String topic;

	private String tag;

	private String message;
}
