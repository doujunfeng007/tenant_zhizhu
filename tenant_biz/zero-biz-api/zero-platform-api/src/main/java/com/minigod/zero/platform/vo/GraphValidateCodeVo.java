package com.minigod.zero.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author caizongtai
 * @since 2023/7/10 17:08
 * 图形验证码请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphValidateCodeVo  implements Serializable {

	@ApiModelProperty(value = "图片宽度")
	@NotNull(message = "图片宽度不能为空")
	@Min(value = 1,message = "宽度不能小于1")
	@Max(value = 600,message = "宽度不能大于500")
	private Integer width;

	@ApiModelProperty(value = "图片高度")
	@NotNull(message = "图片高度不能为空")
	@Min(value = 1,message = "高度不能小于1")
	@Max(value = 300,message = "高度不能大于200")
	private Integer height;

	@ApiModelProperty(value = "验证码长度")
	@NotNull(message = "验证码长度不能为空")
	@Min(value = 1,message = "验证码长度不能小于1")
	@Max(value = 8,message = "验证码长度不能大于8")
	private Integer codeLength;
}
