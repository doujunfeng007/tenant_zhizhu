package com.minigod.zero.bpmn.module.stocktransfer.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName SecTransferredStockExcel.java
 * @Description TODO
 * @createTime 2024年03月07日 19:24:00
 */
@Data
@ColumnWidth(16)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class SecTransferredStockExcel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 外部券商名称
	 */
	@ExcelProperty(value = "转入方券商名称")
	private String secName;

	/**
	 * 外部券商姓名
	 */
	@ExcelProperty(value = "客户姓名")
	private String accountName;

	/**
	 * 外部券商号码
	 */
	@ExcelProperty(value = "转入方券商号码")
	private String accountNumber;

	/**
	 * 接收方名称
	 */
	@ExcelProperty(value = "接收方名称")
	private String receiveSec;

	/**
	 * 接收方账户
	 */
	@ExcelProperty(value = "接收方账户")
	private String receiveAccount;

	/**
	 * 邀请人
	 */
	@ExcelProperty(value = "邀请人")
	private String inviter;

	/**
	 * 用户id
	 */
	@ExcelProperty(value = "用户id")
	private Long custId;

	/**
	 * 转入股票名称
	 */
	@ExcelProperty(value = "转入股票名称")
	private String isSharesName;

	/**
	 * CCASS代码
	 */
	@ExcelProperty(value = "CCASS代码")
	private String ccass;

	/**
	 * 转入状态名称
	 */
	@ExcelProperty(value = "转入状态")
	private String transferStateName;

	/**
	 * 退回理由
	 */
	@ExcelProperty(value = "退回理由")
	private String backReason;

	/**
	 * 交易账号
	 */
	@ExcelProperty(value = "交易账号")
	private String clientId;

	/**
	 * 外部券商联系地址
	 */
	@ExcelProperty(value = "外部券商联系地址")
	private String rolloutContacts;

	/**
	 * 外部券商的联系人电话
	 */
	@ExcelProperty(value = "外部券商的联系人电话")
	private String contactsPhone;

	/**
	 * 1转入记录2转出记录名称
	 */
	@ExcelProperty(value = "记录类型名称")
	private String regulationTypeName;

	/**
	 * 清算行名称
	 */
	@ExcelProperty(value = "清算行名称")
	private String clearingBankName;

	/**
	 * 清算行账号
	 */
	@ExcelProperty(value = "清算行账号")
	private String clearingBankAccount;

	/**
	 * 转出券商的联系人邮箱
	 */
	@ExcelProperty(value = "转出券商的联系人邮箱")
	private String contactsEmail;
}
