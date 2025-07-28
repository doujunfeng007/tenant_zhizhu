package com.minigod.zero.cust.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.ZeroPage;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.entity.CustInvestorStmtEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 投资者声明信息（美股） Feign接口类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@FeignClient(
    value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface ICustInvestorStmtClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/custInvestorStmt";
    String TEST = API_PREFIX + "/test";
    String IS_INVESTOR_PACKAGE = API_PREFIX + "/is_investor_package";

    /**
     * 获取投资者声明信息（美股）列表
     *
     * @param current   页号
     * @param size      页数
     * @return ZeroPage
     */
    @GetMapping(TEST)
    ZeroPage<CustInvestorStmtEntity> top(@RequestParam("current") Integer current, @RequestParam("size") Integer size);

	@GetMapping(IS_INVESTOR_PACKAGE)
	R<Integer> isInvestorPackage();
}
