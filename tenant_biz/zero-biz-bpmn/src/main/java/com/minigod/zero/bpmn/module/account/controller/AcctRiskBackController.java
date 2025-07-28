package com.minigod.zero.bpmn.module.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.account.dto.*;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 风险测评及题库控制器
 *
 * @author eric
 * @since 2024-08-20 16:25:05
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/risk")
@Api(value = "风险测评及题库控制器", tags = "风险测评及题库控制器")
public class AcctRiskBackController {
	private final IAccountRiskQuestionnaireService acctRiskQuestionnaireService;
	private final IAccountRiskQuestionnaireQuestionService acctRiskQuestionnaireQuestionService;
	private final IAccountRiskQuestionnaireRatingService acctRiskQuestionnaireRatingService;
	private final IAccountRiskEvaluationService acctRiskEvaluationService;
	private final IAccountRiskQuestionService acctRiskQuestionService;
	private final IAccountRiskQuestionOptionService acctRiskQuestionOptionService;

	/**
	 * 分页查询风险测评问卷列表
	 *
	 * @param query
	 * @param acctRiskQuestionnaireDTO
	 */
	@ApiLog("分页查询风险测评问卷列表")
	@GetMapping("/page-questionnaire-list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页查询风险测评问卷列表", notes = "分页查询风险测评问卷列表")
	public R<IPage<AccountRiskQuestionnaireVO>> selectAcctRiskQuestionnairePage(Query query, AccountRiskQuestionnaireDTO acctRiskQuestionnaireDTO) {
		return R.data(acctRiskQuestionnaireService.selectAcctRiskQuestionnairePage(Condition.getPage(query), acctRiskQuestionnaireDTO));
	}

