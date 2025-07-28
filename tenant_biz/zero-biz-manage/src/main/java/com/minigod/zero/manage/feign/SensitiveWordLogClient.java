package com.minigod.zero.manage.feign;
import com.minigod.zero.manage.vo.request.SensitiveWordLogSaveVo;
import com.minigod.zero.manage.service.ISensitiveWordLogService;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 *
 * @author 掌上智珠
 * @since 2023/7/25 1:46
 */
@Slf4j
@RestController
public class SensitiveWordLogClient implements ISensitiveWordLogClient {

	@Resource
	private ISensitiveWordLogService sensitiveWordLogService;

	@Override
	public R saveBatch(ArrayList<SensitiveWordLogSaveVo> sensitiveWordLogSaveVo) {
		return R.status(sensitiveWordLogService.saveBatch(sensitiveWordLogSaveVo));
	}
}
