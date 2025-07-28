package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.Param;
import com.minigod.zero.system.service.IParamService;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.tenant.annotation.NonDS;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class ZeroParamClient implements IZeroParamsClient{
	@Resource
	private IParamService paramService;

	@Override
	@GetMapping(GET_DETAIL)
	public Param getDetail(String paramName, String paramKey) {
		Param param = new Param();
		param.setParamName(paramName);
		param.setParamKey(paramKey);
		return paramService.getOne(Condition.getQueryWrapper(param));
	}
}
