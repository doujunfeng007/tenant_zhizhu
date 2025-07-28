package com.minigod.zero.bpmn.module.withdraw.bo;

import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/11 9:35
 * @description：后台管理出金这块参数公共类
 */
@Data
public class WithdrawInfoDTO {

	private String applicationId;
	/**
	 * 理财账号
	 */
	private String clientId;
	/**
	 * 付款银行编号
	 */
	private String payBankCode;
	/**
	 * 申请状态
	 */
	private Integer applicationStatus;
	/**
	 * 状态集合
	 */
	private List<Integer> applicationStatusList;
	/**
	 * 开始时间
	 * 这个时间具体定义什么时间，看页面的定义
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 币种
	 * {@link com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum.CurrencyType}
	 */
	private String currency;
	/**
	 * 收款银行编号
	 */
	private String recvBankCode;
	/**
	 * 取款类型
	 * {@link com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum.TransferTypeEnum}
	 */
	private Integer transferType;
	/**
	 * 状态，具体代表哪个状态看页面的定义
	 */
	private String status;
	/**
	 * 公共字段，多个字段查询合并成一个查询，使用这个字段
	 * ，具体包含哪些字段，看页面定义
	 */
	private String keyword;
	/**
	 * 数据来源
	 * {@link SystemCommonEnum.ApplySource}
	 */
	private Integer applySource;
	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 受理截止时间
	 */
	private String approvalTime;
	/**
	 *
	 */
	private Integer acceptType;

}
