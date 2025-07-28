package com.minigod.zero.bpmn.module.common.feign;

import com.minigod.zero.bpmn.module.common.service.AddressService;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaFundApplicationService;
import com.minigod.zero.bpmn.module.feign.IAddrClient;
import com.minigod.zero.core.tool.api.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.common.feign.AddrClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/18 18:28
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class AddrClient implements IAddrClient {

	@Autowired
	private AddressService sysAddressService;


	@Override
	@GetMapping(GET_ADDRESS)
	public R getAddressName(String value) {

		return R.data(sysAddressService.getAddressName(value));
	}
}
