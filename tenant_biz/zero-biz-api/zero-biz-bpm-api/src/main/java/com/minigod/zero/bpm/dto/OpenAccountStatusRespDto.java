package com.minigod.zero.bpm.dto;


import com.minigod.zero.bpm.entity.AcctOpenImageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "开户状态信息", description = "开户状态信息对象")
public class OpenAccountStatusRespDto implements Serializable {

	@ApiModelProperty(value = "开户状态")
	private String openStatus;
	@ApiModelProperty(value = "开户状态描述")
	private String openStatusDesc;
	@ApiModelProperty(value = "开户结果")
	private String openResult;
	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;
	@ApiModelProperty(value = "入金凭证状态 0 否 1 是")
	private Integer depositStatus;
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
	@ApiModelProperty(value = "开户方式：1、线上预约开户，2、线下（开户宝）3、香港预约开户")
	private Integer openType;
	@ApiModelProperty(value = "CA认证状态")
	private Integer caVerifyStatus;
	@ApiModelProperty(value = "跳转页面值")
	private String jumpCode;
	@ApiModelProperty(value = "0 非港卡开户 1港卡开户")
	private Integer bankType;
	@ApiModelProperty(value = "用户地址证明开关，0-关闭，无需证明，1-开启，需要证明")
	private boolean userAddressProofSwitch;

	private List<AcctOpenImageEntity> errImg;

}
