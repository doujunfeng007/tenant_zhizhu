package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.DiscoverIconEntity;
import com.minigod.zero.manage.vo.DiscoverIconVO;
import com.minigod.zero.manage.vo.response.IconExtVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 焦点功能区图标管理 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface DiscoverIconMapper extends BaseMapper<DiscoverIconEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param DiscoverIcon
	 * @return
	 */
	List<DiscoverIconVO> selectDiscoverIconPage(IPage page, DiscoverIconVO DiscoverIcon);


    List<IconExtVO> getIconExtList(@Param("roleId")int roleId, @Param("isDisplay")boolean isDisplay);

	List<DiscoverIconVO> queryDiscoverIconList();
}
