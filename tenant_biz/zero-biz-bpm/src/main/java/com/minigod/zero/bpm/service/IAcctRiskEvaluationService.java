package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.entity.AcctRiskEvaluationEntity;
import com.minigod.zero.bpm.vo.AcctRiskEvaluationVO;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 风险评测记录表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IAcctRiskEvaluationService extends IService<AcctRiskEvaluationEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRiskEvaluation
	 * @return
	 */
	IPage<AcctRiskEvaluationVO> selectAcctRiskEvaluationPage(IPage<AcctRiskEvaluationVO> page, AcctRiskEvaluationVO acctRiskEvaluation);

	/**
	 * 获取客户基金风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	R<AcctRiskEvaluationEntity> getRiskEvaluationInfo(Long custId);

	/**
	 * 获取客户过去3个月内的基金风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	List<AcctRiskEvaluationEntity> getLastThreeMonthRecords(Long custId);

	/**
	 * 获取客户基金风险评测记录
	 *
	 * @param vo
	 * @return
	 */
	R<AcctRiskEvaluationEntity> getRiskEvaluationInfo(RiskEvaluationReqVo vo);


	/**
	 * 重置风险评测
	 *
	 * @param vo
	 * @return
	 */
	R resetRiskEvaluation(RiskEvaluationReqVo vo);
}
