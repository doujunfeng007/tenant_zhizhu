package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.cms.entity.oms.CashWithdrawalsBankEntity;
import com.minigod.zero.cms.vo.SecWithdrawalsBankVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ICashWithdrawalsBankClient {

	String FIND_WITHDRAWALS_BANK_SETTING = AppConstant.FEIGN_API_PREFIX + "/findWithdrawalsBankSetting";
	String WITHDRAWALS_BANK_FIND = AppConstant.FEIGN_API_PREFIX + "/withdrawalsBankFind";
	String LIKE_RECEIPT_BANK_NAME = AppConstant.FEIGN_API_PREFIX + "/likeReceiptBankName";

	/**
	 * 获取出金银行卡配置列表信息
	 *
	 * @param bankType
	 * @return
	 */
	@PostMapping(FIND_WITHDRAWALS_BANK_SETTING)
	List<SecWithdrawalsBankVo> findWithdrawalsBankSetting(@RequestParam("bankType") Integer bankType);

	/**
	 * 获取香港出金银行配置信息
	 *
	 * @param bankType
	 * @return
	 */
	@PostMapping(WITHDRAWALS_BANK_FIND)
    CashWithdrawalsBankEntity withdrawalsBankFind(@RequestParam("bankType") Integer bankType);

	/**
	 * 获取香港出金银行配置信息
	 *
	 * @param bankName
	 * @return
	 */
	@PostMapping(LIKE_RECEIPT_BANK_NAME)
	CashWithdrawalsBankEntity likeReceiptBankName(@RequestParam("bankName") String bankName);
}
