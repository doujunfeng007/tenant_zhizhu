package com.minigod.zero.data.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 债券交易流水历史表(CustomerBondTradingTransaction)实体类
 *
 * @author makejava
 * @since 2024-05-23 20:43:31
 */
@Data
public class CustomerBondTradingTransaction implements Serializable {
    /**
    * 标识
    */
    private Integer id;
    /**
    * 归集标识
    */
    private Integer nomineeTransId;
    /**
    * 订单标识
    */
    private Integer orderId;
    /**
    * 子账号
    */
    private String subAccountId;
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
    private String fundCode;
    /**
    * 币种
    */
    private String currency;

    private BigDecimal amount;

    private BigDecimal quantity;

    private BigDecimal price;
    /**
    * 交易日期
    */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Timestamp transactionDate;
    /**
    * 确认日期
    */
    private Date confirmedDate;
    /**
    * 预计清算日期
    */
    private Date expectedSettledDate;
    /**
    * 清算日期
    */
    private Date settledDate;
    /**
    * 有效清算日期
    */
    private Date effectiveSettledDate;

    private BigDecimal settledAmount;

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
    private Date createTime;

    private Date updateTime;

}
