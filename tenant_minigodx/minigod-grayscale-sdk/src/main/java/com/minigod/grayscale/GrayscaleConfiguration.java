package com.minigod.grayscale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: guangjie.liao
 * @Date: 2024/4/14 15:40
 * @Description: 配置fegin调用策略
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
public class GrayscaleConfiguration {

	@Value("${spring.application.name}")
	private String serverName;

	@Bean
	@ConditionalOnMissingBean
	public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(LoadBalancerClientFactory loadBalancerClientFactory) {
		return new GrayscaleServiceInstanceLoadBalancer(loadBalancerClientFactory.getLazyProvider(serverName, ServiceInstanceListSupplier.class));
	}
}
