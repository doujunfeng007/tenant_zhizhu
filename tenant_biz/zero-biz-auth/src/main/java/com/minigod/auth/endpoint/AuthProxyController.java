package com.minigod.auth.endpoint;

import com.minigod.auth.constant.LoginConstant;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * 内部调用的相关接口
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX)
public class AuthProxyController {

	@Autowired
	private TokenEndpoint tokenEndpoint;

	/**
	 * 后台登录
	 * @param principal
	 * @param parameters
	 * @return
	 * @throws HttpRequestMethodNotSupportedException
	 */
	@PostMapping(value = "/auth/token")
	public R postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
		throws HttpRequestMethodNotSupportedException {
		parameters.put(LoginConstant.LOGIN_PROXY_TYPE, "1");
		OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
		return R.data(accessToken);
	}






}
