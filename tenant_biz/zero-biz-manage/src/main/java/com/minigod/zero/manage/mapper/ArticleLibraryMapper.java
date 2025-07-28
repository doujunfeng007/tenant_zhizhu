package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.ArticleLibraryEntity;
import com.minigod.zero.manage.vo.ArticleLibraryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章库 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
public interface ArticleLibraryMapper extends BaseMapper<ArticleLibraryEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param articleLibrary
	 * @return
	 */
	List<ArticleLibraryVO> selectArticleLibraryPage(IPage page, @Param("articleLibrary") ArticleLibraryVO articleLibrary);


}
