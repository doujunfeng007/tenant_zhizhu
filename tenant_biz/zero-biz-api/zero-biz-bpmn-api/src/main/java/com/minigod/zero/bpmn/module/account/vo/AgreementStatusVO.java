package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.bpmn.module.account.vo.AgreementStatusVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/12/27 17:07
 * @Version: 1.0
 */
@Data
public class AgreementStatusVO {
	/**
	 *  0 未签署    1 已签署
	 */
	private Integer status;
}
