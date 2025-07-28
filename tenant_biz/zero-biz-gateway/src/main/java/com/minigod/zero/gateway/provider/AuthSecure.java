
package com.minigod.zero.gateway.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义授权规则
 *
 * @author Chill
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSecure {
	/**
	 * 请求路径
	 */
	private String pattern;

}
