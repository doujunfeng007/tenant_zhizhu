package com.minigod.zero.bpmn.module.account.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: AccoountPdfPropertis
 * @Description:
 * @Author chenyu
 * @Date 2024/4/10
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "minigod.tenant.account")
public class AccountPdfPropertis {
    private String placePath;
    private String templacePath;
    Map<String,PDFTemplace> tenantMap;
}
