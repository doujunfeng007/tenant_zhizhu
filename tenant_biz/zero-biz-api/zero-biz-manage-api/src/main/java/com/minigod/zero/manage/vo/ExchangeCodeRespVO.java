package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 查询优惠券返回对象
 *
 * @author eric
 * @since 2024-12-26 13:57:05
 */
@ApiModel(description = "查询优惠券返回对象")
@Data
public class ExchangeCodeRespVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "激活码")
	private String exchangeCode;
	@ApiModelProperty(value = "序列号")
	private String serialNum;
	@ApiModelProperty(value = "卡券名称")
	private String cardName;
	@ApiModelProperty(value = "卡券类型")
	private Integer cardType;
	@ApiModelProperty(value = "优惠券管理ID")
	private Long manageId;
	@ApiModelProperty(value = "渠道ID")
	private String channelId;
	@ApiModelProperty(value = "使用条件")
	private Integer useType;
	@ApiModelProperty(value = "资产条件")
	private Double amount;
	@ApiModelProperty(value = "激活人")
	private Integer useUid;
	@ApiModelProperty(value = "手机号")
	private String phoneNumber;
	@ApiModelProperty(value = "兑换状态")
	private Integer recordStatus;
	@ApiModelProperty(value = "过期时间")
	private Date expiredTime;
	@ApiModelProperty(value = "注册时间")
	private Date registerDate;
	@ApiModelProperty(value = "开户时间")
	private Date openDate;
	@ApiModelProperty(value = "入金时间")
	private Date depositDate;
	@ApiModelProperty(value = "兑换时间")
	private Date recordDate;
	@ApiModelProperty(value = "使用时间")
	private Date useDate;
	@ApiModelProperty(value = "新增资产")
	private Double addAmount;
	@ApiModelProperty(value = "优惠券激活码ID")
	private Long exchangeCodeId;
	@ApiModelProperty(value = "使用状态")
	private Integer useStatus;
	@ApiModelProperty(value = "批次过期时间")
	private Date manageExpiredTime;
	@ApiModelProperty(value = "新增资产")
	private String addAmounts;
	@ApiModelProperty(value = "首次入金金额")
	private Double firstDepositMoney;
}
