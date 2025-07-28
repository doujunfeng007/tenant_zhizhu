package com.minigod.zero.biz.common.mkt.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataSyncMsg implements Serializable {
	private String dataKey;
	private String dataJson;
	private Integer dataType;
	private Integer operatorType;
	private long updateTimes;
}
