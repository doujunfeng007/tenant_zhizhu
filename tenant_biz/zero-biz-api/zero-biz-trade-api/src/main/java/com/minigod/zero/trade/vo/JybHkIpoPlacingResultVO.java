package com.minigod.zero.trade.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 配售结果
 */
@Data
public class JybHkIpoPlacingResultVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 股票代码
	 */
	@ApiModelProperty(value = "股票代码")
	private String assetId;
	/**
	 * 股票名称
	 */
	@ApiModelProperty(value = "股票名称")
	private String name;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String num;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private String lot;
	/**
	 * 最新孖展总额
	 */
	@ApiModelProperty(value = "最新孖展总额")
	private String sz;
	/**
	 * 超购倍数
	 */
	@ApiModelProperty(value = "超购倍数")
	private String rate;
	/**
	 * 预测倍数
	 */
	@ApiModelProperty(value = "预测倍数")
	private String clawBack;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String rLink;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String sLink;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String subscribed;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String codesRate;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String headHammer;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String groupN;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String whiteForm;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String yellowForm;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String priceCeiling;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String priceFloor;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String ipoPricing;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String stockCount;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String personCount;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String priseCount;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String priseRate;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String priseRemark;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
}
