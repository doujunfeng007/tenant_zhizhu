package com.minigod.zero.manage.vo.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/7/18 11:10
 */
@Data
public class SensitiveWordRespDto implements Serializable {

	@ApiModelProperty("id")
	private Long id;

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


	@ApiModelProperty(value = "生效来源")
	private String effectiveSource;


	@ApiModelProperty(value = "股票列表")
	private List<String> assetId;

	@ApiModelProperty(value = "命中次数")
	private Integer hitCount;

	@ApiModelProperty(value = "敏感词创建人名称")
	private String createUserName;

	@ApiModelProperty(value = "生效时间作用范围")
	private Integer timeScope;

	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "生效时间")
	private Date createTime;

	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;


	@ApiModelProperty(value = "修改人")
	private String updateUserName;


}
