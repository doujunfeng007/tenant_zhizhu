package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireQuestionDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireQuestionSortDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireQuestionEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountRiskQuestionnaireQuestionMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionnaireQuestionService;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireAndQuestionVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireQuestionVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 风险测评问卷题目关联实现
 *
 * @author eric
 * @since 2024-08-30 20:33:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountRiskQuestionnaireQuestionServiceImpl extends BaseServiceImpl<AccountRiskQuestionnaireQuestionMapper, AccountRiskQuestionnaireQuestionEntity>
	implements IAccountRiskQuestionnaireQuestionService {

	private final AccountRiskQuestionnaireQuestionMapper accountRiskQuestionnaireQuestionMapper;

	/**
	 * 根据问卷ID查询绑定的问题列表数据
	 *
	 * @param questionnaireId
	 * @param lang
	 * @return
	 */
	@Override
	public AccountRiskQuestionnaireAndQuestionVO getByQuestionnaireId(Long questionnaireId, String lang) {
		List<AccountRiskQuestionnaireQuestionVO> accountRiskQuestionnaireQuestionVOs = accountRiskQuestionnaireQuestionMapper.getByQuestionnaireId(questionnaireId, lang);
		if (accountRiskQuestionnaireQuestionVOs != null && !accountRiskQuestionnaireQuestionVOs.isEmpty()) {
			AccountRiskQuestionnaireAndQuestionVO accountRiskQuestionnaireAndQuestionVO = new AccountRiskQuestionnaireAndQuestionVO();
			AccountRiskQuestionnaireVO accountRiskQuestionnaireVO = new AccountRiskQuestionnaireVO();
			List<AccountRiskQuestionVO> accountRiskQuestionVOs = new ArrayList<>();

			//将问卷数据放入VO (直接使用第一个元素，避免 Stream API 的性能开销)
			AccountRiskQuestionnaireQuestionVO accountRiskQuestionnaireQuestionVO = accountRiskQuestionnaireQuestionVOs.get(0);
			accountRiskQuestionnaireVO.setId(accountRiskQuestionnaireQuestionVO.getQuestionnaireId());
			accountRiskQuestionnaireVO.setQuestionnaireName(accountRiskQuestionnaireQuestionVO.getQuestionnaireName());
			accountRiskQuestionnaireVO.setQuestionnaireDesc(accountRiskQuestionnaireQuestionVO.getQuestionnaireDesc());
			accountRiskQuestionnaireVO.setQuestionnaireType(accountRiskQuestionnaireQuestionVO.getQuestionnaireType());
			accountRiskQuestionnaireVO.setLang(accountRiskQuestionnaireQuestionVO.getLang());
			accountRiskQuestionnaireVO.setCreateTime(accountRiskQuestionnaireQuestionVO.getCreateTime());
			accountRiskQuestionnaireAndQuestionVO.setAccountRiskQuestionnaireVO(accountRiskQuestionnaireVO);

			//将问题数据放入集合
			for (AccountRiskQuestionnaireQuestionVO questionnaireQuestionVO : accountRiskQuestionnaireQuestionVOs) {
				AccountRiskQuestionVO accountRiskQuestionVO = new AccountRiskQuestionVO();
				accountRiskQuestionVO.setQuestionId(questionnaireQuestionVO.getQuestionId());
				accountRiskQuestionVO.setQuestion(questionnaireQuestionVO.getQuestion());
				accountRiskQuestionVO.setOptType(questionnaireQuestionVO.getOptType());
				accountRiskQuestionVO.setCheckType(questionnaireQuestionVO.getCheckType());
				accountRiskQuestionVO.setSort(questionnaireQuestionVO.getSort());
				accountRiskQuestionVO.setQuestionType(questionnaireQuestionVO.getQuestionType());
				accountRiskQuestionVO.setLang(questionnaireQuestionVO.getLang());
				accountRiskQuestionVO.setCreateTime(questionnaireQuestionVO.getCreateTime());
				accountRiskQuestionVO.setFlag(questionnaireQuestionVO.getFlag());
				accountRiskQuestionVO.setId(questionnaireQuestionVO.getId());
				accountRiskQuestionVOs.add(accountRiskQuestionVO);
			}
			accountRiskQuestionnaireAndQuestionVO.setAccountRiskQuestionVOs(accountRiskQuestionVOs);
			return accountRiskQuestionnaireAndQuestionVO;
		}
		return null;
	}

	/**
	 * 添加绑定的问题数据
	 *
	 * @param dtos
	 * @return
	 */
	@Override
	public boolean addQuestionnaireQuestion(List<AccountRiskQuestionnaireQuestionDTO> dtos) {
		if (dtos != null && !dtos.isEmpty()) {
			List<AccountRiskQuestionnaireQuestionEntity> entities = new ArrayList<>();
			for (AccountRiskQuestionnaireQuestionDTO dto : dtos) {
				AccountRiskQuestionnaireQuestionEntity entity = new AccountRiskQuestionnaireQuestionEntity();
				entity.setQuestionnaireId(dto.getQuestionnaireId());
				entity.setQuestionId(dto.getQuestionId());
				entity.setSort(dto.getSort());
				entities.add(entity);
			}
			return this.saveBatch(entities, 100);
		}
		return false;
	}

	/**
	 * 修改绑定的问题顺序
	 *
	 * @param dtos
	 * @return
	 */
	@Override
	public boolean changeSort(List<AccountRiskQuestionnaireQuestionSortDTO> dtos) {
		if (dtos != null && !dtos.isEmpty()) {
			List<AccountRiskQuestionnaireQuestionEntity> entities = new ArrayList<>();
			for (AccountRiskQuestionnaireQuestionSortDTO dto : dtos) {
				AccountRiskQuestionnaireQuestionEntity entity = new AccountRiskQuestionnaireQuestionEntity();
				entity.setId(dto.getId());
				entity.setSort(dto.getSort());
				entities.add(entity);
			}
			return this.updateBatchById(entities);
		}
		return false;
	}

	/**
	 * 根据问卷ID删除绑定的问题数据
	 *
	 * @param questionnaireId
	 * @return
	 */
	@Override
	public boolean deleteByQuestionnaireId(Long questionnaireId) {
		LambdaQueryWrapper<AccountRiskQuestionnaireQuestionEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AccountRiskQuestionnaireQuestionEntity::getQuestionnaireId, questionnaireId);
		int count = accountRiskQuestionnaireQuestionMapper.delete(queryWrapper);
		if (count > 0) {
			log.info("根据问卷ID->{},成功删除绑定的问题数据:{}条!", questionnaireId, count);
			return true;
		} else {
			log.warn("根据问卷ID->{},未成功删除绑定的问题数据!", questionnaireId);
			return false;
		}
	}

	/**
	 * 根据问卷ID批量删除绑定的问题数据
	 *
	 * @param questionnaireIds
	 * @return
	 */
	@Override
	public boolean deleteByQuestionnaireIds(List<Long> questionnaireIds) {
		LambdaQueryWrapper<AccountRiskQuestionnaireQuestionEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(AccountRiskQuestionnaireQuestionEntity::getQuestionnaireId, questionnaireIds);
		int count = accountRiskQuestionnaireQuestionMapper.delete(queryWrapper);
		if (count > 0) {
			log.info("根据问卷ID->{},成功删除绑定的问题数据:{}条!", questionnaireIds, count);
		} else {
			log.warn("根据问卷ID->{},没有符合条件的问题数据!", questionnaireIds);
		}
		return true;
	}

	/**
	 * 根据主键删除数据
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteById(Long id) {
		if (accountRiskQuestionnaireQuestionMapper.deleteById(id) > 0) {
			log.info("根据主键ID->{},成功删除绑定的问题数据!", id);
			return true;
		}
		return false;
	}

	/**
	 * 根据主键批量删除数据
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public boolean deleteByIds(List<Long> ids) {
		if (this.deleteLogic(ids)) {
			log.info("根据主键ID集合->{},成功删除绑定的问题数据!", ids);
			return true;
		}
		return false;
	}
}
