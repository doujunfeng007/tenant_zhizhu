package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoPredictConfigEntity;
import com.minigod.zero.trade.vo.IpoPredictConfigVO;

import java.util.List;

/**
 * 新股预约ipo配置 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IpoPredictConfigMapper extends BaseMapper<IpoPredictConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoRredictConfig
	 * @return
	 */
	List<IpoPredictConfigVO> selectIpoRredictConfigPage(IPage page, IpoPredictConfigVO ipoRredictConfig);


}
