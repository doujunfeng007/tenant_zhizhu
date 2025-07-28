package com.minigod.zero.bpmn.module.margincredit.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName IncreaseCreditQuery.java
 * @Description TODO
 * @createTime 2024年03月11日 17:45:00
 */
@Data
public class IncreaseCreditQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("申请时间yyyy-MM-dd")
	private String startTime;

	@ApiModelProperty("结束时间yyyy-MM-dd")
	private String endTime;

	@ApiModelProperty("交易账号")
	private String tradeAccount;

	@ApiModelProperty("资金账号")
	private String fundAccount;

	@ApiModelProperty("客户中文名")
	private String clientName;

	@ApiModelProperty("客户英文名")
	private String clientNameSpell;

	@ApiModelProperty("审批状态")
	private Integer applicationStatus;


	@ApiModelProperty("")
	private Long userId;

	private String tenantId;

	private String applicationId;

	@ApiModelProperty("申领状态 0 全部 1已申领 2未申领")
	private Integer assignDrafterStatus;

	private Integer isCheck;





}
