package com.minigod.zero.manage.service.impl;

import com.minigod.zero.manage.entity.DiscoverRoleIconEntity;
import com.minigod.zero.manage.vo.DiscoverRoleIconVO;
import com.minigod.zero.manage.mapper.DiscoverRoleIconMapper;
import com.minigod.zero.manage.service.IDiscoverRoleIconService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 焦点功能 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Service
public class DiscoverRoleIconServiceImpl extends BaseServiceImpl<DiscoverRoleIconMapper, DiscoverRoleIconEntity> implements IDiscoverRoleIconService {

	@Override
	public IPage<DiscoverRoleIconVO> selectDiscoverRoleIconPage(IPage<DiscoverRoleIconVO> page, DiscoverRoleIconVO DiscoverRoleIcon) {
		return page.setRecords(baseMapper.selectDiscoverRoleIconPage(page, DiscoverRoleIcon));
	}


}
