package com.minigod.zero.customer.vo;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.StatementUrlVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/29 15:20
 * @Version: 1.0
 */
@Data
public class StatementUrlVO {
	private String url;
	private String fileName;

	public StatementUrlVO(String url, String fileName) {
		this.url = url;
		this.fileName = fileName;
	}
}
