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
 * @TableName ta_t_holding_history
 */
@TableName(value ="ta_t_holding_history")
@Data
public class TaTHoldingHistoryEntity implements Serializable {
    /**
     * 记录标志
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String subaccountid;

    /**
     * 产品/现金id
     */
    private String fundcode;

    /**
     * 持仓类型，1：fund；2：cash；3：icp；64 活利得  65 债券易
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
     * 债券易卖出资本利得
     */
    private BigDecimal capitalgainloss;

    /**
     * 总盈亏
     */
    private BigDecimal totalgainloss;

    /**
     *
     */
    private BigDecimal totalfee;

    /**
     * 预期年化收益率
     */
    private BigDecimal expectedrate;

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
