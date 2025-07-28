
package com.minigod.zero.flow.launch;

import com.minigod.zero.common.constant.LauncherConstant;
import com.minigod.zero.core.auto.service.AutoService;
import com.minigod.zero.core.launch.service.LauncherService;
import com.minigod.zero.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author smallchil
 */
@AutoService(LauncherService.class)
public class LauncherTestServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.namespace", LauncherConstant.nacosNamespace(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.server-addr", LauncherConstant.nacosAddr(profile));
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.namespace", LauncherConstant.nacosNamespace(profile));

		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "false");
	}

}
