package com.minigod.zero.auth.service;

import com.minigod.zero.core.tool.api.R;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/25 10:43
 * @description：
 */
public interface ZeroUserDetailsService extends UserDetailsService {

	R<Object> checkPwd(@RequestParam Map<String, String> parameters);

	R verificationCode(@RequestParam Map<String, String> parameters);

	ZeroUserDetails loadUserByPhone(String tenantId,String phone,String areaCode);

	ZeroUserDetails loadUserByEmail(String tenantId,String email);
}
