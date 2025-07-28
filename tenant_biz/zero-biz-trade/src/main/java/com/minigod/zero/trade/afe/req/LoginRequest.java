package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName LoginRequest.java
 * @Description 登录请求
 * @createTime 2024年04月17日 10:39:00
 */
@Data
public class LoginRequest extends CommonRequest{



	/**
	 * 密码
	 */
	@JSONField(name = "PASSWORD")
	private String password;

	@JSONField(name ="LOGIN_ID")
	private String loginId;



}
