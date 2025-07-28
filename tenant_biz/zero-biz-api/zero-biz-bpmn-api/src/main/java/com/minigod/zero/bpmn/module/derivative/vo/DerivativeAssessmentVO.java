package com.minigod.zero.bpmn.module.derivative.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 衍生及結構性投資產品的認識評估参数对象
 *
 * @author eric
 * @since 2024-06-20 16:51:08
 */
@Data
public class DerivativeAssessmentVO {
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "用户ID")
	private Long custId;
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	@ApiModelProperty(value = "选项项目")
	private String optionItem;
	@ApiModelProperty(value = "选项值")
	private String optionValue;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
}
