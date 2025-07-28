package com.minigod.zero.cust.feign;

import com.minigod.zero.cust.apivo.req.RegisterReq;
import com.minigod.zero.cust.entity.CustThirdOauth;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/27
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface ICustAuthClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/auth";
	String CUST_REGISTER = API_PREFIX + "/cust_register";
	String CUST_LOGIN = API_PREFIX + "/cust_login";
	String ESOP_LOGIN = API_PREFIX + "/esop_login";
	String COMPANY_LOGIN = API_PREFIX + "/company_login";
	String ACCT_REGISTER = API_PREFIX + "/acct_register";
	String ACCT_CHECK_PWD = API_PREFIX + "/acct_check_pwd";
	String LOAD_CUST_INFO = API_PREFIX  + "/load_cust_info";
	String CUST_AUTH_INFO = API_PREFIX  + "/cust_auth_info";
	String ESOP_CUST_REGISTER = API_PREFIX + "/esop_cust_register";

	/**
	 *  客户注册登录
	 */
	@PostMapping(CUST_REGISTER)
	R<Object> custRegister(@RequestBody RegisterReq registerReq);

	/**
	 *  客户密码登录
	 */
	@PostMapping(CUST_LOGIN)
	R<Object> custLogin(@RequestBody RegisterReq registerReq);

	/**
	 *  公司授权人登录
	 */
	@PostMapping(COMPANY_LOGIN)
	R<Object> companyLogin(@RequestBody RegisterReq registerReq);

	/**
	 *  交易账号登录校验密码
	 */
	@PostMapping(ACCT_CHECK_PWD)
	R<Object> acctCheckPwd(@RequestBody Map<String, String> parameters);
	/**
	 *  账号注册登录
	 */
	@PostMapping(ACCT_REGISTER)
	R<Object> acctRegister(@RequestBody RegisterReq registerReq);

	@PostMapping(CUST_AUTH_INFO)
	R<Object> registThirdOauth(@RequestBody CustThirdOauth thirdOauth, @RequestParam("sourceType") Integer sourceType);

	@PostMapping(LOAD_CUST_INFO)
	R<CustInfo> getCustInfo(@RequestParam("custId") Long custId, @RequestBody CustDeviceEntity custDevice, @RequestParam("sourceType") String sourceType, @RequestParam("loginType") Integer loginType);

	/**
	 *  esop客户注册登录
	 */
	@PostMapping(ESOP_CUST_REGISTER)
	R<Object> esopCustRegister(@RequestBody RegisterReq registerReq);

	/**
	 *  ESOP客户登录
	 */
	@PostMapping(ESOP_LOGIN)
	R<Object> esopLogin(@RequestBody RegisterReq registerReq);

}
