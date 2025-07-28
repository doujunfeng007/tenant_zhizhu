package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 新增短信模板页面 删除模板类型列表展示VO
 * @author Zhe.Xiao
 */
@Data
public class MobileDeleteTypeVO {

	@ApiModelProperty(value = "模板类型id")
	private String id;

	@ApiModelProperty(value = "模板类型名称")
	private String tempName;

	@ApiModelProperty(value = "关联模板代码")
	private List<Integer> tempCodeList;

}
