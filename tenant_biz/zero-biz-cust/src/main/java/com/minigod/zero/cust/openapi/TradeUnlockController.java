package com.minigod.zero.cust.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.service.ITradeUblockService;
import com.minigod.zero.cust.vo.Cust2faVO;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.cust.vo.TradeUnlockRes;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/24
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "交易解锁接口", tags = "交易解锁接口")
public class TradeUnlockController {

	@Resource
	private ITradeUblockService tradeUblockService;

	/**
	 * 交易解锁
	 * @param tradeUnlockReq
	 * @return
	 */
	@PostMapping("/tradeUnlock")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "交易解锁", notes = "验证交易账号密码")
	public R<TradeUnlockRes> tradeUnlock(@RequestBody TradeUnlockReq tradeUnlockReq) {
		return tradeUblockService.tradeUnlock(tradeUnlockReq);
	}

	/**
	 * 获取2FA验证码
	 * @param cust2faVO
	 * @return
	 */
	@PostMapping("/get2faCaptcha")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取2FA验证码", notes = "获取2FA验证码")
	public R get2faCaptcha(@RequestBody Cust2faVO cust2faVO) {
		if (cust2faVO == null || cust2faVO.getSceneType() == null) {
			return R.fail("2FA验证场景不能为空");
		}
		return tradeUblockService.get2faCaptcha(cust2faVO);
	}

	/**
	 * 交易2FA验证
	 * @param cust2faVO
	 * @return
	 */
	@PostMapping("/trade2fa")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "交易2FA验证", notes = "短信校验银行卡开户手机号")
	public R<Kv> trade2fa(@RequestBody Cust2faVO cust2faVO) {
		return tradeUblockService.cust2fa(cust2faVO);
	}

	/*@GetMapping("/tradeLogout")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "交易注销", notes = "退出交易登录")
	public R<String> tradeLogout() {
		String tradeToken = WebUtil.getRequest().getHeader(TokenConstant.TRADE_TOKEN);
		if (StringUtils.isBlank(tradeToken)) {
			return R.fail("请求头tradeToken缺失");
		}
		if (tradeUblockService.tradeLogout(tradeToken)) {
			return R.success("交易登录注销成功");
		}
		return R.fail("交易登录注销失败");
	}*/

	/**
	 * 交易密码重置
	 */
	@PostMapping("/resetTradePwd")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "交易密码重置", notes = "交易密码重置")
	public R<TradeUnlockRes> resetTradePwd(@RequestBody ResetTradePwdVO vo) {
		return tradeUblockService.resetTradePwd(vo);
	}

	/**
	 * 校验交易密码
	 */
	@PostMapping("/valid_pwd")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "校验交易密码", notes = "校验交易密码")
	public R<TradeUnlockRes> validTradePwd(@RequestBody TradeUnlockReq req) {
		return tradeUblockService.validTradePwd(req);
	}

	/**
	 * 修改交易密码
	 */
	@PostMapping("/modify_pwd")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "修改交易密码", notes = "修改交易密码")
	public R<TradeUnlockRes> modifyPwd(@RequestBody ModifyPwdVO vo) {
		return tradeUblockService.modifyTradePwd(vo);
	}

}
