package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.bpmn.module.account.api.InvestQuestionnaires;
import com.minigod.zero.bpmn.module.account.service.CustomerInvestKnowledgeQuestionnaireService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 投资知识问卷 控制器
 *
 * @author zxq
 * @date 2024/11/19
 **/
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/account/customerInvestQuestionnaire")
@Api(value = "投资知识问卷控制器", tags = "投资知识问卷控制器")
public class CustomerInvestKnowledgeQuestionnaireController {

	private final CustomerInvestKnowledgeQuestionnaireService investKnowledgeQuestionnaireService;

	@ApiOperation("获取客户开户投资知识问卷信息")
	@GetMapping("/{applicationId}")
	public R<List<InvestQuestionnaires.QuestionAnswer>> getInfoByApplicationId(@ApiParam("预约号")
										@NotNull(message = "预约号不能为空")
										@PathVariable("applicationId") String applicationId) {
		return R.data(investKnowledgeQuestionnaireService.getInfoByApplicationId(applicationId));
	}
}
