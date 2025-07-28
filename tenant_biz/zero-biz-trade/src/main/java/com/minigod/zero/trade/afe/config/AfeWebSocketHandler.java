package com.minigod.zero.trade.afe.config;

import static com.minigod.zero.trade.afe.common.AfeConstants.ORDER_MSG_ID;
import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.afe.common.AfeConstants;
import com.minigod.zero.trade.afe.push.OrderPushVO;
import com.minigod.zero.trade.afe.push.TradePushMsgProducer;
import com.minigod.zero.trade.afe.resp.CommonResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName AfeWebSocketHandler.java
 * @Description TODO
 * @createTime 2024年04月19日 15:00:00
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_AFE)
public class AfeWebSocketHandler implements WebSocketHandler {

	@Autowired
	private AfeMessageStorage afeMessageStorage;

	@Resource
	private ZeroRedis zeroRedis;

	private static TradePushMsgProducer tradePushMsgProducer;

	@Value("${trade.pushOpen:false}")
	private boolean isTradePushOpen;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		/**
		 * 连接建立后的处理逻辑
		 */
		log.info("连接websocket成功===============,{}", session.getId());

	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		/**
		 * 接收到消息的处理逻辑
		 */
		log.info("接收到的消息为session={},消息内容={}", session.getId(), message.getPayload());
		CommonResponse response = JSONUtil.fromJson(message.getPayload().toString(), CommonResponse.class);
		if (null != response) {
			if (ORDER_MSG_ID.equals(response.getMsgId())) {
				// 订单成交推送消息
				OrderPushVO orderPushVO = JSONUtil.fromJson(message.getPayload().toString(), OrderPushVO.class);
				try {
					notifyTradeMessage(session.getId(), orderPushVO);
				} catch (Exception e) {
					log.error("处理消息推送异常", e);
				}
				return;
			}
			String fullKey = response.getReqId();
			if (StringUtils.isEmpty(fullKey)) {
				log.info("消息推送未找到请求ID");
				return;
			}
			CompletableFuture<CommonResponse> future = afeMessageStorage.getMsgIdFutures().get(fullKey);
			if (future != null) {
				future.complete(response);
				return;
			}
		}
	}

	private void notifyTradeMessage(String sessionId, OrderPushVO orderPushVO) {
		if (!isTradePushOpen) {
			log.info("消息推送已关闭");
			return;
		}
		String side = orderPushVO.getSide();
		String status = orderPushVO.getStatus();
		boolean buySell = "B".equals(side) || "S".equals(side);
		/**
		 * PF 部分成交 FF 全部成交
		 */
		boolean hasFinished = "PF".equals(status) || "FF".equals(status);
		if (buySell && hasFinished) {
			String tradeAccount = afeMessageStorage.getTradeAccountInfo().get(sessionId);
			if (StringUtils.isNotBlank(tradeAccount)) {
				orderPushVO.setTradeAccount(tradeAccount);
				tradePushMsgProducer.sendMessage(orderPushVO);

			}

		}

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		/**
		 * 发生传输错误的处理逻辑
		 */
		log.info("发生传输错误的处理逻辑sessionId： {}", session.getId());
		log.info("发生传输错误的处理逻辑{}", exception);


	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("WebSocket连接关闭：sessionId={}, status={}, closeCode={}, reason={}, connectDuration={}ms",
			session.getId(),
			closeStatus,
			closeStatus.getCode(),
			closeStatus.getReason(),
			System.currentTimeMillis() - (Long) session.getAttributes().getOrDefault("connectTime", System.currentTimeMillis())
		);
		try {
			closeDeal(session);
		} catch (Exception e) {
			log.error("关闭连接异常", e);
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	private void closeDeal(WebSocketSession session) {
		String tradeAccount = afeMessageStorage.getTradeAccountInfo().get(session.getId());
		log.info("关闭的会话为={},所属交易账号为{}", session.getId(), tradeAccount);
		if (StringUtils.isNotBlank(tradeAccount)) {
			afeMessageStorage.getTradeAccountInfo().remove(session.getId());
		}
		if (!afeMessageStorage.getUserSessions().isEmpty()) {
			for (String key : afeMessageStorage.getUserSessions().keySet()) {
				log.info("剩下用户交易账号为：{}，会话id为：{}",key,afeMessageStorage.getUserSessions().get(key).getId());
			}
		}

		if (!afeMessageStorage.getTradeAccountInfo().isEmpty()) {
			log.info("收到关闭连接后交易账号存储数据,{}", cn.hutool.json.JSONUtil.toJsonStr(afeMessageStorage.getTradeAccountInfo()));
		}
	}

}
