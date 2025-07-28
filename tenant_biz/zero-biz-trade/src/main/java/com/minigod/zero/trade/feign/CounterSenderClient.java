package com.minigod.zero.trade.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.afe.util.SenderIdUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author chen
 * @ClassName CounterSenderClient.java
 * @Description TODO
 * @createTime 2024年06月28日 15:59:00
 */
@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CounterSenderClient implements ICounterSenderClient{
	@Override
	public R senderIdReset() {
		SenderIdUtil.autoIncrement.set(0);
		return R.success();
	}
}
