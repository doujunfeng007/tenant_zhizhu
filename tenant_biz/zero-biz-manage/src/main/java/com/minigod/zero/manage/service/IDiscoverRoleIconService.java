package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.DiscoverRoleIconEntity;
import com.minigod.zero.manage.vo.DiscoverRoleIconVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 焦点功能 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface IDiscoverRoleIconService extends BaseService<DiscoverRoleIconEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param DiscoverRoleIcon
	 * @return
	 */
	IPage<DiscoverRoleIconVO> selectDiscoverRoleIconPage(IPage<DiscoverRoleIconVO> page, DiscoverRoleIconVO DiscoverRoleIcon);


}
