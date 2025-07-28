package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券兑换码匹配视图层
 *
 * @author eric
 * @since 2024-12-27 09:51:08
 */
@ApiModel(description = "优惠券兑换码匹配视图层")
@Data
public class SnCouponManageMatchVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "序列号")
	private String serialNum;
	@ApiModelProperty(value = "激活码")
	private String code;
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	@ApiModelProperty(value = "渠道ID")
	private String channelId;
	@ApiModelProperty(value = "激活时间")
	private Date recordDate;
	@ApiModelProperty(value = "使用时间")
	private Date useDate;
	@ApiModelProperty(value = "开户时间")
	private Date openDate;
	@ApiModelProperty(value = "入金时间")
	private Date depositDate;
	@ApiModelProperty(value = "入金金额")
	private Double depositMoney;
}
