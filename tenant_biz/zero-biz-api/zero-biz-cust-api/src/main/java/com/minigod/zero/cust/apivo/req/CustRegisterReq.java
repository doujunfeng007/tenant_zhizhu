package com.minigod.zero.cust.apivo.req;

import com.minigod.zero.cust.apivo.params.Cert;
import com.minigod.zero.cust.apivo.params.DeviceInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.UserRegisterReq
 * @Date: 2023年03月15日 14:00
 * @Description:
 */
@Data
public class CustRegisterReq implements Serializable {

	private static final long serialVersionUID = -1L;

	private List<Cert> cert;

	@ApiModelProperty(value = "区号" ,required = true)
	private String areaCode;

	@ApiModelProperty(value = "当前注册用户的手机号(不带区号的手机号)",required = true)
	private String phoneNum;

	@ApiModelProperty(value = "token")
	private String token;

	@ApiModelProperty(value = "解密码的KEY",required = true)
	private String key;

	@ApiModelProperty(value = "用户密码，如果是第三方令牌则填写该值",required = true)
	private String pwd;

	@ApiModelProperty(value = "用户昵称")
	private String nickname;

	@ApiModelProperty(value = "用户性别")
	private Integer gender;

	@ApiModelProperty(value = "用户图像")
	private String userIcon;

	@ApiModelProperty(value = "设备信息")
	private DeviceInfo deviceInfo;

	@ApiModelProperty(value = "注册渠道")
	private String userSourceChannelId;

	@ApiModelProperty(value = "邀请人")
	private Long invUserId;

	@ApiModelProperty(value = "注册来源：1-web 2-ios 3-aos 4-pc 5-h5")
	private Integer regSourceType;

	@ApiModelProperty(value = "注册渠道：0=未知 1=互联网 2=线下开户 3-公司授权")
	private Integer regChannel;

	@ApiModelProperty(value = "注册邮箱")
	private String email;

	@ApiModelProperty(value = "facebook账号")
	private String facebookName;

	@ApiModelProperty(value = "租户id")
	private String tenantId;
}
