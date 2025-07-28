package com.minigod.zero.bpmn.module.withdraw.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.bpmn.module.account.constants.RegexpConstants;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.withdraw.validator.RecvBankAcctAndTypeValid;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: ApplyWithdrawlRequest
 * @Description:
 * @Author chenyu
 * @Date 2024/3/23
 * @Version 1.0
 */
@Data
@Valid
public class ApplyWithdrawlRequest {
	/**
	 * 出金银行ID
	 */
	@ApiModelProperty(value = "出金银行ID")
	private Long withdrawalsId;
	@ApiModelProperty("资金账户")
	private String fundAccount;
	@ApiModelProperty("客户中文名")
	private String clientName;
	@ApiModelProperty("客户英文名")
	private String clientNameSpell;

	@NotBlank(message = "币种代码不能为空", groups = {AddGroup.class})
	@ApiModelProperty("币种")
	private String ccy;
	@ApiModelProperty("收款银行编号")
	private String recvBankCode;
	@ApiModelProperty("银行编号")
	private String bankCode;

	@NotBlank(message = "收款银行帐户不能为空", groups = {AddGroup.class})
	@Length(min = 1, max = 32, message = "收款银行帐户长度不超过32个字符", groups = {AddGroup.class})
	@ApiModelProperty("收款银行账号")
	private String recvBankAcct;
	@ApiModelProperty("收款银行名称")
	private String recvBankName;
	@ApiModelProperty("收款银行代码")
	private String bankId;
	@ApiModelProperty("取款方式 [1海外银行汇款 2 香港银行卡转账]")
	@NotNull(message = "取款方式不能为空", groups = {AddGroup.class})
	private Integer transferType;

	@ApiModelProperty("手续费扣除方式")
	@NotNull(message = "手续费扣除方式不能为空", groups = {AddGroup.class})
	private Integer deductWay;

	@NotNull(message = "取款金额不能为空", groups = {AddGroup.class})
	@Digits(integer = 10, fraction = 2, message = "取款金额最多2位小数")
	@DecimalMin(value = "0.01", message = "取款金额必须大于0", groups = {AddGroup.class})
	@ApiModelProperty("取款金额")
	private Double amount;
	@ApiModelProperty("手续费")
	private Double chargeFee;
	@ApiModelProperty("取款来源")
	private String source;
	@ApiModelProperty("银行账号类型")
	private String bankAcctType; //银行账号类型
	@ApiModelProperty("银行卡类型 1香港 2大陆")
	private String bankType;
	//电汇
	@ApiModelProperty("电汇省份Id")
	private Integer provinceId;

	@ApiModelProperty("电汇城市Id")
	private Integer cityId;
	@ApiModelProperty("电汇地址")
	private String address;
	@NotBlank(message = "电汇代码不能为空")
	@Pattern(regexp = "^[a-z0-9A-Z]+$", message = "电汇代码格式错误")
	@Length(min = 8, max = 11, message = "电汇代码长度8-11个字符")
	@ApiModelProperty("电汇代码")
	private String swiftCode;
	private String visibleFlag;
	@ApiModelProperty("省份名称")
	private String provinceName;
	@ApiModelProperty("城市名称")
	private String cityName;
	@ApiModelProperty("区域类型")
	private String nationality;
	@ApiModelProperty("分区代码")
	private String branchCode;
	@ApiModelProperty("电汇信息 Id")
	private Integer teltransferInfoId;
	@ApiModelProperty("国家")
	private String country;
	@RecvBankAcctAndTypeValid(groups = {AddGroup.class})
	@JSONField(serialize = false)
	private List<String> recvBankAcctAndTypeComposite;

	/**
	 * #######################################################################
	 * ################################扩展字段################################
	 * #######################################################################
	 */

	private List<String> getRecvBankAcctAndTypeComposite() {
		return Arrays.asList(
			getRecvBankAcct(),
			getBankAcctType()
		);
	}

	public void setRecvBankAcctAndTypeComposite(List<String> recvBankAcctAndTypeComposite) {
		this.recvBankAcctAndTypeComposite = recvBankAcctAndTypeComposite;
	}
}
