package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 基金交易流水历史表
 * @TableName customer_fund_trading_transaction
 */
@TableName(value ="customer_fund_trading_transaction")
@Data
public class CustomerFundTradingTransaction implements Serializable {
    /**
     * 标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 归集标识
     */
    private Integer nomineetransid;

    /**
     * 订单标识
     */
    private Integer orderid;

    /**
     * 子账号
     */
    private String subaccountid;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 方式,1:金额;2:数量;3:比例;
     */
    private Integer mode;

    /**
     * 基金代码
     */
    private String fundcode;

    /**
     * 币种
     */
    private String currency;

    /**
     *
     */
    private BigDecimal amount;

    /**
     *
     */
    private BigDecimal quantity;

    /**
     *
     */
    private BigDecimal price;

    /**
     * 交易日期
     */
    private Date transactiondate;

    /**
     * 确认日期
     */
    private Date confirmeddate;

    /**
     * 预计清算日期
     */
    private Date expectedsettleddate;

    /**
     * 清算日期
     */
    private Date settleddate;

    /**
     * 有效清算日期
     */
    private Date effectivesettleddate;

    /**
     *
     */
    private BigDecimal settledamount;

    /**
     *
     */
    private BigDecimal fee;

    /**
     * 交易来源：Client;CorporateAction;ManualAdjustment;Admin
     */
    private String source;

    /**
     * Custody;yfund
     */
    private String location;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态代码,700:CONFIRMED;900:SETTLED
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     *
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
