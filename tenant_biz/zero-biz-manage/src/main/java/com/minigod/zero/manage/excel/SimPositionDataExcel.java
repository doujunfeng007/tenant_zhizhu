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
public class SimPositionDataExcel implements Serializable {

	@ColumnWidth(30)
	@ExcelProperty("ID")
	private String id;

	@ColumnWidth(30)
	@ExcelProperty("报名时间")
	private String openDate;

//	@ColumnWidth(30)
//	@ExcelProperty("客户帐号")
//	private String tradeAccount;
//
//	@ColumnWidth(30)
//	@ExcelProperty("客户姓名")
//	private String clientName;
//
//	@ColumnWidth(30)
//	@ExcelProperty("英文名")
//	private String clientNameSpell;

	@ColumnWidth(30)
	@ExcelProperty("证券手机号码")
	private String phoneNumber;

	@ColumnWidth(30)
	@ExcelProperty("模拟比赛名称")
	private String competitionName;

	@ColumnWidth(30)
	@ExcelProperty("模拟比赛编号")
	private String competitionId;

	@ColumnWidth(30)
	@ExcelProperty("股票名称")
	private String stkName;

	@ColumnWidth(30)
	@ExcelProperty("股票代码")
	private String assetId;

	@ColumnWidth(30)
	@ExcelProperty("持有股数")
	private Integer qty;

	@ColumnWidth(30)
	@ExcelProperty("成本价")
	private BigDecimal costPrice;
}
