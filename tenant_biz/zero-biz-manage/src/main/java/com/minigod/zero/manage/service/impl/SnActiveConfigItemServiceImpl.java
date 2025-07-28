package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnActiveConfigItemEntity;
import com.minigod.zero.manage.enums.RewardStatusEnum;
import com.minigod.zero.manage.enums.RewardTypeEnum;
import com.minigod.zero.manage.enums.SubRewardTypeEnum;
import com.minigod.zero.manage.vo.SnActiveConfigItemVO;
import com.minigod.zero.manage.vo.request.RewardSearchReqVO;
import com.minigod.zero.manage.mapper.SnActiveConfigItemMapper;
import com.minigod.zero.manage.service.ISnActiveConfigItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动营销奖品库服务接口实现
 *
 * @author eric
 * @since 2024-12-25 17:23:08
 */
@Slf4j
@Service
public class SnActiveConfigItemServiceImpl extends BaseServiceImpl<SnActiveConfigItemMapper, SnActiveConfigItemEntity> implements ISnActiveConfigItemService {
	private final SnActiveConfigItemMapper snActiveConfigItemMapper;

	public SnActiveConfigItemServiceImpl(SnActiveConfigItemMapper snActiveConfigItemMapper) {
		this.snActiveConfigItemMapper = snActiveConfigItemMapper;
	}

	/**
	 * 分页查询奖品配置
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	@Override
	public IPage<SnActiveConfigItemVO> queryRewardList(IPage<SnActiveConfigItemEntity> page, RewardSearchReqVO searchReqVO) {
		IPage<SnActiveConfigItemEntity> entityPage = snActiveConfigItemMapper.selectPage(page, buildQueryWrapper(searchReqVO));
		IPage<SnActiveConfigItemVO> voPage = entityPage.convert(entity -> {
			SnActiveConfigItemVO vo = new SnActiveConfigItemVO();
			BeanUtils.copyProperties(entity, vo);
			return vo;
		});
		return voPage;
	}

	/**
	 * 查询奖品配置列表
	 *
	 * @param searchReqVO
	 * @return
	 */
	@Override
	public List<SnActiveConfigItemEntity> queryRewardList(RewardSearchReqVO searchReqVO) {
		LambdaQueryWrapper<SnActiveConfigItemEntity> lqw = buildQueryWrapper(searchReqVO);
		return snActiveConfigItemMapper.selectList(lqw);
	}

	/**
	 * 查询奖品配置详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SnActiveConfigItemEntity getRewardByPk(Long id) {
		LambdaQueryWrapper<SnActiveConfigItemEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnActiveConfigItemEntity::getId, id);
		return snActiveConfigItemMapper.selectOne(lqw);
	}

	/**
	 * 新增奖品配置
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean saveActiveConfigItem(SnActiveConfigItemEntity entity) {
		boolean result = this.save(entity);
		if (result) {
			log.info("新增奖品配置成功, 主键ID:{}", entity.getId());
		} else {
			log.info("新增奖品配置失败, 主键ID:{}", entity.getId());
		}
		return result;
	}

	/**
	 * 更新奖品配置
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean updateActiveConfigItem(SnActiveConfigItemEntity entity) {
		boolean result = this.updateById(entity);
		if (result) {
			log.info("更新奖品配置成功, 主键ID:{}", entity.getId());
		} else {
			log.info("更新奖品配置失败, 主键ID:{}", entity.getId());
		}
		return result;
	}

	/**
	 * 批量更新奖品配置
	 *
	 * @param entityList
	 * @return
	 */
	@Override
	public boolean updateActiveConfigItems(List<SnActiveConfigItemEntity> entityList) {
		boolean result = this.updateBatchById(entityList, 100);
		if (result) {
			log.info("批量更新奖品配置成功, 主键ID:{}", entityList.stream().map(SnActiveConfigItemEntity::getId).collect(Collectors.toList()));
		} else {
			log.info("批量更新奖品配置失败, 主键ID:{}", entityList.stream().map(SnActiveConfigItemEntity::getId).collect(Collectors.toList()));
		}
		return result;
	}

