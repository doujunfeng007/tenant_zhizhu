package com.minigod.zero.cms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OptionalGroupNewVO implements Serializable {
	private static final long serialVersionUID = -5204514948906924721L;
	//
	@ApiModelProperty(value = "分组ID组")
	private List<Long> groupIds;
	@ApiModelProperty(value = "版本")
	private Long version;
	private List<OptionalStockNewVO> portfolio;

	@ApiModelProperty(value = "分组ID")
	private Long groupId;
	@ApiModelProperty(value = "股票列表")
	private List<String> assetIds;
	@ApiModelProperty(value = "操作类型（1： 添加特别关注，2： 取消特别关注）")
	private Integer operateType;
	private Long custId;
}
