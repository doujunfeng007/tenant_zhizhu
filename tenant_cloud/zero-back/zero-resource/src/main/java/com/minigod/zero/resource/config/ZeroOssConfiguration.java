
package com.minigod.zero.resource.config;

import com.minigod.zero.resource.service.IOssService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.oss.props.OssProperties;
import com.minigod.zero.resource.builder.oss.OssBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Oss配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class ZeroOssConfiguration {

	private final OssProperties ossProperties;

	private final IOssService ossService;

	@Bean
	public OssBuilder ossBuilder() {
		return new OssBuilder(ossProperties, ossService);
	}

}
