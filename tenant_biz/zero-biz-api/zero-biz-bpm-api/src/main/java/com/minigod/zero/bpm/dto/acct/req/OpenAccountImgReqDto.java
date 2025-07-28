package com.minigod.zero.bpm.dto.acct.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "开户img对象", description = "开户img对象")
public class OpenAccountImgReqDto implements Serializable {
	@ApiModelProperty(value = "问卷索引位置")
	private boolean isOcr;
	@ApiModelProperty(value = "图片base64")
	private String imgBase64;
	@ApiModelProperty(value = "图片位置[1：身份证 2：银行卡 3：签名照 4：正面照 5：活体]")
	private String location;
	@ApiModelProperty(value = "图片类型[101：身份证正面 102：身份证反面 103: 香港身份证 104：护照 105：香港身份证临时证明 201：银行卡  301：签名  302：签名信息]")
	private String type;
	private Integer lastImg;


	@ApiModelProperty(value = "1线上 2线下")
	private Integer openType = 1;

	@ApiModelProperty(value = "是否进行CA信息补录 0：否 1：是")
	private Integer isCaSupplement = 0;


	@ApiModelProperty(value = "文件扩展名")
	private String extFileName;

	@ApiModelProperty(value = "是否需要压缩")
	private boolean isCompress=true;
}
