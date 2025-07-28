package com.minigod.zero.bpmn.module.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName CustomerCurrencyExchangeInfo.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:50:00
 */

/**
 * 货币兑换信息
 */
@ApiModel(description = "货币兑换信息")
@Data
@TableName(value = "customer_currency_exchange_info")
public class CustomerCurrencyExchangeInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableField(value = "cust_id")
	@ApiModelProperty(value = "用户id")
	private Long custId;

	@TableField(value = "client_name")
	@ApiModelProperty(value = "用户姓名")
	private String clientName;

	/**
	 * 预约号
	 */
	@TableField(value = "application_id")
	@ApiModelProperty(value = "预约号")
	private String applicationId;

	/**
	 * 客户账号
	 */
	@TableField(value = "trade_account")
	@ApiModelProperty(value = "客户账号")
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	@TableField(value = "fund_account")
	@ApiModelProperty(value = "资金账号")
	private String fundAccount;

	/**
	 * 资金账号类型
	 */
	@TableField(value = "fund_account_type")
	@ApiModelProperty(value = "资金账号类型")
	private String fundAccountType;

	/**
	 * 兑出币种[0-人民币CNY 1-美元USD 2-港币HKD]
	 */
	@TableField(value = "currency_out")
	@ApiModelProperty(value = "兑出币种[0-人民币CNY 1-美元USD 2-港币HKD]")
	private Integer currencyOut;

	/**
	 * 兑入币种[0-人民币CNY 1-美元USD 2-港币HKD]
	 */
	@TableField(value = "currency_in")
	@ApiModelProperty(value = "兑入币种[0-人民币CNY 1-美元USD 2-港币HKD]")
	private Integer currencyIn;

	/**
	 * 申请兑出金额
	 */
	@TableField(value = "amount_out")
	@ApiModelProperty(value = "申请兑出金额")
	private BigDecimal amountOut;

	/**
	 * 申请兑入金额
	 */
	@TableField(value = "amount_in")
	@ApiModelProperty(value = "申请兑入金额")
	private BigDecimal amountIn;

	/**
	 * 实际兑出金额
	 */
	@TableField(value = "actual_amount_out")
	@ApiModelProperty(value = "实际兑出金额")
	private BigDecimal actualAmountOut;

	/**
	 * 实际兑入金额
	 */
	@TableField(value = "actual_amount_in")
	@ApiModelProperty(value = "实际兑入金额")
	private BigDecimal actualAmountIn;

	@TableField(value = "available_balance")
	@ApiModelProperty(value = "可取余额")
	private BigDecimal availableBalance;

	/**
	 * 参考兑换汇率
	 */
	@TableField(value = "exchange_rate")
	@ApiModelProperty(value = "参考兑换汇率")
	private BigDecimal exchangeRate;

	/**
	 * 实际兑换汇率
	 */
	@TableField(value = "actual_exchange_rate")
	@ApiModelProperty(value = "实际兑换汇率")
	private BigDecimal actualExchangeRate;

	/**
	 * 币种兑换方向[1-港币兑换美元 2-美元兑换港币 3-人民币兑换美元 4-美元兑换人民币 5-港币兑换人民币 6-人民币兑换港币]
	 */
	@TableField(value = "exchange_direction")
	@ApiModelProperty(value = "币种兑换方向[1-港币兑换美元 2-美元兑换港币 3-人民币兑换美元 4-美元兑换人民币 5-港币兑换人民币 6-人民币兑换港币]")
	private Integer exchangeDirection;

	/**
	 * 兑换方式[0-未知 1-线上兑换 2-手工兑换]
	 */
	@TableField(value = "exchange_type")
	@ApiModelProperty(value = "兑换方式[0-未知 1-线上兑换 2-手工兑换]")
	private Integer exchangeType;

	/**
	 * 柜台处理[0-冻结失败 1-扣款失败 2-解冻失败 3-入账失败 4-成功]
	 */
	@TableField(value = "processing_status")
	@ApiModelProperty(value = "柜台处理[0-冻结失败 1-扣款失败 2-解冻失败 3-入账失败 4-成功]")
	private Integer processingStatus;

	/**
	 * 资金冻结日期
	 */
	@TableField(value = "init_date")
	@ApiModelProperty(value = "资金冻结日期")
	private Integer initDate;

	/**
	 * 租户id
	 */
	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户id")
	private String tenantId;

}
