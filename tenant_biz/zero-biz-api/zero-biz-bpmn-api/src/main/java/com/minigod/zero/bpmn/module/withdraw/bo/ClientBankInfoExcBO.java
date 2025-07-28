package com.minigod.zero.bpmn.module.withdraw.bo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoExcBO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/20 15:58
 * @Version: 1.0
 */
@Data
public class ClientBankInfoExcBO {

	/**
	 * 银行名称
	 */
	@ExcelProperty(value = "银行名称")
	public String bankName;

	/**
	 * 银行代码
	 */
	@ExcelProperty(value = "银行代码")
	public String bankCode;

	/**
	 * SWIFT代码
	 */
	@ExcelProperty(value = "SWIFT代码")
	public String swiftCode;
	/**
	 * bank_id
	 */
	@ExcelProperty(value = "bank_id")
	public String bankId;
}
