package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.UserChannelInfoEntity;

import java.util.List;

/**
 * 渠道信息维护服务接口
 *
 * @author eric
 * @since 2024-12-25 15:25:01
 */
public interface IUserChannelInfoService extends BaseService<UserChannelInfoEntity> {
	/**
	 * 获取渠道列表
	 *
	 * @return
	 */
	List<UserChannelInfoEntity> getChannelList();
}
