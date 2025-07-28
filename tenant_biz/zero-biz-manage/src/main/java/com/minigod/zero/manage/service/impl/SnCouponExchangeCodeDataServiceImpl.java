package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeDataEntity;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;
import com.minigod.zero.manage.mapper.SnCouponExchangeCodeDataMapper;
import com.minigod.zero.manage.service.ISnCouponExchangeCodeDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券兑换码数据统计服务接口实现
 *
 * @author eric
 * @date 2024-12-26 10:48:08
 * @TableName sn_coupon_exchange_code_data
 */
@Slf4j
@Service
public class SnCouponExchangeCodeDataServiceImpl extends BaseServiceImpl<SnCouponExchangeCodeDataMapper, SnCouponExchangeCodeDataEntity>
	implements ISnCouponExchangeCodeDataService {
	/**
	 * 分页查询优惠券兑换码数据统计
	 *
	 * @param page
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@Override
	public IPage<SnCouponExchangeCodeDataEntity> findCouponExchangeCodeDataPage(IPage<SnCouponExchangeCodeDataEntity> page, ExchangeCodeReqVO exchangeCodeReqVo) {
		LambdaQueryWrapper<SnCouponExchangeCodeDataEntity> queryWrapper = new LambdaQueryWrapper<>();
		if (!StringUtils.isEmpty(exchangeCodeReqVo.getChannelId())) {
			queryWrapper.eq(SnCouponExchangeCodeDataEntity::getChannelId, exchangeCodeReqVo.getChannelId());
		}
		if (exchangeCodeReqVo.getCardType() != null) {
			queryWrapper.eq(SnCouponExchangeCodeDataEntity::getCardType, exchangeCodeReqVo.getCardType());
		}
		if (exchangeCodeReqVo.getManageId() != null) {
			queryWrapper.eq(SnCouponExchangeCodeDataEntity::getManageId, exchangeCodeReqVo.getManageId());
		}
		return this.page(page, queryWrapper);
	}

	/**
	 * 查询优惠券兑换码数据统计列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@Override
	public List<SnCouponExchangeCodeDataEntity> findCouponExchangeCodeDataList(ExchangeCodeReqVO exchangeCodeReqVo) {
		LambdaQueryWrapper<SnCouponExchangeCodeDataEntity> queryWrapper = new LambdaQueryWrapper<>();
		if (!StringUtils.isEmpty(exchangeCodeReqVo.getChannelId())) {
			queryWrapper.eq(SnCouponExchangeCodeDataEntity::getChannelId, exchangeCodeReqVo.getChannelId());
		}
		if (exchangeCodeReqVo.getManageId() != null) {
			queryWrapper.eq(SnCouponExchangeCodeDataEntity::getManageId, exchangeCodeReqVo.getManageId());
		}
		return this.list(queryWrapper);
	}

	/**
	 * 保存优惠券兑换码数据统计
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean saveCouponExchangeCodeData(SnCouponExchangeCodeDataEntity entity) {
		boolean result = this.save(entity);
		if (result) {
			log.info("保存优惠券兑换码数据统计成功!");
		} else {
			log.info("保存优惠券兑换码数据统计失败!");
		}
		return result;
	}
}
