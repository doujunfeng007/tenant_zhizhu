package com.minigod.zero.cms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OptionalStockNewVO {
	private static final long serialVersionUID = -5204514948906924721L;
	//
	@ApiModelProperty(value = "三级分类")
	private String thirdClass;
	@ApiModelProperty(value = "市场 eg:HK")
	private String market;
	@ApiModelProperty(value = "股票代码 eg:00700")
	private String rowSymbol;
	@ApiModelProperty(value = "基金代码")
	private String productId;

}
