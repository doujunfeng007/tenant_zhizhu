package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.DiscoverIconEntity;
import com.minigod.zero.manage.vo.DiscoverIconVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 焦点功能区图标管理 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface IDiscoverIconService extends BaseService<DiscoverIconEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param DiscoverIcon
	 * @return
	 */
	IPage<DiscoverIconVO> selectDiscoverIconPage(IPage<DiscoverIconVO> page, DiscoverIconVO DiscoverIcon);


}
