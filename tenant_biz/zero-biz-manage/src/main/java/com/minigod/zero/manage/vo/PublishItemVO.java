package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.PublishContentEntity;
import com.minigod.zero.manage.entity.PublishItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 帮助中心内容发布信息 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PublishItemVO extends PublishItemEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 帮助中心目录配置表
	 */
	@ApiModelProperty(name = "帮助中心目录配置表集")
	List<PublishContentEntity> publishContentList;
}
