package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoFinanceQueueOrderEntity;
import com.minigod.zero.trade.vo.IpoFinanceQueueOrderVO;
import com.minigod.zero.trade.vo.IpoQueueInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * IPO融资排队订单表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-06
 */
public interface IpoFinanceQueueOrderMapper extends BaseMapper<IpoFinanceQueueOrderEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoFinanceQueueOrder
	 * @return
	 */
	List<IpoFinanceQueueOrderVO> selectIpoFinanceQueueOrderPage(IPage page, IpoFinanceQueueOrderVO ipoFinanceQueueOrder);

	/**
	 * 获取有排队订单的股票名称 ，排队订单数
	 * @return
	 */
	List<IpoQueueInfoVO> findQueueOrderInfo();

	/**根据assetId查询累计排队总额
	 *
	 * @param assetId
	 * @param orderType
	 * @return
	 */
    Long getMaxQueueAmount(@Param("assetId") String assetId, @Param("orderType")Integer orderType);
}
