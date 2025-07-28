package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.AuthClient;
import com.minigod.zero.system.service.IAuthClientService;
import com.minigod.zero.core.tenant.annotation.NonDS;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class ZeroClientClient implements IZeroClientClient {

	private final IAuthClientService authClientService;

	@Override
	public List<AuthClient> getZeroClientList() {
		return authClientService.getZeroClientList();
	}

	@Override
	public List<AuthClient> getListByClientIds(List<String> clientIds) {
		return authClientService.getListByClientIds(clientIds);
	}

	@Override
	public AuthClient getByClientId(String clientId) {
		return authClientService.getByClientId(clientId);
	}
}
