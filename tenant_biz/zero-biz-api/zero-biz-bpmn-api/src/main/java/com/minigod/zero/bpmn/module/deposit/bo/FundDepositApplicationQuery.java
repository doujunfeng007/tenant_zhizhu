package com.minigod.zero.bpmn.module.deposit.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: FundDepositApplicationQuery
 * @Description:
 * @Author chenyu
 * @Date 2024/3/9
 * @Version 1.0
 */
@Data
public class FundDepositApplicationQuery implements Serializable {
    @ApiModelProperty(value = "流水号")
    private String applicationId;
    @ApiModelProperty(value = "资金账户")
    private String fundAccount;
    @ApiModelProperty(value = "客户号")
    private String clientId;
    @ApiModelProperty(value = "客户名称")
    private String clientName;
    @ApiModelProperty(value = "客户英文名称")
    private String clientNameEn;
    @ApiModelProperty(value = "申请入金金额")
    private BigDecimal depositAmount;
    @ApiModelProperty(value = "币种")
    private Integer moneyType;
    @ApiModelProperty(value = "汇款银行名称")
    private String remittanceBankName;
    @ApiModelProperty(value = "汇款账户号")
    private String remittanceAccount;
    @ApiModelProperty(value = "汇款账户名称")
    private String remittanceAccountName;
    @ApiModelProperty(value = "银行编码")
    private String bankCode;
    //全球唯一
    @ApiModelProperty(value = "银行代码")
    private String swiftCode;
    @ApiModelProperty(value = "银行分区代码")
    private String bankId;
    @ApiModelProperty(value = "审核状态")
    private Integer applicationStatus;
    @ApiModelProperty(value = "银行流水")
    private String referenceNo;
    @ApiModelProperty(value = "受理人")
    private String assignName;
    @ApiModelProperty(value = "申请开始时间")
    private String beginApplicationDate;
    @ApiModelProperty(value = "申请结束时间")
    private String endApplicationDate;
    @ApiModelProperty(value = "用户号")
    private Long userId;
    @ApiModelProperty(value = "用户手机号码")
    private String phoneNumber;
    @ApiModelProperty(value = "汇款方式")
    private Integer remittanceType;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "审核状态列表")
    private List<Integer> applicationStatusList;

	private String customerWord;
	private String remittanceWord;
}

