package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireRatingDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireRatingEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountRiskQuestionnaireRatingMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionnaireRatingService;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireRatingVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 风险测评风险等级关联实现
 *
 * @author eric
 * @since 2024-09-02 09:50:21
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountRiskQuestionnaireRatingServiceImpl extends BaseServiceImpl<AccountRiskQuestionnaireRatingMapper, AccountRiskQuestionnaireRatingEntity>
	implements IAccountRiskQuestionnaireRatingService {

	private final AccountRiskQuestionnaireRatingMapper accountRiskQuestionnaireRatingMapper;

	/**
	 * 根据主键删除
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public boolean deleteByIds(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			boolean result = this.removeBatchByIds(ids);
			if (result) {
				log.info("根据主键ID集合->{},成功删除绑定的问题数据!", ids);
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据主键ID查询风险测评问卷风险等级关联信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AccountRiskQuestionnaireRatingVO getRiskQuestionnaireById(Long id) {
		if (id != null) {
			AccountRiskQuestionnaireRatingEntity rating = this.getById(id);
			if (rating != null) {
				return AccountRiskQuestionnaireRatingVO.builder()
					.id(rating.getId())
					.questionnaireId(rating.getQuestionnaireId())
					.rating(rating.getRating())
					.ratingName(rating.getRatingName())
					.scoreLower(rating.getScoreLower())
					.scoreUpper(rating.getScoreUpper())
					.sort(rating.getSort())
					.build();
			}
		}
		return null;
	}

	/**
	 * 根据问卷ID及分值查询风险测评问卷风险等级关联信息
	 *
	 * @param questionnaireId
	 * @param score
	 */
	@Override
	public AccountRiskQuestionnaireRatingVO getRiskQuestionnaireByQuestionnaireIdAndScore(Long questionnaireId, Float score) {
		if (questionnaireId != null && score != null) {
			LambdaQueryWrapper<AccountRiskQuestionnaireRatingEntity> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(AccountRiskQuestionnaireRatingEntity::getQuestionnaireId, questionnaireId);
			queryWrapper.le(AccountRiskQuestionnaireRatingEntity::getScoreLower, score);
			queryWrapper.ge(AccountRiskQuestionnaireRatingEntity::getScoreUpper, score);
			queryWrapper.orderByDesc(AccountRiskQuestionnaireRatingEntity::getSort);
			queryWrapper.last("limit 1");

			AccountRiskQuestionnaireRatingEntity entity = accountRiskQuestionnaireRatingMapper.selectOne(queryWrapper);
			if (entity != null) {
				return AccountRiskQuestionnaireRatingVO.builder()
					.id(entity.getId())
					.questionnaireId(entity.getQuestionnaireId())
					.rating(entity.getRating())
					.ratingName(entity.getRatingName())
					.scoreLower(entity.getScoreLower())
					.scoreUpper(entity.getScoreUpper())
					.sort(entity.getSort())
					.build();
			}
		}
		return null;
	}

	/**
	 * 持久化风险测评问卷风险等级关联信息
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public boolean save(AccountRiskQuestionnaireRatingDTO dto) {
		if (dto != null) {
			AccountRiskQuestionnaireRatingEntity entity = new AccountRiskQuestionnaireRatingEntity();
			entity.setQuestionnaireId(dto.getQuestionnaireId());
			entity.setRating(dto.getRating());
			entity.setRatingName(dto.getRatingName());
			entity.setScoreLower(dto.getScoreLower());
			entity.setScoreUpper(dto.getScoreUpper());
			entity.setSort(dto.getSort());
			return this.save(entity);
		}
		return false;
	}

	/**
	 * 批量持久化风险测评问卷风险等级关联信息
	 *
	 * @param dtos
	 * @return
	 */
	@Override
	public boolean batchSave(List<AccountRiskQuestionnaireRatingDTO> dtos) {
		if (dtos != null && dtos.size() > 0) {
			List<AccountRiskQuestionnaireRatingEntity> entities = new ArrayList<>();
			for (AccountRiskQuestionnaireRatingDTO dto : dtos) {
				AccountRiskQuestionnaireRatingEntity entity = new AccountRiskQuestionnaireRatingEntity();
				entity.setQuestionnaireId(dto.getQuestionnaireId());
				entity.setRating(dto.getRating());
				entity.setRatingName(dto.getRatingName());
				entity.setScoreLower(dto.getScoreLower());
				entity.setScoreUpper(dto.getScoreUpper());
				entity.setSort(dto.getSort());
				entities.add(entity);
			}
			return this.saveBatch(entities, 100);
		}
		return false;
	}

	/**
	 * 根据风险测评问卷ID删除该问卷的风险等级关联信息
	 *
	 * @param questionnaireId
	 * @return
	 */
	@Override
	public boolean deleteByQuestionnaireId(Long questionnaireId) {
		if (questionnaireId != null) {
			LambdaQueryWrapper<AccountRiskQuestionnaireRatingEntity> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(AccountRiskQuestionnaireRatingEntity::getQuestionnaireId, questionnaireId);
			int count = accountRiskQuestionnaireRatingMapper.delete(queryWrapper);
			if (count > 0) {
				log.info("根据问卷ID->{},成功删除绑定的问题数据,总条数:{}条!", questionnaireId, count);
			}
		}
		return false;
	}

	/**
	 * 根据风险测评问卷ID查询该问卷的风险等级关联信息
	 *
	 * @param questionnaireId
	 * @return
	 */
	@Override
	public List<AccountRiskQuestionnaireRatingVO> getByQuestionnaireIds(Long questionnaireId) {
		if (questionnaireId != null) {
			LambdaQueryWrapper<AccountRiskQuestionnaireRatingEntity> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(AccountRiskQuestionnaireRatingEntity::getQuestionnaireId, questionnaireId);
			queryWrapper.orderByAsc(AccountRiskQuestionnaireRatingEntity::getSort);
			List<AccountRiskQuestionnaireRatingEntity> entities = this.list(queryWrapper);
			return entities.stream().map(entity -> AccountRiskQuestionnaireRatingVO.builder()
				.id(entity.getId())
				.questionnaireId(entity.getQuestionnaireId())
				.rating(entity.getRating())
				.ratingName(entity.getRatingName())
				.scoreLower(entity.getScoreLower())
				.scoreUpper(entity.getScoreUpper())
				.sort(entity.getSort())
				.createTime(entity.getCreateTime())
				.build()).collect(Collectors.toList());
		}
		return null;
	}
}
