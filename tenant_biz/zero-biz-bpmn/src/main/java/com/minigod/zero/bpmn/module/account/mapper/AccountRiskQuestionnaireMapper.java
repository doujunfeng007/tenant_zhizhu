package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风险评测问卷持久层
 *
 * @author eric
 * @since 2024-09-02 09:27:05
 */
@Mapper
public interface AccountRiskQuestionnaireMapper extends BaseMapper<AccountRiskQuestionnaireEntity> {
	/**
	 * 风险评测问卷分页查询列表
	 *
	 * @param page
	 * @param riskQuestionnaire
	 * @return
	 */
	List<AccountRiskQuestionnaireEntity> selectAcctRiskQuestionnairePage(IPage page, @Param("riskQuestionnaire") AccountRiskQuestionnaireDTO riskQuestionnaire);
}
