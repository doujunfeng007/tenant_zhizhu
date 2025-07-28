package com.minigod.zero.manage.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class simulateAccountFilterExcel implements Serializable {

	@ColumnWidth(30)
	@ExcelProperty("昵称")
	private String nickName;

	@ColumnWidth(30)
	@ExcelProperty("手机号码")
	private String phoneNumber;

	@ColumnWidth(30)
	@ExcelProperty("用户ID")
	private String userId;

	@ColumnWidth(30)
	@ExcelProperty("添加日期")
	private String createTime;

	@ColumnWidth(30)
	@ExcelProperty("状态")
	private String status;
}
