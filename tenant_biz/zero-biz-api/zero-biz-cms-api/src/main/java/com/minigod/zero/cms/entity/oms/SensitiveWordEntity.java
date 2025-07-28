package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author caizongtai
 * @since 2023/7/17 17:17
 */

@Data
@TableName("oms_sensitive_word")
@ApiModel(value = "SensitiveWord对象", description = "敏感词表")
public class SensitiveWordEntity extends BaseEntity {

	/**
	 * 敏感词作用域
	 */
	@ApiModelProperty(value = "敏感词作用域")
	private Integer scope ;
	/**
	 * 敏感词
	 */
	@ApiModelProperty(value = "敏感词")
	private String word;

	/**
	 * 智珠过滤新闻资讯
	 */
	@ApiModelProperty(value = "股票id列表")
	private String assetId;


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
	private Date effectiveTime;
}
