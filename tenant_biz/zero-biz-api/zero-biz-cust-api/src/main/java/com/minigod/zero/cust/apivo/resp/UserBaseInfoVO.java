package com.minigod.zero.cust.apivo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.resp.UserBaseInfoVO
 * @Date: 2023年02月16日 17:01
 * @Description:
 */
@Data
public class UserBaseInfoVO implements Serializable {


	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "用户性别(1男，0女)")
	private Integer gender;

	@ApiModelProperty(value = "手机号")
	private String phoneNum;


	@ApiModelProperty(value = "用户号 为用户ID ，平台ID做了加密混淆。暂用一个字段代替")
	private String userCode;

	@ApiModelProperty(value = "推荐人ID,邀请该用户的用户ID")
	private Long invUserId;

	@ApiModelProperty(value = "渠道id")
	private String userSourceChannelId;

	@ApiModelProperty(value = "用户简介")
	private String profile;

	@ApiModelProperty(value = "用户签名")
	private String signature;

	@ApiModelProperty(value = "大图像")
	private String bigUserIcon;

	@ApiModelProperty(value = "用户昵称")
	private String nickname;

	@ApiModelProperty(value = "认证头衔 1. 认证用户返回认证头衔 2. 非认证用户不返回")
	private String vTitle;

	@ApiModelProperty(value = "头像下载地址 缩略头像下载地址")
	private String userIcon;

	@ApiModelProperty(value = "认证标识 0为没有认证,1为已认证")
	private Integer vType;

	@ApiModelProperty(value = "默认所有用户均为1类型 1：普通用户；2：认证投顾，表示已经审核通过的投顾用户")
	private Integer uType;

	@ApiModelProperty(value = "第三方IM平台的ID")
	private String imId;

	@ApiModelProperty(value = "第三方IM平台的密码")
	private String imPwd;

}
