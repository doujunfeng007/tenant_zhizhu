package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.HotRecommendEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 热门推荐股票 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HotRecommendVO extends HotRecommendEntity {
	private static final long serialVersionUID = 1L;
	private int count = 6;
}
