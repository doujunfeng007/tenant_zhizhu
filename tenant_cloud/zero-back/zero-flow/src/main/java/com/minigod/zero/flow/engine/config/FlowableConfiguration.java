
package com.minigod.zero.flow.engine.config;

import lombok.AllArgsConstructor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.spring.boot.FlowableProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties(FlowableProperties.class)
public class FlowableConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
	private final FlowableProperties flowableProperties;

	@Override
	public void configure(SpringProcessEngineConfiguration engineConfiguration) {
		engineConfiguration.setActivityFontName(flowableProperties.getActivityFontName());
		engineConfiguration.setLabelFontName(flowableProperties.getLabelFontName());
		engineConfiguration.setAnnotationFontName(flowableProperties.getAnnotationFontName());
	}

}
