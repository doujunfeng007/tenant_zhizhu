
package com.minigod.zero.auth.support;

import com.minigod.zero.auth.utils.TokenUtil;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.tool.utils.DigestUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义密码加密
 *
 * @author Chill
 */
public class ZeroPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return DigestUtil.hex((String) rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		HttpServletRequest request = WebUtil.getRequest();
		// 获取请求源
		String sourceType = request.getHeader(TokenUtil.SOURCE_TYPE_KEY);
		String grantType = request.getParameter(TokenUtil.GRANT_TYPE_KEY);
		// esop 已在UserDetailsService比较密码，并做旧密码兼容
		if(sourceType.startsWith(ESourceType.ESOP.getName()) && grantType.equals("password")){
			return true;
		}
		return encodedPassword.equals(encode(rawPassword));
	}

}
