package com.minigod.zero.trade.afe.resp.back;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.Data;

/**
 * @author chen
 * @ClassName CommonResponse.java
 * @Description TODO
 * @createTime 2024年04月17日 10:51:00
 */
@Data
public class BackCommonResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息ID
	 */
	@JSONField(name ="RESPONSE")
	private Response response;

	@Data
	public static class Response {

		@JSONField(name = "RESULT")
		private Object result;

		@JSONField(name = "RETURNCODE")
		private ReturnCode returncode;

		@JSONField(name = "ID")
		private String id;


		@Data
		public static class ReturnCode {

			@JSONField(name = "SUCCESS")
			private Success success;


			private Map<String, Object> dynamicProperties  = new HashMap<>();

			@JsonAnySetter
			public void setDynamicProperty(String name, Object value) {
				this.dynamicProperties.put(name, value);
			}

			@Data
			public static class Success {

				@JSONField(name = "TIME")
				private Time time;

				@Data
				public static class Time {

					@JSONField(name = "content")
					private String content;

					@JSONField(name = "TYPE")
					private String type;
				}
			}
		}
	}


	/**
	 * 判断是否成功
	 * @return 如果 success 不为空则返回 true，否则返回 false
	 */
	public boolean isSuccess() {
		return response != null && response.returncode != null && response.returncode.success != null;
	}

	/**
	 * 获取错误消息
	 * @return
	 */
	public String getMessage() {
		String message ="系统异常";
		Map<String, Object> dynamicProperties =response.getReturncode().getDynamicProperties();
		if(dynamicProperties != null && dynamicProperties.size()>0){
			// 获取map的第一个key
			 message =dynamicProperties.keySet().iterator().next();
		}
		return message;
	}
}
