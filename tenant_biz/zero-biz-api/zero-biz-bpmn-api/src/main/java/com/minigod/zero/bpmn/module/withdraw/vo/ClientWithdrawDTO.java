package com.minigod.zero.bpmn.module.withdraw.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/4 13:42
 * @description：
 */
@Data
public class ClientWithdrawDTO {
	/**
	 * 客户ID
	 */
	@NotNull(message = "客户id不能为空")
	private Long custId;
	/**
	 * 出金银行ID
	 */
	@ApiModelProperty(value = "出金银行ID")
	private Long withdrawalsId;
	/**
	 * 理财账号
	 */
	@NotBlank(message = "理财账号不能为空")
	private String clientId;
	/**
	 * 客户名称
	 */
	@NotBlank(message = "客户名称不能为空")
	private String clientName;
	/**
	 * 客户英文名
	 */
	private String clientNameSpell;
	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号不能为空")
	private String mobile;
	/**
	 * 币种
	 */
	@NotBlank(message = "请选择币种")
	private String currency;
	/**
	 * 委托时间
	 */
	@NotBlank(message = "请选择委托时间")
	private String entrustTime;
	/**
	 * {@link SystemCommonEnum.TransferTypeEnum}
	 */
	@NotNull(message = "请选择取款方式")
	private Integer transferType;
	/**
	 * {@link SystemCommonEnum.BankAcctType}
	 */
	private String bankAcctType;
	/**
	 * 提现金额
	 */
	@NotNull(message = "请输入取款金额")
	private BigDecimal withdrawAmount;
	/**
	 * 提现金额大写
	 */
	private String capitalizationAmount;
	/**
	 * {@link com.minigod.zero.biz.common.enums.BankType}
	 */
	private Integer recvBankType;
	/**
	 * 银行名称
	 */
	private String recvBankName;
	/**
	 * 银行编号
	 */
	private String recvBankCode;
	/**
	 * 银行账号
	 */
	@NotBlank(message = "请输入收款银行账号")
	private String recvBankAcct;
	/**
	 * 收款账户名
	 */
	@NotBlank(message = "请输入收款账户名")
	private String recvBankAcctName;
	/**
	 * swiftCode
	 */
	@NotBlank(message = "请输入收款银行swiftCode")
	private String recvSwiftCode;
	/**
	 * 支行名称
	 */
	private String recvBankBranchName;
	/**
	 * bankId
	 */
	private String recvBankId;
	/**
	 * 手续费
	 */
	private BigDecimal chargeFee;
	/**
	 * {@link SystemCommonEnum.DeductWay}
	 */
	private Integer deductWay;
	/**
	 *
	 * {@link SystemCommonEnum.NationalityTypeEnum}
	 */
	private String nationality;
	/**
	 * 收款银行省份id
	 */
	private Long provinceId;
	/**
	 * 收款银行省份
	 */
	private String provinceName;
	/**
	 * 收款银行城市ID
	 */
	private Long cityId;
	/**
	 * 收款银行城市名称
	 */
	private String cityName;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 与第三张关系
	 */
	private String relationshipWithThirdParties;
	/**
	 * 第三张收款原因
	 */
	private String reasonForReceivingPayment;

}
