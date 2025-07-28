
package com.minigod.zero.flow.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程类型枚举
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum FlowModeEnum {

	/**
	 * 通用流程
	 */
	COMMON("common", 1),

	/**
	 * 定制流程
	 */
	CUSTOM("custom", 2),
	;

	final String name;
	final int mode;

}
