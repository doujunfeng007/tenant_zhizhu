package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName ta_t_daily_interest
 */
@TableName(value ="ta_t_daily_interest")
@Data
public class TaTDailyInterestEntity implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer interestid;

    /**
     * 子账号 id
     */
    private String subaccountid;

    /**
     * 产品 id
     */
    private Integer productid;

    /**
     * 计息日，及产生利息的日期
     */
    private Date interestdate;

    /**
     * 交易币种
     */
    private String currency;

    /**
     * 计息金额
     */
    private BigDecimal amount;

    /**
     * 面值
     */
    private BigDecimal facevalue;

    /**
     * 份额
     */
    private Integer volume;

    /**
     * 当日利息
     */
    private BigDecimal interest;

    /**
     * 利率
     */
    private BigDecimal interestrate;

    /**
     * 记录创建时间
     */
    private Date createtime;

    /**
     * 记录更新时间
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
