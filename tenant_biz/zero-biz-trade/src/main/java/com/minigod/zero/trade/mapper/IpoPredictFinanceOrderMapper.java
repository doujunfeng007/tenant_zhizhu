package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoPredictFinanceOrderEntity;
import com.minigod.zero.trade.vo.IpoPredictFinanceOrderVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 新股预约融资排队订单表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-22
 */
public interface IpoPredictFinanceOrderMapper extends BaseMapper<IpoPredictFinanceOrderEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param IpoPredictFinanceOrder
	 * @return
	 */
	List<IpoPredictFinanceOrderVO> selectIpoPredictFinanceOrderPage(IPage page, IpoPredictFinanceOrderVO IpoPredictFinanceOrder);

	/**
	 *  根据 predictConfigId 获取该只新股的预约队列钟的总金额
	 * @param predictIpoConfigId
	 * @param orderTpye
	 * @return
	 */
	BigDecimal getMaxPredictQueueAmount(@Param("predictConfigId") Long predictIpoConfigId, @Param("orderTpye") int orderTpye);


	List<IpoPredictFinanceOrderEntity> getIpoPredictFinanceOrderByUserId(@Param("predictConfigId")Long predictConfigId, @Param("custId") Long custId, Long id);
}
