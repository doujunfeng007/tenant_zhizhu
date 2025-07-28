/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth;


import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.tool.utils.SpringUtil;
import org.springframework.context.annotation.Import;

/**
 * 用户认证服务器
 *
 * @author zsdp
 */
@Import(value = SpringUtil.class)
@ZeroCloudApplication
public class AuthApplication {

	public static void main(String[] args) {
		ZeroApplication.run("zero-biz-auth", AuthApplication.class, args);
	}

}
