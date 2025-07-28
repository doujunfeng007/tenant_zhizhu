package com.minigod.zero.cust.vo.icbc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 14:31
 * @Description: common
 */

@Data
public class Common implements Serializable {
	private static final long serialVersionUID = 1L;
	private String app_id;
	private String channel_id;
	private String trader_id;
	private String zone_no;
	private String lang;
}
