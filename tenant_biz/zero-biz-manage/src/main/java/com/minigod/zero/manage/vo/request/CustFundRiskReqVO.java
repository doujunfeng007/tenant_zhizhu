package com.minigod.zero.manage.vo.request;

import com.minigod.zero.manage.vo.CustFundRiskVO;
import com.minigod.zero.core.tool.api.SNVersion;
import lombok.Data;

/**
 * 基金风险评测请求值对象
 */

@Data
public class CustFundRiskReqVO extends SNVersion {

	private static final long serialVersionUID = 1L;

	private CustFundRiskVO params;
}
