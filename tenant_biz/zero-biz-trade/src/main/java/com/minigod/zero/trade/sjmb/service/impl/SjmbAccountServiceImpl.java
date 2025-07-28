package com.minigod.zero.trade.sjmb.service.impl;

import cn.hutool.core.date.DateUtil;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IAccountService;
import com.minigod.zero.trade.sjmb.common.MessageCode;
import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.req.brokerapi.AccountAddReq;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import com.minigod.zero.trade.sjmb.resp.brokerapi.AccountAddResp;
import com.minigod.zero.trade.sjmb.service.ICounterService;
import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_SJMB;

/**
 * @author chen
 * @ClassName SjmbAccountServiceImpl.java
 * @Description TODO
 * @createTime 2024年04月13日 18:32:00
 */
@Service
@ConditionalOnProperty(value="trade.type",havingValue = MULTI_SERVER_TYPE_SJMB)
public class SjmbAccountServiceImpl implements IAccountService {


	@Autowired
	private ICounterService counterService;

	@Override
	public R<OpenAccountVO> openAccount(OpenAccountReq openAccountReq,boolean isRepeat) {
		List<AccountAddReq> accountAddReqList = new ArrayList<>();

		OpenAccountVO result = new OpenAccountVO();

		AccountAddReq accountAddReq = new AccountAddReq();
		accountAddReq.setBrokerAccountId(openAccountReq.getTradeAccount());
		accountAddReq.setFirstName(openAccountReq.getFirstName());
		accountAddReq.setMiddleName(openAccountReq.getMiddleName());
		accountAddReq.setLastName(openAccountReq.getLastName());
		accountAddReq.setFirstNameEn(openAccountReq.getFirstNameEn());
		accountAddReq.setLastNameEn(openAccountReq.getLastNameEn());
		accountAddReq.setNationality(openAccountReq.getNationality());

		Date birthDate = DateUtil.parse(openAccountReq.getBirthDate(), "yyyy-MM-dd");
		accountAddReq.setBirthDate(DateUtil.format(birthDate, "yyyy/MM/dd"));
		accountAddReq.setGender(openAccountReq.getGender());
		accountAddReq.setCountryCallingCode(openAccountReq.getAreaCode());
		accountAddReq.setMobile(openAccountReq.getPhoneNumber());
		accountAddReq.setEmail(openAccountReq.getEmail());
		if (1 == openAccountReq.getIdType().intValue() || 2 == openAccountReq.getIdType().intValue() || 3 == openAccountReq.getIdType().intValue()) {
			accountAddReq.setIdType("ID_CARD");
		} else if (4 == openAccountReq.getIdType().intValue()) {
			accountAddReq.setIdType("PASSPORT");
		} else if (5 == openAccountReq.getIdType().intValue()) {
			accountAddReq.setIdType("DL");
		} else {
			accountAddReq.setIdType("OTHER");
		}
		accountAddReq.setIdNumber(openAccountReq.getIdNo());
		accountAddReq.setContactState(openAccountReq.getContactState());
		accountAddReq.setContactCity(openAccountReq.getContactCity());
		accountAddReq.setContactDistrict(openAccountReq.getContactDistrict());
		accountAddReq.setContactAddress(openAccountReq.getContactAddress());
		accountAddReq.setContactPostal(openAccountReq.getContactPostal());
		accountAddReq.setAccountTradeStatus("OPEN"); //账号交易状态
		accountAddReq.setBaseCurrency("HKD");
		if (1 == openAccountReq.getAccountType().intValue()) {
			accountAddReq.setAccountType("CASH");
		} else {
			accountAddReq.setAccountType("MARGIN");
		}
		accountAddReq.setBrokerPractitioner(openAccountReq.getBrokerPractitioner());
		accountAddReq.setDecisionMaker(openAccountReq.getDecisionMaker());
		accountAddReq.setLanguage(openAccountReq.getLanguage());
		accountAddReq.setUserRegion(openAccountReq.getUserRegion());
		accountAddReq.setSharingBp(false);//不同资金组间是否共享购买力
		List<String> tradePerm = new ArrayList<>();
		if (1 == openAccountReq.getIsOpenCnMarket()) {
			tradePerm.add("CN_EQTY");
		}
		if (1 == openAccountReq.getIsOpenHkMarket()) {
			tradePerm.add("HK_EQTY");
			tradePerm.add("HK_WRNT");
		}
		if (1 == openAccountReq.getIsOpenUsMarket()) {
			tradePerm.add("US_EQTY");
		}
		accountAddReq.setAutoHBCAN(true); // 自动分配香港识别码
		accountAddReq.setTradePermissions(tradePerm);
		accountAddReq.setCountryOfTax(openAccountReq.getCountryOfTax());
		accountAddReq.setHasTaxNumber(true);
		accountAddReq.setTaxNumber(openAccountReq.getTaxNumber());
		accountAddReq.setNoTaxReason("OTHERS");
		accountAddReq.setNoTaxReasonDesc("OTHERS");
		accountAddReqList.add(accountAddReq);

		SjmbResponse response = counterService.brokerApiSend(accountAddReqList, SjmbFunctionsUrlEnum.ACCOUNT_ADD_BATCH, HttpMethod.POST);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			List<AccountAddResp> accountAddResps = JSONUtil.getList(response.getData().toString(), AccountAddResp.class);
			if (accountAddResps != null && accountAddResps.size() > 0) {
				result.setTradeAccount(accountAddResps.get(0).getAccountId());
			}

		}
		return R.data(result);
	}

	@Override
	public R updateAccount(ModifyAccountReq modifyAccountReq) {
		return null;
	}
}
