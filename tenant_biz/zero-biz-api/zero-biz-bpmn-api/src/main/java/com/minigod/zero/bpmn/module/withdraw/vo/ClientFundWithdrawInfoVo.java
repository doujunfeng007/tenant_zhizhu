package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenyu
 * @title ClientFundWithdrawInfoVo
 * @date 2023-04-04 20:10
 * @description 客户出金申请信息视图对象 client_fund_withdraw_info
 */
@Data
@ApiModel("客户出金申请信息视图对象")
@ExcelIgnoreUnannotated
public class ClientFundWithdrawInfoVo  {

    private static final long serialVersionUID = 6840397475620248041L;

    /**
     * 自增ID
     */
    @ExcelProperty(value = "自增ID")
    @ApiModelProperty("自增ID")
    private Long id;

    /**
     * 流水号
     */
    @ExcelProperty(value = "流水号")
    @ApiModelProperty("流水号")
    private String applicationId;

    /**
     * 客户帐号
     */
    @ExcelProperty(value = "客户帐号")
    @ApiModelProperty("客户帐号")
    private String clientId;

    /**
     * 资金帐号
     */
    @ExcelProperty(value = "资金帐号")
    @ApiModelProperty("资金帐号")
    private String fundAccount;

    /**
     * 委托日期
     */
    @ExcelProperty(value = "委托日期")
    @ApiModelProperty("委托日期")
    private Date entrustTime;

    /**
     * 证件类型[00=中国居民身份证 01=中国香港居民身份证 02=护照 04=其他国家/地区身份证 10=工商注册营业执照]
     */
    @ExcelProperty(value = "证件类型[00=中国居民身份证 01=中国香港居民身份证 02=护照 04=其他国家/地区身份证 10=工商注册营业执照]")
    @ApiModelProperty("证件类型[00=中国居民身份证 01=中国香港居民身份证 02=护照 04=其他国家/地区身份证 10=工商注册营业执照]")
    private String idType;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    @ApiModelProperty("证件号码")
    private String idCode;

    /**
     * 中文名
     */
    @ExcelProperty(value = "中文名")
    @ApiModelProperty("中文名")
    private String custName;

    /**
     * 英文名
     */
    @ExcelProperty(value = "英文名")
    @ApiModelProperty("英文名")
    private String custEname;

    /**
     * 银行账号类型
     */
    @ExcelProperty(value = "银行账号类型")
    @ApiModelProperty("银行账号类型")
    private String bankAcctType;

    /**
     * 性别[0-男 1-女 2-其它]
     */
    @ExcelProperty(value = "性别[0-男 1-女 2-其它]")
    @ApiModelProperty("性别[0-男 1-女 2-其它]")
    private String sex;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
    @ExcelProperty(value = "取款方式")
    @ApiModelProperty("取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]")
    private Integer transferType;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @ExcelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
    @ApiModelProperty("币种代码[CNY-人民币 USD-美元 HKD-港币]")
    private String ccy;

    /**
     * 提取金额
     */
    @ExcelProperty(value = "提取金额")
    @ApiModelProperty("提取金额")
    private BigDecimal withdrawAmount;

    /**
     * 冻结资金
     */
    @ExcelProperty(value = "冻结资金")
    @ApiModelProperty("冻结资金")
    private BigDecimal frozenBalance;

    /**
     * 可提余额
     */
    @ExcelProperty(value = "可提余额")
    @ApiModelProperty("可提余额")
    private BigDecimal drawableBalance;

    /**
     * 手续费扣除方式[1-从提取金额中扣除,2-从账户余额中扣除]
     */
    @ExcelProperty(value = "手续费扣除方式[1-从提取金额中扣除,2-从账户余额中扣除]")
    @ApiModelProperty("手续费扣除方式[1-从提取金额中扣除,2-从账户余额中扣除]")
    private Integer deductWay;

    /**
     * 手续费
     */
    @ExcelProperty(value = "手续费")
    @ApiModelProperty("手续费")
    private BigDecimal chargeFee;

    /**
     * 实际提取金额
     */
    @ExcelProperty(value = "实际提取金额")
    @ApiModelProperty("实际提取金额")
    private BigDecimal actualAmount;

    /**
     * 资金冻结日期
     */
    @ExcelProperty(value = "资金冻结日期")
    @ApiModelProperty("资金冻结日期")
    private Date initDate;

