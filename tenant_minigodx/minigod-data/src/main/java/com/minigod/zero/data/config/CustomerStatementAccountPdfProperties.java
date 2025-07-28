package com.minigod.zero.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取货币pdf文件地址类
 *
 * @author zxq
 * @date  2024/5/22
 **/
@Data
@Component
@ConfigurationProperties(prefix = "report.customer.statement.account")
public class CustomerStatementAccountPdfProperties {
    private String placePath;
    Map<String, PDFTemplate> tenantMap;
	private String stockPath;
}
