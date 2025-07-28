package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.ArticleLibraryEntity;
import com.minigod.zero.manage.vo.ArticleLibraryVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 文章库 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
public interface IArticleLibraryService extends BaseService<ArticleLibraryEntity> {

	/**
	 * 自定义分页查询文章库列表
	 *
	 * @param page
	 * @param articleLibrary
	 * @return
	 */
	IPage<ArticleLibraryVO> selectArticleLibraryPage(IPage<ArticleLibraryVO> page, ArticleLibraryVO articleLibrary);

	/**
	 * 新增文章库
	 *
	 * @param articleLibrary
	 * @return
	 */
	boolean saveArticleLibrary(ArticleLibraryEntity articleLibrary);

	/**
	 * 修改文章库
	 *
	 * @param articleLibrary
	 * @return
	 */
	boolean updateArticleLibrary(ArticleLibraryEntity articleLibrary);

	/**
	 * 根据标题模糊匹配
	 *
	 * @param articleLibrary
	 * @return
	 */
	List<ArticleLibraryEntity> findByTitle(ArticleLibraryEntity articleLibrary);

	/**
	 * 根据主键查询文章详情
	 *
	 * @param id
	 * @return
	 */
	ArticleLibraryEntity findById(Long id);

	/**
	 * 批量删除文章
	 *
	 * @param ids
	 * @return
	 */
	boolean deleteArticleByIds(List<Long> ids);
}
