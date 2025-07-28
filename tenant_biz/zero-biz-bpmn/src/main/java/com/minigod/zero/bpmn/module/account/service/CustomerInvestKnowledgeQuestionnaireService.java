package com.minigod.zero.bpmn.module.account.service;


import com.minigod.zero.bpmn.module.account.api.InvestQuestionnaires;
import com.minigod.zero.bpmn.module.account.entity.CustomerInvestKnowledgeQuestionnaire;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【customer_invest_knowledge_questionnaire(客户开户投资知识问卷表)】的数据库操作Service
* @createDate 2024-11-18 19:38:52
*/
public interface CustomerInvestKnowledgeQuestionnaireService extends BaseService<CustomerInvestKnowledgeQuestionnaire> {

	/**
	  * 根据流水号查询投资知识问卷调查内容
	  *
	  * @param
	  * @return
	  */
	List<InvestQuestionnaires.QuestionAnswer> getInfoByApplicationId(String applicationId);

	/**
	  * 根据applicationId删除投资知识问卷调查内容
	  *
	  * @param
	  * @return
	  */
	boolean delByApplicationId(String applicationId);
}
