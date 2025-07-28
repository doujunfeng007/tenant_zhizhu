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
 * 提取资金表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_extracting_money")
@ApiModel(value = "CashExtractingMoney对象", description = "提取资金表")
public class CashExtractingMoneyEntity implements Serializable {

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
     * 提取账户
     */
    @ApiModelProperty(value = "提取账户")
    private String extAccount;
    /**
     * 提取账户名称
     */
    @ApiModelProperty(value = "提取账户名称")
    private String extAccountName;
    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String tradeAccount;
    /**
     * 提取方式 1大陆银行 2香港银行
     */
    @ApiModelProperty(value = "提取方式 1大陆银行 2香港银行")
    private Integer extMethod;
    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String bankName;
    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String bankCode;
    /**
     * SWIFT代码
     */
    @ApiModelProperty(value = "SWIFT代码")
    private String swiftCode;
    /**
     * 收款人
     */
    @ApiModelProperty(value = "收款人")
    private String payee;
    /**
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址")
    private String address;
    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    private String bankAccount;
    /**
     * 可提金额
     */
    @ApiModelProperty(value = "可提金额")
    private BigDecimal availableAmount;
    /**
     * 提取金额
     */
    @ApiModelProperty(value = "提取金额")
    private BigDecimal extractionAmount;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    /**
     * 状态 0已提交 1已受理 2已退回 3已完成 4 已取消
     */
    @ApiModelProperty(value = "状态 0已提交 1已受理 2已退回 3已完成 4 已取消")
    private Integer status;
    /**
     * 币种 1港币 2美元
     */
    @ApiModelProperty(value = "币种 1港币 2美元")
    private Integer currency;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal chargeMoney;
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
     * 是否全部加载 0 否 1 是
     */
    @ApiModelProperty(value = "是否全部加载 0 否 1 是")
    private Integer isFind;
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
     * 导出状态 0:未导出  1:已导出
     */
    @ApiModelProperty(value = "导出状态 0:未导出  1:已导出")
    private Boolean exportState;
    /**
     * 推送状态 0：否 1：已推送MQ 2：BPM提交申请成功并且已回调中台
     */
    @ApiModelProperty(value = "0：否 1：已推送MQ 2：BPM提交申请成功并且已回调中台")
    private Integer pushRecved;
    /**
     * 预约号
     */
    @ApiModelProperty(value = "预约流水号")
    private String applicationId;
    /**
     * 回调失败次数
     */
    @ApiModelProperty(value = "回调失败次数")
    private Integer errCnt;
    /**
     * 出金凭证图片id
     */
    @ApiModelProperty(value = "出金凭证图片id")
    private String extractMoneyImgIds;
    /**
     * 银行ID
     */
    @ApiModelProperty(value = "银行ID")
    private String bankId;
    /**
     * 0-银证转账 1-普通转账 2-快速转账
     */
    @ApiModelProperty(value = "0-银证转账 1-普通转账 2-快速转账,4-出金万通")
    private Integer busType;
	/**
	 * 反向流水号
	 */
	@ApiModelProperty(value = "反向流水号")
	private String revertSerialNo;
	/**
	 * 资金冻结日期
	 */
	@ApiModelProperty(value = "资金冻结日期")
	private String initDate;

}
