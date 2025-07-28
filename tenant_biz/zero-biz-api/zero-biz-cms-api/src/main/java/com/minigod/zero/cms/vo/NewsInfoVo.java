package com.minigod.zero.cms.vo;

import com.minigod.zero.cms.entity.NewsInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资讯新闻信息表 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NewsInfoVo extends NewsInfoEntity {
	/**
	 * 新闻来源原类别
	 */
	@ApiModelProperty(value = "新闻来源原类别")
	private String category;
}
