package com.minigod.zero.trade.hs.filter;

import com.minigod.zero.trade.hs.constants.EGrmServerType;
import com.minigod.zero.trade.hs.resp.GrmServerEntity;
import com.minigod.zero.trade.hs.resp.GrmServerKeyEntity;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by sunline on 2016/5/19 17:37.
 * sunline
 */
@Component
public class GrmServerHolder {
//	@Resource
//	private IGrmServerService grmServerService;
//	@Resource
//	private IGrmServerKeyService grmServerKeyService;

	private Map<String , GrmServerEntity> grmSrvs = new HashMap();

	private Map<Integer, GrmServerKeyEntity> grmSrvKeys = new HashMap<>();

	private Set<String> clientSideSids = new HashSet<>();


	@PostConstruct
	private void initLocalCash(){
		if(MapUtils.isEmpty(grmSrvs)){
			List<GrmServerEntity> allServer = null;//grmServerService.getAllServer(); TODO
			if(null != allServer){
				GrmServerEntity server ;
				for(Iterator<GrmServerEntity> it = allServer.iterator();it.hasNext();){
					server = it.next();
					grmSrvs.put(server.getServerCode(),server);
					if(server.getServerType() == EGrmServerType.INNER_CLIENT.getTypeId()){
						clientSideSids.add(server.getServerCode());
					}
				}
			}
		}
		if(MapUtils.isEmpty(grmSrvKeys)){
			List<GrmServerKeyEntity> allSrvKey = null;//grmServerService.getAllServer(); TODO
			if(null != allSrvKey){
				GrmServerKeyEntity serverKey ;
				for(Iterator<GrmServerKeyEntity> it = allSrvKey.iterator();it.hasNext();){
					serverKey = it.next();
					grmSrvKeys.put(serverKey.getServerId(),serverKey);
				}
			}
		}
	}

	public Map<String, GrmServerEntity> getGrmSrvs() {
		return grmSrvs;
	}

	public Map<Integer, GrmServerKeyEntity> getGrmSrvKeys() {
		return grmSrvKeys;
	}

	public Set<String> getClientSideSids() {
		return clientSideSids;
	}

	/**
	 * 检查server是否已登记
	 * @return
	 */
	public boolean isVaildServerCode(String sid){
		return grmSrvs.containsKey(sid);
	}

	public GrmServerEntity getServerBySid(String sid){
		return grmSrvs.get(sid);
	}

	public GrmServerKeyEntity getServerKeyByServer(GrmServerEntity grmServer){
		if(null != grmServer){
			return grmSrvKeys.get(grmServer.getId());
		}
		return null;
	}

	public GrmServerKeyEntity getServerKey(String sid){
		if(StringUtils.isNotEmpty(sid)){
			GrmServerEntity grmServer = grmSrvs.get(sid);
			if(null != grmServer){
				return grmSrvKeys.get(grmServer.getId());
			}
		}
		return null;
	}

	public boolean isInnerClient(String sid){
		// TODO return clientSideSids.contains(sid);
		return true;
	}




}
