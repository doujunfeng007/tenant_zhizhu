package com.minigod.grayscale;

import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.minigod.zero.core.secure.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: guangjie.liao
 * @Date: 2024/4/14 16:10
 * @Description:
 */
@Slf4j
public class GrayscaleServiceInstanceLoadBalancer implements ReactorServiceInstanceLoadBalancer {
	private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

	/**
	 * @param serviceInstanceListSupplierProvider a provider of
	 * {@link ServiceInstanceListSupplier} that will be used to get available instances
	 */
	public GrayscaleServiceInstanceLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
	}

	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
			.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get(request).next()
			.map(serviceInstances -> processInstanceResponse(supplier, serviceInstances));
	}

	private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
															  List<ServiceInstance> serviceInstances) {
		Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
		if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
			((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
		}
		return serviceInstanceResponse;
	}

	private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
		if (instances.isEmpty()) {
			return new EmptyResponse();
		}
		List<ServiceInstance> servers = new ArrayList<>();
		for (ServiceInstance serverInfo : instances) {
			NacosServiceInstance nacosServer = (NacosServiceInstance) serverInfo;
			String gray = nacosServer.getMetadata().get("gray-server");
			if (!StringUtils.hasLength(gray)){
				servers.add(serverInfo);
			}else{
				if (Boolean.valueOf(gray) && AuthUtil.isWhiteList()){
					servers.add(serverInfo);
				}else{
					servers.add(serverInfo);
				}
			}
		}
		return getDefaultResponse(servers);
	}

	private DefaultResponse getDefaultResponse(List<ServiceInstance> servers){
		//打乱随机取一个，也可以设置其他策略
		Collections.shuffle(servers);
		ServiceInstance server = servers.get(0);
		return new DefaultResponse(server);
	}

}
