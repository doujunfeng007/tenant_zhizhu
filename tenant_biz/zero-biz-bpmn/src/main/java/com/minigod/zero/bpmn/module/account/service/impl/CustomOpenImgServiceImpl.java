package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenImgEntity;
import com.minigod.zero.bpmn.module.account.mapper.CustomOpenImgMapper;
import com.minigod.zero.bpmn.module.account.service.ICustomOpenImgService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 开户资料文件上传服务实现类
 *
 * @author Chill
 */
@Service
public class CustomOpenImgServiceImpl extends AppServiceImpl<CustomOpenImgMapper, CustomOpenImgEntity> implements ICustomOpenImgService {

	@Override
	public List<CustomOpenImgEntity> selectByUserId(Long custId) {
		LambdaQueryWrapper<CustomOpenImgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomOpenImgEntity::getUserId, custId);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<CustomOpenImgEntity> selectByUserId(Long custId, String location, String type) {
		LambdaQueryWrapper<CustomOpenImgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomOpenImgEntity::getUserId, custId);
		queryWrapper.eq(CustomOpenImgEntity::getLocationType, type);
		queryWrapper.eq(CustomOpenImgEntity::getLocationKey, location);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public CustomOpenImgEntity selectByUserId(Long custId, String type) {
		LambdaQueryWrapper<CustomOpenImgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomOpenImgEntity::getUserId, custId);
		queryWrapper.eq(CustomOpenImgEntity::getLocationType, type);
		queryWrapper.last("limit 1");
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<CustomOpenImgEntity> selectByIds(List<Long> fileIds) {
		LambdaQueryWrapper<CustomOpenImgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(CustomOpenImgEntity::getId, fileIds);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public int updateAttachmentUserId(List<Long> fileIds, Long userId) {
		int rows = -1;
		LambdaQueryWrapper<CustomOpenImgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(CustomOpenImgEntity::getId, fileIds);
		List<CustomOpenImgEntity> attachments = baseMapper.selectList(queryWrapper);
		if (attachments != null && attachments.size() > 0) {
			attachments.forEach(attachment -> attachment.setUserId(userId));
			boolean result = this.updateBatchById(attachments, 100);
			if (result) {
				rows = attachments.size();
			} else {
				rows = -1;
			}
		}

		return rows;
	}

	@Override
	public CustomOpenImgEntity selectByUserIdAndType(Long custId, String location, String type) {
		LambdaQueryWrapper<CustomOpenImgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomOpenImgEntity::getUserId, custId);
		queryWrapper.eq(CustomOpenImgEntity::getLocationType, type);
		queryWrapper.eq(CustomOpenImgEntity::getLocationKey, location);
		return baseMapper.selectOne(queryWrapper);
	}
}
