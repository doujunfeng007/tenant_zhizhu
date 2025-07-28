package com.minigod.zero.dbs.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.feign.intercept.DbsFeignRequestInterceptor;
import com.minigod.zero.dbs.service.DbsCallBackApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 回调解密报文回传业务系统
 */
@ConditionalOnProperty(prefix = "bank.dbs", name = "enabled", havingValue = "true")
@FeignClient(
        value = "zero-biz-bpmn",
        //url = "${bank.dbs.callbackUrl}",
        //设置所属租户 ID 业务系统识别租户报文
        configuration = DbsFeignRequestInterceptor.class
)
public interface DbsCallbackClient {

    /**
     * icn
     *
     * @param body 解密的 JSON
     * @return
     */
    @PostMapping(value = AppConstant.PROXY_API_PREFIX + DbsCallBackApi.PATH+DbsCallBackApi.ICN, consumes = APPLICATION_JSON_VALUE)
    R icn(@RequestBody String body);

    /**
     * idn
     *
     * @param body 解密的 JSON
     * @return
     */
    @PostMapping(value = AppConstant.PROXY_API_PREFIX + DbsCallBackApi.PATH+DbsCallBackApi.IDN, consumes = APPLICATION_JSON_VALUE)
    R idn(@RequestBody String body);

    /**
     * eddaAck2
     *
     * @param body 解密的 JSON
     * @return
     */
    @PostMapping(value = AppConstant.PROXY_API_PREFIX + DbsCallBackApi.PATH+DbsCallBackApi.EDDA_ACK2, consumes = APPLICATION_JSON_VALUE)
    R eddaAck2(@RequestBody String body);

    /**
     * remitAck
     *
     * @param body 解密的 JSON
     * @return
     */
    @PostMapping(value = AppConstant.PROXY_API_PREFIX + DbsCallBackApi.PATH+DbsCallBackApi.REMIT_ACK, consumes = APPLICATION_JSON_VALUE)
    R remitAck(@RequestBody String body);


}
