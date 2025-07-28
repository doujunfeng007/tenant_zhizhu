
package com.minigod.zero.system.config;


import com.minigod.zero.system.handler.ApiScopePermissionHandler;
import com.minigod.zero.system.handler.DataScopeModelHandler;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.datascope.handler.ScopeModelHandler;
import com.minigod.zero.core.secure.config.RegistryConfiguration;
import com.minigod.zero.core.secure.handler.IPermissionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 公共封装包配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@AutoConfigureBefore(RegistryConfiguration.class)
public class ScopeConfiguration {

	@Bean
	public ScopeModelHandler scopeModelHandler() {
		return new DataScopeModelHandler();
	}

	@Bean
	public IPermissionHandler permissionHandler() {
		return new ApiScopePermissionHandler();
	}

}
