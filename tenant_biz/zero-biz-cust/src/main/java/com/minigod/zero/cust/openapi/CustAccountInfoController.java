package com.minigod.zero.cust.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.req.ReqUpdateCustVO;
import com.minigod.zero.cust.service.ICustAccountInfoService;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.vo.response.CustAccountInfoResp;
import com.minigod.zero.cust.vo.response.CustCapitalInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/account")
@Api(value = "用户账户相关", tags = "用户账户相关")
public class CustAccountInfoController {

	@Resource
	private ICustInfoService custInfoService;
	@Resource
	private ICustAccountInfoService custAccountInfoService;

	/**
	 * 获取客户交易账号列表
	 */
	@GetMapping("/get_cust_account_list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取客户交易账号列表", notes = "获取客户交易账号列表")
	public R<List<CustAccountInfoResp>> getAccountList() {
		return R.data(custAccountInfoService.getAccountList());
	}

	/**
	 * 获取客户资金账号列表
	 */
	@GetMapping("/get_cust_capital_list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取客户资金账号列表", notes = "获取客户资金账号列表")
	public R<List<CustCapitalInfoResp>> getCapitalList(@RequestParam(required = false) Integer moneyType) {
		return R.data(custAccountInfoService.getCapitalList(moneyType));
	}

	@PostMapping("/switch_trade_acct")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "切换交易账号", notes = "修改默认交易账号")
	public R switchTradeAcct(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtils.isBlank(req.getTradeAccount())) {
			return R.fail("交易账号不能为空");
		}
		return custAccountInfoService.switchTradeAcct(req.getTradeAccount());
	}

	@PostMapping("/switch_capital_acct")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "切换资金账号", notes = "修改默认资金账号")
	public R switchCapitalAcct(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtils.isAnyBlank(req.getTradeAccount(), req.getCapitalAccount())) {
			return R.fail("交易账号和资金账号不能为空");
		}
		return custAccountInfoService.switchCapitalAcct(req);
	}

	@GetMapping("/switch_esop_acct")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "切换个人/ESOP户", notes = "切换个人/ESOP户")
	public R switchEsopCust(@RequestParam Integer acctType) {
		if (acctType == null) {
			return R.fail("账户类型不能为空");
		}
		Long custId = custAccountInfoService.switchEsopCust(acctType);
		if (custId == null) {
			return R.fail("切换失败：还未绑定个人户");
		}
		if (custId == -1l) {
			return R.fail("切换失败，请稍后重试");
		}
		return custInfoService.fetchUserInfo(custId);
	}

}
