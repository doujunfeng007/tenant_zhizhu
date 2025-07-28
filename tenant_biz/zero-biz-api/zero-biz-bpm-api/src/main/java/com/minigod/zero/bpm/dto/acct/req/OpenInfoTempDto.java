package com.minigod.zero.bpm.dto.acct.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenInfoTempDto implements Serializable {

	@ApiModelProperty(value = "开户信息")
	private String info;

	@ApiModelProperty(value = "步骤")
	private Integer step; //

	@ApiModelProperty(value = "开户方式 1 线上开户 3 香港开户")
	private Integer openType;

	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;

	private String mark;

}
