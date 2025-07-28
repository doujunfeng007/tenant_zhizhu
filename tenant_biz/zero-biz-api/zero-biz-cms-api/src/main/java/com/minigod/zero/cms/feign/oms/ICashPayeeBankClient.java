package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.cms.vo.CashPayeeBankListVo;
import com.minigod.zero.cms.vo.PayeeBankInfoReq;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ICashPayeeBankClient {

	String FIND_PAYEE_BANK_LIST = AppConstant.FEIGN_API_PREFIX + "/findPayeeBankList";

	/**
	 * 获取付款账户列表
	 * @param params
	 * @return
	 */
	@PostMapping(FIND_PAYEE_BANK_LIST)
	List<CashPayeeBankListVo> findPayeeBankList(@RequestBody PayeeBankInfoReq params);

}
