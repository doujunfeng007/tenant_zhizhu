package com.minigod.zero.bpm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.entity.AcctRiskQuestionOptionEntity;
import com.minigod.zero.bpm.mapper.AcctRiskQuestionOptionMapper;
import com.minigod.zero.bpm.service.IAcctRiskQuestionOptionService;
import com.minigod.zero.bpm.vo.AcctRiskQuestionOptionVO;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionOptionVo;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import org.springframework.stereotype.Service;

/**
 * 风险评测题库-选项 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class AcctRiskQuestionOptionServiceImpl extends ServiceImpl<AcctRiskQuestionOptionMapper, AcctRiskQuestionOptionEntity> implements IAcctRiskQuestionOptionService {

	@Override
	public IPage<AcctRiskQuestionOptionVO> selectAcctRiskQuestionOptionPage(IPage<AcctRiskQuestionOptionVO> page, AcctRiskQuestionOptionVO acctRiskQuestionOption) {
		return page.setRecords(baseMapper.selectAcctRiskQuestionOptionPage(page, acctRiskQuestionOption));
	}

	@Override
	public R<FundRiskQuestionOptionVo> getQuestionOption(RiskEvaluationReqVo vo) {
		if(null == vo.getQuestionId() || null == vo.getOptionId()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return R.data(baseMapper.getQuestionOption(vo));
	}


}
