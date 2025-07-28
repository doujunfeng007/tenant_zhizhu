package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取初始化日期、系统状态及说明
 *
 * @author sunline
 */
@Data
public class EF01100100VO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 系统初始化日期
	 */
	@JSONField(name="init_date")
	private String initDate;

	/**
	 * 系统运行状态（0：Stop Running，1：Running，2：System Testing，3：System Maintenance，4：Daily Clearing）
	 * 系统运行状态（0:停止运行，1:运行，2:系统测试，3:系统维护，4:日常清理）
	 */
	@JSONField(name="sys_status")
	private String sysStatus;

	/**
	 * 系统状态说明
	 */
	@JSONField(name="sys_status_name")
    private String sysStatusName;
}
