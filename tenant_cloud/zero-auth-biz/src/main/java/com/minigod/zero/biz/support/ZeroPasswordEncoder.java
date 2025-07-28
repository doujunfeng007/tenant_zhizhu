
package com.minigod.zero.biz.support;

import com.minigod.zero.core.tool.utils.DigestUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		return encodedPassword.equals(encode(rawPassword));
	}

}
