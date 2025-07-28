package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctRiskEvaluationEntity;
import com.minigod.zero.bpm.vo.AcctRiskEvaluationVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 风险评测记录表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface AcctRiskEvaluationMapper extends BaseMapper<AcctRiskEvaluationEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRiskEvaluation
	 * @return
	 */
	List<AcctRiskEvaluationVO> selectAcctRiskEvaluationPage(IPage page, AcctRiskEvaluationVO acctRiskEvaluation);


	AcctRiskEvaluationEntity selectNewRiskLevelByCustId(Long custId);
}
