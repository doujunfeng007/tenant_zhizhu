package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoPredictFinanceOrderEntity;
import com.minigod.zero.trade.vo.IpoPredictFinanceOrderVO;

import java.util.List;

/**
 * 新股预约融资排队订单表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-22
 */
public interface IIpoPredictFinanceOrderService extends IService<IpoPredictFinanceOrderEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param IpoPredictFinanceOrder
	 * @return
	 */
	IPage<IpoPredictFinanceOrderVO> selectIpoPredictFinanceOrderPage(IPage<IpoPredictFinanceOrderVO> page, IpoPredictFinanceOrderVO IpoPredictFinanceOrder);

	/**
	 * 查询客户新股预约列表
	 * @param predictIpoConfigId
	 * @param custId
	 * @param o
	 * @return
	 */
	List<IpoPredictFinanceOrderEntity> getIpoPredictFinanceOrderByUserId(Long predictIpoConfigId, Long custId, Long id);

	/**
	 *  根据 predictConfigId 获取该只新股的预约队列钟的总金额
	 * @param predictIpoConfigId
	 * @param orderTpye
	 * @param aTrue
	 * @return
	 */
	Number getTotalPredictQueueAmount(Long predictIpoConfigId, int orderTpye, Boolean aTrue);

	/**
	 * 保存
	 * @param ipoPredictFinanceOrder
	 * @return
	 */
	Integer saveIpoPredictFinanceOrder(IpoPredictFinanceOrderEntity ipoPredictFinanceOrder);

	/**
	 * 更新缓存
	 * @param predictIpoConfigId
	 * @param custId
	 * @param aFalse
	 * @return
	 */
	IpoPredictFinanceOrderEntity getIpoPredictFinanceOrderByPredictIpoConfigId(Long predictIpoConfigId, Long custId, Boolean aFalse);

	/**
	 * 更新预约状态
	 * @param ipoPredictFinanceOrder
	 */
	Integer updateIpoPredictOrderQueueStatus(IpoPredictFinanceOrderEntity ipoPredictFinanceOrder);

	Integer updateIpoPredictOrderQueueStatusById(IpoPredictFinanceOrderEntity ipoPredictFinanceOrder);
}
