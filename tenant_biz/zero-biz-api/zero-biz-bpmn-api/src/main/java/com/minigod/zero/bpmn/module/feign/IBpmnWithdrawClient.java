package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.bpmn.module.feign.vo.CustStatementVO;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: com.minigod.zero.bpmn.module.feign.IBpmnWithdrawClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/16 19:24
 * @Version: 1.0
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IBpmnWithdrawClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/withdraw";
	String GET_INFO_BY_APPLICATIONID = API_PREFIX + "/getWithdrawInfoByApplicationId";
	String GET_WITHDRAWMAP_BYCUST_ID = API_PREFIX + "/getWithdrawMapByCustId";
	String GET_DEPOSIT_BYCUST_ID = API_PREFIX + "/getDepositMapByCustId";
	@GetMapping(GET_INFO_BY_APPLICATIONID)
	R<ClientFundWithdrawApplicationVo> getWithdrawInfoByApplicationId(@RequestParam("applicationId") String applicationId);

	/**
	 * 出金map
	 * @param custStatementVO
	 * @return
	 */
	@PostMapping(GET_WITHDRAWMAP_BYCUST_ID)
	R<ClientFundWithdrawApplicationVo> getWithdrawMapByCustId(@RequestParam("CustStatementVO") CustStatementVO custStatementVO);

	/**
	 * 入金map
	 * @param custStatementVO
	 * @return
	 */
	@PostMapping(GET_DEPOSIT_BYCUST_ID)
	R<ClientFundWithdrawApplicationVo> getDepositMapByCustId(@RequestParam("CustStatementVO") CustStatementVO custStatementVO);
}
