package com.minigod.zero.trade.afe.config;

import com.minigod.zero.trade.afe.resp.CommonResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author chen
 * @ClassName MessageFuturesStorage.java
 * @Description 用户会话以及消息的存储
 * @createTime 2024年04月22日 16:59:00
 */
@Component
public class AfeMessageStorage {

	/**
	 * 请求接收消息的map
	 */
	private Map<String, CompletableFuture<CommonResponse>> msgIdFutures = new ConcurrentHashMap<>();

	/**
	 * 用户会话
	 */
	private ConcurrentMap<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

	/**
	 * 交易账号和会话的map
	 */
	private Map<String, String> tradeAccountInfo = new HashMap<>();

	/**
	 * 代理人登录状态缓存
	 */
	private Map<String, Boolean> agentLoginInfo = new HashMap<>();


	public Map<String, CompletableFuture<CommonResponse>> getMsgIdFutures() {
		return msgIdFutures;
	}

	public  ConcurrentMap<String, WebSocketSession> getUserSessions() {
		return userSessions;
	}

	public Map<String, String> getTradeAccountInfo() {
		return tradeAccountInfo;
	}
	public Map<String, Boolean> getAgentLoginInfo() {
		return agentLoginInfo;
	}
}
