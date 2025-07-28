package com.minigod.zero.cms.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 剩余点击数
 */
@Data
public class ClientSurplusInfo implements Serializable {
	private int hkSurplusCount;
	private int usSurplusCount;
	private int cnSurplusCount;
	private Date updateTime;
}