    /**
     * 资金冻结流水
     */
    @ExcelProperty(value = "资金冻结流水")
    @ApiModelProperty("资金冻结流水")
    private String frozenRefId;

    /**
     * 原手续费
     */
    @ExcelProperty(value = "原手续费")
    @ApiModelProperty("原手续费")
    private BigDecimal oldChargeFee;

    /**
     * 免除手续费标识[0-否 1-是]
     */
    @ExcelProperty(value = "免除手续费标识[0-否 1-是]")
    @ApiModelProperty("免除手续费标识[0-否 1-是]")
    private Integer freeFeeFlag;

    /**
     * 已退款金额
     */
    @ExcelProperty(value = "已退款金额")
    @ApiModelProperty("已退款金额")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private BigDecimal refundedAmount;

    /**
     * 已退款日期
     */
    @ExcelProperty(value = "已退款日期")
    @ApiModelProperty("已退款日期")
    private Date refundedDate;

    /**
     * 收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]
     */
    @ExcelProperty(value = "收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]")
    @ApiModelProperty("收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]")
    private Integer recvAccountType;

    /**
     * 收款银行代码
     */
    @ExcelProperty(value = "收款银行代码")
    @ApiModelProperty("收款银行代码")
    private String recvBankCode;

    /**
     * 收款bankId
     */
    @ExcelProperty(value = "收款bankId")
    @ApiModelProperty("收款bankId")
    private String recvBankId;

    /**
     * 收款银行名称
     */
    @ExcelProperty(value = "收款银行名称")
    @ApiModelProperty("收款银行名称")
    private String recvBankName;

    /**
     * 收款银行帐户
     */
    @ExcelProperty(value = "收款银行帐户")
    @ApiModelProperty("收款银行帐户")
    private String recvBankAcct;

    /**
     * 收款银行帐户名称
     */
    @ExcelProperty(value = "收款银行帐户名称")
    @ApiModelProperty("收款银行帐户名称")
    private String recvBankAcctName;

	@ExcelIgnore
	@ApiModelProperty(value="银行卡类型[1香港  2大陆 3海外]")
	private Integer recvBankType;

    /**
     * 收款SWIFT代码
     */
    @ExcelProperty(value = "收款SWIFT代码")
    @ApiModelProperty("收款SWIFT代码")
    private String recvSwiftCode;

    /**
     * 客户联系地址
     */
    @ExcelProperty(value = "客户联系地址")
    @ApiModelProperty("客户联系地址")
    private String recvContactAddress;

    /**
     * 收款银行开户支行名称
     */
    @ExcelProperty(value = "收款银行开户支行名称")
    @ApiModelProperty("收款银行开户支行名称")
    private String recvBankBranchName;

    /**
     * 开户支行代码
     */
    @ExcelProperty(value = "开户支行代码")
    @ApiModelProperty("开户支行代码")
    private String recvBankBranchCode;

    /**
     * 付款信息表ID
     */
    private Long payBankId;

    /**
     * 付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]
     */
    @ExcelProperty(value = "付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]")
    @ApiModelProperty("付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]")
    private Integer payWay;

    /**
     * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    @ExcelProperty(value = "银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]")
    @ApiModelProperty("银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]")
    private Integer payType;

    /**
     * 付款银行代码
     */
    @ExcelProperty(value = "付款银行代码")
    @ApiModelProperty("付款银行代码")
    private String payBankCode;

    /**
     * 付款银行名称
     */
    @ExcelProperty(value = "付款银行名称")
    @ApiModelProperty("付款银行名称")
    private String payBankName;

    /**
     * 付款银行账户名称
     */
    @ExcelProperty(value = "付款银行账户名称")
    @ApiModelProperty("付款银行账户名称")
    private String payAccountName;

    /**
     * 付款银行账户号码
     */
    @ExcelProperty(value = "付款银行账户号码")
    @ApiModelProperty("付款银行账户号码")
    private String payBankAcct;

    /**
     * 银行状态[0-未提交 1-成功 2-失败]
     */
    @ExcelProperty(value = "银行状态[0-未提交 1-成功 2-失败]")
    @ApiModelProperty("银行状态[0-未提交 1-成功 2-失败]")
    private Integer bankState;

