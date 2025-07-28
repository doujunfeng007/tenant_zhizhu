package com.minigod.zero.bpmn.module.pi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 专业投资者PI申请凭证上传对象实体类
 *
 * @author eric
 * @since 2024-05-08 14:03:09
 */
@Data
@ApiModel(value = "ProfessionalInvestorPIImageDTO对象", description = "专业投资者PI申请凭证上传对象实体类")
public class ProfessionalInvestorPIImageDTO implements Serializable {
	/**
	 * 凭证类型
	 */
	@ApiModelProperty(value = "凭证类型[0-资产凭证 1-补充证明]")
	@NotNull(message = "凭证类型[0-资产凭证 1-补充证明]不能为空")
	private Integer type;

	/**
	 * 图片Base64码
	 */
	@ApiModelProperty(value = "图片Base64码")
	@NotBlank(message = "图片Base64码不能为空")
	private String imgBase64;
}
