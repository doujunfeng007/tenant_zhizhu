package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class YfundInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 风险评级[1.保守型 2.稳健型 3.平衡型 4.增长型 5.进取型]
	 */
	@ApiModelProperty(value = "风险评级[1.保守型 2.稳健型 3.平衡型 4.增长型 5.进取型]")
    private Integer riskType;
	/**
	 * 失效日期
	 */
	@ApiModelProperty(value = "失效日期")
    private Date expiryDate;
	/**
	 * 风险评测记录表-开始时间
	 */
	@ApiModelProperty(value = "当前开始时间")
    private Date nowDate;
	/**
	 * 风险评测记录表-开始时间
	 */
	@ApiModelProperty(value = "风险评测记录表-开始时间")
    private Date evaluationDate;
    private String yfFundAccountMain;
	/**
	 * 基金账户
	 */
	@ApiModelProperty(value = "基金账户")
	private String yfFundAccount;
	/**
	 * 基金账户类型
	 */
	@ApiModelProperty(value = "基金账户类型")
	private Integer yfFundOperType;
}
