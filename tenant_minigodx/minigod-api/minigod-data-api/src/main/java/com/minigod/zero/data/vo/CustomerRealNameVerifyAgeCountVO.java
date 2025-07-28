package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统计用户数数据结构
 *
 * @author: eric
 * @date: 2024-10-26 11:40:00
 */
@Data
@ApiModel(value = "CustomerRealNameVerifyAgeCountVO", description = "客户实名认证年龄统计VO")
public class CustomerRealNameVerifyAgeCountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "年龄区间")
	private String ageGroup;
	@ApiModelProperty(value = "数量")
	private Long count;
	@ApiModelProperty(value = "比例")
	private Double ratio;
}
