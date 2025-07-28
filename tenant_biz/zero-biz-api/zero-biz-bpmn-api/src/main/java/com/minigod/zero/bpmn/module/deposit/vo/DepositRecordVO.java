package com.minigod.zero.bpmn.module.deposit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/18 14:55
 * @description：
 */
@Data
public class DepositRecordVO {
	/**
	 * 预约流水号
	 */
	private String applicationId;
	/**
	 * 审核状态
	 */
	private Integer status;
	private String statusName;
	/**
	 * 客户id
	 */
	private String custId;
	/**
	 * 理财账号
	 */
	private String clientId;
	/**
	 * 客户名字
	 */
	private String custName;
	/**
	 * 汇款方式
	 */
	private Integer remittanceType;
	private String remittanceTypeName;
	/**
	 * 汇款银行
	 */
	private String remittanceBankName;
	/**
	 * 银行类型
	 */
	private Integer bankType;
	private String bankTypeName;
	/**
	 * 汇款账号
	 */
	private String remittanceAccount;
	/**
	 * 汇款账户名
	 */
	private String remittanceAccountName;
	/**
	 * 汇款金额
	 */
	private String depositBalance;
	/**
	 * 币种
	 */
	private Integer moneyType;
	private String moneyTypeName;
	/**
	 * 收款银行
	 */
	private String receivingBankName;
	/**
	 * 收款账号
	 */
	private String receivingAccount;
	/**
	 * 收款账户名
	 */
	private String receivingAccountName;
	/**
	 *
	 */
	private String swiftCode;
	/**
	 * 申请时间
	 */
	private String applicationTime;
	/**
	 * 到账时间
	 */
	private String settlementDt;
	/**
	 * 到账金额
	 */
	private BigDecimal settlementAmt = BigDecimal.ZERO;

	/**
	 * 凭证信息
	 */
	private List<FundDepositImageVO> imagesList;

	private String instanceId;

	private String deployId;

	private String definitionId;

	private String assignDrafter;

	private Integer dealPermissions;

}
