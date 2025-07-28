package com.minigod.zero.bpmn.module.withdraw.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.minigod.zero.bpmn.module.withdraw.enums.RefundType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/11 9:54
 * @description：
 */
@Data
public class WithdrawInfoVO {
	/**
	 * 预约流水号
	 */
	private String applicationId;
	/**
	 * 理财账号
	 */
	private String clientId;
	/**
	 * 英文名
	 */
	private String custEname;
	/**
	 * 中文名
	 */
	private String custName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 提现金额
	 */
	private BigDecimal withdrawAmount = BigDecimal.ZERO;
	/**
	 * 实际提出金额
	 */
	private BigDecimal actualAmount = BigDecimal.ZERO;
	/**
	 * 手续费
	 */
	private BigDecimal chargeFee = BigDecimal.ZERO;
	/**
	 * 可提金额
	 */
	private BigDecimal drawableBalance = BigDecimal.ZERO;
	/**
	 * 提取后余额
	 */
	private BigDecimal remainBalance = BigDecimal.ZERO;
	/**
	 * 提取方式
	 * {@link com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum.TransferTypeEnum}
	 */
	private Integer transferType;
	private String transferTypeName;
	/**
	 * 手续费扣除方式
	 * {@link com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum.DeductWay}
	 */
	private Integer deductWay;
	private String deductWayName;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 省份
	 */
	private String provinceName;
	/**
	 * 城市
	 */
	private String cityName;
	/**
	 * 收款账户
	 */
	private String recvBankAcct;
	/**
	 * 收款账户名
	 */
	private String recvBankAcctName;
	/**
	 * 收款银行
	 */
	private String recvBankName;

	private String recvBankId;

	/**
	 * 付款银行名称
	 */
	private String payBankName;
	private String payAccountName;
	private String payBankAcct;
	/**
	 * 付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]
	 */
	@TableField(value = "pay_way")
	@ApiModelProperty(value="付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]")
	private Integer payWay;

	/**
	 * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
	 */
	@TableField(value = "pay_type")
	@ApiModelProperty(value="银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]")
	private Integer payType;

	/**
	 * 状态
	 */
	private Integer status;
	private String statusName;
	/**
	 * 申请时间
	 */
	private String createTime;
	/**
	 * 委托时间
	 */
	private String entrustTime;

	private String instanceId;
	private String definitionId;
	private String processInstanceId;

	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount = BigDecimal.ZERO;
	/**
	 * 退款手续费
	 */
	private BigDecimal refundBankFee = BigDecimal.ZERO;
	/**
	 * 退款净额
	 */
	private BigDecimal  netRefundAmount = BigDecimal.ZERO;
	/**
	 * 退款方式
	 * {@link RefundType}
	 */
	private Integer refundType;
	/**
	 *
	 */
	private String refundTypeName;
	/**
	 * 退款原因
	 */
	private String refundReason;
	/**
	 * 退款时间/审核时间
	 */
	private String refundedDate;
	/**
	 * 是否退款，1退款，0未退款
	 */
	private Integer isRefund;

	private String recvSwiftCode;

	private String bankReference;

	private String custRemark;

	private String taskId;

}
