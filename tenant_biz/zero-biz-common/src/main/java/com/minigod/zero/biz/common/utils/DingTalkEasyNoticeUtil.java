package com.minigod.zero.biz.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class DingTalkEasyNoticeUtil {

	public static Boolean sendNotification(String webHookUrl, String message) {
		HttpURLConnection connection = null;
		try {
			// 创建URL对象
			URL url = new URL(webHookUrl);

			// 创建HTTP连接
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setDoOutput(true);

			// 构建请求体
			String payload = "{\"msgtype\": \"text\", \"text\": {\"content\": \"" + message + "\"}}";
			byte[] data = payload.getBytes(StandardCharsets.UTF_8);
			connection.getOutputStream().write(data);

			// 发起请求并获取响应
			int responseCode = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				log.warn("Failed to send notification. Response Code: {}, Response Message: {}", responseCode, responseMessage);
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		} catch (IOException e) {
			log.error("dingTalkEasyNoticeUtil error", e);
		}finally {
			// 关闭连接
			if (connection != null) {
				connection.disconnect();
			}
		}

		return Boolean.FALSE;
	}
}
