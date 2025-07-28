package com.minigod.zero.bpmn.module.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.common.mapper.FileUploadInfoMapper;
import com.minigod.zero.bpmn.module.common.service.IFileUploadInfoService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件上传表 服务实现类
 *
 * @author 掌上智珠
 * @since 2024-03-13
 */
@Service
public class FileUploadInfoServiceImpl extends BaseServiceImpl<FileUploadInfoMapper, FileUploadInfoEntity> implements IFileUploadInfoService {

	@Override
	public List<FileUploadInfoEntity> selectListByBusinessType(Long id, Integer businessType) {
		LambdaQueryWrapper<FileUploadInfoEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(FileUploadInfoEntity::getBusinessId, id);
		wrapper.eq(FileUploadInfoEntity::getBusinessType, businessType);
		return baseMapper.selectList(wrapper);
	}
}
