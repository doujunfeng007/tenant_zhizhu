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
public class SimRankHisDataExcel implements Serializable {

//	@ColumnWidth(30)
//	@ExcelProperty("帐户审批通过时间")
//	private String openAccountTime;

	@ColumnWidth(30)
	@ExcelProperty("报名时间")
	private String openDate;

	@ColumnWidth(30)
	@ExcelProperty("比赛开始时间")
	private String startTime;

	@ColumnWidth(30)
	@ExcelProperty("比赛结束时间")
	private String endTime;

	@ColumnWidth(30)
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

	@ColumnWidth(30)
	@ExcelProperty("呢称")
	private String nickName;

//	@ColumnWidth(30)
//	@ExcelProperty("英文名")
//	private String clientNameSpell;

	@ColumnWidth(30)
	@ExcelProperty("手机号码")
	private String phoneNumber;

	@ColumnWidth(30)
	@ExcelProperty("比赛名称")
	private String competitionName;

	@ColumnWidth(30)
	@ExcelProperty("持仓总市值(港币)")
	private BigDecimal marketValue;

	@ColumnWidth(30)
	@ExcelProperty("浮动盈亏(港币)")
	private BigDecimal holdProfit;

	@ColumnWidth(30)
	@ExcelProperty("可用资金(港币)")
	private BigDecimal enableFund;

	@ColumnWidth(30)
	@ExcelProperty("累计盈亏(港币)")
	private BigDecimal totalProfit;

	@ColumnWidth(30)
	@ExcelProperty("买入交易次数")
	private Integer tradeCount;

	@ColumnWidth(30)
	@ExcelProperty("参与比赛天数")
	private Long competitionDays;

	@ColumnWidth(30)
	@ExcelProperty("收益率")
	private String profitRate;

	@ColumnWidth(30)
	@ExcelProperty("名次")
	private String rank;
}
