package com.minigod.zero.bpmn.module.margincredit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户信用额度申请入参协议
 */
@Data
public class MarginCreditAdjustApplyDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
    private String tradeAccount;

	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
    private String capitalAccount;

	@ApiModelProperty(value = "姓名")
	private String clientName;

	@ApiModelProperty(value = "英文名")
	private String clientNameSpell;

	@ApiModelProperty(value = "用户id")
	private Long custId;

    private List<LineCreditInfo> lineCreditInfo;

	private Integer applyType ;

	private String tenantId;

	@Data
	public static  class LineCreditInfo implements Serializable{

		private static final long serialVersionUID = 1L;

		private String currency;

		/**
		 * 申请前额度
		 */
		private BigDecimal lineCreditBefore;

		/**
		 * 申请额度
		 */
		private BigDecimal lineCreditApply;


	}
}
