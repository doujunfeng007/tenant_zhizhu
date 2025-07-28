package com.minigod.zero.bpmn.module.margincredit.vo;

import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitApplicationEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName IncreaseCreditLimitApplicationVO.java
 * @Description TODO
 * @createTime 2024年03月11日 17:35:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IncreaseCreditLimitApplicationVO extends IncreaseCreditLimitApplicationEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("交易账号")
	private String tradeAccount;

	@ApiModelProperty("资金账号")
	private String fundAccount;

	@ApiModelProperty("客户中文名")
	private String clientName;

	@ApiModelProperty("客户英文名")
	private String clientNameSpell;

	@ApiModelProperty(value = "当前信用额度")
	private BigDecimal currentLineCredit;

	@ApiModelProperty(value = "申请信用额度")
	private BigDecimal applyQuota;

	@ApiModelProperty(value = "信用比例")
	private BigDecimal creditRating;

	@ApiModelProperty(value = "已使用信用额度")
	private BigDecimal useLineCredit;

	private Integer dealPermissions;

	private Integer applyType;


}
