package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName ResetPasswordRequest.java
 * @Description TODO
 * @createTime 2024年04月19日 11:13:00
 */
@Data
public class ResetPasswordRequest extends CommonRequest{

	/**
	 * 客户ID
	 */
	@JSONField(name ="CLIENT_ID")
	private String clientId;

	/**
	 * 邮箱
	 */
	@JSONField(name ="EMAIL")
	private String email;

	/**
	 * 证件号
	 */
	@JSONField(name ="HKID")
	private String hkid;

}
