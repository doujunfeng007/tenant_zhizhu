package com.minigod.zero.bpmn.module.withdraw.request;

import lombok.Data;

/**
 * @ClassName: TeltransferRequest
 * @Description:
 * @Author chenyu
 * @Date 2024/3/28
 * @Version 1.0
 */
@Data
public class TeltransferRequest {
    private String nationality;
	/**
	 * 1，香港银行，2中国大陆银行，3，海外银行
	 */
	private Integer bankType;
}
