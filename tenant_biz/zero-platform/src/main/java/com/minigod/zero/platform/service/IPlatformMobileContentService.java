package com.minigod.zero.platform.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;

import java.util.Date;
import java.util.List;

public interface IPlatformMobileContentService extends BaseService<PlatformMobileContentEntity> {

	List<PlatformMobileContentEntity> findAllUnsendMobileMsg(Date date);

	void pushUnsendMobileMsg();
}
