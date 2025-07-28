package com.minigod.zero.cust.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.apivo.req.ReqUpdatePwdVO;
import com.minigod.zero.cust.service.ICustInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 李长春
 * 客户密码维护接口
 * @date 2023/5/18
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "客户中心接口", tags = "客户中心接口")
public class CustPassController {

	@Resource
	private ICustInfoService custInfoService;

	@PostMapping("/update_pwd")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "设置/修改登录密码（通用）", notes = "设置/修改登录密码")
	public R updatePwd(@RequestBody ReqUpdatePwdVO vo) {
		if (vo == null || StringUtil.isBlank(vo.getNewPassword())) {
			return R.fail("新密码不能为空，请重新录入");
		}
		return custInfoService.updatePwd(vo);
	}

	@PostMapping("/reset_pwd")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "重置个人户登录密码", notes = "找回登录密码")
	public R resetPwd(@RequestBody ReqUpdatePwdVO vo) {
		if (vo == null || StringUtil.isBlank(vo.getNewPassword()) || StringUtil.isBlank(vo.getCaptchaCode())
			|| StringUtil.isBlank(vo.getCaptchaKey())) {
			return R.fail("密码及验证码参数缺失！");
		}
		if (StringUtil.isBlank(vo.getPhone()) && StringUtil.isBlank(vo.getEmail())) {
			return R.fail("手机号和邮箱不能都为空");
		}
		return custInfoService.resetPwd(vo);
	}


}
