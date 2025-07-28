
package com.minigod.zero.resource.feign;

import com.minigod.zero.core.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class ISmsClientFallback implements ISmsClient {
	@Override
	public R sendMessage(String code, String params, String phones) {
		return R.fail("远程调用失败");
	}

	@Override
	public R sendValidate(String code, String phone) {
		return R.fail("远程调用失败");
	}

	@Override
	public R validateMessage(String code, String id, String value, String phone) {
		return R.fail("远程调用失败");
	}

}
