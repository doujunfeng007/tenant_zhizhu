package com.minigod.zero.cust.feign;

import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.apivo.CustTradePushInfoVO;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.service.IAccountLoginService;
import com.minigod.zero.cust.service.ICustOldPasswordService;
import com.minigod.zero.cust.service.ITradeUblockService;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;


/**
 * 交易服务接口
 */
@Slf4j
@ApiIgnore()
@RestController
public class CustTradeClient implements ICustTradeClient {
	@Resource
	private CustAccountInfoMapper custAccountInfoMapper;
	@Resource
	private IAccountLoginService accountLoginService;
	@Resource
	private ICustOldPasswordService custOldPasswordService;
	@Resource
	private ITradeUblockService tradeUblockService;

	@Override
	@PostMapping(CUST_CURRENT_ACCT)
	public R<CustAccountVO> custCurrentAccount(Long custId) {
		CustAccountVO custAccount = custAccountInfoMapper.getCurrentAccount(custId);
		if (custAccount == null) {
			return R.fail("无该客户有效账号信息");
		}
		return R.data(custAccount);
	}

	@Override
	@PostMapping(GET_CUST_TRADE_PUSH_INFO)
	public R<CustTradePushInfoVO> getCustTradePushInfo(String capitalAccount) {
		CustTradePushInfoVO tradePushInfo = custAccountInfoMapper.getCustTradePushInfo(capitalAccount);
		if (tradePushInfo == null) {
			return R.fail("无该客户有效账号信息");
		}
		return R.data(tradePushInfo);
	}

	@Override
	@GetMapping(CHECK_OLD_TRADE_PWD)
	public R checkOldTradePwd(String tradeAccount, String password) {
		TradeUnlockReq req = new TradeUnlockReq();
		req.setTradeAccount(tradeAccount);
		req.setPassword(password);
		return accountLoginService.validTradePwd(req);
	}

	@Override
	@PostMapping(ACTIVE_TRADE_PWD)
	public R activeTradePwd(Long custId, String tradeAccount) {
		custOldPasswordService.activeTradePwd(custId, tradeAccount);
		return R.success();
	}

	@Override
	@PostMapping(VALID_PWD)
	public R validPwd(String password, String tradeAccount) {
		TradeUnlockReq req = new TradeUnlockReq();
		req.setPassword(password);
		req.setTradeAccount(tradeAccount);
		return tradeUblockService.validTradePwd(req);
	}

	@Override
	@PostMapping(CHECK_OLD_PWD)
	public R checkOldPwd(String tradeAccount, String password) {
		// supperApp存量用户密码表如果能校验通过，需用之前的默认密码校验 TODO
		if (custOldPasswordService.checkOldTradePwd(null, tradeAccount, password)) {
			return R.success();
		} else {
			return R.fail();
		}
	}

}
