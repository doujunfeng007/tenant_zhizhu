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
 * 基金持仓流水历史表
 * @TableName customer_fund_holding_history
 */
@TableName(value ="customer_fund_holding_history")
@Data
public class CustomerFundHoldingHistory implements Serializable {
    /**
     * 记录标志
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 基金账号
     */
    private String subaccountid;

    /**
     * 基金/现金id
     */
    private String fundcode;

    /**
     * 持仓类型，1：fund；2：cash；3：icp
     */
    private Integer holdingtype;

    /**
     *
     */
    private BigDecimal totalquantity;

    /**
     *
     */
    private BigDecimal availablequantity;

    /**
     *
     */
    private BigDecimal frozenquantity;

    /**
     *
     */
    private BigDecimal transitedquantity;

    /**
     *
     */
    private BigDecimal withdrawquantity;

    /**
     *
     */
    private BigDecimal averagecost;

    /**
     *
     */
    private BigDecimal dilutedcost;

    /**
     * 货币类型
     */
    private String currency;

    /**
     *
     */
    private BigDecimal accumulatedbuyamount;

    /**
     *
     */
    private BigDecimal accumulatedcashdividends;

    /**
     *
     */
    private BigDecimal realizedgainloss;

    /**
     * 活利得累计总收益
     */
    private BigDecimal totalgainloss;

    /**
     *
     */
    private BigDecimal totalfee;

    /**
     * 对应flow记录的主键，如果主键有多个字段，需要扩充
     */
    private Integer flowid;

    /**
     *
     */
    private Date transactiontime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
