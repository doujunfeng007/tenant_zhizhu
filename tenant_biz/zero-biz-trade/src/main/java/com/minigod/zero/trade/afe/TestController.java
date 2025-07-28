package com.minigod.zero.trade.afe;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.afe.req.StockMarginRatioRequest;
import com.minigod.zero.trade.feign.ICounterOpenAccountClient;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.vo.sjmb.req.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 业务接口
 * @ClassName: DbsBankController
 * @Author chenyu
 * @Version 1.0
 */
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/trade/afe")
@Api(value = "AFE测试接口", tags = {"AFE测试接口"})
public class TestController {



	@Autowired
	private ICounterOpenAccountClient counterOpenAccountClient;

	@Autowired
	private  IFundService iFundService;

//	@Autowired
//	private IAccountService accountService;

	@ApiOperation("银行转账(Dbs TT 海外转账)")
	@PostMapping("/deposit")
	public R deposit(@RequestBody FundDepositReq fundDepositReq) {
		return iFundService.deposit(fundDepositReq);
	}

	@PostMapping("/unFreezeWithdraw")
	public R unFreezeWithdraw(@RequestBody FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq) {
		return iFundService.unFreezeWithdraw(fundUnFreezeWithdrawReq);
	}

	@ApiOperation("可融资股票")
	@PostMapping("/stockMarginRatio")
	public R stockMarginRatio(@RequestBody StockMarginRatioRequest stockMarginRatio) {
		return iFundService.getStockMarginRatio();
	}

	@ApiOperation("设置可融资股票")
	@PostMapping("/stockMarginRatio/setting")
	public R stockMarginRatioSetting(@RequestBody StockMarginSettingReq stockMarginSettingReq) {
		return iFundService.stockMarginRatioSetting(stockMarginSettingReq);
	}

	@ApiOperation("资金取出")
	@PostMapping("/withdrawal")
	public R withdrawal(@RequestBody FundWithdrawalReq fundWithdrawalReq) {
		return iFundService.withdrawal(fundWithdrawalReq);
	}

	@ApiOperation("资金冻结")
	@PostMapping("/freeze")
	public R freeze(@RequestBody FundFreezeReq fundFreezeReq) {
		return iFundService.freeze(fundFreezeReq);
	}

	@ApiOperation("资金解冻")
	@PostMapping("/unFreeze")
	public R unFreeze(@RequestBody FundUnFreezeReq fundFreezeReq) {
		return iFundService.unFreeze(fundFreezeReq);
	}

//	@ApiOperation("开户")
//	@PostMapping("/openAccount")
//	public R openAccount(@RequestBody OpenAccountReq openAccountReq) {
//		return accountService.openAccount(openAccountReq,false);
//	}

}
