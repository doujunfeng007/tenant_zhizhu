package com.minigod.zero.bpm.dto.acct.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CaCertificationDto implements Serializable {

	private Integer userId;

	@ApiModelProperty(value = "银行预留手机号")
	private String phone;

	private String bankPhoneNumber;

	@ApiModelProperty(value = "银行卡号")
	private String bankNo;

	private String imageLocation;

	private String imageLocationType;

	private String imageUrl;

	private String name;

	private String idCard;

	private String phoneInfo;

	@ApiModelProperty(value = "0：其他 1：身份证")
	private Integer cardType;

	private String checkType;

	private String email;

	@ApiModelProperty(value = "银行代码")
	private String bankCode;

	@ApiModelProperty(value = "1.图片,2.视频")
	private String fileType;

	private String extName;

	private Long changeId;

	private String address;

	private String imgBase64;

}
