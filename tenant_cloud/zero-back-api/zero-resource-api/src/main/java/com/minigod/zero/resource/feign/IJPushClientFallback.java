package com.minigod.zero.resource.feign;

import com.minigod.zero.resource.dto.JPushDTO;
import com.minigod.zero.core.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class IJPushClientFallback implements IJPushClient {


	@Override
	public R saveAndPushMsgToList(JPushDTO jPushDTO) {
		return R.fail("远程调用失败");
	}

	@Override
	public R saveAndPushMsgToApp(JPushDTO jPushDTO) {
		return R.fail("远程调用失败");
	}
}
