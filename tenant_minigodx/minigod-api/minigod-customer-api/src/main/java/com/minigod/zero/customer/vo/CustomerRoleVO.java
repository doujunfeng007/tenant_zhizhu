package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "客户角色配置列表")
public class CustomerRoleVO {
	@ApiModelProperty(value = "客户ID")
	private Long custId;
	@ApiModelProperty(value = "角色ID集合")
	private List<Integer> roleIds;
}
