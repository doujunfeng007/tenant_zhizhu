package com.minigod.zero.cust.apivo.req;

import com.minigod.zero.cust.apivo.SNVersion;
import com.minigod.zero.cust.apivo.params.DeviceInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.UserLoginVO
 * @Date: 2023年02月16日 20:08
 * @Description:
 */
@Data
public class UserLoginReqVO extends SNVersion {

	private static final long serialVersionUID = -1L;

	public UserLoginVO params;

	@Data
	public static class UserLoginVO implements Serializable {


		private static final long serialVersionUID = -1L;

		@ApiModelProperty(value = "appid")
		private String appid;

		@ApiModelProperty(value = "凭证类型(0-手机,1-邮箱,2-微信,3-微博,4-QQ)")
		private Integer certType;

		@ApiModelProperty(value = "凭证内容(手机号、邮箱、QQ号，微博号、微信号、sunline用户名、OpenID等)")
		private String certCode;

		@ApiModelProperty(value = "手机号登录区号要填写")
		private String areaCode;

		@ApiModelProperty(value = "事件ID 获取验证码时返回的值")
		private Integer eventId;

		@ApiModelProperty(value = "解密码的KEY")
		private String key;

		@ApiModelProperty(value = "用户密码，如果是第三方令牌则填写该值")
		private String pwd;

		@ApiModelProperty(value = "微信token")
		private String token;

		@ApiModelProperty(value = "用户昵称(允许重复)")
		private String nickname;

		@ApiModelProperty(value = "用户性别(1男，0女)")
		private Integer gender;

		@ApiModelProperty(value = "用户图像")
		private String userIcon;

		@ApiModelProperty(value = "注册ip")
		private String regIp;

		@ApiModelProperty(value = "设备信息")
		private DeviceInfo deviceInfo;

		@ApiModelProperty(value = "渠道标示，应用是从哪个")
		private String channel;

	}
}
