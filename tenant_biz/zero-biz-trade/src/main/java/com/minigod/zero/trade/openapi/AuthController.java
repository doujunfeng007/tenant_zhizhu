package com.minigod.zero.trade.openapi;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IAuthService;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import com.minigod.zero.trade.vo.request.BaseRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/trade/auth_api")
@Api(value = "登陆相关接口", tags = "登陆相关接口")
public class AuthController {
	private final IAuthService authService;

    @PostMapping(value = "/get_server_type")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取交易柜台类型", notes = "传入request")
    public R getServerType(@Valid @RequestBody String request) {
        return authService.getServerType(request);
    }

    @PostMapping(value = "/login")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "交易接口登录", notes = "传入request")
    public R tradeLogin(@Valid @RequestBody BaseRequest request) {
		if(StringUtils.isEmpty(request.getTradeAccount())){
			return R.fail("交易账号不能为空");
		}
        return authService.login(request);
    }

    @PostMapping(value = "/switch_token")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "开启关闭token", notes = "传入request")
    public R switchToken(@Valid @RequestBody String request) {
        return authService.switchToken(request);
    }

    @PostMapping(value = "/valid_pwd")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "验证交易密码", notes = "传入request")
    public R validPwd(@Valid @RequestBody ValidPwdVO request) {
        return authService.validPwd(request);
    }

    @PostMapping(value = "/modify_pwd")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改交易密码", notes = "传入request")
    public R modifyPwd(@Valid @RequestBody ModifyPwdVO request) {
        return authService.modifyPwd(request);
    }

    @PostMapping(value = "/reset_pwd")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "重置交易密码", notes = "传入request")
    public R resetPwd(@Valid @RequestBody ResetTradePwdVO request) {
        return authService.resetPwd(request);
    }

    @PostMapping(value = "/get_risk_level")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询风险等级", notes = "传入request")
    public R getRiskLevel(@Valid @RequestBody String request) {
        return authService.getRiskLevel(request);
    }
}
