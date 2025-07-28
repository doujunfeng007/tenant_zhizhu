package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 理财账户金额流水
 * @TableName financing_account_amount_flows
 */
@Data
public class FinancingAccountAmountFlows implements Serializable {
    /**
     *
     */
	@TableId(
		value = "id",
		type = IdType.AUTO
	)
    private Integer id;

    /**
     * 理财账户账号
     */
    private String accountId;

    /**
     * 操作前金额
     */
    private BigDecimal beforeAmount;

    /**
     * 操作金额
     */
    private BigDecimal amount;

    /**
     * 操作后金额
     */
    private BigDecimal afterAmount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 说明
     */
    private String remark;
    /**
     * 操作类型0：入金，1：冻结（可用转冻结）,2:解冻（扣除冻结），3：解冻（冻结转可用）
     */
    private Integer type;
	/**
	 * {@link com.minigod.zero.customer.enums.ThawingType}
	 */
	private Integer businessType;

	/**
	 * {@link com.minigod.zero.customer.enums.FlowBusinessType}
	 */
	private Integer operationType;
	/**
	 * 业务id
	 */
	private Integer accountBusinessFlowId;

}
