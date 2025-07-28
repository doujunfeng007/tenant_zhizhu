package com.minigod.zero.manage.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class CustInfoExcel implements Serializable {
	@ColumnWidth(15)
	@ExcelProperty("客户ID")
	private Long userId;
	@ColumnWidth(30)
	@ExcelProperty("用户名")
	private String userName;
	@ColumnWidth(30)
	@ExcelProperty("手机号")
	private String mobile;
}
