package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.cms.vo.DepositBankRespVo;
import com.minigod.zero.cms.vo.SecDepositTypeVo;
import com.minigod.zero.cms.entity.oms.CashDepositBankEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.support.Kv;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ICashDepositBankClient {

	String CASH_DEPOSIT_BANK_DETAIL = AppConstant.FEIGN_API_PREFIX + "/cashDepositBankDetail";
	String FIND_DEPOSIT_BANK_SETTING = AppConstant.FEIGN_API_PREFIX + "/findDepositBankSetting";
	String FIND_DEPOSIT_TYPE_SETTING = AppConstant.FEIGN_API_PREFIX + "/findDepositTypeSetting";
	String FIND_DEPOSIT_BANK_BY_AREA = AppConstant.FEIGN_API_PREFIX + "/findDepositBankByArea";

	@PostMapping(CASH_DEPOSIT_BANK_DETAIL)
	CashDepositBankEntity cashDepositBankDetail(@RequestBody CashDepositBankEntity cashDepositBankEntity);

	/**
	 * 获取入金银行配置信息
	 *
	 * @param bankType
	 * @return
	 */
	@PostMapping(FIND_DEPOSIT_BANK_SETTING)
	List<DepositBankRespVo> findDepositBankSetting(@RequestParam("bankType") Integer bankType);

	/**
	 * 获取入金方式配置列表信息
	 *
	 * @param bankType
	 * @param key
	 * @return
	 */
	@PostMapping(FIND_DEPOSIT_TYPE_SETTING)
	List<SecDepositTypeVo> findDepositTypeSetting(@RequestParam("bankType") Integer bankType, @RequestParam("key") String key);

	/**
	 * 根据区域获取按汇款方式转账的银行列表
	 *
	 * @param custId
	 * @param bankType
	 * @return
	 */
	@PostMapping(FIND_DEPOSIT_BANK_BY_AREA)
	Kv findDepositBankByArea(@RequestParam("custId") Long custId, @RequestParam("bankType") Integer bankType);
}
