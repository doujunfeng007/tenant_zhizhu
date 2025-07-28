package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.ArticleLibraryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文章库 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleLibraryVO extends ArticleLibraryEntity {
	private static final long serialVersionUID = 1L;
	private Date startTime;
	private Date endTime;
}
