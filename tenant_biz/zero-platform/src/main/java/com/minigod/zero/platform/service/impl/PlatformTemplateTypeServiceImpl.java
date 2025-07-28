package com.minigod.zero.platform.service.impl;

import com.minigod.zero.platform.mapper.PlatformTemplateTypeMapper;
import com.minigod.zero.platform.service.IPlatformTemplateTypeService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.platform.entity.PlatformTemplateTypeEntity;
import org.springframework.stereotype.Service;

/**
 * 模块类型信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Service
public class PlatformTemplateTypeServiceImpl extends BaseServiceImpl<PlatformTemplateTypeMapper, PlatformTemplateTypeEntity> implements IPlatformTemplateTypeService {

//	@Override
//	public IPage<PlatformTemplateTypeVO> selectPlatformTemplateTypePage(IPage<PlatformTemplateTypeVO> page, PlatformTemplateTypeVO platformTemplateType) {
//		return page.setRecords(baseMapper.selectPlatformTemplateTypePage(page, platformTemplateType));
//	}


}
