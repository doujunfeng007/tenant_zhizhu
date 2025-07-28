package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户证件类型统计
 *
 * @author eric
 * @date 2024/10/26 17:12:05
 */
@Data
@ApiModel(value = "客户证件类型统计")
public class CustomerBasicInfoIdKindCountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "证件类型")
	private String idKind;
	@ApiModelProperty(value = "证件类型名称")
	private String idKindName;
	@ApiModelProperty(value = "数量")
	private Long count;
	@ApiModelProperty(value = "比例")
	private Double ratio;
}
