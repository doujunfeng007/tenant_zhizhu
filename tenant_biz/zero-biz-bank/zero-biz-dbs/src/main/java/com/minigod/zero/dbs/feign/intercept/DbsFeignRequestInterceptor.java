package com.minigod.zero.dbs.feign.intercept;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.dbs.config.DbsBankConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 银行回调实现 头部公共拦截设置
 *
 * @author chenyu
 * @title DbsFeignRequestInterceptor
 * @date 2023-04-12 20:05
 * @description
 */
@Component
@AllArgsConstructor
@Slf4j
public class DbsFeignRequestInterceptor implements RequestInterceptor {

    private final DbsBankConfig dbsBankConfig;

    @Override
    public void apply(RequestTemplate template) {
        if (dbsBankConfig != null) {
            template.header("Tenant-Id", dbsBankConfig.getTenantId());
            template.header("Source-Type", "web");
        }

        log.info("DBS网关回调业务系统接口url:{}", template.feignTarget().url() + template.url());
        log.info("DBS网关回调业务系统接口header:{}", JSON.toJSONString(template.headers()));
        log.info("DBS网关回调业务系统接口body:{}", new String(template.body()));
    }
}
