package com.minigod.zero.customer.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.customer.emuns.StatementEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.vo.StatementHistoryListVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 14:41
 * @Version: 1.0
 */
@Data
@ColumnWidth(16)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class StatementHistoryListExcelVO implements Serializable {
	/**
	 * 发送时间
	 */
	@ExcelProperty(value = "系统发送结单日期时间", index = 0)
	@com.alibaba.excel.annotation.format.DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;

	/**
	 *结单日期
	 */
	@ExcelProperty(value = "结单日期", index = 1)
	@com.alibaba.excel.annotation.format.DateTimeFormat(value = "yyyy-MM-dd")
	private String statementTime;

	/**
	 * 客户中文姓名
	 */
	@ExcelProperty(value = "用户中文名", index = 2)
	private String name;

	/**
	 * 用户id
	 */
	@ExcelIgnore
	private Long custId;

	/**
	 * 英文名
	 */
	@ExcelProperty(value = "用户英文名", index = 3)
	private String enName;

	/**
	 * 理财账号
	 */
	@ExcelProperty(value = "理财账号", index = 4)
	private String accountId;
	@ExcelProperty(value = "结单类型", index = 5)
	private String statementTypeName;
	/**
	 * 开户邮箱
	 */
	@ExcelProperty(value = "客户邮箱地址", index = 6)
	private String openEmail;



	/**
	 * 结单发送状态  0未发送 1已发送 2发送失败
	 */
	@ExcelIgnore
	private Integer statementStatus;
	/**
	 * 发送次数
	 */
	@ExcelProperty(value = "邮件发送次数", index = 7)
	private Integer sendNum;
	@ExcelProperty(value = "结单发送状态", index = 8)
	private String statementStatusName;

	/**
	 * 结单文件id
	 */
	@ExcelIgnore
	private Long statementFileId;




	/**
	 * 结单类型  1日结单 2月结单
	 */
	@ExcelIgnore
	private Integer statementType;



	public String getStatementStatusName() {
		if (statementStatus == null) {
			statementStatusName = "";
		}else if (statementStatus.equals(StatementEnums.FileSendStatus.UN_SET.getCode())) {
			statementStatusName = "未发送";
		} else if (statementStatus.equals(StatementEnums.FileSendStatus.SEND_SUCCESS.getCode())) {
			statementStatusName = "已发送";
		} else if (statementStatus.equals(StatementEnums.FileSendStatus.SEND_FAIL.getCode())) {
			statementStatusName = "发送失败";
		}
		return statementStatusName;
	}

	public String getStatementTypeName() {
		if (statementType == null) {
			statementTypeName = "";
		} else if (statementType == 1) {
			statementTypeName = "日结单";
		} else if (statementType == 2) {
			statementTypeName = "月结单";
		}
		return statementTypeName;
	}
}
