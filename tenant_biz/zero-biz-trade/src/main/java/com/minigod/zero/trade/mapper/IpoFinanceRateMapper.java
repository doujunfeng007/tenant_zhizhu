package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoFinanceRateEntity;
import com.minigod.zero.trade.vo.IpoFinanceRateVO;

import java.util.List;

/**
 * IPO认购融资利率 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-01-30
 */
public interface IpoFinanceRateMapper extends BaseMapper<IpoFinanceRateEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoFinanceRate
	 * @return
	 */
	List<IpoFinanceRateVO> selectIpoFinanceRatePage(IPage page, IpoFinanceRateVO ipoFinanceRate);


}