	/**
	 * 更新奖品配置状态
	 *
	 * @param id
	 * @param status
	 */
	@Override
	public void updateStatus(Long id, Integer status) {
		LambdaQueryWrapper<SnActiveConfigItemEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnActiveConfigItemEntity::getId, id);
		SnActiveConfigItemEntity snActiveConfigItem = snActiveConfigItemMapper.selectOne(lqw);
		if (snActiveConfigItem != null) {
			snActiveConfigItem.setStatus(status);
			int rows = snActiveConfigItemMapper.updateById(snActiveConfigItem);
			if (rows > 0) {
				log.info("更新奖品配置状态成功, 主键ID:{}, 状态:{}", snActiveConfigItem.getId(), status);
			} else {
				log.info("更新奖品配置状态失败, 主键ID:{}, 状态:{}", snActiveConfigItem.getId(), status);
			}
		}
	}

	/**
	 * 更新奖品配置
	 *
	 * @param snActiveConfigItem
	 */
	@Override
	public void updateReward(SnActiveConfigItemVO snActiveConfigItem) {
		this.checkReward(snActiveConfigItem);
		boolean result = this.updateById(snActiveConfigItem);
		if (result) {
			log.info("更新奖品配置成功, 主键ID:{}", snActiveConfigItem.getId());
		} else {
			log.info("更新奖品配置失败, 主键ID:{}", snActiveConfigItem.getId());
		}
	}

	/**
	 * 新增奖品配置
	 *
	 * @param snActiveConfigItem
	 * @return
	 */
	@Override
	public void saveReward(SnActiveConfigItemVO snActiveConfigItem) {
		this.checkReward(snActiveConfigItem);
		boolean result = this.save(snActiveConfigItem);
		if (result) {
			log.info("新增奖品配置成功, 主键ID:{}", snActiveConfigItem.getId());
		} else {
			log.info("新增奖品配置失败, 主键ID:{}", snActiveConfigItem.getId());
		}
	}

	/**
	 * 查询奖品配置详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SnActiveConfigItemEntity findReward(Long id) {
		LambdaQueryWrapper<SnActiveConfigItemEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnActiveConfigItemEntity::getId, id);
		SnActiveConfigItemEntity entity = snActiveConfigItemMapper.selectOne(lqw);
		return entity;
	}

	/**
	 * 删除奖品配置
	 *
	 * @param id
	 */
	@Override
	public void deleteReward(Long id) {
		LambdaQueryWrapper<SnActiveConfigItemEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnActiveConfigItemEntity::getId, id);
		SnActiveConfigItemEntity entity = snActiveConfigItemMapper.selectOne(lqw);
		if (entity != null) {
			entity.setStatus(RewardStatusEnum.DELETED.getVaule());
			int rows = snActiveConfigItemMapper.updateById(entity);
			if (rows > 0) {
				log.info("删除奖品配置成功, 主键ID:{}", entity.getId());
			} else {
				log.info("删除奖品配置失败, 主键ID:{}", entity.getId());
			}
		}
	}

	/**
	 * 构建查询条件
	 *
	 * @param searchReqVO
	 * @return
	 */
	private LambdaQueryWrapper<SnActiveConfigItemEntity> buildQueryWrapper(RewardSearchReqVO searchReqVO) {
		LambdaQueryWrapper<SnActiveConfigItemEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(searchReqVO.getId() != null, SnActiveConfigItemEntity::getId, searchReqVO.getId());
		lqw.like(searchReqVO.getActiveItemName() != null && !searchReqVO.getActiveItemName().isEmpty(), SnActiveConfigItemEntity::getActiveItemName, searchReqVO.getActiveItemName());
		lqw.eq(searchReqVO.getRewardType() != null, SnActiveConfigItemEntity::getRewardType, searchReqVO.getRewardType());
		lqw.eq(searchReqVO.getStatus() != null, SnActiveConfigItemEntity::getStatus, searchReqVO.getStatus());
		lqw.orderByDesc(SnActiveConfigItemEntity::getCreateTime);
		return lqw;
	}

	/**
	 * 校验奖品数据模型
	 *
	 * @param itemVO
	 */
	private void checkReward(SnActiveConfigItemVO itemVO) {
		if (itemVO.getRewardType() == RewardTypeEnum.COMMISSS_FREE.getValue()) {
			if (itemVO.getCommissionDay() == null) {
				throw new ServiceException("请填写免佣天数");
			}
		}
		if ((itemVO.getRewardType() == RewardTypeEnum.MONEY.getValue() || itemVO.getRewardType() == RewardTypeEnum.CASH_DEDUCTION.getValue())
			&& itemVO.getRewardSubtype() != SubRewardTypeEnum.TYP_3.value) {
			if (itemVO.getRewardMoney() == null) {
				throw new ServiceException("请填写现金奖励金额");
			}
		}

		if (itemVO.getRewardType() == RewardTypeEnum.HANDSEL_STOCK.getValue()) {
			if (itemVO.getAssetId() == null) {
				throw new ServiceException("请选择(填写)股票代码");
			}
			itemVO.setAssetId(itemVO.getAssetId().toUpperCase());
			if (itemVO.getStkNum() == null) {
				throw new ServiceException("请填写股票数量");
			}
		}

		if (itemVO.getRewardType() == RewardTypeEnum.GROUP_POINTS.getValue()) {
			if (itemVO.getPoints() == null) {
				throw new ServiceException("请填写积分数量");
			}
		}

		if (itemVO.getRewardType() == RewardTypeEnum.CASH_DEDUCTION.getValue() && itemVO.getRewardSubtype() == SubRewardTypeEnum.TYP_3.value) {
			if (itemVO.getStkNum() == null) {
				throw new ServiceException("请填写申购数量");
			}
		}
		if (StringUtils.isEmpty(itemVO.getActiveItemName())) {
			throw new ServiceException("请填写奖品名称");
		}
		if (itemVO.getRemainCount() == null) {
			throw new ServiceException("请填写奖品库存数量");
		}
	}
}
