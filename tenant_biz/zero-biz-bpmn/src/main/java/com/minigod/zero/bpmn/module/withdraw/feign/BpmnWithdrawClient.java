package com.minigod.zero.bpmn.module.withdraw.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositFundsMapper;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.mapper.ClientEddaFundApplicationMapper;
import com.minigod.zero.bpmn.module.edda.mapper.ClientEddaInfoApplicationMapper;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.exchange.mapper.CustomerCurrencyExchangeInfoMapper;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExcVO;
import com.minigod.zero.bpmn.module.feign.IBpmnWithdrawClient;
import com.minigod.zero.bpmn.module.feign.vo.CustStatementVO;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundWithdrawInfoMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawApplicationService;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.minigod.zero.bpmn.module.feign.IBpmnBankCardClient.BIND_BANK_CARD;

/**
 * @ClassName: com.minigod.zero.bpmn.module.withdraw.feign.BpmnWithdrawsClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/16 19:23
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class BpmnWithdrawClient implements IBpmnWithdrawClient {
	@Autowired
	IClientFundWithdrawApplicationService clientFundWithdrawApplicationService;
	@Autowired
	ClientFundWithdrawInfoMapper clientFundWithdrawInfoMapper;
	@Autowired
	SecDepositFundsMapper secDepositFundsMapper;
	@Autowired
	ClientEddaFundApplicationMapper clientEddaFundApplicationMapper;
	@Autowired
	CustomerCurrencyExchangeInfoMapper customerCurrencyExchangeInfoMapper;
	@Autowired
	AccountOpenInfoMapper accountOpenInfoMapper;

	@Override
	@ApiOperation("获取info信息")
	@GetMapping(GET_INFO_BY_APPLICATIONID)
	public R<ClientFundWithdrawApplicationVo> getWithdrawInfoByApplicationId(String applicationId) {
		ClientFundWithdrawApplicationVo vo = clientFundWithdrawApplicationService.queryByApplicationId(applicationId);

		return R.data(vo);
	}

	@Override
	public R<ClientFundWithdrawApplicationVo> getWithdrawMapByCustId(CustStatementVO custStatementVO) {

		AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoMapper.queryByUserId(Long.valueOf(custStatementVO.getCustId()));

		LambdaQueryWrapper<SecDepositFundsEntity> fundsWrapper = new LambdaQueryWrapper<>();
		fundsWrapper.eq(SecDepositFundsEntity::getState, DepositStatusEnum.SecDepositFundsStatus.FINISH.getCode());
		fundsWrapper.between(SecDepositFundsEntity::getSettlementDt, custStatementVO.getStartDate(), custStatementVO.getEndDate());
		fundsWrapper.eq(SecDepositFundsEntity::getUserId, custStatementVO.getCustId());
		List<SecDepositFundsEntity> depositList = secDepositFundsMapper.selectList(fundsWrapper);

		LambdaQueryWrapper<ClientEddaFundApplicationEntity> eddaWrapper = new LambdaQueryWrapper<>();
		eddaWrapper.eq(ClientEddaFundApplicationEntity::getBankState, SystemCommonEnum.EDDABankStateTypeEnum.AUTHORIZING.getValue());
		eddaWrapper.between(ClientEddaFundApplicationEntity::getSettlementDt, custStatementVO.getStartDate(), custStatementVO.getEndDate());
		eddaWrapper.eq(ClientEddaFundApplicationEntity::getClientId, accountOpenInfoVO.getClientId());
		List<ClientEddaFundApplicationEntity> eddalist = clientEddaFundApplicationMapper.selectList(eddaWrapper);

		/*LambdaQueryWrapper<ClientFundWithdrawInfo> withdrawWrapper = new LambdaQueryWrapper<>();
		withdrawWrapper.eq(ClientFundWithdrawInfo::getStatus, BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS);
		withdrawWrapper.between(ClientFundWithdrawInfo::getSettlementDt, custStatementVO.getStartDate(), custStatementVO.getEndDate());
		withdrawWrapper.eq(ClientFundWithdrawInfo::getClientId, custStatementVO.getCustId());
		clientFundWithdrawInfoMapper.selectList(withdrawWrapper);*/


		LambdaQueryWrapper<CustomerCurrencyExchangeInfo> exchangeWrapper = new LambdaQueryWrapper<>();
		exchangeWrapper.eq(CustomerCurrencyExchangeInfo::getProcessingStatus, 4);
		//exchangeWrapper.between(CustomerCurrencyExchangeInfo::getSettlementDt, custStatementVO.getStartDate(), custStatementVO.getEndDate());
		exchangeWrapper.eq(CustomerCurrencyExchangeInfo::getCustId, custStatementVO.getCustId());
		List<CustomerCurrencyExchangeInfo> exchangeList = customerCurrencyExchangeInfoMapper.selectList(exchangeWrapper);
		//兑出是出金
		// 根据customerId分组
		Map<Integer, List<CustomerCurrencyExchangeInfo>> exchangeCollect = exchangeList.stream()
			.collect(Collectors.groupingBy(CustomerCurrencyExchangeInfo::getExchangeDirection));

		ArrayList<CurrencyExcVO> wAmountList = new ArrayList<>();
		ArrayList<CurrencyExcVO> dAmountList = new ArrayList<>();
		for (Integer integer : exchangeCollect.keySet()) {
			List<CustomerCurrencyExchangeInfo> customerCurrencyExchangeInfos = exchangeCollect.get(integer);
			for (CustomerCurrencyExchangeInfo info : customerCurrencyExchangeInfos) {
				String toCurrency = CurrencyExcEnum.CurrencyConversion.getToCurrency(info.getExchangeDirection());
				CurrencyExcVO currencyExcVO = new CurrencyExcVO();
				currencyExcVO.setCurrency(toCurrency);
				currencyExcVO.setAmount(info.getAmountIn());
				dAmountList.add(currencyExcVO);

				String fromCurrency = CurrencyExcEnum.CurrencyConversion.getFromCurrency(info.getExchangeDirection());
				CurrencyExcVO currencyExcVO2 = new CurrencyExcVO();
				currencyExcVO2.setCurrency(fromCurrency);
				currencyExcVO2.setAmount(info.getAmountIn());
				wAmountList.add(currencyExcVO2);
			}
		}


		return null;
	}

	@Override
	public R<ClientFundWithdrawApplicationVo> getDepositMapByCustId(CustStatementVO custStatementVO) {
		return null;
	}
}
