package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.feign.ICustInvestorStmtClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.feign.ICustInvestorStmtClient;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.mp.support.ZeroPage;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.cust.entity.CustInvestorStmtEntity;
import com.minigod.zero.cust.service.ICustInvestorStmtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 投资者声明信息（美股） Feign实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CustInvestorStmtClient implements ICustInvestorStmtClient {

    private final ICustInvestorStmtService custInvestorStmtService;

    @Override
    @GetMapping(TEST)
    public ZeroPage<CustInvestorStmtEntity> top(Integer current, Integer size) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        IPage<CustInvestorStmtEntity> page = custInvestorStmtService.page(Condition.getPage(query));
        return ZeroPage.of(page);
    }

	@Override
	public R<Integer> isInvestorPackage() {
		return R.data(custInvestorStmtService.isInvestorPackage());
	}

}
