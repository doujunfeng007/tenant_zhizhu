package com.minigod.zero.data.vo;

import lombok.Data;

/**
 * 客户风险等级返回对象
 *
 * @author zxq
 * @since 2024-04-11
 */
@Data
public class CustRiskInfoVO {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户id
	 */
	private Long custId;
	/**
	 * 客户名称
	 */
	private String clientName;

	/**
	 * 客户手机号
	 */
	private String cellPhone;

	/**
	 * 客户PI等级
	 */
	private String piLevel;

	/**
	 * 活利得风险等级
	 */
	private Integer hldRiskLevel;

	/**
	 * 基金风险等级
	 */
	private Integer fundRiskLevel;

	/**
	 * 债券易风险等级
	 */
	private Integer bondRiskLevel;

	/**
	 * 基金账号是否开通
	 */
	private boolean isFundOpen;

	/**
	 * 活利得账号是否开通
	 */
	private boolean isHldOpen;

	/**
	 * 债券易账号是否开通
	 */
	private boolean isBondOpen;

	private Long bondCustId;

	private Long fundCustId;

	private Long hldCustId;
}
