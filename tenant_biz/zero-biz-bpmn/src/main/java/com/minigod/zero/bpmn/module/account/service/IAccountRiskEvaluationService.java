package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskEvaluationDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskEvaluationSubmitDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskEvaluationEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskEvaluationTempVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskEvaluationVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 风险测评结果服务接口
 *
 * @author eric
 * @since 2024-08-20 14:19:08
 */
public interface IAccountRiskEvaluationService extends IService<AccountRiskEvaluationEntity> {

	/**
	 * 风险评测记录分页列表
	 *
	 * @param page
	 * @param acctRiskEvaluation
	 * @return
	 */
	IPage<AccountRiskEvaluationVO> selectAcctRiskEvaluationPage(IPage<AccountRiskEvaluationVO> page, AccountRiskEvaluationDTO acctRiskEvaluation);

	/**
	 * 获取风险评测记录详情
	 *
	 * @param id
	 * @return
	 */
	AccountRiskEvaluationVO getRiskEvaluationInfoById(Long id);

	/**
	 * 暂存风险测评结果
	 *
	 * @param accRiskEvaluationSubmit
	 * @return
	 */
	R saveEvaluationTemp(AccountRiskEvaluationSubmitDTO accRiskEvaluationSubmit);

	/**
	 * 获取当前用户风险测评暂存记录
	 *
	 * @return
	 */
	R<AccountRiskEvaluationTempVO> evaluationTemp();

	/**
	 * 提交风险评测结果
	 *
	 * @param accRiskEvaluationSubmit
	 * @return
	 */
	R saveRiskEvaluation(AccountRiskEvaluationSubmitDTO accRiskEvaluationSubmit);

	/**
	 * 获取客户风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	R<AccountRiskEvaluationVO> getRiskEvaluationInfo(Long custId);

	/**
	 * 获取客户风险评测记录列表
	 *
	 * @param custId
	 * @return
	 */
	R<List<AccountRiskEvaluationVO>> getRiskEvaluationList(Long custId);

	/**
	 * 获取客户过去3个月内的基金风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	List<AccountRiskEvaluationEntity> getLastThreeMonthRecords(Long custId);

	/**
	 * 获取客户风险评测记录
	 *
	 * @param accRiskEvaluation
	 * @return
	 */
	R<AccountRiskEvaluationVO> getRiskEvaluationInfo(AccountRiskEvaluationDTO accRiskEvaluation);


	/**
	 * 获取客户风险评测记录
	 *
	 * @return
	 */
	R<AccountRiskEvaluationVO> getRiskEvaluationInfo();

	/**
	 * 重置风险评测
	 *
	 * @param accRiskEvaluation
	 * @return
	 */
	R resetRiskEvaluation(AccountRiskEvaluationDTO accRiskEvaluation);
}

