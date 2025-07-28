package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskEvaluationDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskEvaluationEntity;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 风险评测记录表 Mapper 接口
 *
 * @author eric
 * @since 2024-08-20 11:24:02
 */
public interface AccountRiskEvaluationMapper extends BaseMapper<AccountRiskEvaluationEntity> {

	/**
	 * 风险评测记录自定义分页查询
	 *
	 * @param page
	 * @param acctRiskEvaluation
	 * @return
	 */
	List<AccountRiskEvaluationEntity> selectAcctRiskEvaluationPage(IPage page, @Param("acctRiskEvaluation") AccountRiskEvaluationDTO acctRiskEvaluation);

	/**
	 * 获取指定用户最新风险等级测评
	 *
	 * @param custId
	 * @return
	 */
	AccountRiskEvaluationEntity selectNewRiskLevelByCustId(Long custId);
}
