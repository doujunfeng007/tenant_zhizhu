package com.minigod.zero.manage.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.HqPackageInfoEntity;
import com.minigod.zero.manage.entity.SnActiveConfigItemEntity;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;
import com.minigod.zero.manage.enums.RewardStatusEnum;
import com.minigod.zero.manage.enums.RewardTypeEnum;
import com.minigod.zero.manage.service.*;
import com.minigod.zero.manage.vo.*;
import com.minigod.zero.manage.vo.request.GiftSearchReqVO;
import com.minigod.zero.manage.vo.request.GiveOutSearchReqVO;
import com.minigod.zero.manage.vo.request.RewardSearchReqVO;
import com.minigod.zero.manage.dto.SnActiveConfigItemDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 奖品管理
 *
 * @author eric
 * @since 2024-12-24 16:56:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/reward")
@Api(value = "奖品管理", tags = "奖品管理")
public class RewardController extends ZeroController {
	private final IRewardService rewardService;
	private final IOperateActRewardPlanService rewardPlanService;
	private final IOperateActPlanService actPlanService;
	private final IUserChannelInfoService userChannelInfoService;
	private final IChannelGiftService channelGiftService;
	private final ISnActiveConfigItemService snActiveConfigItemService;

	/**
	 * 发送奖品，选择奖品时的信息列表
	 *
	 * @param searchVO
	 * @param query
	 * @return
	 */
	@GetMapping("/reward_list")
	@ApiOperationSupport(order = 0)
	@ApiOperation(value = "查询奖品列表", notes = "传入searchVO")
	public R<IPage<SnActiveConfigItemVO>> rewardData(RewardSearchReqVO searchVO, Query query) {
		IPage<SnActiveConfigItemVO> pages = snActiveConfigItemService.queryRewardList(Condition.getPage(query), searchVO);
		if (pages != null && CollectionUtils.isNotEmpty(pages.getRecords())) {
			for (SnActiveConfigItemVO vo : pages.getRecords()) {
				if (vo.getRewardType() == RewardTypeEnum.HANDSEL_STOCK.getValue()) {
					vo.setStkName(vo.getAssetId());
				}
			}
		}
		return R.data(pages);
	}

	/**
	 * 查询免佣奖励信息
	 */
	@GetMapping("/reward_pool_list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询免佣奖励信息", notes = "传入searchVO")
	public R<IPage<SnActiveConfigItemVO>> findRewardRecord(RewardSearchReqVO searchVO, Query query) {
		IPage<SnActiveConfigItemVO> pages = snActiveConfigItemService.queryRewardList(Condition.getPage(query), searchVO);
		return R.data(pages);
	}

	/**
	 * 奖品发放列表查询
	 *
	 * @param searchVO
	 * @param query
	 * @return
	 */
	@GetMapping("/reward_give_out_list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询奖励信息", notes = "传入searchVO")
	public R<GiveOutQueryResultVO> findGiveOutRecord(GiveOutSearchReqVO searchVO, Query query) {
		GiveOutQueryResultVO result = new GiveOutQueryResultVO();
		IPage<RewardGiveOutVO> pages = rewardService.queryGiveOutList(query, searchVO);
		RewardSearchReqVO searchReqVO = new RewardSearchReqVO();
		searchReqVO.setStatus(RewardStatusEnum.DISPLAY.getVaule());
		List<SnActiveConfigItemEntity> rewardList = snActiveConfigItemService.queryRewardList(searchReqVO);
		result.setPage(pages);
		result.setRewardList(rewardList);
		return R.data(result);
	}

