
package com.minigod.zero.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minigod.zero.gateway.provider.ResponseProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 异常处理
 *
 * @author Chill
 */
@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class ErrorExceptionHandler implements ErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		log.error("Handle Error: " + ex);

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		if (ex instanceof ResponseStatusException) {
			response.setStatusCode(((ResponseStatusException) ex).getStatus());
		}
		return response.writeWith(Mono.fromSupplier(() -> {
			DataBufferFactory bufferFactory = response.bufferFactory();
			try {
				int status = 500;
				if (response.getStatusCode() != null && HttpStatus.OK != response.getStatusCode()) {
					status = response.getStatusCode().value();
				}
				if (ex instanceof ServerWebInputException) {
					status = 400;
				}
				Map<String, Object> result = ResponseProvider.response(status, this.buildMessage(request, status, ex));
				return bufferFactory.wrap(objectMapper.writeValueAsBytes(result));
			} catch (JsonProcessingException e) {
				return bufferFactory.wrap(new byte[0]);
			}
		}));
	}


	/**
	 * 构建异常信息
	 */
	private String buildMessage(ServerHttpRequest request, int code, Throwable ex) {
		/*String uri = request.getURI().toString();
		if (uri.endsWith("doc.html")) {
			return "[Swagger聚合网关] 已迁移至 [zero-swagger] 服务，请开启 [zero-swagger] 服务并访问 [http://127.0.0.1:18000/doc.html]";
		}*/
		StringBuilder message = new StringBuilder("Failed to handle request [");
		message.append(request.getMethodValue());
		message.append(" ");
		message.append(request.getURI());
		message.append("]");
		if (ex != null) {
			message.append(": ");
			if (code == 404) {
				message.append("地址有误或服务维护中");
			} else {
				message.append(ex.getMessage());
			}
		}
		return message.toString();
	}

}
