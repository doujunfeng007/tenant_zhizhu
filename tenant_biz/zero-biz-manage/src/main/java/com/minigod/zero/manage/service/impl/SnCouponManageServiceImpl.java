package com.minigod.zero.manage.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.SnCouponManageEntity;
import com.minigod.zero.manage.mapper.SnCouponManageMapper;
import com.minigod.zero.manage.service.ISnCouponManageService;
import com.minigod.zero.manage.vo.SnCouponManageVO;
import com.minigod.zero.manage.vo.request.ExchangeBulkListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券管理服务接口实现
 *
 * @author eric
 * @since 2024-12-26 10:11:08
 */
@Slf4j
@Service
public class SnCouponManageServiceImpl extends BaseServiceImpl<SnCouponManageMapper, SnCouponManageEntity> implements ISnCouponManageService {
	private final SnCouponManageMapper snCouponManageMapper;

	public SnCouponManageServiceImpl(SnCouponManageMapper snCouponManageMapper) {
		this.snCouponManageMapper = snCouponManageMapper;
	}

	/**
	 * 分页查询优惠券管理
	 *
	 * @param page
	 * @return
	 */
	@Override
	public IPage<SnCouponManageEntity> findCouponManagePage(IPage<SnCouponManageEntity> page) {
		return this.page(page);
	}

	/**
	 * 批量兑换码列表
	 *
	 * @param exchangeBulkListReqVo
	 * @return
	 */
	@Override
	public IPage<SnCouponManageVO> findExchangeBulkPage(IPage<SnCouponManageEntity> page, ExchangeBulkListReqVO exchangeBulkListReqVo) {
		LambdaQueryWrapper<SnCouponManageEntity> queryWrapper = new LambdaQueryWrapper<>();
		if (exchangeBulkListReqVo != null) {
			// 进行时间区间的判断是否为null
			if (StringUtils.isNotBlank(exchangeBulkListReqVo.getStartDate())) {
				Date startDate = DateUtil.parse(exchangeBulkListReqVo.getStartDate(), "yyyy-MM-dd");
				queryWrapper.ge(SnCouponManageEntity::getCreateTime, startDate);
			}
			if (StringUtils.isNotBlank(exchangeBulkListReqVo.getEndDate())) {
				Date endDate = DateUtil.parse(exchangeBulkListReqVo.getEndDate() + " 59:59:59", "yyyy-MM-dd HH:mm:ss");
				queryWrapper.le(SnCouponManageEntity::getCreateTime, endDate);
			}
			if (StringUtils.isNotBlank(exchangeBulkListReqVo.getOprName())) {
				queryWrapper.eq(SnCouponManageEntity::getOprName, exchangeBulkListReqVo.getOprName());
			}
			if (exchangeBulkListReqVo.getUseStatus() > -1) {
				queryWrapper.eq(SnCouponManageEntity::getUseStatus, exchangeBulkListReqVo.getUseStatus());
			}
		}
		queryWrapper.isNotNull(SnCouponManageEntity::getCode);

		IPage<SnCouponManageEntity> couponManagePage = this.page(page, queryWrapper);
		List<SnCouponManageVO> couponManageList = couponManagePage.getRecords().stream().map(couponManage -> {
			SnCouponManageVO snCouponManageVO = new SnCouponManageVO();
			BeanUtil.copyNonNull(couponManage, snCouponManageVO);
			return snCouponManageVO;
		}).collect(Collectors.toList());

		IPage<SnCouponManageVO> resultPage = new Page<>();
		resultPage.setRecords(couponManageList);
		resultPage.setTotal(couponManagePage.getTotal());
		resultPage.setCurrent(couponManagePage.getCurrent());
		resultPage.setSize(couponManagePage.getSize());
		resultPage.setPages(couponManagePage.getPages());
		return resultPage;
	}

	/**
	 * 保存优惠券管理
	 *
	 * @param couponManage
	 */
	@Override
	public void saveCouponManage(SnCouponManageEntity couponManage) {
		int rows = snCouponManageMapper.insert(couponManage);
		if (rows != 1) {
			log.error("保存优惠券管理失败!");
		} else {
			log.info("保存优惠券管理成功!");
		}
	}

	/**
	 * 查询优惠券管理详情
	 *
	 * @param manageId
	 * @return
	 */
	@Override
	public SnCouponManageEntity findCouponManage(Long manageId) {
		return this.getById(manageId);
	}
}
