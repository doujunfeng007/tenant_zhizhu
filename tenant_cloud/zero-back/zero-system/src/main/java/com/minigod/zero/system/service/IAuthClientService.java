
package com.minigod.zero.system.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.system.entity.AuthClient;
import com.minigod.zero.system.vo.req.AuthClientReq;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAuthClientService extends BaseService<AuthClient> {

	List<AuthClient> getZeroClientList();

    List<AuthClient> getListByClientIds(List<String> clientIds);

	AuthClient getByClientId(String clientId);

	List<AuthClient> getClientList(AuthClientReq req);
}
