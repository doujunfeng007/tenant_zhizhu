package com.minigod.zero.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;
import com.minigod.zero.platform.mapper.PlatformCommonTemplateMapper;
import com.minigod.zero.platform.service.IPlatformCommonTemplateService;
import org.springframework.stereotype.Service;

@Service
public class PlatformCommonTemplateServiceImpl extends ServiceImpl<PlatformCommonTemplateMapper, PlatformCommonTemplateEntity> implements IPlatformCommonTemplateService {
	@Override
	public PlatformCommonTemplateEntity selectByTempCode(Integer tempCode) {
		return baseMapper.findInformCommonTemp(tempCode);
	}
}
