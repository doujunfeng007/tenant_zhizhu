package com.minigod.zero.manage.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class ReportLogExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	@ColumnWidth(30)
	@ExcelProperty("上报时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date createTime;

	/**
	 * 客户ID
	 */
	@ColumnWidth(15)
	@ExcelProperty("UserID")
	private Integer userId;
	/**
	 * 业务账号
	 */
	@ColumnWidth(20)
	@ExcelProperty("业务账号")
	private String busAccount;
	/**
	 * 手机
	 */
	@ColumnWidth(30)
	@ExcelProperty("手机号")
	private String phoneNumber;
	/**
	 * 邮箱
	 */
	@ColumnWidth(25)
	@ExcelProperty("邮箱")
	private String email;
	/**
	 * 日志内容
	 */
	@ExcelProperty(value = "日志内容")
	private String logContent;
	/**
	 * 日志路径
	 */
	@ExcelProperty(value = "日志路径")
	private String logPath;
}
