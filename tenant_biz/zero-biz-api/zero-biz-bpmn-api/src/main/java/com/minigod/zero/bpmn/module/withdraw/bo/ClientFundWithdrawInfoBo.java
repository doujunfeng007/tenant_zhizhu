package com.minigod.zero.bpmn.module.withdraw.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.bpmn.module.account.constants.RegexpConstants;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.bpmn.module.withdraw.validator.RecvBankAcctAndTypeValid;
import com.minigod.zero.bpmn.module.withdraw.validator.SwiftCodeAndTransferTypeValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author chenyu
 * @title ClientFundWithdrawInfoBo
 * @date 2023-04-04 20:10
 * @description 客户出金申请信息业务对象
 */
@Data
@ApiModel("客户出金申请信息业务对象")
public class ClientFundWithdrawInfoBo {
    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;

	/**
	 * 出金银行ID
	 */
	@ApiModelProperty(value = "出金银行ID")
	private Long withdrawalsId;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    @NotBlank(message = "流水号不能为空", groups = { EditGroup.class })
    private String applicationId;

    /**
     * 客户帐号
     */
    @ApiModelProperty(value = "客户帐号")
    private String clientId;

    /**
     * 资金帐号
     */
    @ApiModelProperty(value = "资金帐号")
    @NotBlank(message = "资金帐号不能为空", groups = { AddGroup.class})
    @Pattern(regexp = RegexpConstants.REGEX_NUMBER, message = "资金账号格式错误 如: 6600200013", groups = { AddGroup.class})
    private String fundAccount;

    /**
     * 委托日期
     */
    @ApiModelProperty(value = "委托日期")
    @NotNull(message = "委托日期不能为空", groups = { AddGroup.class})
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date entrustTime;

    /**
     * 证件类型[00=中国居民身份证 01=中国香港居民身份证 02=护照 04=其他国家/地区身份证 10=工商注册营业执照]
     */
    @ApiModelProperty(value = "证件类型[00=中国居民身份证 01=中国香港居民身份证 02=护照 04=其他国家/地区身份证 10=工商注册营业执照]")
    private Integer IdKind;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private String idNo;

    /**
     * 中文名
     */
    @ApiModelProperty(value = "中文名")
    private String clientName;

    /**
     * 英文名
     */
    @ApiModelProperty(value = "英文名")
    private String clientNameSpell;

    /**
     * 银行账号类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]
     */
    @ApiModelProperty(value = "银行账号类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]")
    private String bankAcctType;

