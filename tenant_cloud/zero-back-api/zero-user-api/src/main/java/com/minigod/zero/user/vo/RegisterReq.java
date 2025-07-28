package com.minigod.zero.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.RegisterReq
 * @Date: 2023年03月17日 17:07
 * @Description:
 */
@Data
public class RegisterReq implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "区号")
	private String area;

	@ApiModelProperty(value = "手机号(不带区号)")
	private String phone;

	@ApiModelProperty(value = "真实姓名")
	private String realName;

	@ApiModelProperty(value = "性别")
	private Integer sex;

	@ApiModelProperty(value = "请求源", required = true)
	private String SourceType;

	@ApiModelProperty(value = "证件类型", required = true)
	private Integer IdCardType;

	@ApiModelProperty(value = "证件号码", required = true)
	private String IdCardNo;

}
