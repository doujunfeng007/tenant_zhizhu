package com.minigod.zero.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.utils.DateUtils;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnActiveRewardRecordEntity;
import com.minigod.zero.manage.enums.*;
import com.minigod.zero.manage.mapper.SnActiveRewardRecordMapper;
import com.minigod.zero.manage.service.IActiveService;
import com.minigod.zero.manage.service.ISnActiveRewardRecordService;
import com.minigod.zero.manage.vo.request.ManualRewardReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 活动实现类
 *
 * @author eric
 * @since 2024-12-24 13:47:08
 */
@Slf4j
@Service
public class ActiveServiceImpl extends BaseServiceImpl<SnActiveRewardRecordMapper, SnActiveRewardRecordEntity> implements IActiveService {
	private final ISnActiveRewardRecordService snActiveRewardRecordService;

	public ActiveServiceImpl(ISnActiveRewardRecordService snActiveRewardRecordService) {
		this.snActiveRewardRecordService = snActiveRewardRecordService;
	}

	/**
	 * 发放非活动类型的奖励
	 *
	 * @param rewardReqVOs
	 * @return
	 */
	@Override
	public boolean giveOutReward(List<ManualRewardReqVO> rewardReqVOs) {
		log.info("人工发放奖品请求, 参数:{}", JSONObject.toJSONString(rewardReqVOs));
		Date nowDate = new Date();
		int rows = 0;
		for (ManualRewardReqVO reqVO : rewardReqVOs) {
			SnActiveRewardRecordEntity snActiveRewardRecord = new SnActiveRewardRecordEntity();
			Integer rewardType = reqVO.getRewardType();
			if (rewardType == RewardTypeEnum.COMMISSS_FREE.getValue()) {//免佣
				snActiveRewardRecord.setRewardType(RewardTypeEnum.COMMISSS_FREE.getValue());
				snActiveRewardRecord.setCommissionDay(reqVO.getFreeDays());
				snActiveRewardRecord.setCommissionType(1);//目前只支持发放港股行情
			} else if (rewardType == RewardTypeEnum.MktINFO_FREE.getValue()) {//行情
				snActiveRewardRecord.setRewardType(RewardTypeEnum.MktINFO_FREE.getValue());
				snActiveRewardRecord.setPackageId(reqVO.getPackageId().longValue());
				snActiveRewardRecord.setCommissionDay(reqVO.getFreeDays());
			} else if (rewardType == RewardTypeEnum.MONEY.getValue()) {//现金
				snActiveRewardRecord.setRewardType(RewardTypeEnum.MONEY.getValue());
				snActiveRewardRecord.setRewardMoney(reqVO.getTotalMoney().multiply(new BigDecimal(100.00)).setScale(2, BigDecimal.ROUND_HALF_UP));

			} else if (rewardType == RewardTypeEnum.HANDSEL_STOCK.getValue()) {//股票
				snActiveRewardRecord.setRewardType(RewardTypeEnum.HANDSEL_STOCK.getValue());
				snActiveRewardRecord.setAssetId(reqVO.getAssetId());
				snActiveRewardRecord.setQuantity(reqVO.getQuantity());
			} else if (rewardType == RewardTypeEnum.GROUP_POINTS.getValue()) {//积分
				snActiveRewardRecord.setRewardType(RewardTypeEnum.GROUP_POINTS.getValue());
			} else if (rewardType == RewardTypeEnum.CASH_DEDUCTION.getValue()) {
				snActiveRewardRecord.setRewardType(RewardTypeEnum.CASH_DEDUCTION.getValue());
				if (reqVO.getRewardSubtype() == SubRewardTypeEnum.TYP_1.value) {
					snActiveRewardRecord.setCardType(RewardCardTypeEnum.TYPE_3.value);
					snActiveRewardRecord.setRewardMoney(reqVO.getRewardMoney().multiply(new BigDecimal(100.00)).setScale(2, BigDecimal.ROUND_HALF_UP));
				} else if (reqVO.getRewardSubtype() == SubRewardTypeEnum.TYP_2.value) {
					snActiveRewardRecord.setCardType(RewardCardTypeEnum.TYPE_4.value);
					snActiveRewardRecord.setRewardMoney((reqVO.getRewardMoney().multiply(new BigDecimal(100.00)).setScale(2, BigDecimal.ROUND_HALF_UP)));
				} else if (reqVO.getRewardSubtype() == SubRewardTypeEnum.TYP_3.value) {
					snActiveRewardRecord.setCardType(RewardCardTypeEnum.TYPE_5.value);
				}
			}

			snActiveRewardRecord.setRewardCondition(reqVO.getRewardCondition());
			snActiveRewardRecord.setActiveName(reqVO.getSendSource());
			snActiveRewardRecord.setActiveType(ActiveTypeEnum.SHUANG_DAN.getVaule());
			snActiveRewardRecord.setSnActiveConfigItemId(reqVO.getActiveCfgItemId().longValue());//奖品ID
			snActiveRewardRecord.setActiveItemName(reqVO.getActiveItemName());//奖品名称
			snActiveRewardRecord.setUserId(reqVO.getUserId());
			snActiveRewardRecord.setSourceType(SourceTypeEnum.MANUAL.getValue());//奖品为人工发放
			snActiveRewardRecord.setRewardStatus(RewardStatusTypeEnum.UNCLAIMED.getValue());//默认已经领取
			snActiveRewardRecord.setStatus(0);
			if (null == reqVO.getUseDay()) reqVO.setUseDay(30);
			//领取奖励截止时间为无穷大
			snActiveRewardRecord.setConfirmEndDatetime(LocalDateTime.ofInstant(DateUtils.plusDays(nowDate, reqVO.getUseDay()).toInstant(), ZoneId.systemDefault()));

			snActiveRewardRecord.setOprId(reqVO.getOprId());//操作人员ID
			snActiveRewardRecord.setOprName(reqVO.getOprName());//操作人员名称
			snActiveRewardRecord.setOprReason(reqVO.getOprReason());//申请原因
			snActiveRewardRecord.setCreateTime(nowDate);
			snActiveRewardRecord.setUpdateTime(nowDate);

			boolean result = snActiveRewardRecordService.addActiveRewardRecord(snActiveRewardRecord);
			if (result) {
				rows++;
			}
		}
		if (rows > 0) {
			log.info("发放奖品成功, 总记录数:{}", rows);
			return true;
		} else {
			log.info("发放奖品失败, 总记录数:{}", rows);
			return false;
		}
	}
}
