package com.minigod.zero.cms.vo;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.oms.dto.BankListDTO
 * @Description: 入参
 * @Author: linggr
 * @CreateDate: 2024/5/16 16:23
 * @Version: 1.0
 */
@Data
public class PayeeBankListDTO {
	/**
	 * 银行详情id
	 */
	private String payeeBankDetailId;

	/**
	 * 币种 1港币 2美元 3人民币
	 */
	private int currency;
	/**
	 * 1fps 2网银 3支票 4快捷入金 5银证转账 6edda
	 */
	//必传
	private int supportType;
	/**
	 * 是否1级可见 1可见 0不可见
	 */
	private int enable;

	/**
	 * 是否2级可见 1可见 0不可见
	 */
	private int subEnable;
}
