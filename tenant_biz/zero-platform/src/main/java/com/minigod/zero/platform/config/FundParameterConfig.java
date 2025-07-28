package com.minigod.zero.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/20 20:19
 * @description：
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(value = "thirdparty")
public class FundParameterConfig {
	private String fundOpenAccountUrl;
	private String selectMarketValueUrl;
	private String selectExchangeRateUrl;
	private String downloadConfirmationUrl;
	private String selectConfirmationUrl;
	private Integer channel;
	private String companyCode;
}
