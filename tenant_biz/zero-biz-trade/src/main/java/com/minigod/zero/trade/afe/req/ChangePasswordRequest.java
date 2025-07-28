package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName ChangePasswordRequest.java
 * @Description TODO
 * @createTime 2024年04月19日 11:10:00
 */

@Data
public class ChangePasswordRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;

	@JSONField(name ="OLD_PASSWORD")
	private String oldPassword;

	@JSONField(name ="NEW_PASSWORD")
	private String newPassword;

	@JSONField(name ="RECONFIRM_PASSWORD")
	private String reconfirmPassword ;

	@JSONField(name ="OTP_CODE")
	private String otpCode ;

}
