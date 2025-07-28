package com.minigod.zero.manage.service.impl;

import com.minigod.zero.manage.entity.DiscoverIconEntity;
import com.minigod.zero.manage.mapper.DiscoverIconMapper;
import com.minigod.zero.manage.service.IDiscoverIconService;
import com.minigod.zero.manage.vo.DiscoverIconVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 焦点功能区图标管理 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Service
public class DiscoverIconServiceImpl extends BaseServiceImpl<DiscoverIconMapper, DiscoverIconEntity> implements IDiscoverIconService {

	@Override
	public IPage<DiscoverIconVO> selectDiscoverIconPage(IPage<DiscoverIconVO> page, DiscoverIconVO DiscoverIcon) {
		return page.setRecords(baseMapper.selectDiscoverIconPage(page, DiscoverIcon));
	}


}
