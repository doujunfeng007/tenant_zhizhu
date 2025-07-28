package com.minigod.zero.customer.fegin;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.CustBrokerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author dell
 */
@FeignClient("minigod-broker")
public interface IBrokerClient {
    String API_PREFIX = "/client/broker";
    String BIND_CUST_BROKER = API_PREFIX+"/bindCustBroker";
    String INFO_BIND_TASK = API_PREFIX+"/infoBindTask";

    @PostMapping({BIND_CUST_BROKER})
	R bindCustBroker(@RequestBody CustBrokerDTO custBrokerDTO);
}
