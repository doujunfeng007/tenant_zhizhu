package com.minigod.zero.customer.dto;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.dto.StatementFileListDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 11:22
 * @Version: 1.0
 */
@Data
public class StatementFileListDTO {
	/**
	 * 结单文件id
	 */
	private Long statementFileId;

	/**
	 * 开户邮箱
	 */
	private String openEmail;
}