    /**
     * 性别[0-男 1-女 2-其它]
     */
    @ApiModelProperty(value = "性别[0-男 1-女 2-其它]")
    private String sex;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Length(min = 0, max = 20, message = "手机号码长度最大20个数字", groups = { AddGroup.class})
    private String mobile;

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]
     */
    @ApiModelProperty(value = "取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]")
    @NotNull(message = "取款方式不能为空")
    private Integer transferType;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @ApiModelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
    @NotBlank(message = "币种代码不能为空")
    private String ccy;

    /**
     * 提取金额
     */
    @ApiModelProperty(value = "提取金额")
    @NotNull(message = "提取金额不能为空")
    @Digits(integer = 10, fraction = 3, message = "提取金额最多3位小数")
    @DecimalMin(value = "0.001", message = "提取金额必须大于0", groups = { AddGroup.class})
    private BigDecimal withdrawAmount;

    /**
     * 冻结资金
     */
    @ApiModelProperty(value = "冻结资金")
    private BigDecimal frozenBalance;

    /**
     * 可提余额
     */
    @ApiModelProperty(value = "可提余额")
    private BigDecimal drawableBalance;

    /**
     * 手续费扣除方式[1-从提取金额中扣除,2-从账户余额中扣除]
     */
    @ApiModelProperty(value = "手续费扣除方式[0-从提取金额中扣除,1-从账户余额中扣除]")
    @NotNull(message = "手续费扣除方式不能为空")
    private Integer deductWay;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    @Digits(integer = 10, fraction = 3, message = "手续费最多3位小数")
    @DecimalMin(value = "0", message = "手续费金额大于0", groups = { AddGroup.class})
    private BigDecimal chargeFee;

    /**
     * 实际提取金额
     */
    @ApiModelProperty(value = "实际提取金额")
    private BigDecimal actualAmount;

    /**
     * 资金冻结日期
     */
    @ApiModelProperty(value = "资金冻结日期")
    private Date initDate;

    /**
     * 资金冻结流水
     */
    @ApiModelProperty("资金冻结流水")
    private String frozenRefId;

    /**
     * 原手续费
     */
    @ApiModelProperty(value = "原手续费")
    private BigDecimal oldChargeFee;

    /**
     * 免手续费标识[0-否 1-是]
     */
    @ApiModelProperty(value = "免除手续费标识[0-否 1-是]")
    private Integer freeFeeFlag;

    /**
     * 已退款金额
     */
    @ApiModelProperty(value = "已退款金额")
    private BigDecimal refundedAmount;

    /**
     * 已退款日期
     */
    @ApiModelProperty(value = "已退款日期")
    private Date refundedDate;

    /**
     * 收款账户类型
     */
    @ApiModelProperty(value = "收款账户类型")
    private Integer recvAccountType;
	/**
	 * 收款银行卡类型[1香港  2大陆 3海外]
	 */
	@ApiModelProperty(value="收款银行卡类型[1香港  2大陆 3海外]")
	private Integer recvBankType;

    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String recvBankCode;

    /**
     * 收款bankId
     */
    @ApiModelProperty(value = "收款bankId")
    private String recvBankId;

    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String recvBankName;

    /**
     * 收款银行帐户
     */
    @ApiModelProperty(value = "收款银行帐户")
    @NotBlank(message = "收款银行帐户不能为空")
    @Length(min = 1, max = 32, message = "收款银行帐户长度不超过32个字符", groups = { AddGroup.class})
    private String recvBankAcct;

    /**
     * 收款银行帐户名称
     */
    @ApiModelProperty(value = "收款银行帐户名称")
    @NotBlank(message = "收款银行帐户名称不能为空")
    private String recvBankAcctName;


    /**
     * 收款SWIFT代码
     */
    @ApiModelProperty(value = "收款SWIFT代码")
    private String recvSwiftCode;

    /**
     * 客户联系地址
     */
    @ApiModelProperty(value = "客户联系地址")
    private String recvContactAddress;

    /**
     * 收款银行开户支行名称
     */
    @ApiModelProperty(value = "收款银行开户支行名称")
    private String recvBankBranchName;

    /**
     * 开户支行代码
     */
    @ApiModelProperty(value = "开户支行代码")
    private String recvBankBranchCode;

    /**
     * 付款信息表ID
     */
    private Long payBankId;

    /**
     * 付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]
     */
    @ApiModelProperty(value = "付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]")
    private Integer payWay;

    /**
     * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    @ApiModelProperty(value = "银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]")
    private Integer payType;

    /**
     * 付款银行代码
     */
    @ApiModelProperty(value = "付款银行代码")
    @NotBlank(message = "付款银行代码不能为空", groups = { EditGroup.class })
    private String payBankCode;

    /**
     * 付款银行名称
     */
    @ApiModelProperty(value = "付款银行名称")
    @NotBlank(message = "付款银行名称不能为空", groups = { EditGroup.class })
    private String payBankName;

    /**
     * 付款银行账户名称
     */
    @ApiModelProperty(value = "付款银行账户名称")
    private String payAccountName;

    /**
     * 付款银行账户号码
     */
    @ApiModelProperty(value = "付款银行账户号码")
    @NotBlank(message = "付款银行账户号码不能为空", groups = { EditGroup.class })
    private String payBankAcct;

    /**
     * 银行状态[0-未提交 1-成功 2-失败]
     */
    @ApiModelProperty(value = "银行状态[0-未提交 1-成功 2-失败]")
    private Integer bankState;

    /**
     * 银行事务id
     */
    @ApiModelProperty(value = "银行事务id")
    private String bankRefId;

    /**
     * 银行响应消息
     */
    @ApiModelProperty(value = "银行响应编码")
    private String bankRtCode;

    /**
     * 银行响应消息
     */
    @ApiModelProperty(value = "银行响应消息")
    private String bankRtMsg;

    /**
     * 汇款编号
     */
    @ApiModelProperty(value = "汇款编号")
    private String remittanceId;

    /**
     * 第三者收款标记[0-否 1-是]
     */
    @ApiModelProperty(value = "第三者收款标记[0-否 1-是]")
    private Integer thirdRecvFlag;

    /**
     * 与第三者收款人关系
     */
    @ApiModelProperty(value = "与第三者收款人关系")
    private String thirdRecvReal;

    /**
     * 第三者收款原因
     */
    @ApiModelProperty(value = "第三者收款原因")
    private String thirdRecvReason;

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    private Integer callbackStatus;

    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    @ApiModelProperty(value = "柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]")
    private Integer gtBusinessStep;

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    @ApiModelProperty(value = "柜台处理状态[0-未知 1-处理成功 2-处理失败]")
    private Integer gtDealStatus;

    /**
     * 柜台处理时间
     */
    @ApiModelProperty(value = "柜台处理时间")
    private Date gtDealDate;

    /**
     * 柜台响应编码
     */
    @ApiModelProperty(value = "柜台响应编码")
    private String gtRtCode;

    /**
     * 柜台响应消息
     */
    @ApiModelProperty(value = "柜台响应消息")
    private String gtRtMsg;


    /**
     * 导出状态[0-未导出 1-已导出]
     */
    @ApiModelProperty(value = "导出状态[0-未导出 1-已导出]")
    private Integer exportStatus;

    /**
     * 导出时间
     */
    @ApiModelProperty(value = "导出时间")
    private Date exportDate;

    /**
     * 打印状态[0-未打印 1-已打印]
     */
    @ApiModelProperty(value = "打印状态[0-未打印 1-已打印]")
    private Integer printStatus;

    /**
     * 打印时间
     */
    @ApiModelProperty(value = "打印时间")
    private Date printDate;

    /**
     * 申请来源[1-客户提交  2-后台录入]
     */
    @ApiModelProperty(value = "申请来源[1-客户提交  2-后台录入]")
    private Integer applySource;

    /**
     * 客服备注
     */
    @ApiModelProperty(value = "客服备注")
    private String custRemark;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

	private String phoneAreaCode;
	private String phoneNumber;


    /**
     * #######################################################################
     * ################################扩展字段################################
     * #######################################################################
     */

    @RecvBankAcctAndTypeValid(groups = {AddGroup.class})
    @JSONField(serialize = false)
    private List<String> getRecvBankAcctAndTypeComposite() {
        return Arrays.asList(
            getRecvBankAcct(),
            getBankAcctType()
        );
    }

    @SwiftCodeAndTransferTypeValid(emptyMessage = "收款SWIFT代码不能为空", formatMessage = "收款SWIFT代码格式错误, 限字母数字", min = 8,
        max = 11, lengthMessage = "收款SWIFT代码格式错误, 限8-11个字符。", groups = {AddGroup.class})
    @JSONField(serialize = false)
    private List<String> getSwiftCodeAndTransferTypeComposite() {
        return Arrays.asList(
            getRecvSwiftCode(),
            getTransferType() != null ? String.valueOf(getTransferType()) : ""
        );
    }

    /**
     * 付款银行信息Id
     */
    @ApiModelProperty(value = "付款银行信息")
    private Long bankPayingId;

    /** 流水号列表 */
    private List<String> applicationIdList;

    /**
     *  海外汇款信息
     */
    @ApiModelProperty(value = "海外汇款信息")
    private ClientTeltransferInfoBo telegram;

    @ApiModelProperty(value = "银行参考流水")
    private String bankReference;
	/**
	 * 汇款凭证地址
	 */
	private String remittanceVoucher;

    private Long userId;


    private String tenantId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
