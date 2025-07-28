package com.minigod.zero.trade.afe.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

/**
 * @author chen
 * @ClassName WebSocketClientConfig.java
 * @Description TODO
 * @createTime 2024年04月19日 14:58:00
 */
@Configuration
@EnableWebSocket
@ConditionalOnProperty(value="trade.type",havingValue = MULTI_SERVER_TYPE_AFE)
public class WebSocketClientConfig {

	@Bean
	public WebSocketClient webSocketClient() {
		return new StandardWebSocketClient();
	}

	@Bean
	public WebSocketHandler webSocketHandler() {
		return new AfeWebSocketHandler();
	}
}
