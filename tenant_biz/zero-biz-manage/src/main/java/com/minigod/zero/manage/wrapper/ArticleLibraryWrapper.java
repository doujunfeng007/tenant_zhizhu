package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.ArticleLibraryEntity;
import com.minigod.zero.manage.vo.ArticleLibraryVO;

import java.util.Objects;

/**
 * 文章库 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
public class ArticleLibraryWrapper extends BaseEntityWrapper<ArticleLibraryEntity, ArticleLibraryVO>  {

	public static ArticleLibraryWrapper build() {
		return new ArticleLibraryWrapper();
 	}

	@Override
	public ArticleLibraryVO entityVO(ArticleLibraryEntity articleLibrary) {
	    ArticleLibraryVO articleLibraryVO = new ArticleLibraryVO();
    	if (articleLibrary != null) {
		    articleLibraryVO = Objects.requireNonNull(BeanUtil.copy(articleLibrary, ArticleLibraryVO.class));
        }
		return articleLibraryVO;
	}
}
