package com.minigod.zero.bpmn.module.pi.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * H5提交PI认证参数对象
 *
 * @author eric
 * @since 2024-05-08 09:17:00
 */
@Data
@ApiModel(value = "PI认证参数对象", description = "PI认证参数对象")
public class ProfessionalInvestorPIBO {
	@ApiModelProperty("凭证图片Id集合")
	private List<String> imageIds;
	@ApiModelProperty("专业投资者之待遇")
	private List<String> treatments;
	@ApiModelProperty("专业投资者之被视为专业投资者身份的确认书声明(勾选项)")
	private List<String> declarations;
}
