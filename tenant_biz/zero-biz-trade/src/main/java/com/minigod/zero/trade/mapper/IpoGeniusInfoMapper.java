package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoGeniusInfoEntity;
import com.minigod.zero.trade.vo.IpoGeniusInfoVO;

import java.util.List;

/**
 * IPO打新牛人信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IpoGeniusInfoMapper extends BaseMapper<IpoGeniusInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoGeniusInfo
	 * @return
	 */
	List<IpoGeniusInfoVO> selectIpoGeniusInfoPage(IPage page, IpoGeniusInfoVO ipoGeniusInfo);


}
