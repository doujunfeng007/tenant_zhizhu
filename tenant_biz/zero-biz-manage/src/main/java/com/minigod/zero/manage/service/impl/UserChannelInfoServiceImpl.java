package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.UserChannelInfoEntity;
import com.minigod.zero.manage.mapper.UserChannelInfoMapper;
import com.minigod.zero.manage.service.IUserChannelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 渠道信息维护服务实现
 *
 * @author eric
 * @since 2024-12-25 15:28:11
 */
@Slf4j
@Service
public class UserChannelInfoServiceImpl extends BaseServiceImpl<UserChannelInfoMapper, UserChannelInfoEntity> implements IUserChannelInfoService {
	/**
	 * 获取渠道列表
	 *
	 * @return
	 */
	@Override
	public List<UserChannelInfoEntity> getChannelList() {
		return this.list();
	}
}