    /**
     * 银行事务id
     */
    @ExcelProperty(value = "银行事务id")
    @ApiModelProperty("银行事务id")
    private String txnRefId;

    /**
     * 银行响应消息
     */
    @ApiModelProperty(value = "银行响应编码")
    private String bankRtCode;

    /**
     * 银行响应消息
     */
    @ExcelProperty(value = "银行响应消息")
    @ApiModelProperty("银行响应消息")
    private String bankMsg;

    /**
     * 汇款编号
     */
    @ExcelProperty(value = "汇款编号")
    @ApiModelProperty("汇款编号")
    private String remittanceId;

    /**
     * 第三者收款标记[0-否 1-是]
     */
    @ExcelProperty(value = "第三者收款标记[0-否 1-是]")
    @ApiModelProperty("第三者收款标记[0-否 1-是]")
    private Integer thirdRecvFlag;

    /**
     * 与第三者收款人关系
     */
    @ExcelProperty(value = "与第三者收款人关系")
    @ApiModelProperty("与第三者收款人关系")
    private String thirdRecvReal;

    /**
     * 第三者收款原因
     */
    @ExcelProperty(value = "第三者收款原因")
    @ApiModelProperty("第三者收款原因")
    private String thirdRecvReason;

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    private Integer callbackStatus;

    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    @ExcelProperty(value = "柜台业务处理步骤[10000-资金冻结 10001-资金解冻 10002-资金取出  10003-存入资金]")
    @ApiModelProperty("柜台业务处理步骤[10000-资金冻结 10001-资金解冻 10002-资金取出  10003-存入资金]")
    private Integer gtBusinessStep;

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    @ExcelProperty(value = "柜台处理状态[0-未知 1-处理成功 2-处理失败]")
    @ApiModelProperty("柜台处理状态[0-未知 1-处理成功 2-处理失败]")
    private Integer gtDealStatus;

    /**
     * 柜台处理时间
     */
    @ExcelProperty(value = "柜台处理时间")
    @ApiModelProperty("柜台处理时间")
    private Date gtDealDate;

    /**
     * 柜台响应编码
     */
    @ExcelProperty(value = "柜台响应编码")
    @ApiModelProperty("柜台响应编码")
    private String gtRtCode;

    /**
     * 柜台响应消息
     */
    @ExcelProperty(value = "柜台响应消息")
    @ApiModelProperty("柜台响应消息")
    private String gtMsg;

    /**
     * 导出状态[0-未导出 1-已导出]
     */
    @ExcelProperty(value = "导出状态[0-未导出 1-已导出]")
    @ApiModelProperty("导出状态[0-未导出 1-已导出]")
    private Integer exportStatus;

    /**
     * 导出时间
     */
    @ExcelProperty(value = "导出时间")
    @ApiModelProperty("导出时间")
    private Date exportDate;

    /**
     * 打印状态[0-未打印 1-已打印]
     */
    @ExcelProperty(value = "打印状态[0-未打印 1-已打印]")
    @ApiModelProperty("打印状态[0-未打印 1-已打印]")
    private Integer printStatus;

    /**
     * 打印时间
     */
    @ExcelProperty(value = "打印时间")
    @ApiModelProperty("打印时间")
    private Date printDate;

    /**
     * 申请来源[1-网上交易  2-网上营业厅 3-综合后台录入 4-手机证券]
     */
    @ExcelProperty(value = "申请来源[1-网上交易  2-网上营业厅 3-综合后台录入 4-手机证券]")
    @ApiModelProperty("申请来源[1-网上交易  2-网上营业厅 3-综合后台录入 4-手机证券]")
    private Integer applySource;

    /**
     * 客服备注
     */
    @ExcelProperty(value = "客服备注")
    @ApiModelProperty("客服备注")
    private String custRemark;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;


    private Long userId;

    /**
     * #######################################################################
     * ################################扩展字段################################
     * #######################################################################
     */

    /**
     * 取款后余额
     */
    @ApiModelProperty("取款后余额")
    private BigDecimal remainBalance;
    public BigDecimal getRemainBalance(){
        if(ObjectUtil.isNotEmpty(drawableBalance)){
            return drawableBalance.subtract(frozenBalance);
        }
        return null;
    }

    /**
     * 区域
     */
    @ApiModelProperty("区域")
    private String nationality;


    private String bankReference;

}
