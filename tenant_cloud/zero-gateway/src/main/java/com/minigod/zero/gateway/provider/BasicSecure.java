
package com.minigod.zero.gateway.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础授权规则
 *
 * @author Chill
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
