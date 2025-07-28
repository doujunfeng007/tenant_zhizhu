package com.minigod.zero.biz.common.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-08-22 14:06
 * @Description: 接口维护配置
 */
@Data
@RefreshScope
@ConfigurationProperties("zero.maintenance")
public class MaintenanceProperties {
	/**
	 * 维护接口配置
	 */
	private final List<String> url = new ArrayList<>();

	/**
	 * 维护开始时间 格式：yyyyMMddHHmmss，例：20230819080000
	 */
	private String startTime = "";

	/**
	 * 维护结束时间 格式：yyyyMMddHHmmss，例：20230819080000
	 */
	private String endTime = "";

	/**
	 * 英文消息
	 */
	private String msgEn = "";

	/**
	 * 简体消息
	 */
	private String msgHans = "";

	/**
	 * 繁体消息
	 */
	private String msgHant = "";
}
