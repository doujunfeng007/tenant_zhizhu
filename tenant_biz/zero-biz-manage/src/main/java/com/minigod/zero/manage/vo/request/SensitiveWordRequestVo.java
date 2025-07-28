package com.minigod.zero.manage.vo.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 掌上智珠
 * @since 2023/7/17 18:26
 */
@Data
public class SensitiveWordRequestVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("文本")
	private String word;

	@ApiModelProperty("作用域")
	private Integer scope;

	@ApiModelProperty("敏感词状态")
	private Integer status;

	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "生效时间")
	private Date effectiveTime;

	@ApiModelProperty(value = "股票列表")
	private String assetId;

	@ApiModelProperty(value = "新闻来源")
	private String effectiveSource;

	@ApiModelProperty(value = "生效时间作用范围")
	private Integer timeScope;
}
