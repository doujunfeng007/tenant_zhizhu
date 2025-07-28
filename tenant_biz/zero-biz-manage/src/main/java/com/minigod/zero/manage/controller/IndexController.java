package com.minigod.zero.manage.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.resp.TenantCustInfoVO;
import com.minigod.zero.cust.feign.ICustBackClient;
import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.system.feign.ITenantClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/index")
@Slf4j
public class IndexController extends ZeroController {


	@Resource
	private ICustBackClient custBackClient;

	@Resource
	private ITenantClient tenantClient;


	@GetMapping("/tenant")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "租户首页信息", notes = "租户首页信息")
	public R<TenantCustInfoVO> tenant() {

		TenantCustInfoVO tenantCustInfoVO =custBackClient.queryTenantCustInfo();
		log.info("查询用户的租户id：{}",AuthUtil.getTenantId());
		Tenant tenant =tenantClient.getByTenantId(AuthUtil.getTenantId());
		if(null != tenant.getExpireTime()){
			tenantCustInfoVO.setExpireTime(DateUtil.formatDateTime(tenant.getExpireTime()));
		}
		return R.data(tenantCustInfoVO);
	}











}
