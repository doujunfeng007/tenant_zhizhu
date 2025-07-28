package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountRiskQuestionnaireMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionnaireQuestionService;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionnaireService;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 风险测评问卷服务实现类
 *
 * @author eric
 * @since 2024-08-30 20:15:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountRiskQuestionnaireServiceImpl extends BaseServiceImpl<AccountRiskQuestionnaireMapper, AccountRiskQuestionnaireEntity> implements IAccountRiskQuestionnaireService {
	private final AccountRiskQuestionnaireMapper accountRiskQuestionnaireMapper;
	private final IAccountRiskQuestionnaireQuestionService iAccountRiskQuestionnaireQuestionService;

	/**
	 * 风险测评问卷列表分页
	 *
	 * @param page
	 * @param riskQuestionnaireDTO
	 * @return
	 */
	@Override
	public IPage<AccountRiskQuestionnaireVO> selectAcctRiskQuestionnairePage(IPage<AccountRiskQuestionnaireVO> page, AccountRiskQuestionnaireDTO riskQuestionnaireDTO) {
		List<AccountRiskQuestionnaireVO> accountRiskQuestionnaireVOS = new ArrayList<>();
		List<AccountRiskQuestionnaireEntity> accountRiskQuestionnaires = accountRiskQuestionnaireMapper.selectAcctRiskQuestionnairePage(page, riskQuestionnaireDTO);
		for (AccountRiskQuestionnaireEntity accountRiskQuestionnaire : accountRiskQuestionnaires) {
			AccountRiskQuestionnaireVO accountRiskQuestionnaireVO = new AccountRiskQuestionnaireVO();
			accountRiskQuestionnaireVO.setId(accountRiskQuestionnaire.getId());
			accountRiskQuestionnaireVO.setQuestionnaireDesc(accountRiskQuestionnaire.getQuestionnaireDesc());
			accountRiskQuestionnaireVO.setQuestionnaireName(accountRiskQuestionnaire.getQuestionnaireName());
			accountRiskQuestionnaireVO.setQuestionnaireType(accountRiskQuestionnaire.getQuestionnaireType());
			accountRiskQuestionnaireVO.setLang(accountRiskQuestionnaire.getLang());
			accountRiskQuestionnaireVO.setCreateTime(accountRiskQuestionnaire.getCreateTime());
			accountRiskQuestionnaireVOS.add(accountRiskQuestionnaireVO);
		}
		return page.setRecords(accountRiskQuestionnaireVOS);
	}

	/**
	 * 根据ID查询风险测评问卷
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AccountRiskQuestionnaireVO getAcctRiskQuestionnaireById(Long id) {
		AccountRiskQuestionnaireEntity accountRiskQuestionnaire = accountRiskQuestionnaireMapper.selectById(id);
		AccountRiskQuestionnaireVO accountRiskQuestionnaireVO = null;
		if (accountRiskQuestionnaire != null) {
			accountRiskQuestionnaireVO = new AccountRiskQuestionnaireVO();
			accountRiskQuestionnaireVO.setQuestionnaireDesc(accountRiskQuestionnaire.getQuestionnaireDesc());
			accountRiskQuestionnaireVO.setQuestionnaireName(accountRiskQuestionnaire.getQuestionnaireName());
			accountRiskQuestionnaireVO.setQuestionnaireType(accountRiskQuestionnaire.getQuestionnaireType());
			accountRiskQuestionnaireVO.setLang(accountRiskQuestionnaire.getLang());
			accountRiskQuestionnaireVO.setCreateTime(accountRiskQuestionnaire.getCreateTime());
			accountRiskQuestionnaireVO.setId(accountRiskQuestionnaire.getId());
		}
		return accountRiskQuestionnaireVO;
	}

	/**
	 * 新增风险测评问卷
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public boolean save(AccountRiskQuestionnaireDTO dto) {
		AccountRiskQuestionnaireEntity entity = new AccountRiskQuestionnaireEntity();
		entity.setQuestionnaireDesc(dto.getQuestionnaireDesc());
		entity.setQuestionnaireName(dto.getQuestionnaireName());
		entity.setQuestionnaireType(dto.getQuestionnaireType());
		entity.setLang(dto.getLang());
		return this.save(entity);
	}

	/**
	 * 根据主键删除风险测评问卷
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteById(Long id) {
		int count = accountRiskQuestionnaireMapper.deleteById(id);
		if (count > 0) {
			log.info("根据主键ID->{},成功删除风险测评问卷数据!", id);
			return true;
		}
		return false;
	}

	/**
	 * 根据主键批量删除风险测评问卷
	 *
	 * @param ids
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean deleteByIds(List<Long> ids) {
		try {
			int count = accountRiskQuestionnaireMapper.deleteBatchIds(ids);
			if (count > 0) {
				boolean result = iAccountRiskQuestionnaireQuestionService.deleteByQuestionnaireIds(ids);
				if (!result) {
					log.error("根据主键ID集合->{}, 删除风险测评问卷数据失败!", ids);
					return false;
				} else {
					log.info("根据主键ID集合->{}, 成功删除风险测评问卷数据!", ids);
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			log.error("根据主键ID集合->{}, 删除风险测评问卷数据时发生异常: {}", ids, e.getMessage(), e);
			throw e; // 重新抛出异常，确保事务回滚
		}
	}
}
