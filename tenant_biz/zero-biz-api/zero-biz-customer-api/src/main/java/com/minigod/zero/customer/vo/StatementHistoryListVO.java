package com.minigod.zero.customer.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.vo.StatementHistoryListVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 14:41
 * @Version: 1.0
 */
@Data
public class StatementHistoryListVO {
	/**
	 * 用户id
	 */
	private Long custId;
	/**
	 * 客户中文姓名
	 */
	private String name;
	/**
	 * 英文名
	 */
	private String enName;
	/**
	 * 开户邮箱
	 */
	private String openEmail;
	/**
	 * 理财账号
	 */
	private String accountId;

	/**
	 * 结单发送状态  0未发送 1已发送 2发送失败
	 */
	private Integer statementStatus;
	/**
	 * 结单发送失败原因
	 */
	private String errorMsg;

	/**
	 * 结单文件id
	 */
	private Long statementFileId;

	/**
	 *结单日期
	 */
	private String statementTime;

	/**
	 * 发送时间
	 */
	private Date sendTime;

	/**
	 * 发送次数
	 */
	private Integer sendNum;

	/**
	 * 结单类型  1日结单 2月结单
	 */
	private Integer statementType;
}
