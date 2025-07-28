package com.minigod.zero.bpmn.module.bank.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;

/**
 * @ClassName: DbsGatewayProperties
 * @Description:
 * @Author chenyu
 * @Date 2024/3/21
 * @Version 1.0
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "dbs.gateway")
public class DbsGatewayProperties {
    private Map<String, DbsTenant> tenant;
}
