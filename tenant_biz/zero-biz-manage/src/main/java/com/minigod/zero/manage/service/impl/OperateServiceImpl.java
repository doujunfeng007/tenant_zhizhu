package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.minigod.zero.manage.entity.OperateActPlanEntity;
import com.minigod.zero.manage.entity.OperateActRewardPlanEntity;
import com.minigod.zero.manage.entity.OperateRuleRewardRelationEntity;
import com.minigod.zero.manage.entity.SnActiveConfigItemEntity;
import com.minigod.zero.manage.enums.ERuleType;
import com.minigod.zero.manage.enums.RewardTypeEnum;
import com.minigod.zero.manage.mapper.OperateActRewardPlanMapper;
import com.minigod.zero.manage.mapper.OperateRuleRewardRelationMapper;
import com.minigod.zero.manage.mapper.SnActiveConfigItemMapper;
import com.minigod.zero.manage.service.IOperateRuleRewardRelationService;
import com.minigod.zero.manage.service.IOperateService;
import com.minigod.zero.manage.vo.DeductionReward;
import com.minigod.zero.manage.vo.GivenTheStockVO;
import com.minigod.zero.manage.vo.RewardRuleCommonVO;
import com.minigod.zero.manage.mapper.OperateActPlanMapper;
import com.minigod.zero.manage.service.IOperateActPlanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 奖励方案管理服务接口实现
 *
 * @author eric
 * @since 2024-12-24 17:14:05
 */
@Slf4j
@Service
public class OperateServiceImpl implements IOperateService {

	private final OperateActPlanMapper operateActPlanMapper;
	private final OperateActRewardPlanMapper operateActRewardPlanMapper;
	private final OperateRuleRewardRelationMapper operateRuleRewardRelationMapper;
	private final SnActiveConfigItemMapper snActiveConfigItemMapper;
	private final IOperateRuleRewardRelationService operateRuleRewardRelationService;
	private final IOperateActPlanService operateActPlanService;

	public OperateServiceImpl(OperateActPlanMapper operateActPlanMapper,
							  OperateActRewardPlanMapper operateActRewardPlanMapper,
							  OperateRuleRewardRelationMapper operateRuleRewardRelationMapper,
							  SnActiveConfigItemMapper snActiveConfigItemMapper,
							  IOperateRuleRewardRelationService operateRuleRewardRelationService,
							  IOperateActPlanService operateActPlanService) {
		this.operateActPlanMapper = operateActPlanMapper;
		this.operateActRewardPlanMapper = operateActRewardPlanMapper;
		this.operateRuleRewardRelationMapper = operateRuleRewardRelationMapper;
		this.snActiveConfigItemMapper = snActiveConfigItemMapper;
		this.operateRuleRewardRelationService = operateRuleRewardRelationService;
		this.operateActPlanService = operateActPlanService;
	}

	/**
	 * 查询奖励方案列表
	 *
	 * @param page
	 * @param planId
	 * @param planName
	 * @return
	 */
	@Override
	public IPage<OperateActPlanEntity> findOperateActPlanPage(IPage page, Long planId, String planName) {
		return operateActPlanMapper.queryPage(page, planId, planName);
	}

	/**
	 * 查询奖励方案详情
	 *
	 * @param planId
	 * @return
	 */
	@Override
	public OperateActPlanEntity findOperateActPlan(Long planId) {
		return operateActPlanMapper.selectById(planId);
	}

