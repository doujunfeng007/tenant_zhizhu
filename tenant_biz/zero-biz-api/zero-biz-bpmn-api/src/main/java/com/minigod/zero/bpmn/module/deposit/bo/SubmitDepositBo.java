package com.minigod.zero.bpmn.module.deposit.bo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: SubmitDepositBo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/2
 * @Version 1.0
 */
@Data
@Valid
public class SubmitDepositBo {
    private Long id;//主键
    @NotNull(message = "请选择币种")
    private Integer currency;//币种 1港币 2 美元
    @NotNull(message = "请选择银行卡类型")
    private Integer bankType;//银行 1大陆 2香港 3其他
    private String clientId;//客户账户
    @NotBlank(message = "请填写银行名称")
    private String bankName;//银行名称：FPS转数快；快捷入账
    @NotBlank(message = "请填写银行编号")
    private String bankCode;//银行代码：FPS     ； EDDA
    @NotBlank(message = "请选择资金账户")
    private String fundAccount;//资金账户
    @NotBlank(message = "请选择资金账户")
    private String fundAccountName;//存入账户名称
    @NotNull(message = "请填写存入金额")
    @DecimalMin(value = "0.01" ,message = "金额不能小于 0.01 ")
    private BigDecimal depositMoney;//存入金额
    private String remarks;//备注信息
    private BigDecimal chargeMoney;//手续费
    @Size(min = 1,message = "请上传入金凭证")
    private List<Long> accImgIds;//上传凭证图片ID
    @NotBlank( message = "请填写收款账户")
    private String receivingAccount;//收款账户号码
    @NotBlank( message = "请填写收款账户名")
    private String receivingAccountName;//收款人账户名
    @NotBlank( message = "请填写收款人地址")
    private String receivingAddress;//收款人地址
    @NotBlank( message = "请填写收款人中文名")
    private String receivingBankNameCn;//收款银行中文名
    @NotBlank( message = "请填写收款人英文名")
    private String receivingBankNameEn;//收款银行英文名
    @NotBlank( message = "请填写收款银行编号")
    private String receivingBankCode;//收款银行编码
    @NotBlank( message = "请填写收款银行地址")
    private String receivingBankAddress;//收款银行地址
    @NotBlank( message = "请填写收款银行 SWIFT CODE")
    private String swiftCode;//SWIFT代码
    @NotBlank( message = "请填写汇款银行名称")
    private String remittanceBankName; // 汇款银行名称
    @NotBlank( message = "请填写汇款账号")
    private String remittanceBankAccount; // 汇款账号
    @NotBlank( message = "请填写汇款银行户名")
    private String remittanceAccountNameEn; // 汇款银行户名
    @NotBlank( message = "请填写汇款银行cord简称")
    private String remittanceBankCorde; //汇款银行cord简称
    private String remittanceBankId;//edda入金需要：汇款银行bankId
    //账户类别[1-港币 2-美元 3-人民币 0-综合账户]
    @NotNull( message = "请选择账户类别")
    private Integer bankAccountCategory;
    //app是否可撤销[0-否 1-是]
    private Integer isCancel;
}
