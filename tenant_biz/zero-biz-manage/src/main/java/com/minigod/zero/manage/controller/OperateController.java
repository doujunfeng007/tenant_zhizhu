package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Lists;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.dto.RewardRuleCommonDTO;
import com.minigod.zero.manage.entity.OperateActPlanEntity;
import com.minigod.zero.manage.entity.OperateActRewardPlanEntity;
import com.minigod.zero.manage.entity.OperateRuleRewardRelationEntity;
import com.minigod.zero.manage.enums.ERuleType;
import com.minigod.zero.manage.service.IOperateActPlanService;
import com.minigod.zero.manage.service.IOperateActRewardPlanService;
import com.minigod.zero.manage.service.IOperateRuleRewardRelationService;
import com.minigod.zero.manage.service.IOperateService;
import com.minigod.zero.manage.vo.RewardRuleCommonVO;
import com.minigod.zero.manage.vo.request.OperateRuleRewardRelationReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 奖励方案管理
 *
 * @author eric
 * @since 2024-12-24 16:56:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX +"/operate")
@Api(value = "奖励方案管理", tags = "奖励方案管理")
public class OperateController extends ZeroController {
	private final IOperateService operateService;
	private final IOperateActPlanService operateActPlanService;
	private final IOperateActRewardPlanService operateActRewardPlanService;
	private final IOperateRuleRewardRelationService operateRuleRewardRelationService;

	/**
	 * 查询奖励方案列表
	 *
	 * @param query
	 * @param planId
	 * @param planName
	 * @return
	 */
	@GetMapping("/plan_list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询奖励方案列表", notes = "传入planId、planName")
	public R<IPage<OperateActPlanEntity>> findOperateActPlanPage(Query query, Long planId, String planName) {
		return R.data(operateService.findOperateActPlanPage(Condition.getPage(query), planId, planName));
	}

