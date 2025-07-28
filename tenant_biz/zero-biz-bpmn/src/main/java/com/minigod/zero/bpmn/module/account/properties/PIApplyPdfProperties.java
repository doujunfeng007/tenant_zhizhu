package com.minigod.zero.bpmn.module.account.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PI 申请相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "minigod.tenant.pi")
public class PIApplyPdfProperties {
	/**
	 * 占位符路径
	 */
	private String placePath;
	/**
	 * 模板路径
	 */
	private String templatePath;
	/**
	 * 不同租户的模板
	 */
	Map<String, PIPDFTemplate> tenantMap;
}
