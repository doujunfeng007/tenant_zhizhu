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
public class EntrustOrderExcel implements Serializable {

	@ColumnWidth(30)
	@ExcelProperty("比赛ID")
	private String competitionId;

	@ColumnWidth(30)
	@ExcelProperty("比赛名称")
	private String competitionName;

	@ColumnWidth(20)
	@ExcelProperty("比赛状态")
	private String competitionStatus;

	@ColumnWidth(30)
	@ExcelProperty("用户ID")
	private String userId;

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

	@ColumnWidth(20)
	@ExcelProperty("订单日期")
	private String orderDate;

	@ColumnWidth(20)
	@ExcelProperty("交易类型")
	private String tradeType;

	@ColumnWidth(20)
	@ExcelProperty("订单状态")
	private String orderState;

	@ColumnWidth(30)
	@ExcelProperty("股票名称")
	private String stkName;

	@ColumnWidth(20)
	@ExcelProperty("股票代码")
	private String assetId;

	@ColumnWidth(15)
	@ExcelProperty("数量")
	private Long orderQty;

	@ColumnWidth(15)
	@ExcelProperty("价格")
	private BigDecimal orderPrice;

	@ColumnWidth(15)
	@ExcelProperty("已成交数量")
	private Integer tradeQty;

	@ColumnWidth(15)
	@ExcelProperty("成交均价")
	private BigDecimal tradePrice;
}
