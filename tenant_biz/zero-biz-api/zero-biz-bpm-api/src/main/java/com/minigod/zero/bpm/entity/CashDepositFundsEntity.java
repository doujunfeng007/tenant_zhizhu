package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.Boolean;
import java.io.Serializable;

/**
 * 存入资金表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_deposit_funds")
@ApiModel(value = "CashDepositFunds对象", description = "存入资金表")
public class CashDepositFundsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 币种 1港币 2 美元 3 人民币
     */
    @ApiModelProperty(value = "币种 1港币 2 美元 3 人民币")
    private Integer currency;
    /**
     * 银行 1大陆 2香港
     */
    @ApiModelProperty(value = "银行 1大陆 2香港")
    private Integer bankType;
    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String tradeAccount;
    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码")
    private String bankCode;
    /**
     * 存入账户
     */
    @ApiModelProperty(value = "存入账户")
    private String depositAccount;
    /**
     * 存入账户名称
     */
    @ApiModelProperty(value = "存入账户名称")
    private String depositAccountName;
    /**
     * 存入金额
     */
    @ApiModelProperty(value = "存入金额")
    private BigDecimal depositMoney;
    /**
     * 邀请人
     */
    @ApiModelProperty(value = "邀请人")
    private Long inviter;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;
    /**
     * 状态 0已提交 1已受理 2已退回 3已完成
     */
    @ApiModelProperty(value = "状态 0已提交 1已受理 2已退回 3已完成")
    private Integer status;
    /**
     * 是否全部加载 0 否 1 是
     */
    @ApiModelProperty(value = "是否全部加载 0 否 1 是")
    private Integer isFind;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal chargeMoney;
    /**
     * 上传凭证图片ID
     */
    @ApiModelProperty(value = "上传凭证图片ID")
    private Long accImgId;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String backPerson;
    /**
     * 退回理由
     */
    @ApiModelProperty(value = "退回理由")
    private String backReason;
    /**
     * 前端保存信息
     */
    @ApiModelProperty(value = "前端保存信息")
    private String jsonInfo;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;
    /**
     * 收款账户号码
     */
    @ApiModelProperty(value = "收款账户号码")
    private String getAccount;
    /**
     * 收款人账户名
     */
    @ApiModelProperty(value = "收款人账户名")
    private String getAccountName;
    /**
     * 收款人地址
     */
    @ApiModelProperty(value = "收款人地址")
    private String getAddress;
    /**
     * 收款行中文名称
     */
    @ApiModelProperty(value = "收款行中文名称")
    private String getBankNameCn;
    /**
     * 收款行英文名称
     */
    @ApiModelProperty(value = "收款行英文名称")
    private String getBankNameEn;
    /**
     * 收款银行地址
     */
    @ApiModelProperty(value = "收款银行地址")
    private String getBankAddress;
    /**
     * SWIFT代码
     */
    @ApiModelProperty(value = "SWIFT代码")
    private String swiftCode;
    /**
     * 是否发放奖
     */
    @ApiModelProperty(value = "是否发放奖")
    private Integer isReward;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    private Integer actId;
    /**
     * 汇款银行名称
     */
    @ApiModelProperty(value = "汇款银行名称")
    private String remittanceBankName;
    /**
     * 汇款银行账号
     */
    @ApiModelProperty(value = "汇款银行账号")
    private String remittanceBankAccount;
    /**
     * 汇款户名(英文)
     */
    @ApiModelProperty(value = "汇款户名(英文)")
    private String remittanceAccountNameEn;
    /**
     * 上传凭证图片ID
     */
    @ApiModelProperty(value = "上传凭证图片ID")
    private Long accImgIdA;
    /**
     * 回调状态
     */
    @ApiModelProperty(value = "回调状态 0：异常 1：已推送MQ 2：BPM提交入金或eDDI成功，并且回调中台成功")
    private Integer pushRecved;
    /**
     * 预约号
     */
    @ApiModelProperty(value = "预约流水号")
    private String applicationId;
    /**
     * 回调错误次数
     */
    @ApiModelProperty(value = "回调错误次数")
    private Integer errCnt;
    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String getBankCode;
    /**
     * 汇款银行cord简称
     */
    @ApiModelProperty(value = "汇款银行cord简称")
    private String remittanceBankCorde;
    /**
     * 汇款银行id(edda入金需要：汇款银行bankId)
     */
    @ApiModelProperty(value = "汇款银行id(edda入金需要：汇款银行bankId)")
    private String remittanceBankId;
    /**
     * edda入金流水号
     */
    @ApiModelProperty(value = "edda入金流水号")
    private String eddaApplicationId;

}
