
package com.minigod.zero.log.feign;

import com.minigod.zero.core.log.feign.ILogClient;
import com.minigod.zero.log.service.ILogErrorService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.log.model.LogApi;
import com.minigod.zero.core.log.model.LogError;
import com.minigod.zero.core.log.model.LogUsual;
import com.minigod.zero.log.service.ILogApiService;
import com.minigod.zero.log.service.ILogUsualService;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志服务Feign实现类
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
public class LogClient implements ILogClient {

	private final ILogUsualService usualLogService;

	private final ILogApiService apiLogService;

	private final ILogErrorService errorLogService;

	@Override
	@PostMapping(API_PREFIX + "/saveUsualLog")
	public R<Boolean> saveUsualLog(@RequestBody LogUsual log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(usualLogService.save(log));
	}

	@Override
	@PostMapping(API_PREFIX + "/saveApiLog")
	public R<Boolean> saveApiLog(@RequestBody LogApi log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(apiLogService.save(log));
	}

	@Override
	@PostMapping(API_PREFIX + "/saveErrorLog")
	public R<Boolean> saveErrorLog(@RequestBody LogError log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(errorLogService.save(log));
	}
}
