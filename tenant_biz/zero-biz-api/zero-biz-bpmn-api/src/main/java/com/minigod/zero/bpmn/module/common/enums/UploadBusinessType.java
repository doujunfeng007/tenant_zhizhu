package com.minigod.zero.bpmn.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传文件业务类型
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum UploadBusinessType {

	/**
	 * 信用额度凭证
	 */
	INCREASE_CREDIT_CERTIFICATE(1),
	/**
	 * 货币兑换凭证
	 */
	CURRENCY_EXCHANGE_CERTIFICATE(2),

	/**
	 * 股票转入转出
	 */
	STOCK_TRANSFERRED_CERTIFICATE(3),

	/**
	 * PI认证通知书《关于被视为专业投资者的通知》
	 */
	PI_NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS(4);


	private final Integer businessType;
}
