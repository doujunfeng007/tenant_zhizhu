package com.minigod.zero.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;

public interface IPlatformCommonTemplateService extends IService<PlatformCommonTemplateEntity> {

	PlatformCommonTemplateEntity selectByTempCode(Integer tempCode);
}
