package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.account.api.InvestQuestionnaires;
import com.minigod.zero.bpmn.module.account.entity.CustomerInvestKnowledgeQuestionnaire;
import com.minigod.zero.bpmn.module.account.service.CustomerInvestKnowledgeQuestionnaireService;
import com.minigod.zero.bpmn.module.account.mapper.CustomerInvestKnowledgeQuestionnaireMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【customer_invest_knowledge_questionnaire(客户开户投资知识问卷表)】的数据库操作Service实现
 * @createDate 2024-11-18 19:38:52
 */
@Service
public class CustomerInvestKnowledgeQuestionnaireServiceImpl extends BaseServiceImpl<CustomerInvestKnowledgeQuestionnaireMapper, CustomerInvestKnowledgeQuestionnaire> implements CustomerInvestKnowledgeQuestionnaireService {

	@Override
	public List<InvestQuestionnaires.QuestionAnswer> getInfoByApplicationId(String applicationId) {
		CustomerInvestKnowledgeQuestionnaire investQuestionnaire = getOne(new QueryWrapper<CustomerInvestKnowledgeQuestionnaire>().lambda().eq(CustomerInvestKnowledgeQuestionnaire::getApplicationId, applicationId));
		if (investQuestionnaire == null) {
			return Collections.emptyList();
		}
		String optionData = investQuestionnaire.getOptionData();
		JSONObject jsonObject = StringUtils.isNotEmpty(optionData) ? JSONObject.parseObject(optionData) : null;
		JSONArray investQuestionnairesArray = jsonObject != null ? jsonObject.getJSONArray("questionAnswers") : null;
		return CollUtil.isNotEmpty(investQuestionnairesArray) ? investQuestionnairesArray.toJavaList(InvestQuestionnaires.QuestionAnswer.class) : null;
	}

	@Override
	public boolean delByApplicationId(String applicationId) {
		return this.remove(new QueryWrapper<CustomerInvestKnowledgeQuestionnaire>().lambda().eq(CustomerInvestKnowledgeQuestionnaire::getApplicationId, applicationId));
	}
}
