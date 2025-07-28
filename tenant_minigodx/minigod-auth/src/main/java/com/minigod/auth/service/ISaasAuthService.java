package com.minigod.auth.service;

import com.minigod.zero.core.tool.api.R;

/**
 * @author chen
 * @ClassName ISaasAuthService.java
 * @Description saas端功能
 * @createTime 2025年01月15日 18:51:00
 */
public interface ISaasAuthService {
	/**
	 * SAAS端登录
	 * @param custId
	 * @return
	 */
	R login(String custId,String sourceType);
}
