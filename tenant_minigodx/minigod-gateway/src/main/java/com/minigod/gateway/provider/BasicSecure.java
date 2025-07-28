/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.gateway.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础授权规则
 *
 * @author zsdp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicSecure {
	/**
	 * 请求路径
	 */
	private String pattern;

}
