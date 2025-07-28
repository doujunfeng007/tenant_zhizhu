package com.minigod.zero.trade.proxy;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.mkt.vo.StrategyOrderVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.ITradeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author:yanghu.luo
 * @create: 2023-05-19 17:30
 * @Description: 行情服务交互接口
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/proxy/mktService")
public class MktServiceController {

	private final ITradeService tradeService;

	@PostMapping(value = "/triggerStrategyOrder")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "条件单触发", notes = "传入request")
	public R triggerStrategyOrder(@Valid @RequestBody StrategyOrderVO request) {
		return tradeService.triggerStrategyOrder(request);
	}
}
