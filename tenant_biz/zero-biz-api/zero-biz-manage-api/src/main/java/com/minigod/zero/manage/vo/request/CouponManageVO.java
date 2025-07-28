package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 优惠券管理
 *
 * @author: eric
 * @since 2024-12-26 09:34:01
 */
@ApiModel(description = "优惠券管理")
@Data
public class CouponManageVO implements Serializable {
	@ApiModelProperty(value = "卡券类型")
	private Integer cardType;
	@ApiModelProperty(value = "卡券名称")
	private String cardName;
	@ApiModelProperty(value = "卡券ID")
	private Long cardId;
	@ApiModelProperty(value = "卡券使用条件: 1首次开户 2首次入金 3首次转仓 4入金金额")
	private Integer useType;
	@ApiModelProperty(value = "资产条件")
	private Double amount = 0d;
	@ApiModelProperty(value = "创建时间")
	private String createTime;
	@ApiModelProperty(value = "过期时间")
	private String expiredTime;
	@ApiModelProperty(value = "渠道ID")
	private String channelId;
	@ApiModelProperty(value = "使用有效期(天)")
	private Integer useValidityDay;
	@ApiModelProperty(value = "条件描述")
	private String rewardCondition;
	@ApiModelProperty(value = "兑换上限(次)")
	private Integer exchangeMaxNumber;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "创建人")
	private String oprName;
	@ApiModelProperty(value = "创建数量")
	private Integer createNumber;
	@ApiModelProperty(value = "短信推送 0否 1是")
	private Integer sendMobileMsg = 0;
	@ApiModelProperty(value = "短信推送内容")
	private String sendMobileMsgContent;
	@ApiModelProperty(value = "到帐张数")
	private Integer reciveNumber;
}
