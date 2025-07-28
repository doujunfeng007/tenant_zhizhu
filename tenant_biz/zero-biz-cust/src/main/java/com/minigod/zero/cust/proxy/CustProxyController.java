package com.minigod.zero.cust.proxy;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.service.ICustDeviceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 内部调用
 *
 * @author minigod
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX)
@Api(value = "内部调用", tags = "内部调用")
@Slf4j
public class CustProxyController {

	@Resource
	private ICustDeviceService custDeviceService;

	/**
	 * 根据custId获取当前语言
	 *
	 * @param custId
	 * @return
	 */
	@GetMapping("/getLangByCustId")
	public String sendEmail(Long custId) {
		String lang = null;
		if (custId == null) {
			return null;
		}
		CustDeviceEntity custDevice = custDeviceService.getCustDevice(custId);
		if (custDevice != null) {
			lang = custDevice.getLang();
		}
		return lang;
	}

}
