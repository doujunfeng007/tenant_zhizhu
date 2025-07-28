package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoOmsConfigEntity;
import com.minigod.zero.trade.vo.IpoOmsConfigVO;

import java.util.List;

/**
 * 新股ipo配置 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IpoOmsConfigMapper extends BaseMapper<IpoOmsConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoOmsConfig
	 * @return
	 */
	List<IpoOmsConfigVO> selectIpoOmsConfigPage(IPage page, IpoOmsConfigVO ipoOmsConfig);


}
