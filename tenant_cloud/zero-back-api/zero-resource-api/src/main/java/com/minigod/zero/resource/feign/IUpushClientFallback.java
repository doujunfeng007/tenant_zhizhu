package com.minigod.zero.resource.feign;

import com.minigod.zero.resource.dto.UpushDTO;
import com.minigod.zero.core.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class IUpushClientFallback implements IUpushClient {

	@Override
	public R sendByDevice(UpushDTO upushDTO) {
		return R.fail("远程调用失败");
	}

	@Override
	public R sendByApp(UpushDTO upushDTO) {
		return R.fail("远程调用失败");
	}

	@Override
	public R sendByTags(UpushDTO upushDTO) {
		return R.fail("远程调用失败");
	}
}
