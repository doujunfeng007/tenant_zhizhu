package com.minigod.zero.customer.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.minigod.zero.core.http.util.HttpUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.ITenantsCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @ClassName TenantsCustomerServiceImpl.java
 * @Description TODO
 * @createTime 2024年09月06日 19:03:00
 */
@Service
@Slf4j
public class TenantsCustomerServiceImpl implements ITenantsCustomerService {


//	@Value("${tenants.customer.url}")
	private String customerUrl ;

	private static String CUSTOMER_DETAIL_INFO = "proxy/detail";

	@Override
	public R queryCustomerDetail(Long custId) {
		Map<String, Object> params =new HashMap<>();
		params.put("custId",custId);
		R result = null;
		try {
			String response =HttpUtil.get(customerUrl+CUSTOMER_DETAIL_INFO,params);
			log.info("查询用户信息返回{}",response);
			result = JSONUtil.toBean(response, R.class);
		} catch (Exception e) {
			return R.fail("查询用户信息异常");
		}
		return result;
	}
}