	/**
	 * 添加风险测评问卷
	 *
	 * @param acctRiskQuestionnaireDTO
	 * @return
	 */
	@ApiLog("添加风险测评问卷")
	@PostMapping("/add-questionnaire")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "添加风险测评问卷", notes = "添加风险测评问卷")
	public R addQuestionnaire(@RequestBody AccountRiskQuestionnaireDTO acctRiskQuestionnaireDTO) {
		boolean isSuccess = acctRiskQuestionnaireService.save(acctRiskQuestionnaireDTO);
		if (isSuccess) {
			return R.success("添加风险测评问卷成功");
		} else {
			return R.fail("添加风险测评问卷失败");
		}
	}

	/**
	 * 根据主键ID查询风险测评问卷
	 *
	 * @param id
	 * @return
	 */
	@ApiLog("根据主键ID，获取风险测评问卷")
	@GetMapping("/get-questionnaire/{id}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "根据指定的主键ID，获取风险测评问卷")
	public R<AccountRiskQuestionnaireVO> getQuestionnaire(@PathVariable("id") Long id) {
		return R.data(acctRiskQuestionnaireService.getAcctRiskQuestionnaireById(id));
	}

	/**
	 * 根主键ID集合删除风险测评问卷
	 *
	 * @param ids
	 * @return
	 */
	@ApiLog("删除风险测评问卷")
	@DeleteMapping("/delete-questionnaire")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除风险测评问卷")
	public R deleteQuestionnaire(@ApiParam(value = "主键集合", required = true) @RequestBody List<Long> ids) {
		boolean isSuccess = acctRiskQuestionnaireService.deleteByIds(ids);
		if (isSuccess) {
			return R.success("删除风险测评问卷成功");
		} else {
			return R.fail("删除风险测评问卷失败");
		}
	}

	/**
	 * 新增风险测评题目及问题选择项
	 *
	 * @param acctRiskQuestionDTO
	 * @return
	 */
	@ApiLog("新增风险测评题目及问题选择项")
	@PostMapping("/add-question")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增风险测评题目及问题选择项", notes = "新增风险测评题目及问题选择项")
	public R addQuestion(@RequestBody AccountRiskQuestionAddDTO acctRiskQuestionDTO) {
		return acctRiskQuestionService.addQuestion(acctRiskQuestionDTO);
	}

	/**
	 * 批量新增风险测评题目及问题选择项
	 *
	 * @param acctRiskQuestionDTOs
	 * @return
	 */
	@ApiLog("批量新增风险测评题目及问题选择项")
	@PostMapping("/batch-add-question")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "批量新增风险测评题目及问题选择项", notes = "批量新增风险测评题目及问题选择项")
	public R batchAddQuestion(@RequestBody List<AccountRiskQuestionAddDTO> acctRiskQuestionDTOs) {
		return acctRiskQuestionService.batchAddQuestion(acctRiskQuestionDTOs);
	}

	/**
	 * 风险测评题库分页列表
	 *
	 * @param query
	 * @param acctRiskQuestionDTO
	 * @return
	 */
	@ApiLog("风险测评题库分页列表查询")
	@GetMapping("/page-question-list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "风险测评题库分页列表")
	public R<IPage<AccountRiskQuestionVO>> selectAcctRiskQuestionPage(Query query, AccountRiskQuestionDTO acctRiskQuestionDTO) {
		IPage<AccountRiskQuestionVO> result = acctRiskQuestionService.selectAcctRiskQuestionPage(Condition.getPage(query), acctRiskQuestionDTO);
		return R.data(result);
	}

	/**
	 * 风险评测题库及答案选项信息
	 *
	 * @param query
	 * @param acctRiskQuestionDTO
	 * @return
	 */
	@ApiLog("风险评测题库及答案选项信息")
	@GetMapping("/page-question-option-info-list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "风险评测题库及答案选项信息")
	public R<IPage<AccountRiskQuestionAndOptionCountVO>> selectAcctRiskQuestionAndOptionPage(Query query, AccountRiskQuestionDTO acctRiskQuestionDTO) {
		IPage<AccountRiskQuestionAndOptionCountVO> result = acctRiskQuestionService.selectAcctRiskQuestionAndOptionPage(Condition.getPage(query), acctRiskQuestionDTO);
		return R.data(result);
	}

	/**
	 * 风险测评题目选项列表
	 *
	 * @param questionId
	 * @return
	 */
	@ApiLog("风险测评题目选项列表")
	@GetMapping("/question-option-list/{questionId}")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "风险测评题目选项列表")
	public R<List<AccountRiskQuestionOptionVO>> selectAcctRiskQuestionOptionList(@PathVariable("questionId") Long questionId) {
		return R.data(acctRiskQuestionOptionService.selectAcctRiskQuestionOptionList(questionId));
	}

	/**
	 * 批量删除风险测评题目及问题选择项
	 *
	 * @param ids
	 * @return
	 */
	@ApiLog("批量删除风险测评题目及问题选择项")
	@DeleteMapping("/delete-question")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "批量删除风险测评题目及问题选择项")
	public R deleteQuestion(@ApiParam(value = "主键集合", required = true) @RequestBody List<Long> ids) {
		return acctRiskQuestionService.deleteQuestion(ids);
	}

	/**
	 * 根据主键集合删除风险测评问题选择项
	 *
	 * @param ids
	 * @return
	 */
	@ApiLog("根据主键集合删除风险测评问题选择项")
	@DeleteMapping("/delete-question-option")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "根据主键集合删除风险测评问题选择项")
	public R deleteQuestionOption(@ApiParam(value = "主键集合", required = true) @RequestBody List<Long> ids) {
		return acctRiskQuestionOptionService.deleteQuestionOption(ids);
	}

	/**
	 * 添加风险测评问卷和题目的关联关系
	 *
	 * @param questionnaireQuestionDTOs
	 * @return
	 */
	@ApiLog("添加风险测评问卷和题目的关联关系")
	@PostMapping("/add-questionnaire-question")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "添加风险测评问卷和题目的关联关系", notes = "添加风险测评问卷和题目的关联关系")
	public R addQuestionnaireQuestion(@RequestBody List<AccountRiskQuestionnaireQuestionDTO> questionnaireQuestionDTOs) {
		boolean isSuccess = acctRiskQuestionnaireQuestionService.addQuestionnaireQuestion(questionnaireQuestionDTOs);
		if (isSuccess) {
			return R.success("添加风险测评题目到问卷成功!");
		} else {
			return R.fail("添加风险测评题目到问卷失败!");
		}
	}

	/**
	 * 根据问卷ID和语言类型获取风险测评问卷和题目的关联关系
	 */
	@ApiLog("根据问卷ID和语言类型获取风险测评问卷和题目的关联关系")
	@GetMapping("/get-questionnaire-question/{questionnaireId}/{lang}")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "根据问卷ID和语言类型获取风险测评问卷和题目的关联关系", notes = "根据问卷ID和语言类型获取风险测评问卷和题目的关联关系")
	public R<AccountRiskQuestionnaireAndQuestionVO> getQuestionnaireQuestion(@PathVariable("questionnaireId") Long questionnaireId, @PathVariable("lang") String lang) {
		return R.data(acctRiskQuestionnaireQuestionService.getByQuestionnaireId(questionnaireId, lang));
	}

	/**
	 * 根据主键ID移除风险测评问卷和题目的关联关系
	 */
	@ApiLog("根据主键ID移除风险测评问卷和题目的关联关系")
	@DeleteMapping("/delete-questionnaire-question")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "根据主键ID移除风险测评问卷和题目的关联关系", notes = "根据主键ID移除风险测评问卷和题目的关联关系")
	public R deleteQuestionnaireQuestion(@RequestBody List<Long> ids) {
		boolean isSuccess = acctRiskQuestionnaireQuestionService.deleteByIds(ids);
		if (isSuccess) {
			return R.success("根据主键ID从问题中移除风险测评题目成功!");
		} else {
			return R.fail("根据主键ID从问题中移除风险测评题目失败!");
		}
	}

	/**
	 * 根据问卷ID移除风险测评问卷和题目的关联关系
	 */
	@ApiLog("根据问卷ID移除风险测评问卷和题目的关联关系")
	@DeleteMapping("/delete-questionnaire-question/by-questionnaire-id")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "根据问卷ID移除风险测评问卷和题目的关联关系", notes = "根据问卷ID移除风险测评问卷和题目的关联关系")
	public R deleteQuestionnaireQuestionByQuestionnaireId(@RequestBody List<Long> questionnaireIds) {
		boolean isSuccess = acctRiskQuestionnaireQuestionService.deleteByQuestionnaireIds(questionnaireIds);
		if (isSuccess) {
			return R.success("根据问卷ID从问题中移除风险测评题目成功!");
		} else {
			return R.fail("根据问卷ID从问题中移除风险测评题目失败!");
		}
	}

	/**
	 * 调整问卷中问题的顺序
	 */
	@ApiLog("调整问卷中问题的顺序")
	@PostMapping("/change-questionnaire-question-sort")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "调整问卷中问题的顺序", notes = "调整问卷中问题的顺序")
	public R changeQuestionnaireQuestionSort(@RequestBody List<AccountRiskQuestionnaireQuestionSortDTO> questionSortDTOs) {
		boolean isSuccess = acctRiskQuestionnaireQuestionService.changeSort(questionSortDTOs);
		if (isSuccess) {
			return R.success("调整问卷中问题的顺序成功!");
		} else {
			return R.fail("调整问卷中问题的顺序失败!");
		}
	}

	/**
	 * 添加问卷目风险等级
	 */
	@ApiLog("添加问卷目风险等级")
	@PostMapping("/add-questionnaire-question-rating")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "添加问卷目风险等级", notes = "添加问卷目风险等级")
	public R addQuestionnaireQuestionRating(@RequestBody List<AccountRiskQuestionnaireRatingDTO> riskRatingDTOs) {
		boolean isSuccess = acctRiskQuestionnaireRatingService.batchSave(riskRatingDTOs);
		if (isSuccess) {
			return R.success("添加问卷目风险等级成功!");
		} else {
			return R.fail("添加问卷目风险等级失败!");
		}
	}

	/**
	 * 根据问卷ID获取风险等级
	 */
	@ApiLog("根据问卷ID获取风险等级")
	@GetMapping("/get-questionnaire-question-rating/by-questionnaire-id/{questionnaireId}")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "根据问卷ID获取风险等级")
	public R<List<AccountRiskQuestionnaireRatingVO>> getQuestionnaireQuestionRating(@PathVariable("questionnaireId") Long questionnaireId) {
		return R.data(acctRiskQuestionnaireRatingService.getByQuestionnaireIds(questionnaireId));
	}

	/**
	 * 根据问卷ID和分值获取风险等级
	 */
	@ApiLog("根据问卷ID和分值获取风险等级")
	@PostMapping("/get-questionnaire-question-rating-by-score")
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "根据问卷ID和分值获取风险等级")
	public R<AccountRiskQuestionnaireRatingVO> getQuestionnaireQuestionRatingByScore(@RequestBody AccountRiskRatingQueryDTO acctRiskRatingQuery) {
		if (acctRiskRatingQuery.getScore() == null) {
			return R.fail("分值不能为空!");
		}
		if (acctRiskRatingQuery.getQuestionnaireId() == null) {
			return R.fail("问卷ID不能为空!");
		}
		return R.data(acctRiskQuestionnaireRatingService.getRiskQuestionnaireByQuestionnaireIdAndScore(acctRiskRatingQuery.getQuestionnaireId(), acctRiskRatingQuery.getScore()));
	}

	/**
	 * 根据主键ID获取风险等级详情
	 */
	@ApiLog("根据主键ID获取风险等级详情")
	@GetMapping("/get-questionnaire-question-rating/by-id/{id}")
	@ApiOperationSupport(order = 19)
	@ApiOperation(value = "根据主键ID获取风险等级详情")
	public R<AccountRiskQuestionnaireRatingVO> getQuestionnaireQuestionRatingById(@PathVariable("id") Long id) {
		return R.data(acctRiskQuestionnaireRatingService.getRiskQuestionnaireById(id));
	}

	/**
	 * 根据主键ID删除问卷目风险等级关系
	 */
	@ApiLog("根据主键ID删除问卷目风险等级")
	@DeleteMapping("/delete-questionnaire-question-rating")
	@ApiOperationSupport(order = 20)
	@ApiOperation(value = "根据主键ID删除问卷目风险等级", notes = "根据主键ID删除问卷目风险等级")
	public R deleteQuestionnaireQuestionRating(@RequestBody List<Long> ids) {
		boolean isSuccess = acctRiskQuestionnaireRatingService.deleteByIds(ids);
		if (isSuccess) {
			return R.success("根据主键ID删除问卷目风险等级成功!");
		} else {
			return R.fail("根据主键ID删除问卷目风险等级失败!");
		}
	}

	/**
	 * 风险测评记录分页列表
	 *
	 * @param query
	 * @param acctRiskEvaluationDTO
	 * @return
	 */
	@ApiLog("风险测评记录分页列表查询")
	@GetMapping("/page-evaluation-list")
	@ApiOperationSupport(order = 21)
	@ApiOperation(value = "风险测评记录分页列表")
	public R<IPage<AccountRiskEvaluationVO>> selectAcctRiskEvaluationPage(Query query, AccountRiskEvaluationDTO acctRiskEvaluationDTO) {
		return R.data(acctRiskEvaluationService.selectAcctRiskEvaluationPage(Condition.getPage(query), acctRiskEvaluationDTO));
	}

	/**
	 * 根据指定的CustID，获取最新一条风险测评结果
	 *
	 * @param custId
	 * @return
	 */
	@ApiLog("根据指定的CustID，获取最新一条风险测评结果")
	@GetMapping("/get-risk-evaluation-info")
	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "根据指定的风CustID，获取最新一条风险测评结果")
	public R<AccountRiskEvaluationVO> getRiskEvaluationInfo(@ApiParam(value = "用户CustID", required = true) @RequestParam("custId") Long custId) {
		return acctRiskEvaluationService.getRiskEvaluationInfo(custId);
	}

	/**
	 * 根据指定的CustID，获取所有风险测评结果列表
	 *
	 * @param custId
	 * @return
	 */
	@ApiLog("根据指定的CustID，获取所有风险测评结果列表")
	@GetMapping("/get-risk-evaluation-list")
	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "根据指定的风CustID，获取所有风险测评结果列表")
	public R<List<AccountRiskEvaluationVO>> getRiskEvaluationList(@ApiParam(value = "用户CustID", required = true) @RequestParam("custId") Long custId) {
		return acctRiskEvaluationService.getRiskEvaluationList(custId);
	}

	/**
	 * 根据主键ID，获取风险测评详情
	 *
	 * @param id
	 * @return
	 */
	@ApiLog("根据指定的主键ID，获取风险测评详情")
	@GetMapping("/get-risk-evaluation-detail")
	@ApiOperationSupport(order = 23)
	@ApiOperation(value = "根据指定的主键ID，获取风险测评详情")
	public R<AccountRiskEvaluationVO> getRiskEvaluationInfoById(@ApiParam(value = "主键ID", required = true) @RequestParam("id") Long id) {
		return R.data(acctRiskEvaluationService.getRiskEvaluationInfoById(id));
	}
}