	/**
	 * 奖品详情
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "奖品详情", notes = "传入奖品ID")
	public R<SnActiveConfigItemDTO> detail(Long id) {
		SnActiveConfigItemEntity vo = snActiveConfigItemService.findReward(id);
		HqPackageInfoEntity packageInfo = rewardService.getPackagesByPK(vo.getPackageId());
		SnActiveConfigItemVO dbVO = null;
		if (vo != null) {
			dbVO = new SnActiveConfigItemVO();
			BeanUtils.copyProperties(vo, dbVO);
		}

		SnActiveConfigItemDTO dto = new SnActiveConfigItemDTO();
		dto.setSnActiveConfigItem(dbVO);
		dto.setHqPackageInfo(packageInfo);
		return R.data(dto);
	}

	/**
	 * 奖品修改/新增
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping("/save_update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "奖品修改/新增", notes = "奖品数据模型")
	public R saveOrUpdate(@RequestBody SnActiveConfigItemVO vo) {
		if (vo == null) {
			return R.fail("参数错误");
		}
		if (vo.getId() == null) {
			snActiveConfigItemService.saveReward(vo);
		} else {
			snActiveConfigItemService.updateReward(vo);
		}
		return R.success("保存成功");
	}

	/**
	 * 奖品上下架操作
	 *
	 * @param arrayJson
	 * @return
	 */
	@PutMapping("/save_display_status")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "奖品上架/下架", notes = "传入奖品类型:1.免佣 3.行情 2.现金")
	public R saveDisplayStatus(String arrayJson) {
		//修改上下架状态
		Map<String, Object> changeMap = (Map) JSON.parse(arrayJson);
		for (String id : changeMap.keySet()) {
			snActiveConfigItemService.updateStatus(Long.valueOf(id), Integer.valueOf((String) changeMap.get(id)));
		}
		return R.success("保存成功");
	}

	/**
	 * 删除奖品
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete_reward")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "删除奖品", notes = "传入奖品ID")
	public R deleteReward(Long id) {
		snActiveConfigItemService.deleteReward(id);
		return R.success("删除成功");
	}

	/**
	 * 渠道礼包详情
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/channel_gift_detail")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "渠道礼包详情", notes = "传入ID")
	public R<ChannelGiftQueryResultVO> channelGiftDetail(Long id) {
		ChannelGiftQueryResultVO resultVO = new ChannelGiftQueryResultVO();
		try {
			resultVO.setChannelGift(rewardService.getSnChannelGift(id.longValue()));
			/** 方案对应的规则列表 **/
			resultVO.setRuleRewards(rewardPlanService.getRuleRewardListByPlan(resultVO.getChannelGift().getPlanId(), 2));
			resultVO.setPlanList(actPlanService.getPlanLists(1));//1.有效
		} catch (Exception e) {
			log.error("查看渠道礼包信息异常->", e);
			return R.fail("查看渠道礼包信息异常!");
		}
		/** 规则范围做一次转换 **/
		if (CollectionUtils.isNotEmpty(resultVO.getRuleRewards())) {
			for (RuleRewardVO ruleRewardVO : resultVO.getRuleRewards()) {
				if (ruleRewardVO.getStartAmount() != null) {
					ruleRewardVO.setStartAmountStr(String.format("%.0f", ruleRewardVO.getStartAmount()));
				}
				if (ruleRewardVO.getEndAmount() != null) {
					ruleRewardVO.setEndAmountStr(String.format("%.0f", ruleRewardVO.getEndAmount()));
				}
			}
		}
		return R.data(resultVO);
	}

	/**
	 * 查询渠道礼包列表
	 *
	 * @param searchVO
	 * @param query
	 * @return
	 */
	@GetMapping("/channel_gift_list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "查询渠道礼包列表", notes = "传入searchVO")
	public R<IPage<ChannelGiftVO>> channelGiftList(GiftSearchReqVO searchVO, Query query) {
		IPage<ChannelGiftVO> pages = rewardService.queryChannelGiftList(Condition.getPage(query), searchVO);
		return R.data(pages);
	}

	/**
	 * 查询渠道礼包详情信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/save_update_gift_ui")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询渠道礼包详情信息", notes = "传入ID")
	public R<GiftQueryResultVO> querySaveOrUpdateGift(Long id) {
		GiftQueryResultVO resultVO = new GiftQueryResultVO();
		try {
			if (id != null) {
				resultVO.setChannelGift(rewardService.getSnChannelGift(id.longValue()));
				/** 方案对应的规则列表 **/
				resultVO.setRuleRewards(rewardPlanService.getRuleRewardListByPlan(resultVO.getChannelGift().getPlanId(), 2));
			} else {
				resultVO.setRuleRewards(rewardPlanService.getRuleRewardListByPlan(1L, 2));//默认方案入金奖励规则
			}
			/** 方案列表 **/
			resultVO.setPlanList(actPlanService.getPlanLists(1));//1.有效
			resultVO.setChannelList(userChannelInfoService.getChannelList());
		} catch (Exception e) {
			log.error("查询渠道礼包详情信息异常->", e);
		}
		/** 规则范围做一次转换 **/
		if (CollectionUtils.isNotEmpty(resultVO.getRuleRewards())) {
			for (RuleRewardVO ruleRewardVO : resultVO.getRuleRewards()) {
				if (ruleRewardVO.getStartAmount() != null) {
					ruleRewardVO.setStartAmountStr(String.format("%.0f", ruleRewardVO.getStartAmount()));
				}
				if (ruleRewardVO.getEndAmount() != null) {
					ruleRewardVO.setEndAmountStr(String.format("%.0f", ruleRewardVO.getEndAmount()));
				}
			}
		}
		return R.data(resultVO);
	}

	/**
	 * 渠道礼包修改/新增
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping("/save_update_gift")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "渠道礼包修改/新增", notes = "渠道礼包数据模型")
	public R saveOrUpdateGift(@RequestBody SnChannelGiftEntity vo) {
		vo.setRewardOpen(true);
		this.checkChannelGift(vo);
		if (vo.getId() == null) {
			rewardService.saveChannelGift(vo);
		} else {
			rewardService.updateChannelGift(vo);
		}
		return R.success("保存成功");
	}

	/**
	 * 渠道礼包上架/下架
	 *
	 * @param arrayJson
	 * @return
	 */
	@PutMapping("/save_gift_display_status")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "渠道礼包上架/下架", notes = "传入渠道礼包ID")
	public R saveGiftDisplayStatus(String arrayJson) {
		Map<String, Object> changeMap = (Map) JSON.parse(arrayJson);
		for (String id : changeMap.keySet()) {
			channelGiftService.updateGiftStatus(Long.valueOf(id), Integer.valueOf((String) changeMap.get(id)));
		}
		return R.success("保存成功");
	}

	/**
	 * 删除渠道礼包
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete_channel_gift")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "删除渠道礼包", notes = "传入渠道礼包ID")
	public R deleteChannelGift(Long id) {
		channelGiftService.deleteChannelGift(id);
		return R.success("删除成功");
	}

	/**
	 * 渠道礼包修改/新增参数校验
	 *
	 * @param vo
	 */
	private void checkChannelGift(SnChannelGiftEntity vo) {
		if (StringUtils.isEmpty(vo.getChannelId())) {
			throw new ServiceException("请选择渠道");
		}
		if (StringUtils.isEmpty(vo.getGiftName())) {
			throw new ServiceException("请填写渠道礼包名称");
		}
		if (StringUtils.isEmpty(vo.getChannelPwd())) {
			throw new ServiceException("请填写渠道礼包密码");
		}
		if (org.apache.commons.lang3.StringUtils.length(vo.getChannelPwd()) > 20) {
			throw new ServiceException("密码长度不能超过20位");
		}
//		if (CommonUtil.isSpecialChar(vo.getChannelPwd())) {
//			throw new ServiceException("密码不能包含特殊字符");
//		}
		if (vo.getRewardOpen() == null) {
			throw new ServiceException("请选择是否开启入金奖励");
		}
	}
}