	/**
	 * 根据奖励方案ID和功能类型查询奖励规则
	 *
	 * @param planId
	 * @param funType
	 * @return
	 */
	@Override
	public List<RewardRuleCommonVO> findRewardRule(Long planId, Integer funType) {
		List<RewardRuleCommonVO> list = Lists.newArrayList();
		List<OperateActRewardPlanEntity> rewardPlanList = operateActRewardPlanMapper.queryByPlanId(planId, funType);
		for (OperateActRewardPlanEntity operateActRewardPlan : rewardPlanList) {
			List<OperateRuleRewardRelationEntity> rewardRelationList = operateRuleRewardRelationMapper.findOperateRuleRewardRelation(operateActRewardPlan.getId());

			RewardRuleCommonVO commonVo = new RewardRuleCommonVO();
			commonVo.setGivenTheStockList(new ArrayList<>());
			if (CollectionUtils.isNotEmpty(rewardRelationList)) {
				List<DeductionReward> arr = new ArrayList<>();
				for (OperateRuleRewardRelationEntity operateRuleRewardRelation : rewardRelationList) {
					commonVo.setStartAmount(String.format("%.2f", operateActRewardPlan.getStartAmount()));
					commonVo.setEndAmount(String.format("%.2f", operateActRewardPlan.getEndAmount()));

					if (StringUtils.isBlank(operateActRewardPlan.getRewardCondition())) {
						commonVo.setRewardCondition("--");
					} else {
						commonVo.setRewardCondition(operateActRewardPlan.getRewardCondition());
					}

					SnActiveConfigItemEntity configItem = snActiveConfigItemMapper.selectById(operateRuleRewardRelation.getRewardId());
					if (configItem.getRewardType() == RewardTypeEnum.COMMISSS_FREE.getValue()) {
						commonVo.setCommissionDay(configItem.getCommissionDay());
						commonVo.setCommissionRewardId(operateRuleRewardRelation.getRewardId());
					}
					if (configItem.getRewardType() == RewardTypeEnum.MONEY.getValue()) {
						commonVo.setRewardMoney(configItem.getRewardMoney());
						commonVo.setMoneyRewardId(operateRuleRewardRelation.getRewardId());
					}
					if (configItem.getRewardType() == RewardTypeEnum.HANDSEL_STOCK.getValue()) {
						if (ERuleType.DEPOSIT.getTypeCode().equals(funType) || ERuleType.TRANSFER.getTypeCode().equals(funType)) {
							GivenTheStockVO givenTheStockVo = new GivenTheStockVO();
							givenTheStockVo.setStkName(configItem.getAssetId());
							givenTheStockVo.setStkQuantity(configItem.getStkNum());
							givenTheStockVo.setStockRewardId(operateRuleRewardRelation.getRewardId());
							givenTheStockVo.setStocksOpenDay(operateRuleRewardRelation.getStocksOpenDay());
							commonVo.getGivenTheStockList().add(givenTheStockVo);
						} else {
							commonVo.setStkName(configItem.getAssetId());
							commonVo.setStkQuantity(configItem.getStkNum());
							commonVo.setStockRewardId(operateRuleRewardRelation.getRewardId());
						}
					}

					if (configItem.getRewardType() == RewardTypeEnum.MktINFO_FREE.getValue()) {
						commonVo.setHqRewardId(operateRuleRewardRelation.getRewardId());
						commonVo.setHqRewardName(configItem.getActiveItemName());
					}

					if (configItem.getRewardType() == RewardTypeEnum.CASH_DEDUCTION.getValue()) {

						DeductionReward deductionReward = new DeductionReward();
						// 设置奖品id
						deductionReward.setRewardId(operateRuleRewardRelation.getRewardId());
						// 设置奖品数量
						deductionReward.setRewardNum(operateRuleRewardRelation.getRewardNum());
						// 设置奖品有效天数
						deductionReward.setValidityDay(operateRuleRewardRelation.getValidityDay());
						// 设置奖品金额
						deductionReward.setRewardMoney(configItem.getRewardMoney());
						// 设置奖品名称
						deductionReward.setRewardName(configItem.getActiveItemName());
						arr.add(deductionReward);
					}
				}
				commonVo.setDeductionRewardList(arr);
				list.add(commonVo);
			}
		}

		if (CollectionUtils.isEmpty(list)) {
			RewardRuleCommonVO commonVo = new RewardRuleCommonVO();
			commonVo.setStartAmount(null);
			commonVo.setEndAmount(null);
			commonVo.setRewardCondition("--");
			list.add(commonVo);
		}
		return list;
	}

	/**
	 * 保存存储奖励规则关联关系
	 *
	 * @param operateRuleRewardRelations
	 */
	@Override
	public void saveOperateRuleRewardRelation(List<OperateRuleRewardRelationEntity> operateRuleRewardRelations) {
		operateRuleRewardRelationService.saveOperateRuleRewardRelation(operateRuleRewardRelations);
	}

	/**
	 * 保存奖励方案
	 *
	 * @param operateActPlan
	 * @return
	 */
	@Override
	public OperateActPlanEntity saveOrUpdateOperateActPlan(OperateActPlanEntity operateActPlan) {
		return operateActPlanService.saveOrUpdateOperateActPlan(operateActPlan);
	}
}
