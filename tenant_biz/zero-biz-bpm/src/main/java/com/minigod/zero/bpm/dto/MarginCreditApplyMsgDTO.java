package com.minigod.zero.bpm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户信用额度申请入参协议
 */
@Data
public class MarginCreditApplyMsgDTO implements Serializable {

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
    private String clientId;

	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
    private String fundAccount;

	/**
	 * 调整额度的原因（数组）
	 */
	@ApiModelProperty(value = "调整额度的原因（数组）")
    private List<String> reasonType;

	/**
	 * 其他原因
	 */
	@ApiModelProperty(value = "其他原因")
    private String otherReason;

	/**
	 * 当前额度
	 */
	@ApiModelProperty(value = "当前额度")
    private BigDecimal currentCreditAmount;

	/**
	 * 申请额度
	 */
	@ApiModelProperty(value = "申请额度")
    private BigDecimal applyCreditAmount;

	/**
	 * 资产凭证
	 */
	@ApiModelProperty(value = "资产凭证")
    private List<String> fileList;
}
