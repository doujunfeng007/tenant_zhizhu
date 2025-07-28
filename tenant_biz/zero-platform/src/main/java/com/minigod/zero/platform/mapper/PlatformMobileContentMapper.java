package com.minigod.zero.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;

import java.util.List;

public interface PlatformMobileContentMapper extends BaseMapper<PlatformMobileContentEntity> {

	void saveBatchInformMobileContent(List<PlatformMobileContentEntity> list);

	PlatformMobileContentEntity selectById(Long id);

	void updateByMsgId(PlatformMobileContentEntity mobileContent);
}
