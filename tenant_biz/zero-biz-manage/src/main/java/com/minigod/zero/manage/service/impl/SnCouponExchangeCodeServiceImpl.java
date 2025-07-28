package com.minigod.zero.manage.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnActiveRewardRecordEntity;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeEntity;
import com.minigod.zero.manage.entity.SnCouponManageEntity;
import com.minigod.zero.manage.mapper.SnCouponExchangeCodeMapper;
import com.minigod.zero.manage.mapper.SnCouponManageMapper;
import com.minigod.zero.manage.service.ISnActiveRewardRecordService;
import com.minigod.zero.manage.service.ISnCouponExchangeCodeService;
import com.minigod.zero.manage.vo.ExchangeCodeRespVO;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 优惠券兑换码服务接口实现
 *
 * @author eric
 * @since 2024-12-26 10:08:01
 */
@Slf4j
@Service
public class SnCouponExchangeCodeServiceImpl extends BaseServiceImpl<SnCouponExchangeCodeMapper, SnCouponExchangeCodeEntity> implements ISnCouponExchangeCodeService {
	private final SnCouponExchangeCodeMapper snCouponExchangeCodeMapper;
	private final SnCouponManageMapper snCouponManageMapper;
	private final ISnActiveRewardRecordService snActiveRewardRecordService;

	public SnCouponExchangeCodeServiceImpl(SnCouponExchangeCodeMapper snCouponExchangeCodeMapper,
										   SnCouponManageMapper snCouponManageMapper,
										   ISnActiveRewardRecordService snActiveRewardRecordService) {
		this.snCouponExchangeCodeMapper = snCouponExchangeCodeMapper;
		this.snCouponManageMapper = snCouponManageMapper;
		this.snActiveRewardRecordService = snActiveRewardRecordService;
	}

	/**
	 * 分页查询优惠券兑换码列表
	 *
	 * @param page              分页对象
	 * @param exchangeCodeReqVo 查询条件
	 * @return
	 */
	@Override
	public IPage<ExchangeCodeRespVO> findExchangeCodePage(IPage<ExchangeCodeRespVO> page, ExchangeCodeReqVO exchangeCodeReqVo) {
		return snCouponExchangeCodeMapper.findExchangeCodePage(page, exchangeCodeReqVo);
	}

	/**
	 * 批量插入优惠券兑换码
	 *
	 * @param list
	 * @return
	 */
	@Override
	public boolean batchInsert(List<SnCouponExchangeCodeEntity> list) {
		boolean result = this.saveBatch(list, DEFAULT_BATCH_SIZE);
		if (result) {
			log.info("-->优惠券兑换码批量插入成功!");
		} else {
			log.info("-->优惠券兑换码批量插入失败!");
		}
		return result;
	}

	/**
	 * 作废优惠券兑换码
	 *
	 * @param id
	 */
	@Override
	public void updateExchangeCode(Long id) {
		SnCouponExchangeCodeEntity exchangeCode = this.getById(id);
		if (null != exchangeCode) {
			exchangeCode.setUseStatus(3);
			boolean result = this.updateById(exchangeCode);
			if (result) {
				log.info("-->作废优惠券兑换码,优惠券兑换码状态(3)更新成功!");
			} else {
				log.info("-->作废优惠券兑换码,优惠券兑换码状态(3)更新失败!");
			}
		}
	}

	/**
	 * 更新优惠券兑换码状态延长天数
	 *
	 * @param id
	 * @param recordStatus
	 * @param days
	 */
	@Override
	public void updateExchangeCode(Long id, Integer recordStatus, Integer days) {
		SnCouponExchangeCodeEntity exchangeCode = this.getById(id);
		//兼容测试环境旧数据
		if (null == exchangeCode.getExpiredTime()) {
			SnCouponManageEntity snCouponManage = snCouponManageMapper.selectById(exchangeCode.getManageId());
			exchangeCode.setExpiredTime(DateUtil.offsetDay(com.minigod.zero.biz.common.utils.DateUtil.parseDate(snCouponManage.getExpiredTime()), days).toLocalDateTime());
		} else {
			exchangeCode.setExpiredTime(DateUtil.offsetDay(com.minigod.zero.biz.common.utils.DateUtil.parseDate(exchangeCode.getExpiredTime()), days).toLocalDateTime());
		}
		this.updateById(exchangeCode);
		if (1 == recordStatus) {
			//修改奖励表中的过期时间
			SnActiveRewardRecordEntity rewardRecord = snActiveRewardRecordService.findActiveRewardRecordByExchangeCodeId(exchangeCode.getId());
			if (rewardRecord != null) {
				rewardRecord.setConfirmEndDatetime(exchangeCode.getExpiredTime());
				snActiveRewardRecordService.updateActiveRewardRecord(rewardRecord);
			}
		}
	}

	/**
	 * 查询优惠券兑换码列表
	 *
	 * @param manageId 优惠券管理ID
	 * @return
	 */
	@Override
	public List<SnCouponExchangeCodeEntity> findCouponExchangeCodeList(Long manageId) {
		LambdaQueryWrapper<SnCouponExchangeCodeEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SnCouponExchangeCodeEntity::getManageId, manageId);
		return this.list(queryWrapper);
	}

	/**
	 * 查询优惠券兑换码列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@Override
	public List<ExchangeCodeRespVO> findExchangeCodeList(ExchangeCodeReqVO exchangeCodeReqVo) {
		List<ExchangeCodeRespVO> exchangeCodeRespVoList = snCouponExchangeCodeMapper.findExchangeCodeList(exchangeCodeReqVo);
		if (CollectionUtils.isNotEmpty(exchangeCodeRespVoList)) {
			for (ExchangeCodeRespVO exchangeCodeRespVo : exchangeCodeRespVoList) {
				if (null != exchangeCodeRespVo.getAddAmount()) {
					DecimalFormat df = new DecimalFormat("#.00");
					String addAmounts = df.format(exchangeCodeRespVo.getAddAmount());
					exchangeCodeRespVo.setAddAmounts(addAmounts);
				}
				/* todo 以下逻辑需要依赖用户相关信息表
				if (null != exchangeCodeRespVo.getUseUid()) {
					UserInfo userInfo = db.select(tUserInfo.all).from(tUserInfo).where(tUserInfo.userId.eq(exchangeCodeRespVo.getUseUid())).queryObject(UserInfo.class);
					if (null != userInfo) {
						exchangeCodeRespVo.setRegisterDate(userInfo.getCreateTime());
					}
					OpenUserInfo openUserInfo = db.select(tOpenUserInfo.all).from(tOpenUserInfo).where(tOpenUserInfo.userId.eq(exchangeCodeRespVo.getUseUid())).queryObject(OpenUserInfo.class);
					if (null != openUserInfo) {
						if ("0".equals(openUserInfo.getOpenstatus())) {
							exchangeCodeRespVo.setOpenDate(openUserInfo.getOpenDate());
						}
					}
					SecFirstDepositRecord secFirstDepositRecord = db.select(tSecFirstDepositRecord.all).from(tSecFirstDepositRecord).where(tSecFirstDepositRecord.userId.eq(exchangeCodeRespVo.getUseUid())).queryObject(SecFirstDepositRecord.class);
					if (null != secFirstDepositRecord) {
						if (3 == secFirstDepositRecord.getState()) {
							exchangeCodeRespVo.setDepositDate(secFirstDepositRecord.getCreatedTime());
						}
					}
				}*/
			}
		}
		return exchangeCodeRespVoList;
	}
}
