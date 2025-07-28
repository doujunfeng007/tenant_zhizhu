package com.minigod.zero.trade.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class IpoRecommend implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "市场")
	private String mktCode;
	@ApiModelProperty(value = "股票代码")
    private String assetId;
	@ApiModelProperty(value = "页码")
    private Integer currentPage;
	@ApiModelProperty(value = "页条数")
    private Integer pageSize;
	@ApiModelProperty(value = "变盘时间")
    private String time;//变盘时间
	@ApiModelProperty(value = "建议价格")
    private Float price;//建议价格
	@ApiModelProperty(value = "交易方向")
    private Integer direction;//交易方向 int（1：买入， 0：卖出）
	@ApiModelProperty(value = "交易数量")
    private String volume;//交易数量 string （比如“1/3, 1/4”）
	@ApiModelProperty(value = "k线类型")
    private Integer kType;//k线类型 int （按分钟计数，例如1就是 1分钟线， 60就是小时线， 330是日线）
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "限制数量")
    private Integer count;
}
