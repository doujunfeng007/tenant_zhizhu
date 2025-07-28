/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.support;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 无密码加密
 *
 * @author zsdp
 */
public class MiniGodNoOpPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

	/**
	 * Get the singleton {@link MiniGodNoOpPasswordEncoder}.
	 */
	public static PasswordEncoder getInstance() {
		return INSTANCE;
	}

	private static final PasswordEncoder INSTANCE = new MiniGodNoOpPasswordEncoder();

	private MiniGodNoOpPasswordEncoder() {
	}

}
