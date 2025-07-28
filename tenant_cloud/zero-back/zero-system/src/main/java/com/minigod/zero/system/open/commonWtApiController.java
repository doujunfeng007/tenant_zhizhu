package com.minigod.zero.system.open;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.system.entity.Param;
import com.minigod.zero.system.service.IParamService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * commonWtApi 无鉴权控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/wt/common_api")
@Api(value = "公共无鉴权控制器", tags = "接口")
public class commonWtApiController {

	@Resource
	private IParamService paramService;

	/**
	 * 详情
	 */
	@PostMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入param")
	public R<Param> detail(@RequestBody Param param) {
		return R.data(paramService.getOne(Condition.getQueryWrapper(param)));
	}
}
