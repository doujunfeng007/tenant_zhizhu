package com.minigod.zero.trade.tiger.service.impl;

import com.minigod.zero.biz.common.vo.ipo.IPOClientApply;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IIpoCounterService;
import com.minigod.zero.trade.vo.resp.IPOAppliesResponse;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;
import com.minigod.zero.trade.vo.resp.IPOListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_TIGER;


/**
 * @author chen
 * @date 2025/5/20 14:34
 * @description
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_TIGER)
public class TigerIpoCounterServiceImpl implements IIpoCounterService {
	@Override
	public R<IPODetailsResponse> queryIpoDetails(String assetId, String tradeAccount) {
		return null;
	}

	@Override
	public R<IPOListResponse> queryIpoStockList(IpoVO ipoVO) {
		return null;
	}

	@Override
	public R applyIpo(IpoVO ipoVO) {
		return null;
	}

	@Override
	public R applyCancel(IpoVO ipoVO) {
		return null;
	}

	@Override
	public R<IPOAppliesResponse> queryIpoApplyList(IpoVO ipoVO) {
		return null;
	}

	@Override
	public R<List<IPOClientApply>> queryIpoWinningList() {
		return null;
	}
}
