package com.minigod.zero.bpmn.module.stock.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: CustomerAccountStockApplicationQuery
 * @Description:
 * @Author wengzejie
 * @Date 2024/12/23
 * @Version 1.0
 */
@Data
public class CustomerAccountStockApplicationQuery {

	@ApiModelProperty("用户号")
	private String userId;

	@ApiModelProperty("客户中文名")
	private String clientName;

	@ApiModelProperty("英文名")
	private String clientNameSpell;

	@ApiModelProperty("证件号码")
	private String idNo;

	@ApiModelProperty("手机号")
	private String phoneNumber;

	@ApiModelProperty("开户状态")
	private Integer applicationStatus;

}
