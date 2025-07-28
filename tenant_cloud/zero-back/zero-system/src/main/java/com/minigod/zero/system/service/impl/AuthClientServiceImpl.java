
package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.system.entity.AuthClient;
import com.minigod.zero.system.mapper.AuthClientMapper;
import com.minigod.zero.system.service.IAuthClientService;
import com.minigod.zero.system.vo.req.AuthClientReq;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AuthClientServiceImpl extends BaseServiceImpl<AuthClientMapper, AuthClient> implements IAuthClientService {

	@Override
	public List<AuthClient> getZeroClientList() {
		return new LambdaQueryChainWrapper<>(baseMapper).list();
	}

	@Override
	public List<AuthClient> getListByClientIds(List<String> clientIds) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.in(AuthClient::getClientId, clientIds)
			.list();
	}

	@Override
	public AuthClient getByClientId(String clientId) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(AuthClient::getClientId, clientId)
			.one();
	}

	@Override
	public List<AuthClient> getClientList(AuthClientReq req) {
		List<AuthClient> authClients = baseMapper.selectList(Condition.getQueryWrapper(req));
		if (req.getFilterZhiZhu() == null || req.getFilterZhiZhu() == Boolean.TRUE){
			authClients = authClients.stream().filter(item -> !item.getClientId().contains("zhizhu")).collect(Collectors.toList());
		}
		return authClients;
	}
}
