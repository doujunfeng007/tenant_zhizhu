package com.minigod.zero.bpm.service.openAccount;

import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.BpmFundAcctRespDto;
import com.minigod.zero.bpm.dto.BpmSecuritiesRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.RiskEvaluationReqDto;
import com.minigod.zero.bpm.dto.acct.resp.BpmResponseDto;
import com.minigod.zero.bpm.dto.acct.resp.RiskEvaluationRespDto;
import com.minigod.zero.bpm.dto.acct.resp.RiskQuestionRespDto;
import com.minigod.zero.bpm.vo.UserHkidrVo;
import com.minigod.zero.bpm.vo.request.YfundRiskTempVo;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

public interface IBpmAccountService {

	R<BpmAccountRespDto> getAccountRespDto(Long custId);

	R<BpmAccountRespDto> getAccountRespDto(String tradeAccount,String fundAccount);

	BpmSecuritiesRespDto getSecuritiesRespDto(Long custId);

	BpmTradeAcctRespDto getCurrentAcctInfo(Long custId);

	BpmTradeAcctRespDto selectAcctInfo(String tradeAccount,String fundAccount);

	BpmFundAcctRespDto getFundAcctRespDto(Long custId);

	/**
	 * 获取基金风险评测题库
	 * @return
	 */
	R<List<RiskQuestionRespDto>> riskQuestion(RiskEvaluationReqDto reqDto);

	R saveEvaluationTemp(RiskEvaluationReqDto reqDto);

	R<YfundRiskTempVo> evaluationTemp();

	R saveRiskEvaluation(RiskEvaluationReqDto reqDto);

	R<RiskEvaluationRespDto> getRiskEvaluationInfo();

	R<BpmResponseDto> caCertification(CaCertificationDto reqDto);

	R<BpmResponseDto> caCertificationStatus(CaCertificationDto reqDto);

	R bpmUpdateInfo(BpmSecuritiesRespDto dto);

	/**
	 * 获取用户是否需要获取授权
	 * 1.用户已开户
	 * 2.用户是个人户
	 * 3.账号不是基金单边户
	 * 4.账号在20220307之前开通
	 * @Description: 获取用户是否需要获取授权
	 * @date 2022年11月2日
	 */
	R needGrantHkidr(Long custId);

	/**
	 * 用户授权信息保存
	 * @Description: 用户授权信息保存
	 * @author yanghu.luo
	 * @param vo
	 * @date 2022年11月2日
	 */
	R grantHkidr(UserHkidrVo vo);

	R grantHkidrEsop(UserHkidrVo vo);


	/**
	 * 申请开通中华通
	 * @return
	 */
	R applyOpenZht();

	/**
	 * 查询客户交易账号信息
	 * @param custId
	 * @return
	 */
    R<BpmTradeAcctRespDto> bpmTradeAcctInfo(Long custId);

}
