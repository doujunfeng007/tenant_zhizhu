package com.minigod.zero.bpmn.module.bank.feign.intercept;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import com.minigod.zero.bpmn.module.bank.properties.DbsGatewayProperties;
import com.minigod.zero.bpmn.module.bank.properties.DbsTenant;
import com.minigod.zero.core.log.exception.ServiceException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

/**
 * 银行回调实现 头部公共拦截设置
 *
 * @author chenyu
 * @title DbsFeignRequestInterceptor
 * @date 2023-04-12 20:05
 * @description
 */
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties({DbsGatewayProperties.class})
public class DbsGatewayFeignRequestInterceptor implements RequestInterceptor {
    private final DbsGatewayProperties dbsGatewayProperties;

    @Override
    public void apply(RequestTemplate template) {
        String body = new String(template.body());
        if (StringUtils.isNotBlank(body)) {
            DbsBaseRequestVO dbsBaseRequestVO = JSONObject.parseObject(body, DbsBaseRequestVO.class);
            if (StringUtils.isNotBlank(dbsBaseRequestVO.getTenantId())) {
                Map<String, DbsTenant> tenant = dbsGatewayProperties.getTenant();
                if (tenant.containsKey(dbsBaseRequestVO.getTenantId())) {
					String url = tenant.get(dbsBaseRequestVO.getTenantId()).getUrl();
					template.target(url);
					log.debug("请求 DBS 网关地址:{}", template.url());
                } else {
                    throw new ServiceException("请配置DBS网关地址");
                }
            } else {
                throw new ServiceException("租户ID为空");
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("请求 DBS 网关地址:{}", template.url());
            log.debug("请求 DBS 网关body:{}", body);
        }
    }
}
