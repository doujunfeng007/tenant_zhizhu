package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.cust.apivo.CustLoginLogVO;
import com.minigod.zero.cust.apivo.excel.CustInfoExcel;
import com.minigod.zero.cust.apivo.req.CustInfoQueryReq;
import com.minigod.zero.cust.apivo.resp.CustInfoResp;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.cust.apivo.resp.TenantCustInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/12/7
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface ICustBackClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/custBack";
	String QUERY_PAGE_CUST = API_PREFIX + "/query_page_cust";
	String CUST_EXPORT = API_PREFIX + "/cust_export";
	String PAGE_LOGIN_LOG = API_PREFIX + "/login_log";
	String TENANT_CUST_INFO = API_PREFIX + "/tenant_cust_info";

	/**
	 * 分页查询注册用户信息
	 * @param custInfoQueryReq
	 * @return
	 */
	@GetMapping(QUERY_PAGE_CUST)
	Page<CustInfoResp> queryPageCustInfo(@SpringQueryMap CustInfoQueryReq custInfoQueryReq, @SpringQueryMap Query query);

	@GetMapping(CUST_EXPORT)
	List<CustInfoExcel> exportCust(@SpringQueryMap CustInfoQueryReq custInfoQueryReq);

	@GetMapping(PAGE_LOGIN_LOG)
	Page<CustLoginLogVO> getPageLoginLog(@SpringQueryMap CustLoginLogVO loginLog, @SpringQueryMap Query query);

	@GetMapping(TENANT_CUST_INFO)
	TenantCustInfoVO queryTenantCustInfo();
}
