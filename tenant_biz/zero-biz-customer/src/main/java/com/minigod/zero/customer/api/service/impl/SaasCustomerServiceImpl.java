package com.minigod.zero.customer.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.minigod.zero.core.http.util.HttpUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.customer.api.service.ISaasCustomerService;
import com.minigod.zero.customer.vo.CustomerInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @ClassName SaasCustomerServiceImpl.java
 * @Description TODO
 * @createTime 2024年09月13日 18:47:00
 */
@Service
@Slf4j
public class SaasCustomerServiceImpl implements ISaasCustomerService {

	@Value("${saas.customer.url}")
	private String customerUrl;

	@Value("${saas.customer.tenantId}")
	private String saasTenantId;

	private static String CUSTOMER_REGISTER = "tenantRegister";

	@Override
	public R register(CustomerInfoVO customerInfo) {

		Map<String ,Object> params =new HashMap<>();
		params.put("cellPhone", customerInfo.getCellPhone());
		params.put("areaCode", customerInfo.getAreaCode());
		params.put("tenantId", saasTenantId);
		params.put("tenantCustId", customerInfo.getId());

		R result = null;
		try {
			log.info("注册租户请求参数{}",params);
			String response =HttpUtil.postJson(customerUrl+CUSTOMER_REGISTER, JsonUtil.toJson(params));
			log.info("注册租户返回{}",response);
			result = JSONUtil.toBean(response, R.class);
		} catch (Exception e) {
			return R.fail("注册租户异常");
		}
		return result;
	}
}
