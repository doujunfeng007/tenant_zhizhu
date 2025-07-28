package com.minigod.zero.bpmn.module.exchange.vo;

import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName CurrencyExchangeApplicationVO.java
 * @Description TODO
 * @createTime 2024年03月16日 17:16:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CurrencyExchangeApplicationVO extends CustomerCurrencyExchangeApplication {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("客户账号")
	private String tradeAccount;

	@ApiModelProperty("资金账号")
	private String fundAccount;

	@ApiModelProperty("客户姓名")
	private String clientName;

	@ApiModelProperty("英文名")
	private String clientNameSpell;

	@ApiModelProperty("区号")
	private String phoneArea;

	@ApiModelProperty("手机号")
	private String phoneNumber;

	@ApiModelProperty("手机号+区号")
	private String phoneAreaNumber;

	@ApiModelProperty("客户级别")
	private Integer vipLevel;

	@ApiModelProperty("兑换方式")
	private Integer exchangeType;

	@ApiModelProperty("兑换方式名称")
	private String exchangeTypeName;

	@ApiModelProperty(value = "申请兑出金额")
	private BigDecimal amountOut;

	@ApiModelProperty(value = "参考兑换汇率")
	private BigDecimal exchangeRate;

	@ApiModelProperty(value = "申请兑入金额")
	private BigDecimal amountIn;

	@ApiModelProperty(value = "兑换方向")
	private Integer exchangeDirection;

	@ApiModelProperty(value = "兑换方向名称")
	private String exchangeDirectionName;

	@ApiModelProperty(value = "柜台处理状态")
	private Integer processingStatus;

	@ApiModelProperty(value = "柜台处理状态名称")
	private String processingStatusName;

	@ApiModelProperty(value = "数据业务状态")
	private Integer appStatus;

	@ApiModelProperty(value = "数据业务状态名称")
	private String appStatusName;

	@ApiModelProperty(value = "预约申请状态名称")
	private String applicationStatusName;

	@ApiModelProperty("审核人")
	private String nickName;
}
