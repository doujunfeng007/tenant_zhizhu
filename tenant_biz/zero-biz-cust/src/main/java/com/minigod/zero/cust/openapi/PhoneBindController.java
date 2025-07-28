package com.minigod.zero.cust.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.service.IPhoneBindService;
import com.minigod.zero.cust.vo.CustPhoneBindOpenVO;
import com.minigod.zero.cust.vo.CustPhoneBindVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 11:05
 * @Description: 手机号绑定
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "手机号绑定", tags = "手机号绑定")
public class PhoneBindController extends ZeroController {

	@Resource
	private IPhoneBindService phoneBindService;

	@PostMapping("/checkPhoneBind")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "登录交易账号后，绑定手机号", notes = "登录交易账号后，绑定手机号")
	public R<Kv> checkPhoneBind(@RequestBody CustPhoneBindVO vo) {
		return R.data(Kv.create().set("status", phoneBindService.checkPhoneBind(vo)));
	}

	@PostMapping("/phoneBind")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "登录交易账号后，绑定手机号", notes = "登录交易账号后，绑定手机号")
	public R<Kv> phoneBind(@RequestBody CustPhoneBindVO vo) {
		return phoneBindService.phoneBind(vo);
	}

	@PostMapping("/phoneBindOpen")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "绑定开户手机号到登陆手机号", notes = "绑定开户手机号到登陆手机号")
	public R<Kv> phoneBindOpen(@RequestBody CustPhoneBindOpenVO vo) {
		return phoneBindService.phoneBindOpen(vo);
	}

	@PostMapping("/checkPhoneLogin")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "交易账号/用户号/邮箱登陆绑定注册手机号", notes = "交易账号/用户号/邮箱登陆绑定注册手机号")
	public R<Kv> checkPhoneLogin(@RequestBody CustPhoneBindVO vo) {
		return phoneBindService.checkPhoneLogin(vo);
	}

	@PostMapping("/phoneBindLogin")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "交易账号/用户号/邮箱登陆绑定注册手机号", notes = "交易账号/用户号/邮箱登陆绑定注册手机号")
	public R<Kv> phoneBindLogin(@RequestBody CustPhoneBindVO vo) {
		return phoneBindService.phoneBindLogin(vo);
	}
}
