package com.minigod.zero.customer.back.controller;

import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.customer.api.service.IIpAddressService;
import com.minigod.zero.customer.entity.CustomerIpAddress;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.minigod.zero.core.tool.api.R;

import javax.annotation.Resource;

@RestController
@AllArgsConstructor
@RequestMapping("/login-ip")
@Api(value = "登录IP", tags = "登陆IP接口")
public class IpAddressController extends ZeroController {
	@Resource
	private IIpAddressService ipAddressService;

	@GetMapping("/detail")
	public R<CustomerIpAddress> detail(String ip) {
		return R.data(ipAddressService.findIpAddress(ip));
	}
}
