package com.minigod.zero.manage.feign;
import com.minigod.zero.manage.dto.ComPlexSimpleReplaceDto;
import com.minigod.zero.manage.service.IComplexTranSimpleService;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 掌上智珠
 * @since 2023/8/3 11:21
 */
@RestController
public class ComplexTransSimpleClient implements IComplexTransSimpleClient {
	@Resource
	private IComplexTranSimpleService complexTranSimpleService;

	@Override
	public R<List<ComPlexSimpleReplaceDto>> getReplaceWords() {
		List<ComPlexSimpleReplaceDto> result = complexTranSimpleService.findAll().stream().map(e -> {
			ComPlexSimpleReplaceDto comPlexSimpleReplaceDto = new ComPlexSimpleReplaceDto();
			comPlexSimpleReplaceDto.setBeforeReplace(e.getBeforeReplace());
			comPlexSimpleReplaceDto.setAfterReplace(e.getAfterReplace());
			return comPlexSimpleReplaceDto;
		}).collect(Collectors.toList());
		return R.data(result);
	}
}
