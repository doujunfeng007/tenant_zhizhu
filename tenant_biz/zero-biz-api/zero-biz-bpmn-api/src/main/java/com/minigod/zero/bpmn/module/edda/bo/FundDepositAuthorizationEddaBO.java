package com.minigod.zero.bpmn.module.edda.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.customer.vo.FundDepositEddaVO
 * @Description: 入金授权 入参
 * @Author: linggr
 * @CreateDate: 2024/5/9 18:03
 * @Version: 1.0
 */
@Data
public class FundDepositAuthorizationEddaBO {

	@ApiModelProperty(value = "bankType银行 1大陆 0香港 2其他")
	@NotNull(message = "bankType银行类型不能为空")
	private Integer bankType;//_banks接口

	@ApiModelProperty(value = "bankName银行名称")
	@NotNull(message = "银行名称不能为空")
	private String bankName;//_banks接口

	@ApiModelProperty(value = "bankIdNo银行id")
	@NotNull(message = "银行id不能为空")
	private String bankId;//_banks接口

	@ApiModelProperty(value = "银行代码")
	@NotNull(message = "银行代码不能为空")
	private String bankCode;//_banks接口

	@ApiModelProperty(value = "bankIdNo银行开户证件号码")
	@NotNull(message = "银行开户证件号码不能为空")
	private String bankIdNo;//custinfo+手填


	@ApiModelProperty(value = "bankIdKind银行证件类型[1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照]")
	@NotNull(message = "银行证件类型不能为空")
	private Integer bankIdKind;//custinfo+手填


	@ApiModelProperty(value = "depositAccountType存入账户类型:1 港币账户; 2 综合多币种账户")
	@NotNull(message = "账户类型不能为空")
	private Integer depositAccountType;

	@ApiModelProperty(value = "depositAccount存入银行账户")
	@NotNull(message = "银行账户账号不能为空")
	private String depositAccount;


	/**
	 * 收款银行名称
	 */
	@ApiModelProperty(value = "收款银行名称")
	@NotNull(message = "收款银行名称不能为空")
	private String benefitBank;

	/**
	 * 收款银行code
	 */
	@ApiModelProperty(value = "收款银行code")
	@NotNull(message = "收款银行code不能为空")
	private String benefitBankCode;

	/**
	 * 收款账号
	 */
	@ApiModelProperty(value = "收款账号")
	@NotNull(message = "收款账号不能为空")
	private String benefitNo;

	/**
	 * 收款账户名称
	 */
	@ApiModelProperty(value = "收款账户名称")
	@NotNull(message = "收款账户名称不能为空")
	private String benefitAccount;
	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种代码[ 币种 1港币 2美元 3人民币]")
	@NotNull(message = "币种不能为空  币种 1港币 2美元 3人民币")
	private Integer moneyType;


	@ApiModelProperty(value = "银行app端图标")
	@NotNull(message = "银行app端图标不能为空")
	private String appIcon;

	@ApiModelProperty(value = "单笔限额")
	@NotNull(message = "单笔限额不能为空")
	private BigDecimal maxAmt;

	@ApiModelProperty(value = "银行配置id")
	//@NotNull(message = "银行配置id不能为空")
	private Long secDepositBankId;

}
