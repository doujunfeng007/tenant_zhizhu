package com.minigod.zero.cms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 经纪人信息
 * sunline
 */
@Data
public class BrokerInfo implements Serializable{

	private static final long serialVersionUID = -9131264100638653497L;

	String brokerIds ; //经纪编号
	String brokerCode; //经纪代码
	String tcName;  //繁体简称
	String tcFullName; //繁体全称
	String scName;
	String scFullName;
	String enName;
	String enFullName;
	String brokerIdStr;//经纪编号串，用逗号分隔

	@Override
	public String toString() {
		return "BrokerInfo{" +
			"brokerIds=" + brokerIds +
			", brokerCode='" + brokerCode + '\'' +
			", tcName='" + tcName + '\'' +
			", tcFullName='" + tcFullName + '\'' +
			", scName='" + scName + '\'' +
			", scFullName='" + scFullName + '\'' +
			", enName='" + enName + '\'' +
			", enFullName='" + enFullName + '\'' +
			'}';
	}
}
