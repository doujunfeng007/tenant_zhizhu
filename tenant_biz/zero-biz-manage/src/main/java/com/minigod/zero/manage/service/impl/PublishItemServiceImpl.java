package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.manage.entity.PublishItemEntity;
import com.minigod.zero.manage.service.IPublishItemService;
import com.minigod.zero.manage.vo.request.PublishItemRequest;
import com.minigod.zero.manage.mapper.PublishItemMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 帮助中心目录配置 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Service
public class PublishItemServiceImpl extends BaseServiceImpl<PublishItemMapper, PublishItemEntity> implements IPublishItemService {
	/**
	 * 查询主菜单或非主菜单
	 *
	 * @param id
	 * @param used true-排除常见问题菜单 false-包括常见问题菜单
	 * @return
	 */
	@Override
	public List<PublishItemEntity> findPublishItem(Long id, Boolean used) {
		LambdaQueryWrapper<PublishItemEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PublishItemEntity::getParentId, id);
		queryWrapper.eq(BaseEntity::getStatus, true);
		queryWrapper.orderByAsc(PublishItemEntity::getSortId);
		if (used) {
			queryWrapper.ne(PublishItemEntity::getName, "常见问题");
		}
		return baseMapper.selectList(queryWrapper);
	}

	/**
	 * 查询指定菜单ID下的子菜单列表
	 *
	 * @param id
	 * @return
	 */
	@Override
	public List<PublishItemEntity> publishTree(Long id) {
		if (id == null) {
			id = 0L;
		}
		//used true-排除常见问题菜单 false-包括常见问题菜单
		return this.findPublishItem(id, false);
	}

	/**
	 * 根据id查询菜单详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public PublishItemEntity findById(Long id) {
		return this.getById(id);
	}

	/**
	 * 保存/更新菜单
	 *
	 * @param publishItemRequest
	 * @return
	 */
	@Override
	public Long saveUpdate(PublishItemRequest publishItemRequest) {
		PublishItemEntity publishItemEntity = new PublishItemEntity();
		if (publishItemRequest.getId() == null) {
			if (publishItemRequest.getParentId() == null) {
				publishItemRequest.setParentId(0L);
			}
			publishItemRequest.setIsLeaf(false);
			BeanUtils.copyProperties(publishItemRequest, publishItemEntity);
			this.save(publishItemEntity);
		} else {
			BeanUtils.copyProperties(publishItemRequest, publishItemEntity);
			this.updateById(publishItemEntity);
		}
		return publishItemRequest.getId();
	}

	/**
	 * 更改菜单状态
	 *
	 * @param publishItemRequest
	 * @return
	 */
	@Override
	public R updateStatus(PublishItemRequest publishItemRequest) {
		if (publishItemRequest == null || publishItemRequest.getId() == null || publishItemRequest.getStatus() == null) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		LambdaQueryWrapper<PublishItemEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PublishItemEntity::getId, publishItemRequest.getId());
		PublishItemEntity publishItemEntity = baseMapper.selectOne(queryWrapper);
		if (publishItemEntity == null) {
			return R.fail(ResultCode.NONE_DATA);
		}
		publishItemEntity.setStatus(publishItemRequest.getStatus());
		boolean success = this.updateById(publishItemEntity);
		return success ? R.success() : R.fail();
	}

	/**
	 * 删除指定ID的菜单项
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public boolean deleteByIds(List<Long> ids) {
		return this.deleteLogic(ids);
	}
}
