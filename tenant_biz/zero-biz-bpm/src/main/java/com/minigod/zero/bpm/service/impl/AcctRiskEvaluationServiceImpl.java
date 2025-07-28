package com.minigod.zero.bpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.entity.AcctRiskEvaluationEntity;
import com.minigod.zero.bpm.mapper.AcctRiskEvaluationMapper;
import com.minigod.zero.bpm.service.IAcctRiskEvaluationService;
import com.minigod.zero.bpm.vo.AcctRiskEvaluationVO;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 风险评测记录表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class AcctRiskEvaluationServiceImpl extends ServiceImpl<AcctRiskEvaluationMapper, AcctRiskEvaluationEntity> implements IAcctRiskEvaluationService {

	@Override
	public IPage<AcctRiskEvaluationVO> selectAcctRiskEvaluationPage(IPage<AcctRiskEvaluationVO> page, AcctRiskEvaluationVO acctRiskEvaluation) {
		return page.setRecords(baseMapper.selectAcctRiskEvaluationPage(page, acctRiskEvaluation));
	}

	/**
	 * 返回当前上下文中的用户风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public R<AcctRiskEvaluationEntity> getRiskEvaluationInfo(Long custId) {
		LambdaQueryWrapper<AcctRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AcctRiskEvaluationEntity::getCustId, custId);
		riskEvaluationWrapper.orderByDesc(AcctRiskEvaluationEntity::getCreateTime);
		riskEvaluationWrapper.last("limit 1");
		AcctRiskEvaluationEntity riskEvaluation = getOne(riskEvaluationWrapper);
		return R.data(riskEvaluation);
	}

	/**
	 * 获取客户过去3个月内的基金风险评测记录
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public List<AcctRiskEvaluationEntity> getLastThreeMonthRecords(Long custId) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -3);
		Date lastThreeMonthTime = calendar.getTime();
		LambdaQueryWrapper<AcctRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AcctRiskEvaluationEntity::getCustId, custId);
		riskEvaluationWrapper.between(AcctRiskEvaluationEntity::getCreateTime, lastThreeMonthTime, new Date());
		riskEvaluationWrapper.orderByDesc(AcctRiskEvaluationEntity::getCreateTime);
		List<AcctRiskEvaluationEntity> riskEvaluations = baseMapper.selectList(riskEvaluationWrapper);
		return riskEvaluations;
	}

	@Override
	public R<AcctRiskEvaluationEntity> getRiskEvaluationInfo(RiskEvaluationReqVo vo) {
		if (null == vo.getCustId()) return R.fail(ResultCode.PARAM_VALID_ERROR);

		LambdaQueryWrapper<AcctRiskEvaluationEntity> riskEvaluationWrapper = new LambdaQueryWrapper<>();
		riskEvaluationWrapper.eq(AcctRiskEvaluationEntity::getCustId, vo.getCustId());
		riskEvaluationWrapper.orderByDesc(AcctRiskEvaluationEntity::getCreateTime);
		riskEvaluationWrapper.last("limit 1");
		AcctRiskEvaluationEntity riskEvaluation = getOne(riskEvaluationWrapper);
		return R.data(riskEvaluation);
	}

	@Override
	public R resetRiskEvaluation(RiskEvaluationReqVo vo) {
		if (null == vo.getId()) return R.fail(ResultCode.PARAM_VALID_ERROR);

		AcctRiskEvaluationEntity updRisk = new AcctRiskEvaluationEntity();
		updRisk.setId(vo.getId());
		updRisk.setRetestSts(1);
		updateById(updRisk);

		return R.success();
	}


}
