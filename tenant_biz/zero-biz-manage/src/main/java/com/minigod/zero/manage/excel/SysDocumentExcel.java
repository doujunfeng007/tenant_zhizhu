package com.minigod.zero.manage.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class SysDocumentExcel {

	/** 标题 */
	@ColumnWidth(25)
	@ExcelProperty("标题")
	private String docTitle;

	/** 索引（与前端对应） */
	@ColumnWidth(25)
	@ExcelProperty("docKey")
	private String docKey;

	/** 内容 */
	@ColumnWidth(254)
	@ExcelProperty("内容")
	private String docContent;

	@ColumnWidth(30)
	@ExcelProperty("状态")
	private String statusStr;

	@ColumnWidth(30)
	@ExcelProperty("创建时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date createTime;
	@ColumnWidth(30)
	@ExcelProperty("修改时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date updateTime;
}
