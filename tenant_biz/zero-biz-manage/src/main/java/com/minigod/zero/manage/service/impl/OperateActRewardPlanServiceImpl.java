package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.OperateActRewardPlanEntity;
import com.minigod.zero.manage.entity.SnActiveConfigItemEntity;
import com.minigod.zero.manage.enums.ERuleType;
import com.minigod.zero.manage.enums.RewardTypeEnum;
import com.minigod.zero.manage.vo.RuleRewardVO;
import com.minigod.zero.manage.mapper.OperateActRewardPlanMapper;
import com.minigod.zero.manage.mapper.SnActiveConfigItemMapper;
import com.minigod.zero.manage.service.IOperateActRewardPlanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 运营活动奖励方案维护服务接口实现
 *
 * @author eric
 * @since 2024-12-25 10:36:08
 */
@Slf4j
@Service
public class OperateActRewardPlanServiceImpl extends BaseServiceImpl<OperateActRewardPlanMapper, OperateActRewardPlanEntity> implements IOperateActRewardPlanService {
	private final OperateActRewardPlanMapper operateActRewardPlanMapper;
	private final SnActiveConfigItemMapper snActiveConfigItemMapper;

	public OperateActRewardPlanServiceImpl(OperateActRewardPlanMapper operateActRewardPlanMapper, SnActiveConfigItemMapper snActiveConfigItemMapper) {
		this.operateActRewardPlanMapper = operateActRewardPlanMapper;
		this.snActiveConfigItemMapper = snActiveConfigItemMapper;
	}

	/**
	 * 查询运营活动奖励方案列表
	 *
	 * @param planId
	 * @param funType
	 * @return
	 */
	@Override
	public List<OperateActRewardPlanEntity> findOperateActRewardPlanList(Long planId, Integer funType) {
		return operateActRewardPlanMapper.queryByPlanId(planId, funType);
	}

	/**
	 * 根据方案ID和规则类型获取规则列表
	 *
	 * @param planId
	 * @param funType
	 * @return
	 */
	@Override
	public List<RuleRewardVO> getRuleRewardListByPlan(Long planId, Integer funType) {
		/** 这里查询入金规则列表 */
		List<RuleRewardVO> ruleRewardVOList = operateActRewardPlanMapper.queryRuleRewardByPlanId(planId,funType);
		/** 根据规则查询入金对应的奖品,填入VO中 **/
		for(RuleRewardVO ruleRewardVO:ruleRewardVOList){
			List<SnActiveConfigItemEntity> list = snActiveConfigItemMapper.getRewardsListByRule(ruleRewardVO.getId());
			for(SnActiveConfigItemEntity bean:list){
				if(bean.getRewardType().equals(RewardTypeEnum.COMMISSS_FREE.getValue())){
					ruleRewardVO.setCommissionDay(bean.getCommissionDay());
					ruleRewardVO.setCommissionDayId(bean.getId().intValue());
				}
				if(bean.getRewardType().equals(RewardTypeEnum.MONEY.getValue())){
					ruleRewardVO.setRewardMoney(bean.getRewardMoney());
					ruleRewardVO.setRewardMoneyId(bean.getId().intValue());
				}
				if(bean.getRewardType().equals(RewardTypeEnum.HANDSEL_STOCK.getValue())){
					ruleRewardVO.setStkNum(bean.getStkNum());
					ruleRewardVO.setAssetId(bean.getAssetId());
					ruleRewardVO.setRewardAssetId(bean.getId().intValue());
				}
			}
		}
		/** 前端排序需要，按照 开户->邀请开户->入金->邀请入金排列 **/
		List<RuleRewardVO> finalList = new ArrayList<RuleRewardVO>();
		List<RuleRewardVO> openList = new ArrayList<RuleRewardVO>();
		List<RuleRewardVO> inviteOpenList = new ArrayList<RuleRewardVO>();
		List<RuleRewardVO> depositList = new ArrayList<RuleRewardVO>();
		List<RuleRewardVO> inviteDepositList = new ArrayList<RuleRewardVO>();
		List<RuleRewardVO> transferList = new ArrayList<RuleRewardVO>();
		List<RuleRewardVO> inviteTransferList = new ArrayList<RuleRewardVO>();
		for(RuleRewardVO vo:ruleRewardVOList){
			if(vo.getFunType().equals(ERuleType.OPEN.getTypeCode().intValue())){
				openList.add(vo);
			}else if(vo.getFunType().equals(ERuleType.INVITE_OPEN.getTypeCode().intValue())){
				inviteOpenList.add(vo);
			}else if(vo.getFunType().equals(ERuleType.DEPOSIT.getTypeCode().intValue())){
				depositList.add(vo);
			}else if(vo.getFunType().equals(ERuleType.INVITE_DEPOSIT.getTypeCode().intValue())){
				inviteDepositList.add(vo);
			}else if(vo.getFunType().equals(ERuleType.TRANSFER.getTypeCode().intValue())){
				transferList.add(vo);
			}else if(vo.getFunType().equals(ERuleType.INVITE_TRANSFER.getTypeCode().intValue())){
				inviteTransferList.add(vo);
			}
		}
		finalList.addAll(openList);
		finalList.addAll(inviteOpenList);
		if(CollectionUtils.isNotEmpty(depositList)){
			finalList.addAll(depositList);
		}
		if(CollectionUtils.isNotEmpty(inviteDepositList)){
			finalList.addAll(inviteDepositList);
		}
		if(CollectionUtils.isNotEmpty(transferList)){
			finalList.addAll(transferList);
		}
		if(CollectionUtils.isNotEmpty(inviteTransferList)){
			finalList.addAll(inviteTransferList);
		}
		return finalList;
	}

	/**
	 * 保存或更新运营活动奖励方案
	 *
	 * @param operateActRewardPlan
	 * @return
	 */
	@Override
	public OperateActRewardPlanEntity saveOrUpdateOperateActRewardPlan(OperateActRewardPlanEntity operateActRewardPlan) {
		if (operateActRewardPlan.getId() == null) {
			this.save(operateActRewardPlan);
		} else {
			this.updateById(operateActRewardPlan);
		}
		return operateActRewardPlan;
	}

	/**
	 * 删除运营活动奖励方案
	 *
	 * @param planId
	 * @param funType
	 */
	@Override
	public int deleteOperateActRewardPlan(Long planId, Integer funType) {
		return operateActRewardPlanMapper.deleteOperateActRewardPlan(planId, funType);
	}
}
