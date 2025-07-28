package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.cms.vo.PayeeBankListDTO;
import com.minigod.zero.cms.vo.PayeeBankListVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ICashPayeeBankDetailClient {

	String FIND_SUPPORT_INFO_BY_DEPOSIT = AppConstant.FEIGN_API_PREFIX + "/findSupportInfoByDeposit";
	String FIND_SUPPORT_BANKLIST = AppConstant.FEIGN_API_PREFIX + "/bankList";

	/**
	 * 根据银行获取转账类型信息
	 *
	 * @param depositId
	 * @return
	 */
	@PostMapping(FIND_SUPPORT_INFO_BY_DEPOSIT)
	Map<String, Object> findSupportInfoByDeposit(@RequestParam("depositId") Long depositId);

	@PostMapping(FIND_SUPPORT_BANKLIST)
	R<List<PayeeBankListVO>> bankList(@RequestBody  PayeeBankListDTO payeeBankListDTO);

}
