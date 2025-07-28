package com.minigod.zero.bpmn.module.withdraw.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;

/**
 * 银行信息记录对象 client_bank_info
 *
 * @author zsdp
 * @date 2023-03-24
 */
@Data
@TableName("client_bank_info")
public class ClientBankInfo extends BaseEntity {

	public static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId(value = "id")
	@ExcelIgnore
	public Long id;
	/**
	 * 银行代码
	 */
	@ExcelProperty(value = "银行代码")
	public String bankCode;
	/**
	 * 银行名称
	 */
	@ExcelProperty(value = "银行名称")
	public String bankName;
	/**
	 * 银行名称(英文)
	 */
	@ExcelIgnore
	public String bankNameEn;
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
	/**
	 * 银行地区类型[1-香港本地银行 2-中国大陆银行 3-海外银行]
	 */
	@ExcelIgnore
	public Integer bankAreaType;
	/**
	 * 支持多种取款方式[1-FPSID 2-香港本地银行 2-电汇至中国大陆银行 3 电汇至海外银行]
	 */
	@ExcelIgnore
	public String withdrawType;
	/**
	 * 状态[0-无效 1-有效]
	 */
	@ExcelIgnore
	public Integer status;

	/**
	 * 类型[0-存款 1-取款]
	 */
	@ExcelIgnore
	public Integer deposit;


}
