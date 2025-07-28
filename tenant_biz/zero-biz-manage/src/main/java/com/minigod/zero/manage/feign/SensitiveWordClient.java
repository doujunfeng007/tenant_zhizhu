package com.minigod.zero.manage.feign;
import com.minigod.zero.manage.vo.response.GpSensitiveWordDto;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.service.ISensitiveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/7/17 17:03
 */
@Slf4j
@RestController
public class SensitiveWordClient implements ISensitiveWordClient {
	@Resource
	ISensitiveWordService sensitiveWordService;

	@Override
	public R<List<GpSensitiveWordDto>> getGpSensitiveWordList(Integer scope, String source) {
		return R.data(sensitiveWordService.getGpSensitiveWordList(scope,source));
	}
}
