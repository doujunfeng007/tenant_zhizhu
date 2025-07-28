
package com.minigod.zero.biz.common.config;


import com.minigod.zero.biz.common.props.MaintenanceProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 公共封装包配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties({MaintenanceProperties.class})
public class ZeroCommonConfiguration {

}
