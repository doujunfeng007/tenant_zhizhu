package com.minigod.zero.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;

/**
 * Mapper接口
 *
 * @author minigod
 */
public interface PlatformCommonTemplateMapper extends BaseMapper<PlatformCommonTemplateEntity> {

	PlatformCommonTemplateEntity findInformCommonTemp(Integer tempCode);
	PlatformCommonTemplateEntity findTemplateByCodeAndTenantId(Integer tempCode,String tenantId);

}
