/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.gateway.dynamic;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由断言定义模型
 *
 * @author zsdp
 */
@Data
public class GatewayPredicate {

	/**
	 * 断言对应的Name
	 */
	private String name;

	/**
	 * 配置的断言规则
	 */
	private Map<String, String> args = new LinkedHashMap<>();
}
