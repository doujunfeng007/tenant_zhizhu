package com.minigod.zero.customer.back.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.vo.BackCustomerDetailVO;
import com.minigod.zero.customer.vo.CustomerOpenAccountVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/8 18:35
 * @description：
 */
@FeignClient(value = "zero-biz-bpmn")
public interface AccountOpenClient {
	String QUERY_OPEN_ACCOUNT_USER_LIST = "/back/account/accountOpenInfo/userList";

	String QUERY_OPEN_ACCOUNT_USER_DETAIL = "/back/account/accountOpenInfo/userDetail";

	@GetMapping(QUERY_OPEN_ACCOUNT_USER_LIST)
	R<Page<CustomerOpenAccountVO>> openAccountUserList(@RequestParam("keyword") String keyword, @RequestParam("startTime")String startTime,
													   @RequestParam("endTime") String endTime, @RequestParam("pageIndex")Integer pageIndex,
													   @RequestParam("pageSize") Integer pageSize);

	@GetMapping(QUERY_OPEN_ACCOUNT_USER_DETAIL)
	R<BackCustomerDetailVO> openAccountDetail(@RequestParam("userId") Long userId);
}
