package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.CustomerFundTradingRecordsDTO;
import com.minigod.zero.customer.vo.*;

/**
 * @ClassName: com.minigod.zero.customer.api.service.CustomerTradingService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/7 9:05
 * @Version: 1.0
 */
public interface CustomerTradingService {
	R fundTradingList(CustomerFundTradingRecordsListVO customerFundTradingRecordsListVO);

	R bondTradingList(CustomerBondTradingRecordsListVO customerBondTradingRecordsListVO);

	R fundHoldingList(CustomerFundHoldingListVO customerFundHoldingListVO);

	R bondHoldingList(CustomerBondHoldingListVO customerBondHoldingListVO);

	R hldHoldingList(CustomerHldHoldingListVO customerHldHoldingListVO);

	R hldTradingList(CustomerHldTradingRecordsListVO customerHldTradingRecordsListVO);
}
