
package com.minigod.zero.biz.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * 客户端信息
 *
 * @author Chill
 */
@Service
public class ZeroClientDetailsServiceImpl extends JdbcClientDetailsService {

	public ZeroClientDetailsServiceImpl(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 缓存客户端信息
	 *
	 * @param clientId 客户端id
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) {
		try {
			return super.loadClientByClientId(clientId);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
