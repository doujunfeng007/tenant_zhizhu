package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.system.service.ITradeKeyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class TradeKeyClient implements ITradeKeyClient{

	@Resource
	ITradeKeyService tradeKeyService;


	@Override
	public TradeKey getTradeKeyByTenantId(String tenantId,String counterType) {
		return tradeKeyService.getByTenantId(tenantId,counterType);
	}

}
