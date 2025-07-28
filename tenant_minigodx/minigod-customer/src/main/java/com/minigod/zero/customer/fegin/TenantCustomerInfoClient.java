package com.minigod.zero.customer.fegin;

import com.alibaba.fastjson.JSON;
import com.minigod.common.exceptions.BusinessException;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.ITradeUblockService;
import com.minigod.zero.customer.api.service.PayService;
import com.minigod.zero.customer.back.service.ICustomerInfoService;
import com.minigod.zero.customer.dto.AmountDTO;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.mapper.CustomerFinancingAccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX)
public class TenantCustomerInfoClient {

	private final PayService payService;
	private final ITradeUblockService tradeUblockService;
	private final CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@RequestMapping(value = "/tenant/checkTradePwd", method = {RequestMethod.POST, RequestMethod.GET})
	R checkPwd(@RequestParam("custId") Long custId,
			   @RequestParam("pwd") String pwd) {
		return tradeUblockService.tradeUnlock(custId, pwd);
	}

	/**
	 * 扣款接口
	 *
	 * @param amountDTO
	 * @return
	 */
	@RequestMapping(value = "/tenant/deduction", method = {RequestMethod.POST})
	R deduction(@RequestBody AmountDTO amountDTO) {
		Long custId = amountDTO.getCustId();
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			throw new BusinessException("解锁失败，统一账号异常");
		}
		amountDTO.setAccountId(financingAccount.getAccountId());
		return payService.scratchButton(amountDTO);
	}

}
