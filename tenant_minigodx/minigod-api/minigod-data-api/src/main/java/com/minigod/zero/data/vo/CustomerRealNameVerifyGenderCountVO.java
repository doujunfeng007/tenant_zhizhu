package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 客户实名认证性别统计VO
 *
 * @author eric
 * @date 2024-10-26 11:42:18
 */
@Data
@ApiModel(value = "CustomerRealNameVerifyGenderCountVO", description = "客户实名认证性别统计VO")
public class CustomerRealNameVerifyGenderCountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "性别")
	private String gender;
	@ApiModelProperty(value = "数量")
	private Long count;
	@ApiModelProperty(value = "比例")
	private Double ratio;
}
