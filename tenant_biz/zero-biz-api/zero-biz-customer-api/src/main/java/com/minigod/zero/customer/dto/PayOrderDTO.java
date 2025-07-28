package com.minigod.zero.customer.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/26 10:38
 * @description：
 */
@Data
public class PayOrderDTO implements Serializable {
	/**
	 * 理财账号
	 */
	private String accountId;
	/**
	 * 调用方业务id
	 */
	private String businessId;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 业务类型
	 * {@link com.minigod.zero.customer.enums.BusinessType}
	 */
	private Integer businessType;
	/**
	 *
	 */
	private List<PayDetailDTO> payDetails;
}
