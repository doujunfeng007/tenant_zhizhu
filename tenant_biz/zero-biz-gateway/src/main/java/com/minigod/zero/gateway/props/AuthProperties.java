
package com.minigod.zero.gateway.props;

import com.minigod.zero.gateway.provider.AuthSecure;
import com.minigod.zero.gateway.provider.BasicSecure;
import com.minigod.zero.gateway.provider.SignSecure;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 *
 * @author Chill
 */
@Data
@RefreshScope
@ConfigurationProperties("zero.secure")
public class AuthProperties {

	/**
	 * 放行API集合
	 */
	private final List<String> skipUrl = new ArrayList<>();

	/**
	 * 自定义授权配置
	 */
	private final List<AuthSecure> auth = new ArrayList<>();

	/**
	 * 基础认证配置
	 */
	private final List<BasicSecure> basic = new ArrayList<>();

	/**
	 * 签名认证配置
	 */
	private final List<SignSecure> sign = new ArrayList<>();

}
