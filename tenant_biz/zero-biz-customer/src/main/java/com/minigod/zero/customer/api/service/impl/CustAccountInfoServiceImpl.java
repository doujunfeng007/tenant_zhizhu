package com.minigod.zero.customer.api.service.impl;

import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.api.service.ICustAccountInfoService;
import com.minigod.zero.customer.back.service.ICustomerTradeAccountService;
import com.minigod.zero.customer.back.service.ICustomerTradeSubAccountService;
import com.minigod.zero.customer.dto.ReqUpdateCustDTO;
import com.minigod.zero.customer.emuns.BusinessTypeEnums;
import com.minigod.zero.customer.emuns.MarketTypeEnums;
import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import com.minigod.zero.customer.entity.CustomerTradeSubAccountEntity;
import com.minigod.zero.customer.enums.StatusEnum;
import com.minigod.zero.customer.vo.CustAccountInfoVO;
import com.minigod.zero.customer.vo.CustCapitalInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CustAccountInfoServiceImpl implements ICustAccountInfoService {

	@Autowired
	private ICustomerTradeAccountService customerTradeAccountService;

	@Autowired
	private ICustomerTradeSubAccountService customerTradeSubAccountService;

	@Override
	public List<CustAccountInfoVO> getAccountList() {
		String accountId = AuthUtil.getTenantUser().getAccount();
		List<CustAccountInfoVO> resp = new ArrayList<>();

		CustomerTradeAccountEntity accountInfo = customerTradeAccountService.selectTradeByAccountAndType(accountId, BusinessTypeEnums.FUND.getBusinessType());

		CustAccountInfoVO accountInfoResp = BeanUtil.copy(accountInfo, CustAccountInfoVO.class);

		List<CustomerTradeSubAccountEntity> subAccountList = customerTradeSubAccountService.selecctSubAccountByTradeId(accountInfo.getId());

		List<CustCapitalInfoVO> capitalInfos = new ArrayList<>();
		subAccountList.stream().forEach(capitalInfo -> {
			CustCapitalInfoVO capitalInfoResp = new CustCapitalInfoVO();
			capitalInfoResp.setTradeAccount(capitalInfo.getTradeAccount());
			capitalInfoResp.setCapitalAccount(capitalInfo.getSubAccount());
			capitalInfos.add(capitalInfoResp);
		});
		accountInfoResp.setCapitalAccounts(capitalInfos);
		resp.add(accountInfoResp);
		return resp;
	}

	@Override
	public List<CustCapitalInfoVO> getCapitalList(String moneyType) {
		String accountId = AuthUtil.getTenantUser().getAccount();
		List<CustCapitalInfoVO> resp = new ArrayList<>();
		CustomerTradeAccountEntity currentAccount = customerTradeAccountService.selectTradeByAccountAndType(accountId, BusinessTypeEnums.FUND.getBusinessType());
		if (currentAccount == null) {
			return resp;
		}
		List<CustomerTradeSubAccountEntity> subAccountList = customerTradeSubAccountService.selecctSubAccountByTradeId(currentAccount.getId());
		for (CustomerTradeSubAccountEntity capitalInfo : subAccountList) {
			CustCapitalInfoVO capitalInfoResp = new CustCapitalInfoVO();
			capitalInfoResp.setTradeAccount(capitalInfo.getTradeAccount());
			capitalInfoResp.setCapitalAccount(capitalInfo.getSubAccount());
			resp.add(capitalInfoResp);
		}
		return resp;
	}

	@Override
	public R switchTradeAcct(String tradeAccount) {

		String accountId = AuthUtil.getTenantUser().getAccount();
		CustomerTradeAccountEntity accountInfo = customerTradeAccountService.selectTradeByAccountAndType(accountId, BusinessTypeEnums.FUND.getBusinessType());
        if(null == accountInfo  || !accountInfo.getTradeAccount().equals(tradeAccount)){
			return R.fail("切换交易账号失败，请检查交易账号是否正确");
		}
		accountInfo.setIsCurrent(StatusEnum.YES.getCode());
		accountInfo.setUpdateTime(new Date());
		customerTradeAccountService.updateById(accountInfo);
		return R.success("默认交易账号切换成功");
	}

	@Override
	public R switchCapitalAcct(ReqUpdateCustDTO req) {
		CustomerTradeSubAccountEntity capitalInfo =new CustomerTradeSubAccountEntity();
		capitalInfo.setIsMaster(StatusEnum.YES.getCode());
		capitalInfo.setUpdateTime(new Date());
		customerTradeSubAccountService.updateMasterAccount(capitalInfo, req.getCapitalAccount(),req.getTradeAccount(), MarketTypeEnums.FUND.getMarketType());
		return R.success("默认资金账号切换成功");
	}

}
