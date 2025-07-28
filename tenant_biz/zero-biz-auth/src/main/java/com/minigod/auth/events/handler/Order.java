package com.minigod.auth.events.handler;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/8 9:27
 * @description：
 */
public interface Order {
	enum SUCCESS{
		DEL_FAILURE_NUM(1),
		OFFLINE_OTHER_DEVICES(2),
		SEND_EMAIL(3),
		SAVE_LOGIN_LOG(4);

		private Integer code;
		SUCCESS(Integer code){
			this.code = code;
		}

		public Integer getCode() {
			return code;
		}
	}

	enum FAILURE{
		FAILURE_PASSWORD_ERROR_COUNT(1);
		private Integer code;
		FAILURE(Integer code){
			this.code = code;
		}

		public Integer getCode() {
			return code;
		}
	}
}
