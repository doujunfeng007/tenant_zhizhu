package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel(value="com.minigod.zero.customer.entity.CustomerHldTradingRecordsEntity")
@TableName("`customer_hld_trading_records`")
public class CustomerHldTradingRecordsEntity  implements Serializable {

	@ApiModelProperty("主键id")
	@TableId(
		value = "id",
		type = IdType.ASSIGN_ID
	)
	private Long id;

    /**
     * 子账户id
     */
    @ApiModelProperty(value="subAccountId子账户id")
    private String subAccountId;

    /**
     * 交易日期(提交日期)
     */
    @ApiModelProperty(value="submitDate交易日期(提交日期)")
    private Date submitDate;

    /**
     * 清算日期
     */
    @ApiModelProperty(value="settledDate清算日期")
    private Date settledDate;

    /**
     * 确认日期
     */
    @ApiModelProperty(value="confirmedDate确认日期")
    private Date confirmedDate;

    /**
     * 拒绝日期
     */
    @ApiModelProperty(value="rejectedDate拒绝日期")
    private Date rejectedDate;

    /**
     * 取消日期
     */
    @ApiModelProperty(value="canceledDate取消日期")
    private Date canceledDate;

    /**
     * 资金代码
     */
    @ApiModelProperty(value="fundCode资金代码")
    private String hldCode;

    /**
     * 资金名称
     */
    @ApiModelProperty(value="fundName资金名称")
    private String hldName;

    /**
     * 类型,1:买;2:卖;3:交换买;4:交换卖;
     */
    @ApiModelProperty(value="type类型,1:买;2:卖;3:交换买;4:交换卖;")
    private Integer type;

    /**
     * 方式,1:金额;2:数量;3:权重比例;
     */
    @ApiModelProperty(value="mode方式,1:金额;2:数量;3:权重比例;")
    private Integer mode;

    /**
     * 币种
     */
    @ApiModelProperty(value="currency币种")
    private String currency;

    /**
     * 申请金额
     */
    @ApiModelProperty(value="amount申请金额")
    private BigDecimal amount;

    /**
     * 成交数量
     */
    @ApiModelProperty(value="businessAmount成交数量")
    private BigDecimal businessAmount;

    /**
     * 成交金额
     */
    @ApiModelProperty(value="businessBalance成交金额")
    private BigDecimal businessBalance;

    /**
     * 成交价格
     */
    @ApiModelProperty(value="businessPrice成交价格")
    private BigDecimal businessPrice;

    /**
     * 佣金
     */
    @ApiModelProperty(value="feeCount佣金")
    private BigDecimal feeCount;
    /**
     * 活利得、债券易卖出应收利息，债券易买入应付利息
     */
    @ApiModelProperty(value="transaction_gain_loss活利得、债券易卖出应收利息，债券易买入应付利息")
    private BigDecimal transactionGainLoss;

    /**
     * 交易流水号
     */
    @ApiModelProperty(value="sequenceNo交易流水号")
    private Integer sequenceNo;

    /**
     * 状态 ,状态 100 已保存；200 已提交；230 已发送（ta）；270 已确认；300 已结算；500 已撤销；720 过期作废
     */
    @ApiModelProperty(value="status状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;")
    private Integer status;

    /**
     * 状态描述
     */
    @ApiModelProperty(value="statusDesc状态描述")
    private String statusDesc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="createTime创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="updateTime修改时间")
    private Date updateTime;

    @ApiModelProperty(value="isDeleted")
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;


}
