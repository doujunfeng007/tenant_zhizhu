package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 掌上智珠
 * @since 2023/7/25 9:40
 */

@Data
@TableName("oms_sensitive_word_log")
@ApiModel(value = "SensitiveWord对象", description = "敏感词表")
public class SensitiveWordLogEntity extends BaseEntity {
	/**
	 * 敏感词
	 */
	@ApiModelProperty(value = "敏感词")
	private String word;

	@ApiModelProperty(value = "敏感词作用域")
	private Integer scope ;

	@ApiModelProperty(value = "文章外部id")
	private String extId ;

	@ApiModelProperty(value = "文章标题")
	private String title ;

	@ApiModelProperty(value = "文章内容")
	private String content;

	@ApiModelProperty(value = "文章对应股票列表")
	private String stocks;

	@ApiModelProperty(value = "生效来源")
	private String effectiveSource;

	/**
	 * 管理员设置的生效时间
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "生效时间")
	private Date logTime;


	@ApiModelProperty(value = "拦截某篇文章命中的次数")
	private Integer hitCount;


}
