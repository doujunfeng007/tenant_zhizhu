package com.minigod.zero.cust.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.service.ITradeAccountBindService;
import com.minigod.zero.cust.vo.TradeAccountBindVO;
import com.minigod.zero.cust.vo.TradeAccountBind2faVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 11:05
 * @Description: 交易账户绑定
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "交易账户绑定", tags = "交易账户绑定")
public class TradeAccountBindController extends ZeroController {

	@Resource
	private ITradeAccountBindService tradeAccountBindService;

	@GetMapping("/checkAcc")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "检查交易账号绑定状态", notes = "检查交易账号绑定状态")
	public R<Kv> checkAcct(@RequestParam String tradeAccount) {
		return R.data(Kv.create().set("status", tradeAccountBindService.checkAcct(tradeAccount)));
	}

	@PostMapping("/checkAcc2fa")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "交易账号绑定密码校验", notes = "交易账号绑定密码校验")
	public R<Kv> checkAcc2fa(@RequestBody TradeAccountBind2faVO req) {
		if (req == null || req.getUnlockType() == null || StringUtil.isBlank(AuthUtil.getDeviceCode())) {
			return R.fail("解锁方式或设备号不能为空");
		}
		if (req.getUnlockType() == 0 && StringUtil.isAnyBlank(req.getPassword(), req.getRule())) {
			return R.fail("交易密码不能为空");
		}
		if (req.getUnlockType() == 1 && StringUtil.isBlank(req.getChangeRule())) {
			return R.fail("流动编码参数不能为空");
		}
		if (StringUtil.isAnyBlank(req.getAccount(), req.getStatus())) {
			return R.fail("交易账号或账号状态不能为空");
		}
		return tradeAccountBindService.checkAcc2fa(req);
	}

	@PostMapping("/accBind")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "交易账号绑定", notes = "交易账号绑定")
	public R<String> acctBind2fa(@RequestBody TradeAccountBindVO req) {
		return tradeAccountBindService.acctBind(req);
	}
}
