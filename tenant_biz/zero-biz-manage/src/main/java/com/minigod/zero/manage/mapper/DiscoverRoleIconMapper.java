package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.DiscoverRoleIconEntity;
import com.minigod.zero.manage.vo.DiscoverRoleIconVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 焦点功能 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface DiscoverRoleIconMapper extends BaseMapper<DiscoverRoleIconEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param DiscoverRoleIcon
	 * @return
	 */
	List<DiscoverRoleIconVO> selectDiscoverRoleIconPage(IPage page, DiscoverRoleIconVO DiscoverRoleIcon);


}
