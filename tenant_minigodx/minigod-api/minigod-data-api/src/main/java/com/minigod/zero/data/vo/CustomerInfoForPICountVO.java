package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统计PI用户数数据结构
 *
 * @author: eric
 * @date: 2024-10-26 11:40:00
 */
@Data
@ApiModel(value = "CustomerInfoForPICountVO", description = "统计PI用户数数据结构VO")
public class CustomerInfoForPICountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "4级PI用户数")
	private int piLevelFourCount;
	@ApiModelProperty(value = "非4级PI用户数")
	private int piLevelNotFourCount;
	@ApiModelProperty(value = "4级PI用户比例")
	private Double piLevelFourRatio;
	@ApiModelProperty(value = "非4级PI用户比例")
	private Double piLevelNotFourRatio;
}
