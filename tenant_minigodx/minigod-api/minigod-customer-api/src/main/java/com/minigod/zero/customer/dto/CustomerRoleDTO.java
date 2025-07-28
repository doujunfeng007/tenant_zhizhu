package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 客户角色配置参数
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/14 16:49
 * @description：
 */
@Data
@ApiModel(description = "客户角色配置参数")
public class CustomerRoleDTO {
	@ApiModelProperty(value = "客户ID")
	private Long custId;
	@ApiModelProperty(value = "角色ID")
	private Integer roleId;
	@ApiModelProperty(value = "角色ID集合")
	private List<Integer> roleIds;
}
