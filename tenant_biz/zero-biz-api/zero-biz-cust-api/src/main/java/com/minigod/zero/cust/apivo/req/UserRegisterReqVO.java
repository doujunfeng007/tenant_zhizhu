package com.minigod.zero.cust.apivo.req;

import com.minigod.zero.cust.apivo.SNVersion;
import com.minigod.zero.cust.apivo.params.Cert;
import com.minigod.zero.cust.apivo.params.DeviceInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.UserRegisterReqVO
 * @Date: 2023年02月15日 18:43
 * @Description:
 */
@Data
public class UserRegisterReqVO extends SNVersion {

	private static final long serialVersionUID = 591550343234575530L;

	public UserRegisterVO params;

	@Data
	public static class UserRegisterVO implements Serializable {
		private static final long serialVersionUID = 7439222677006997194L;


		private List<Cert> cert;

		@ApiModelProperty(value = "区号")
		private String areaCode;

		@ApiModelProperty(value = "当前注册用户的手机号(不带区号的手机号)")
		private String phoneNum;

		@ApiModelProperty(value = "token")
		private String token;

		@ApiModelProperty(value = "解密码的KEY")
		private String key;

		@ApiModelProperty(value = "用户密码，如果是第三方令牌则填写该值")
		private String pwd;

		@ApiModelProperty(value = "用户昵称")
		private String nickname;

		@ApiModelProperty(value = "用户性别")
		private Integer gender;

		@ApiModelProperty(value = "用户图像")
		private String userIcon;

		@ApiModelProperty(value = "事件ID")
		private Integer eventId;

		@ApiModelProperty(value = "设备信息")
		private DeviceInfo deviceInfo;

		@ApiModelProperty(value = "客户端IP")
		private String ip;

		@ApiModelProperty(value = "注册渠道")
		private String userSourceChannelId;

		@ApiModelProperty(value = "邀请人")
		private Long invUserId;

		@ApiModelProperty(value = "注册来源:：1-web 2-ios 3-aos 4-pc 5-h5")
		private Integer regSourceType;

		@ApiModelProperty(value = "注册渠道：0=未知 1=互联网 2=线下开户 3-公司授权")
		private Integer regChannel;

		@ApiModelProperty(value = "注册邮箱")
		private String email;

		@ApiModelProperty(value = "facebook账号")
		private String facebookName;

	}
}