	/**
	 * 获取更新奖励方案数据对象
	 *
	 * @param planId
	 * @return
	 */
	@GetMapping("/update_plan_ui")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "更新奖励方案", notes = "传入OperateActPlanEntity")
	public R<RewardRuleCommonDTO> findOperateActPlan(Long planId) {
		// 奖励方案信息
		OperateActPlanEntity operateActPlan = operateService.findOperateActPlan(planId);
		// 注册奖励
		List<RewardRuleCommonVO> registerRewardList = operateService.findRewardRule(planId, ERuleType.REGISTER.getTypeCode());
		RewardRuleCommonVO registerReward = new RewardRuleCommonVO();
		if (CollectionUtils.isNotEmpty(registerRewardList)) {
			registerReward = registerRewardList.get(0);
		}
		// 开户奖励
		List<RewardRuleCommonVO> openRewardList = operateService.findRewardRule(planId, ERuleType.OPEN.getTypeCode());
		RewardRuleCommonVO openReward = new RewardRuleCommonVO();
		if (CollectionUtils.isNotEmpty(openRewardList)) {
			openReward = openRewardList.get(0);
		}
		// 邀请开户奖励
		List<RewardRuleCommonVO> invOpenRewardList = operateService.findRewardRule(planId, ERuleType.INVITE_OPEN.getTypeCode());
		RewardRuleCommonVO invOpenReward = new RewardRuleCommonVO();
		if (CollectionUtils.isNotEmpty(invOpenRewardList)) {
			invOpenReward = invOpenRewardList.get(0);
		}
		// 入金奖励
		List<RewardRuleCommonVO> depositReward = operateService.findRewardRule(planId, ERuleType.DEPOSIT.getTypeCode());
		// 邀请入金奖励
		List<RewardRuleCommonVO> invDepositReward = operateService.findRewardRule(planId, ERuleType.INVITE_DEPOSIT.getTypeCode());
		// 转仓奖励
		List<RewardRuleCommonVO> transferReward = operateService.findRewardRule(planId, ERuleType.TRANSFER.getTypeCode());
		// 邀请转仓奖励
		List<RewardRuleCommonVO> invTransferReward = operateService.findRewardRule(planId, ERuleType.INVITE_TRANSFER.getTypeCode());

		RewardRuleCommonDTO rewardRuleCommonDTO = new RewardRuleCommonDTO();
		rewardRuleCommonDTO.setOperateActPlan(operateActPlan);
		rewardRuleCommonDTO.setRegisterReward(registerReward);
		rewardRuleCommonDTO.setOpenReward(openReward);
		rewardRuleCommonDTO.setInvOpenReward(invOpenReward);
		rewardRuleCommonDTO.setDepositReward(depositReward);
		rewardRuleCommonDTO.setInvDepositReward(invDepositReward);
		rewardRuleCommonDTO.setTransferReward(transferReward);
		rewardRuleCommonDTO.setInvTransferReward(invTransferReward);
		return R.data(rewardRuleCommonDTO);
	}

	/**
	 * 保存奖励方案
	 *
	 * @return
	 */
	@PostMapping("/save_plan")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "保存奖励方案", notes = "传入OperateRuleRewardRelationReqVO")
	public R savePlan(@RequestBody OperateRuleRewardRelationReqVO reqVO) {
		try {
			// 新增方案信息
			OperateActPlanEntity operateActPlan = new OperateActPlanEntity();
			operateActPlan.setPlanName(reqVO.getPlanName());
			operateActPlan.setStatus(1);
			operateActPlan.setOpenValidDay(reqVO.getOpenValidDay());
			operateActPlan.setDepositValidDay(reqVO.getDepositValidDay());
			operateActPlan.setTransferValidDay(reqVO.getTransferValidDay());
			operateActPlan.setRewardValidDay(reqVO.getRewardValidDay());
			operateActPlan.setInvOpenValidDay(reqVO.getInvOpenValidDay());
			operateActPlan.setInvDepositValidDay(reqVO.getInvDepositValidDay());
			operateActPlan.setInvTransferValidDay(reqVO.getInvTransferValidDay());
			operateActPlan.setIsBusinessReward(reqVO.getIsBusinessReward());
			OperateActPlanEntity plan = operateService.saveOrUpdateOperateActPlan(operateActPlan);
			Long planId = plan.getId();

			// 生成对应规则的奖品信息
			List<OperateRuleRewardRelationEntity> rewardRelationList = Lists.newArrayList();

			// 注册奖励配置信息处理
			OperateActRewardPlanEntity registerOperateActRewardPlan = new OperateActRewardPlanEntity();
			registerOperateActRewardPlan.setFunType(ERuleType.REGISTER.getTypeCode());
			registerOperateActRewardPlan.setPlanId(planId);
			registerOperateActRewardPlan.setRewardCondition(reqVO.getRegisterRewardCondition());
			OperateActRewardPlanEntity rewardPlan = operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(registerOperateActRewardPlan);

			// 抵扣券奖品
			if (reqVO.getRegisterDeductionRewardIds() == null) {
				reqVO.setRegisterDeductionRewardIds(new Integer[]{});
				reqVO.setRegisterDeductionNums(new Integer[]{});
			}

			for (int i = 0; i < reqVO.getRegisterDeductionRewardIds().length; i++) {
				OperateRuleRewardRelationEntity registerOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
				registerOperateRuleRewardRelation.setRuleId(registerOperateActRewardPlan.getId());
				registerOperateRuleRewardRelation.setRewardId(reqVO.getRegisterDeductionRewardIds()[i].longValue());
				registerOperateRuleRewardRelation.setRewardNum(reqVO.getRegisterDeductionNums()[i]);
				registerOperateRuleRewardRelation.setValidityDay(reqVO.getRegisterDeductionDay());
				rewardRelationList.add(registerOperateRuleRewardRelation);
			}

			// 开户奖励配置信息处理
			OperateActRewardPlanEntity openOperateActRewardPlan = new OperateActRewardPlanEntity();
			openOperateActRewardPlan.setFunType(ERuleType.OPEN.getTypeCode());
			openOperateActRewardPlan.setPlanId(planId);
			openOperateActRewardPlan.setRewardCondition(reqVO.getOpenRewardCondition());
			rewardPlan = operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(openOperateActRewardPlan);

			// 抵扣券奖品
			if (reqVO.getOpenDeductionRewardIds() == null) {
				reqVO.setOpenDeductionRewardIds(new Integer[]{});
				reqVO.setOpenStocksOpenDays(new Integer[]{});
			}
			for (int i = 0; i < reqVO.getOpenDeductionRewardIds().length; i++) {
				OperateRuleRewardRelationEntity openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
				openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
				openOperateRuleRewardRelation.setRewardId(reqVO.getOpenDeductionRewardIds()[i].longValue());
				openOperateRuleRewardRelation.setValidityDay(reqVO.getOpenStocksOpenDays()[i]);
				rewardRelationList.add(openOperateRuleRewardRelation);
			}

			// 免佣奖品
			OperateRuleRewardRelationEntity openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenCommissionRewardId() == null ? null : reqVO.getOpenCommissionRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 赠股奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenStockRewardId() == null ? null : reqVO.getOpenStockRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 现金奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getAccountOpeningCash() == null ? null : reqVO.getAccountOpeningCash().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			//行情
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenHqRewardId() == null ? null : reqVO.getOpenHqRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			//打新
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenDozenNewSecuritiesId() == null ? null : reqVO.getOpenDozenNewSecuritiesId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			operateService.saveOperateRuleRewardRelation(rewardRelationList);

			// 邀请开户奖励配置信息处理
			openOperateActRewardPlan = new OperateActRewardPlanEntity();
			openOperateActRewardPlan.setFunType(ERuleType.INVITE_OPEN.getTypeCode());
			openOperateActRewardPlan.setPlanId(planId);
			openOperateActRewardPlan.setRewardCondition(reqVO.getInvOpenRewardCondition());
			rewardPlan = operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(openOperateActRewardPlan);

			// 生成对应规则的奖品信息
			rewardRelationList = Lists.newArrayList();
			// 免佣奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenCommissionRewardId() == null ? null : reqVO.getInvOpenCommissionRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 现金奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenMoneyRewardId() == null ? null : reqVO.getInvOpenMoneyRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 赠股奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenStockRewardId() == null ? null : reqVO.getInvOpenStockRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			//行情
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenHqRewardId() == null ? null : reqVO.getInvOpenHqRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			//打新
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(rewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenDozenNewSecuritiesId() == null ? null : reqVO.getOpenDozenNewSecuritiesId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);
			operateService.saveOperateRuleRewardRelation(rewardRelationList);

			// 处理入金、邀请入金、转仓、邀请转仓奖励规则
			Integer[] ruleTypes = new Integer[]{ERuleType.DEPOSIT.getTypeCode(), ERuleType.INVITE_DEPOSIT.getTypeCode(), ERuleType.TRANSFER.getTypeCode(), ERuleType.INVITE_TRANSFER.getTypeCode()};
			for (Integer ruleType : ruleTypes) {

				BigDecimal[] startAmounts = null;
				BigDecimal[] endAmounts = null;
				Integer[] commissionRewardIds = null;
				Integer[] moneyRewardIds = null;
				Integer[] stockRewardIds = null;
				String[] rewardConditions = null;
				Integer[] hqRewardIds = null;
				Integer[] dozenNewSecuritiesIds = null;
				Integer[] stockRewardNums = null; //入金,转仓当前行有多少个赠股
				Integer[] stocksOpenDays = null; //赠股留存多少天
				if (ruleType.equals(ERuleType.DEPOSIT.getTypeCode())) {
					startAmounts = reqVO.getDepositStartAmounts();
					endAmounts = reqVO.getDepositEndAmounts();
					commissionRewardIds = reqVO.getDepositCommissionRewardIds();
					moneyRewardIds = reqVO.getDepositMoneyRewardIds();
					stockRewardIds = reqVO.getDepositStockRewardIds();
					rewardConditions = reqVO.getDepositRewardConditions();
					hqRewardIds = reqVO.getDepositHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getDepositDozenNewSecuritiesIds();
					stockRewardNums = reqVO.getDepositStockRewardNums();
					stocksOpenDays = reqVO.getDepositStocksOpenDays();
				}
				if (ruleType.equals(ERuleType.INVITE_DEPOSIT.getTypeCode())) {
					startAmounts = reqVO.getInvDepositStartAmounts();
					endAmounts = reqVO.getInvDepositEndAmounts();
					commissionRewardIds = reqVO.getInvDepositCommissionRewardIds();
					moneyRewardIds = reqVO.getInvDepositMoneyRewardIds();
					stockRewardIds = reqVO.getInvDepositStockRewardIds();
					rewardConditions = reqVO.getInvDepositRewardConditions();
					hqRewardIds = reqVO.getInvDepositHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getInvDepositDozenNewSecuritiesIds();
				}
				if (ruleType.equals(ERuleType.TRANSFER.getTypeCode())) {
					startAmounts = reqVO.getTransferStartAmounts();
					endAmounts = reqVO.getTransferEndAmounts();
					commissionRewardIds = reqVO.getTransferCommissionRewardIds();
					moneyRewardIds = reqVO.getTransferMoneyRewardIds();
					stockRewardIds = reqVO.getTransferStockRewardIds();
					rewardConditions = reqVO.getTransferRewardConditions();
					hqRewardIds = reqVO.getTransferHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getTransferDozenNewSecuritiesIds();
					stockRewardNums = reqVO.getTransferStockRewardNums();
					stocksOpenDays = reqVO.getTransferStocksOpenDays();
				}
				if (ruleType.equals(ERuleType.INVITE_TRANSFER.getTypeCode())) {
					startAmounts = reqVO.getInvTransferStartAmounts();
					endAmounts = reqVO.getInvTransferEndAmounts();
					commissionRewardIds = reqVO.getInvTransferCommissionRewardIds();
					moneyRewardIds = reqVO.getInvTransferMoneyRewardIds();
					stockRewardIds = reqVO.getInvTransferStockRewardIds();
					rewardConditions = reqVO.getInvTransferRewardConditions();
					hqRewardIds = reqVO.getInvTransferHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getInvTransferDozenNewSecuritiesIds();
				}

				// 生成规则和奖励
				List<Long> rewardPlanIds = Lists.newArrayList();
				int tmpCount = 0;
				int sum = 0;
				if (startAmounts == null || startAmounts.length == 0) {
					log.error("请求参数,金额为空!");
					continue;
				}
				for (int i = 0; i < startAmounts.length; i++) {
					BigDecimal startAmount = startAmounts[i];
					BigDecimal endAmount = endAmounts[i];
					OperateActRewardPlanEntity operateActRewardPlan = new OperateActRewardPlanEntity();
					operateActRewardPlan.setFunType(ruleType);
					operateActRewardPlan.setPlanId(planId);
					operateActRewardPlan.setStartAmount(startAmount);
					operateActRewardPlan.setEndAmount(endAmount);
					operateActRewardPlan.setRewardCondition(rewardConditions[i]);
					rewardPlanIds.add(operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(operateActRewardPlan).getId());
				}
				rewardRelationList = Lists.newArrayList();
				for (int i = 0; i < rewardPlanIds.size(); i++) {
					Long ruleId = rewardPlanIds.get(i);
					// 免佣奖品
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (commissionRewardIds != null && commissionRewardIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(commissionRewardIds[i] == null ? null : commissionRewardIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);

					// 现金奖品
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (moneyRewardIds != null && moneyRewardIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(moneyRewardIds[i] == null ? null : moneyRewardIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);

					//判断是否是入金或转仓
					if (ruleType.equals(ERuleType.DEPOSIT.getTypeCode()) || ruleType.equals(ERuleType.TRANSFER.getTypeCode())) {
						// 获取当前行有多少个赠股
						if (stockRewardIds != null && stockRewardIds.length > 0 && stockRewardNums != null && stockRewardNums.length > 0) {
							sum = sum + stockRewardNums[i];
							int inx = sum;
							for (int srId = tmpCount; srId < inx; srId++) {
								// 赠股奖品
								openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
								openOperateRuleRewardRelation.setRuleId(ruleId);
								openOperateRuleRewardRelation.setRewardId(stockRewardIds[srId] == null ? null : stockRewardIds[srId].longValue());
								if (stocksOpenDays != null && stocksOpenDays.length > 0) {
									if (stockRewardIds[srId] != null && stocksOpenDays[srId] == null) {
										log.error("请求参数,留存天数为空!");
									} else if (stocksOpenDays[srId] != null && stockRewardIds[srId] == null) {
										log.error("请求参数,股票为空!");
									} else if (stocksOpenDays[srId] != null && stocksOpenDays[srId] > 1000) {
										log.error("请求参数,留存天数不能大于1000!");
									}
									openOperateRuleRewardRelation.setStocksOpenDay(stocksOpenDays[srId] == null ? null : stocksOpenDays[srId]);
								}
								rewardRelationList.add(openOperateRuleRewardRelation);
								tmpCount++;
							}
						} else {
							// 赠股奖品
							openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
							openOperateRuleRewardRelation.setRuleId(ruleId);
							openOperateRuleRewardRelation.setRewardId(null);
							openOperateRuleRewardRelation.setStocksOpenDay(null);
							rewardRelationList.add(openOperateRuleRewardRelation);
						}
					} else {
						// 赠股奖品
						openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
						openOperateRuleRewardRelation.setRuleId(ruleId);
						if (stockRewardIds != null && stockRewardIds.length > 0) {
							openOperateRuleRewardRelation.setRewardId(stockRewardIds[i] == null ? null : stockRewardIds[i].longValue());
						} else {
							openOperateRuleRewardRelation.setRewardId(null);
						}
						rewardRelationList.add(openOperateRuleRewardRelation);
					}

					//行情
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (hqRewardIds != null && hqRewardIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(hqRewardIds[i] == null ? null : hqRewardIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}

					rewardRelationList.add(openOperateRuleRewardRelation);

					//打新
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (dozenNewSecuritiesIds != null && dozenNewSecuritiesIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(dozenNewSecuritiesIds[i] == null ? null : dozenNewSecuritiesIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);
				}
				operateService.saveOperateRuleRewardRelation(rewardRelationList);
			}
		} catch (Exception e) {
			log.error("保存奖励方案异常,异常详情->", e);
			return R.fail();
		}
		return R.success();
	}

	/**
	 * 更新奖励方案
	 *
	 * @return
	 */
	@PutMapping("/update_plan")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "更新奖励方案", notes = "传入OperateRuleRewardRelationReqVO")
	public R updatePlan(@RequestBody OperateRuleRewardRelationReqVO reqVO) {
		try {
			// 修改方案信息
			OperateActPlanEntity operateActPlan = operateService.findOperateActPlan(reqVO.getPlanId());
			operateActPlan.setPlanName(reqVO.getPlanName());
			operateActPlan.setOpenValidDay(reqVO.getOpenValidDay());
			operateActPlan.setDepositValidDay(reqVO.getDepositValidDay());
			operateActPlan.setTransferValidDay(reqVO.getTransferValidDay());
			operateActPlan.setRewardValidDay(reqVO.getRewardValidDay());
			operateActPlan.setInvOpenValidDay(reqVO.getInvOpenValidDay());
			operateActPlan.setInvDepositValidDay(reqVO.getInvDepositValidDay());
			operateActPlan.setInvTransferValidDay(reqVO.getInvTransferValidDay());
			operateActPlan.setIsBusinessReward(reqVO.getIsBusinessReward());
			operateActPlan.setIsTransferReward(reqVO.getIsTransferReward());
			operateActPlanService.saveOrUpdateOperateActPlan(operateActPlan);

			// 生成对应规则的奖品信息
			List<OperateRuleRewardRelationEntity> rewardRelationList = Lists.newArrayList();

			// 注册奖励配置信息处理 历史数据不存在注册奖
			List<OperateActRewardPlanEntity> rewardPlanList = operateActRewardPlanService.findOperateActRewardPlanList(reqVO.getPlanId(), ERuleType.REGISTER.getTypeCode());
			OperateActRewardPlanEntity registerOperateActRewardPlan;
			if (CollectionUtils.isNotEmpty(rewardPlanList)) {
				registerOperateActRewardPlan = rewardPlanList.get(0);
			} else {
				registerOperateActRewardPlan = new OperateActRewardPlanEntity();
				registerOperateActRewardPlan.setPlanId(reqVO.getPlanId());
				registerOperateActRewardPlan.setFunType(ERuleType.REGISTER.getTypeCode());
			}
			registerOperateActRewardPlan.setRewardCondition(reqVO.getRegisterRewardCondition());
			operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(registerOperateActRewardPlan);
			// 删除对应规则的奖品信息
			operateRuleRewardRelationService.deleteOperateRuleRewardRelation(new Long[]{registerOperateActRewardPlan.getId()});

			// 抵扣券奖品
			if (reqVO.getRegisterDeductionRewardIds() == null) {
				reqVO.setRegisterDeductionRewardIds(new Integer[]{});
				reqVO.setRegisterDeductionNums(new Integer[]{});
			}
			for (int i = 0; i < reqVO.getRegisterDeductionRewardIds().length; i++) {
				OperateRuleRewardRelationEntity registerOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
				registerOperateRuleRewardRelation.setRuleId(registerOperateActRewardPlan.getId());
				registerOperateRuleRewardRelation.setRewardId(reqVO.getRegisterDeductionRewardIds()[i].longValue());
				registerOperateRuleRewardRelation.setRewardNum(reqVO.getRegisterDeductionNums()[i]);
				registerOperateRuleRewardRelation.setValidityDay(reqVO.getRegisterDeductionDay());
				rewardRelationList.add(registerOperateRuleRewardRelation);
			}

			// 开户奖励配置信息处理
			OperateActRewardPlanEntity openOperateActRewardPlan = operateActRewardPlanService.findOperateActRewardPlanList(reqVO.getPlanId(), ERuleType.OPEN.getTypeCode()).get(0);
			openOperateActRewardPlan.setRewardCondition(reqVO.getOpenRewardCondition());
			OperateActRewardPlanEntity rewardPlan = operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(openOperateActRewardPlan);
			// 删除对应规则的奖品信息
			operateRuleRewardRelationService.deleteOperateRuleRewardRelation(new Long[]{openOperateActRewardPlan.getId()});

			// 抵扣券奖品
			if (reqVO.getOpenDeductionRewardIds() == null) {
				reqVO.setOpenDeductionRewardIds(new Integer[]{});
			}
			for (int i = 0; i < reqVO.getOpenDeductionRewardIds().length; i++) {
				if (reqVO.getOpenDeductionNums() != null && reqVO.getOpenDeductionNums().length > 0) {
					OperateRuleRewardRelationEntity openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
					openOperateRuleRewardRelation.setRewardId(reqVO.getOpenDeductionRewardIds()[i].longValue());
					openOperateRuleRewardRelation.setRewardNum(reqVO.getOpenDeductionNums()[i]);
					openOperateRuleRewardRelation.setValidityDay(reqVO.getOpenDeductionDay());
					rewardRelationList.add(openOperateRuleRewardRelation);
				}
			}
			// 免佣奖品
			OperateRuleRewardRelationEntity openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenCommissionRewardId() == null ? null : reqVO.getOpenCommissionRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 赠股奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenStockRewardId() == null ? null : reqVO.getOpenStockRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 打新劵奖品
			//openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			//openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
			//openOperateRuleRewardRelation.setRewardId(reqVO.getOpenDozenNewSecuritiesId() == null ? null : reqVO.getOpenDozenNewSecuritiesId().longValue());
			//rewardRelationList.add(openOperateRuleRewardRelation);

			// 现金奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getAccountOpeningCash() == null ? null : reqVO.getAccountOpeningCash().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			//行情
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getOpenHqRewardId() == null ? null : reqVO.getOpenHqRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			operateService.saveOperateRuleRewardRelation(rewardRelationList);


			// 邀请开户奖励配置信息处理
			OperateActRewardPlanEntity invOpenOperateActRewardPlan = operateActRewardPlanService.findOperateActRewardPlanList(reqVO.getPlanId(), ERuleType.INVITE_OPEN.getTypeCode()).get(0);
			invOpenOperateActRewardPlan.setRewardCondition(reqVO.getInvOpenRewardCondition());
			operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(invOpenOperateActRewardPlan);
			// 删除对应规则的奖品信息
			operateRuleRewardRelationService.deleteOperateRuleRewardRelation(new Long[]{invOpenOperateActRewardPlan.getId()});


			// 生成对应规则的奖品信息
			rewardRelationList = Lists.newArrayList();
			// 免佣奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(invOpenOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenCommissionRewardId() == null ? null : reqVO.getInvOpenCommissionRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 现金奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(invOpenOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenMoneyRewardId() == null ? null : reqVO.getInvOpenMoneyRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 赠股奖品
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(invOpenOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenStockRewardId() == null ? null : reqVO.getInvOpenStockRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			//行情
			openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
			openOperateRuleRewardRelation.setRuleId(invOpenOperateActRewardPlan.getId());
			openOperateRuleRewardRelation.setRewardId(reqVO.getInvOpenHqRewardId() == null ? null : reqVO.getInvOpenHqRewardId().longValue());
			rewardRelationList.add(openOperateRuleRewardRelation);

			// 抵扣券奖品
            /*
			if (reqVO.getOpenDeductionRewardIds() == null) {
				reqVO.setOpenDeductionRewardIds(new Integer[]{});
                reqVO.setOpenDeductionNums(new Integer[]{});
            }
            openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
            for (int i = 0; i < reqVO.getOpenDeductionRewardIds().length; i++) {
                if (reqVO.getOpenDeductionNums() != null && reqVO.getOpenDeductionNums().length > 0){
                    openOperateRuleRewardRelation.setRuleId(openOperateActRewardPlan.getId());
                    openOperateRuleRewardRelation.setRewardId(reqVO.getOpenDeductionRewardIds()[i].longValue());
                    openOperateRuleRewardRelation.setRewardNum(reqVO.getOpenDeductionNums()[i]);
                    openOperateRuleRewardRelation.setValidityDay(reqVO.getOpenDeductionDay());
                    rewardRelationList.add(openOperateRuleRewardRelation);
                }
            }*/

			operateService.saveOperateRuleRewardRelation(rewardRelationList);


			// 处理入金、邀请入金、转仓、邀请转仓奖励规则
			Integer[] ruleTypes = new Integer[]{ERuleType.DEPOSIT.getTypeCode(), ERuleType.INVITE_DEPOSIT.getTypeCode(), ERuleType.TRANSFER.getTypeCode(), ERuleType.INVITE_TRANSFER.getTypeCode()};
			for (Integer ruleType : ruleTypes) {
				operateActRewardPlanService.deleteOperateActRewardPlan(reqVO.getPlanId(), ruleType);

				BigDecimal[] startAmounts = null;
				BigDecimal[] endAmounts = null;
				Integer[] commissionRewardIds = null;
				Integer[] moneyRewardIds = null;
				Integer[] stockRewardIds = null;
				String[] rewardConditions = null;
				Integer[] hqRewardIds = null;
				Integer[] dozenNewSecuritiesIds = null;
				Integer[] stockRewardNums = null;
				Integer[] stocksOpenDays = null;
				if (ruleType.equals(ERuleType.DEPOSIT.getTypeCode())) {
					startAmounts = reqVO.getDepositStartAmounts();
					endAmounts = reqVO.getDepositEndAmounts();
					commissionRewardIds = reqVO.getDepositCommissionRewardIds();
					moneyRewardIds = reqVO.getDepositMoneyRewardIds();
					stockRewardIds = reqVO.getDepositStockRewardIds();
					rewardConditions = reqVO.getDepositRewardConditions();
					hqRewardIds = reqVO.getDepositHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getDepositDozenNewSecuritiesIds();
					stockRewardNums = reqVO.getDepositStockRewardNums();
					stocksOpenDays = reqVO.getDepositStocksOpenDays();
				}
				if (ruleType.equals(ERuleType.INVITE_DEPOSIT.getTypeCode())) {
					startAmounts = reqVO.getInvDepositStartAmounts();
					endAmounts = reqVO.getInvDepositEndAmounts();
					commissionRewardIds = reqVO.getInvDepositCommissionRewardIds();
					moneyRewardIds = reqVO.getInvDepositMoneyRewardIds();
					stockRewardIds = reqVO.getInvDepositStockRewardIds();
					rewardConditions = reqVO.getInvDepositRewardConditions();
					hqRewardIds = reqVO.getInvDepositHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getInvDepositDozenNewSecuritiesIds();
				}
				if (ruleType.equals(ERuleType.TRANSFER.getTypeCode())) {
					startAmounts = reqVO.getTransferStartAmounts();
					endAmounts = reqVO.getTransferEndAmounts();
					commissionRewardIds = reqVO.getTransferCommissionRewardIds();
					moneyRewardIds = reqVO.getTransferMoneyRewardIds();
					stockRewardIds = reqVO.getTransferStockRewardIds();
					rewardConditions = reqVO.getTransferRewardConditions();
					hqRewardIds = reqVO.getTransferHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getTransferDozenNewSecuritiesIds();
					stockRewardNums = reqVO.getTransferStockRewardNums();
					stocksOpenDays = reqVO.getTransferStocksOpenDays();
				}
				if (ruleType.equals(ERuleType.INVITE_TRANSFER.getTypeCode())) {
					startAmounts = reqVO.getInvTransferStartAmounts();
					endAmounts = reqVO.getInvTransferEndAmounts();
					commissionRewardIds = reqVO.getInvTransferCommissionRewardIds();
					moneyRewardIds = reqVO.getInvTransferMoneyRewardIds();
					stockRewardIds = reqVO.getInvTransferStockRewardIds();
					rewardConditions = reqVO.getInvTransferRewardConditions();
					hqRewardIds = reqVO.getInvTransferHqRewardIds();
					dozenNewSecuritiesIds = reqVO.getInvTransferDozenNewSecuritiesIds();
				}

				List<OperateActRewardPlanEntity> operateActRewardPlanList = operateActRewardPlanService.findOperateActRewardPlanList(rewardPlan.getPlanId(), ruleType);
				List<Long> ruleIds = Lists.newArrayList();
				for (OperateActRewardPlanEntity operateActRewardPlan : operateActRewardPlanList) {
					ruleIds.add(operateActRewardPlan.getId());
				}
				// 删除对应方案规则的奖品信息
				if (CollectionUtils.isNotEmpty(ruleIds)) {
					operateRuleRewardRelationService.deleteOperateRuleRewardRelation(ruleIds.toArray(new Long[ruleIds.size()]));
				}

				// 生成入金规则
				List<Long> rewardPlanIds = Lists.newArrayList();

				if (startAmounts == null || startAmounts.length == 0) {
					log.error("请求参数, 金额不能为空!");
					continue;
				}
				for (int i = 0; i < startAmounts.length; i++) {
					BigDecimal startAmount = startAmounts[i];
					BigDecimal endAmount = endAmounts[i];
					if (startAmount.toString().length() > 11 || endAmount.toString().length() > 11) {
						log.error("请求参数, 金额长度不能大于11位!");
						continue;
					}
					OperateActRewardPlanEntity operateActRewardPlan = new OperateActRewardPlanEntity();
					operateActRewardPlan.setFunType(ruleType);
					operateActRewardPlan.setPlanId(rewardPlan.getPlanId());
					operateActRewardPlan.setStartAmount(startAmount);
					operateActRewardPlan.setEndAmount(endAmount);
					operateActRewardPlan.setRewardCondition(rewardConditions[i]);
					rewardPlanIds.add(operateActRewardPlanService.saveOrUpdateOperateActRewardPlan(operateActRewardPlan).getId());
				}
				rewardRelationList = Lists.newArrayList();
				Integer sum = 0;
				Integer tmpCount = 0;
				for (int i = 0; i < rewardPlanIds.size(); i++) {
					Long ruleId = rewardPlanIds.get(i);
					// 免佣奖品
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (commissionRewardIds != null && commissionRewardIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(commissionRewardIds[i] == null ? null : commissionRewardIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);

					// 现金奖品
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (moneyRewardIds != null && moneyRewardIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(moneyRewardIds[i] == null ? null : moneyRewardIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);

					// 赠股奖品
					//判断是否是入金或转仓
					if (ruleType.equals(ERuleType.DEPOSIT.getTypeCode()) || ruleType.equals(ERuleType.TRANSFER.getTypeCode())) {
						// 获取当前行有多少个赠股

						if (stockRewardIds != null && stockRewardIds.length > 0 && stockRewardNums != null && stockRewardNums.length > 0) {
							sum = sum + stockRewardNums[i];
							int inx = sum;
							for (int srId = tmpCount; srId < inx; srId++) {
								// 赠股奖品
								openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
								openOperateRuleRewardRelation.setRuleId(ruleId);
								openOperateRuleRewardRelation.setRewardId(stockRewardIds[srId] == null ? null : stockRewardIds[srId].longValue());
								if (stocksOpenDays != null && stocksOpenDays.length > 0) {
									if (stockRewardIds[srId] != null && stocksOpenDays[srId] == null) {
										log.error("请求参数, 留存天数不能为空!");
										continue;
									}
									if (stocksOpenDays[srId] != null && stockRewardIds[srId] == null) {
										log.error("请求参数, 股票不能为空!");
										continue;
									}
									if (stocksOpenDays[srId] != null && stocksOpenDays[srId] > 1000) {
										log.error("请求参数, 留存天数不能大于1000!");
										continue;
									}
									openOperateRuleRewardRelation.setStocksOpenDay(stocksOpenDays[srId] == null ? null : stocksOpenDays[srId]);
								}
								rewardRelationList.add(openOperateRuleRewardRelation);
								tmpCount++;
							}
						} else {
							// 赠股奖品
							openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
							openOperateRuleRewardRelation.setRuleId(ruleId);
							openOperateRuleRewardRelation.setRewardId(null);
							openOperateRuleRewardRelation.setStocksOpenDay(null);
							rewardRelationList.add(openOperateRuleRewardRelation);
						}
					} else {
						openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
						openOperateRuleRewardRelation.setRuleId(ruleId);
						if (stockRewardIds != null && stockRewardIds.length > 0) {
							openOperateRuleRewardRelation.setRewardId(stockRewardIds[i] == null ? null : stockRewardIds[i].longValue());
						} else {
							openOperateRuleRewardRelation.setRewardId(null);
						}
						rewardRelationList.add(openOperateRuleRewardRelation);
					}

					//行情
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (hqRewardIds != null && hqRewardIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(hqRewardIds[i] == null ? null : hqRewardIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);

					//打新
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (dozenNewSecuritiesIds != null && dozenNewSecuritiesIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(dozenNewSecuritiesIds[i] == null ? null : dozenNewSecuritiesIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);

					//现金抵用券
					openOperateRuleRewardRelation = new OperateRuleRewardRelationEntity();
					openOperateRuleRewardRelation.setRuleId(ruleId);
					if (dozenNewSecuritiesIds != null && dozenNewSecuritiesIds.length > 0) {
						openOperateRuleRewardRelation.setRewardId(dozenNewSecuritiesIds[i] == null ? null : dozenNewSecuritiesIds[i].longValue());
					} else {
						openOperateRuleRewardRelation.setRewardId(null);
					}
					rewardRelationList.add(openOperateRuleRewardRelation);
				}
				operateService.saveOperateRuleRewardRelation(rewardRelationList);
			}
		} catch (Exception e) {
			log.error("更新奖励方案异常,异常详情->", e);
			return R.fail();
		}
		return R.success();
	}
}
