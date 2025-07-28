package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 16:12
 * @Description: W8居住地址
 */
@Data
public class W8Addr implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 国家
	 */
	@ApiModelProperty(value = "国家")
	private String country;

	/**
	 * 州,郡,省（香港地区）
	 */
	@ApiModelProperty(value = "州,郡,省（香港地区）")
	private String state;

	/**
	 * 地区/县/街道
	 */
	@ApiModelProperty(value = "地区/县/街道")
	private String district;

	/**
	 * 屋苑(地址)
	 */
	@ApiModelProperty(value = "屋苑(地址)")
	private String building;

	/**
	 * 座/大厦名
	 */
	@ApiModelProperty(value = "座/大厦名")
	private String block;

	/**
	 * 房间/楼层
	 */
	@ApiModelProperty(value = "房间/楼层")
	private String floor;
}
