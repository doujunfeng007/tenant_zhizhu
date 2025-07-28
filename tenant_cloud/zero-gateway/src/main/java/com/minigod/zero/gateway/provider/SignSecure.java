
package com.minigod.zero.gateway.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签名授权规则
 *
 * @author Chill
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignSecure {
	/**
	 * 请求路径
	 */
	private String pattern;

}
