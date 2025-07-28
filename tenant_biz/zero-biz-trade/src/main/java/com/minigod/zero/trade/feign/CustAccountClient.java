package com.minigod.zero.trade.feign;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.FundAccountInfo;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CustAccountClient implements ICustAccountClient {

	@Resource
	private IFundService fundService;

	@Override
	public R<FundQueryVO> getDetailAccount(Long custId, String fundAccount, String moneyType) {
		return fundService.getDetailAccount(custId, fundAccount, moneyType);
	}

	@Override
	public R<String> getExtractableMoney(String fundAccount, String moneyType) {
		return fundService.getExtractableMoney(fundAccount, moneyType);
	}

	@Override
	public R<List<FundAccountInfo>> getCapitalAccount(String tradeAccount) {
		return null;
	}
}
