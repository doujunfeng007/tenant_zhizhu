package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionAddDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionAndOptionCountVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionAndOptionVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionVO;
import com.minigod.zero.bpmn.module.account.vo.FundRiskQuestionVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 风险评测题库 服务接口
 *
 * @author eric
 * @since 2024-08-20 14:42:25
 */
public interface IAccountRiskQuestionService extends IService<AccountRiskQuestionEntity> {

	/**
	 * 风险测评题库自定查询分页
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	IPage<AccountRiskQuestionVO> selectAcctRiskQuestionPage(IPage<AccountRiskQuestionVO> page, AccountRiskQuestionDTO acctRiskQuestion);

	/**
	 * 风险测评题库绑定问卷题目选择列表分页
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	IPage<AccountRiskQuestionAndOptionCountVO> selectAcctRiskQuestionAndOptionPage(IPage<AccountRiskQuestionAndOptionCountVO> page, AccountRiskQuestionDTO acctRiskQuestion);

	/**
	 * 获取基金风险评测题库
	 *
	 * @return
	 */
	R<List<AccountRiskQuestionAndOptionVO>> riskQuestion(AccountRiskQuestionDTO acctRiskQuestion);

	/**
	 * 获取问题列表
	 *
	 * @param acctRiskQuestion
	 * @return
	 */
	R<List<FundRiskQuestionVO>> findQuestion(AccountRiskQuestionDTO acctRiskQuestion);

	/**
	 * 添加问题
	 *
	 * @param acctRiskQuestion
	 * @return
	 */
	R addQuestion(AccountRiskQuestionAddDTO acctRiskQuestion);

	/**
	 * 批量添加问题
	 *
	 * @param acctRiskQuestions
	 * @return
	 */
	R batchAddQuestion(List<AccountRiskQuestionAddDTO> acctRiskQuestions);

	/**
	 * 根据主键IDs删除问题
	 *
	 * @param ids
	 * @return
	 */
	R deleteQuestion(List<Long> ids);
}
