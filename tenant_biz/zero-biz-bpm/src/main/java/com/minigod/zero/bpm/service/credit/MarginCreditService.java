package com.minigod.zero.bpm.service.credit;

import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import com.minigod.zero.core.tool.api.R;

public interface MarginCreditService {

	/**
	 * 提交信用额度调整申请
	 *
	 * @param marginCreditAdjustApplyDTO 客户信用额度申请入参协议
	 * @return R
	 */
	R applyMarginCredit(MarginCreditAdjustApplyDTO marginCreditAdjustApplyDTO);

	/**
	 * 获取客户信用额度申请状态
	 * @return R
	 */
	R getCreditAdjustStatus();

	/**
	 * 获取客户信用额度申请列表
	 * @return R
	 */
	R getCreditAdjustList();

	/**
	 * 获取客户信用额度设置
	 * @return
	 */
	R getClientCreditConf();

	R queryDetailByApplication(String tradeAccount);

	/**
	 * 手工提交信用额度
	 * @param marginCreditAdjustApplyDTO
	 * @return
	 */
	R manualSumbit(MarginCreditAdjustApplyDTO marginCreditAdjustApplyDTO);
}
