package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.Boolean;
import java.math.BigDecimal;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 基金账户信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_fund_capital_account")
@ApiModel(value = "CustomerFundCapitalAccount对象", description = "基金账户信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomerFundCapitalAccountEntity extends BaseEntity {

    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String tradeAccount;
    /**
     * 基金账户
     */
    @ApiModelProperty(value = "基金账户")
    private String fundAccount;
    /**
     * 是否是主账号：0-不是 1-是
     */
    @ApiModelProperty(value = "是否是主账号：0-不是 1-是")
    private Integer isMaster;
    /**
     * 基金账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
     */
    @ApiModelProperty(value = "基金账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]")
    private Integer accountStatus;
	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount;
	/**
	 * 冻结金额
	 */
	private BigDecimal freezeAmount;
	/**
	 * 可用金额
	 */
	private BigDecimal availableAmount;
	/**
	 * 账户类型[0=现金账户 M=保证金账户]
	 */
	private String accountType;
}
