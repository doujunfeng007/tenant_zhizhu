package com.minigod.zero.system.open;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.system.service.ITenantService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/wt/tenant")
@Api(value = "租户信息", tags = "租户信息")
public class TenantWtApiController {

	private final ITenantService tenantService;

	@PostMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "租户列表", notes = "")
	public R<List<Tenant>> list() {
		return R.data(tenantService.list());
	}
}
