package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.PublishContentEntity;
import com.minigod.zero.manage.service.IPublishContentService;
import com.minigod.zero.manage.vo.request.PublishContentRequest;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.manage.vo.PublishContentVO;
import com.minigod.zero.manage.mapper.PublishContentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 帮助中心内容发布信息 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Service
public class PublishContentServiceImpl extends BaseServiceImpl<PublishContentMapper, PublishContentEntity> implements IPublishContentService {
	/**
	 * 分页查询内容发布信息
	 *
	 * @param page
	 * @param publishContent
	 * @return
	 */
	@Override
	public IPage<PublishContentVO> selectPublishContentPage(IPage<PublishContentVO> page, PublishContentVO publishContent) {
		return page.setRecords(baseMapper.selectPublishContentPage(page, publishContent));
	}

	/**
	 * 获取发布的【常见问题】内容信息
	 *
	 * @return
	 */
	@Override
	public List<PublishContentEntity> findPublishContentCommon() {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(PublishContentEntity::getIsCommon, true)
			.eq(BaseEntity::getStatus, true)
			.eq(BaseEntity::getIsDeleted, false)
			.orderByAsc(PublishContentEntity::getSortId)
			.list();
	}

	/**
	 * 根据目录ID获取发布内容信息
	 *
	 * @param ResourceId
	 * @return
	 */
	@Override
	public List<PublishContentEntity> findListUsedByResourceId(Long ResourceId) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(PublishContentEntity::getResourceId, ResourceId)
			.eq(BaseEntity::getStatus, true)
			.eq(BaseEntity::getIsDeleted, false)
			.orderByAsc(PublishContentEntity::getSortId)
			.list();
	}

	/**
	 * 添加点击次数
	 *
	 * @param id
	 */
	@Override
	public void addClicksById(Long id) {
		PublishContentEntity one = new LambdaQueryChainWrapper<>(baseMapper).eq(BaseEntity::getId, id).one();
		one.setClicks(one.getClicks() + 1);
		baseMapper.updateById(one);
	}

	/**
	 * 根据标题模糊查询发布内容信息
	 *
	 * @param title
	 * @return
	 */
	@Override
	public List<PublishContentEntity> searchPublishTitle(String title) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.like(PublishContentEntity::getTitle, title)
			.eq(BaseEntity::getStatus, true)
			.eq(BaseEntity::getIsDeleted, false)
			.orderByDesc(PublishContentEntity::getClicks)
			.orderByDesc(PublishContentEntity::getCreateTime)
			.list();
	}

	/**
	 * 保存/更新发布内容信息
	 *
	 * @param publishContentRequest
	 * @return
	 */
	@Override
	public Long saveUpdate(PublishContentRequest publishContentRequest) {
		PublishContentEntity publishContentEntity = new PublishContentEntity();
		if (publishContentRequest.getId() == null) {
			//创建的时候设置默认为不常见问题
			if (publishContentRequest.getIsCommon() == null) {
				publishContentRequest.setIsCommon(false);
			}
			//创建的时候设置默认为可分享
			if (publishContentRequest.getIsShare() == null) {
				publishContentRequest.setIsShare(true);
			}

			Integer maxSortIdBySourceId = this.findMaxSortIdBySourceId(publishContentRequest.getResourceId());
			publishContentRequest.setSortId(maxSortIdBySourceId);
			BeanUtils.copyProperties(publishContentRequest, publishContentEntity);
			this.save(publishContentEntity);
		} else {
			BeanUtils.copyProperties(publishContentRequest, publishContentEntity);
			this.updateById(publishContentEntity);
		}
		return publishContentEntity.getId();
	}

	/**
	 * 根据id查找发布内容信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public PublishContentEntity findById(Long id) {
		return this.getById(id);
	}

	/**
	 * 查找指定目录下最大排序编号
	 *
	 * @param resourceId
	 * @return
	 */
	@Override
	public Integer findMaxSortIdBySourceId(Long resourceId) {
		LambdaQueryWrapper<PublishContentEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(PublishContentEntity::getSortId);
		queryWrapper.eq(PublishContentEntity::getResourceId, resourceId);
		queryWrapper.orderByDesc(PublishContentEntity::getSortId);
		queryWrapper.last("limit 1");
		PublishContentEntity publishContentEntity = baseMapper.selectOne(queryWrapper);
		return publishContentEntity != null ? publishContentEntity.getSortId() + 1 : 1;
	}

	/**
	 * 更改内容发布状态
	 *
	 * @param publishContentRequest
	 * @return
	 */
	@Override
	public R updateBean(PublishContentRequest publishContentRequest) {
		if (publishContentRequest == null || publishContentRequest.getId() == null) {
			return R.fail(ResultCode.PARAM_MISS);
		}

		LambdaQueryWrapper<PublishContentEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PublishContentEntity::getId, publishContentRequest.getId());
		PublishContentEntity publishContentEntity = baseMapper.selectOne(queryWrapper);
		if (publishContentEntity == null) {
			return R.fail(ResultCode.NONE_DATA);
		}

		publishContentEntity.setStatus(publishContentRequest.getStatus());
		publishContentEntity.setIsCommon(publishContentRequest.getIsCommon());
		publishContentEntity.setIsShare(publishContentRequest.getIsShare());
		publishContentEntity.setSortId(publishContentRequest.getSortId());

		boolean success = this.updateById(publishContentEntity);
		return success ? R.success() : R.fail();
	}

	/**
	 * 批量删除内容发布信息
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public Boolean removeBatchByIds2(List<Long> ids) {
		return this.deleteLogic(ids);
	}
}
