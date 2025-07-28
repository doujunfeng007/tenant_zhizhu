package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctRiskQuestionEntity;
import com.minigod.zero.bpm.mapper.AcctRiskQuestionMapper;
import com.minigod.zero.bpm.service.IAcctRiskQuestionService;
import com.minigod.zero.bpm.vo.AcctRiskQuestionVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionVo;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 风险评测题库 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class AcctRiskQuestionServiceImpl extends ServiceImpl<AcctRiskQuestionMapper, AcctRiskQuestionEntity> implements IAcctRiskQuestionService {

	@Override
	public IPage<AcctRiskQuestionVO> selectAcctRiskQuestionPage(IPage<AcctRiskQuestionVO> page, AcctRiskQuestionVO acctRiskQuestion) {
		return page.setRecords(baseMapper.selectAcctRiskQuestionPage(page, acctRiskQuestion));
	}

	@Override
	public R<List<FundRiskQuestionVo>> findQuestion(RiskEvaluationReqVo vo) {
		if(null == vo.getQuestionType()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return R.data(baseMapper.findQuestion(vo));
	}


}
