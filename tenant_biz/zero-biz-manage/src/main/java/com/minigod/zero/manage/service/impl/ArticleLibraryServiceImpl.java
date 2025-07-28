package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.manage.entity.ArticleLibraryEntity;
import com.minigod.zero.manage.mapper.ArticleLibraryMapper;
import com.minigod.zero.manage.service.IArticleLibraryService;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.vo.ArticleLibraryVO;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.feign.IUserClient;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文章库 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
@Service
public class ArticleLibraryServiceImpl extends BaseServiceImpl<ArticleLibraryMapper, ArticleLibraryEntity> implements IArticleLibraryService {

	@Resource
	private ICustInfoService custInfoService;
	@Resource
	private IUserClient userClient;

	/**
	 * 自定义分页查询文章库列表
	 *
	 * @param page
	 * @param articleLibrary
	 * @return
	 */
	@Override
	public IPage<ArticleLibraryVO> selectArticleLibraryPage(IPage<ArticleLibraryVO> page, ArticleLibraryVO articleLibrary) {

		//特殊处理
		if (StringUtil.isNotBlank(articleLibrary.getUpdateUserName())) {
			List<Long> idList = userClient.userInfoByRealName(articleLibrary.getUpdateUserName())
				.stream().filter(user -> user.getTenantId().equals("000000")).map(User::getId).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(idList)) {
				articleLibrary.setUpdateUser(idList.get(0));
			} else {
				page.setRecords(new ArrayList<>());
				return page;
			}
		}

		List<ArticleLibraryVO> list = baseMapper.selectArticleLibraryPage(page, articleLibrary);
		List<Long> updateUserIds = null;
		if (null != list) {
			updateUserIds = list.stream().filter(item -> item.getUpdateUser() != null).map(BaseEntity::getUpdateUser).collect(Collectors.toList());
			Map<Long, User> updateUserMap = custInfoService.userInfoByIds(updateUserIds);
			if (MapUtils.isNotEmpty(updateUserMap)) {
				for (ArticleLibraryVO articleLibraryVO : list) {
					if (updateUserMap.get(articleLibraryVO.getUpdateUser()) != null) {
						articleLibraryVO.setUpdateUserName(updateUserMap.get(articleLibraryVO.getUpdateUser()).getName());
					}
				}
			}
		}
		return page.setRecords(list);
	}

	/**
	 * 新增文章库
	 *
	 * @param articleLibrary
	 * @return
	 */
	@Override
	public boolean saveArticleLibrary(ArticleLibraryEntity articleLibrary) {
		return this.save(articleLibrary);
	}

	/**
	 * 修改文章库
	 *
	 * @param articleLibrary
	 * @return
	 */
	@Override
	public boolean updateArticleLibrary(ArticleLibraryEntity articleLibrary) {
		return this.updateById(articleLibrary);
	}

	/**
	 * 根据标题模糊匹配
	 *
	 * @param articleLibrary
	 * @return
	 */
	@Override
	public List<ArticleLibraryEntity> findByTitle(ArticleLibraryEntity articleLibrary) {
		if (StringUtil.isBlank(articleLibrary.getTitle())) {
			return new ArrayList<>();
		}
		return new LambdaQueryChainWrapper<>(baseMapper).eq(ArticleLibraryEntity::getIsDeleted, 0)
			.like(ArticleLibraryEntity::getTitle, articleLibrary.getTitle()).orderByDesc(ArticleLibraryEntity::getUpdateTime).list();
	}

	/**
	 * 根据主键查询文章详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ArticleLibraryEntity findById(Long id) {
		return this.getById(id);
	}

	/**
	 * 批量删除文章
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public boolean deleteArticleByIds(List<Long> ids) {
		return this.deleteLogic(ids);
	}
}
