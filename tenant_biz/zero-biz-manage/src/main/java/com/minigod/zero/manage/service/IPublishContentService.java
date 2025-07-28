package com.minigod.zero.manage.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.PublishContentEntity;
import com.minigod.zero.manage.vo.PublishContentVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.vo.request.PublishContentRequest;

import java.util.List;

/**
 * 帮助中心内容发布信息 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface IPublishContentService extends IService<PublishContentEntity> {

	/**
	 * 分页查询内容发布信息
	 *
	 * @param page
	 * @param publishContent
	 * @return
	 */
	IPage<PublishContentVO> selectPublishContentPage(IPage<PublishContentVO> page, PublishContentVO publishContent);

	/**
	 * 获取发布的【常见问题】内容信息
	 *
	 * @return
	 */
	List<PublishContentEntity> findPublishContentCommon();

	/**
	 * 根据目录ID获取发布内容信息
	 *
	 * @param ResourceId
	 * @return
	 */
	List<PublishContentEntity> findListUsedByResourceId(Long ResourceId);

	/**
	 * 添加点击次数
	 */
	void addClicksById(Long id);

	/**
	 * 根据标题模糊查询发布内容信息
	 *
	 * @param title
	 * @return
	 */
	List<PublishContentEntity> searchPublishTitle(String title);

	/**
	 * 保存/更新发布内容信息
	 *
	 * @param publishContentRequest
	 * @return
	 */
	Long saveUpdate(PublishContentRequest publishContentRequest);

	/**
	 * 根据id查找发布内容信息
	 *
	 * @param id
	 * @return
	 */
	PublishContentEntity findById(Long id);

	/**
	 * 查找指定目录下最大排序编号
	 *
	 * @param resourceId
	 * @return
	 */
	Integer findMaxSortIdBySourceId(Long resourceId);

	/**
	 * 更改内容发布状态
	 *
	 * @param publishContentRequest
	 * @return
	 */
	R updateBean(PublishContentRequest publishContentRequest);

	/**
	 * 批量删除内容发布信息
	 *
	 * @param ids
	 * @return
	 */
	Boolean removeBatchByIds2(List<Long> ids);
}
