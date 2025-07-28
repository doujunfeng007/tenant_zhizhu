package com.minigod.zero.bpmn.module.exchange.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName CurrencyExchangeQuery.java
 * @Description TODO
 * @createTime 2024年03月16日 17:17:00
 */
@Data
public class CurrencyExchangeQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("客户账号")
	private String tradeAccount;

	@ApiModelProperty("资金账号")
	private String fundAccount;

	@ApiModelProperty("兑换方式")
	private Integer exchangeType;

	@ApiModelProperty("兑出币种")
	private Integer currencyOut;

	@ApiModelProperty("兑入币种")
	private Integer currencyIn;

	@ApiModelProperty("客户级别")
	private Integer vipLevel;

	@ApiModelProperty("兑换方向")
	private Integer exchangeDirection;

	@ApiModelProperty("柜台状态")
	private Integer processingStatus;

	@ApiModelProperty("流程状态")
	private Integer applicationStatus;

	@ApiModelProperty(value = "申请状态")
	private Integer appStatus;

	@ApiModelProperty("手机号")
	private String phoneNumber;

	@ApiModelProperty("客户姓名")
	private String clientName;

	@ApiModelProperty("英文名")
	private String clientNameSpell;

	@ApiModelProperty("预约号")
	private String applicationId;

	@ApiModelProperty("兑换申请开始时间 yyyy-mm-dd")
	private String startTime;

	@ApiModelProperty("兑换申请结束时间 yyyy-mm-dd")
	private String endTime;

	@ApiModelProperty("是否查询待审核的记录:1-待初审 2-待复审 10-挂起")
	private Integer isCheck;

	@ApiModelProperty("租户id")
	private String tenantId;

	@ApiModelProperty("当前处理人")
	private String assignDrafter;
}
