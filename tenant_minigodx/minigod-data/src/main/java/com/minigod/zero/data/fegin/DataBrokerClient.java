package com.minigod.zero.data.fegin;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.dto.BrokerOrderInfoDTO;
import com.minigod.zero.data.dto.OrderInfoDTO;
import com.minigod.zero.data.mapper.CustomerFundAssetsHistoryMapper;
import com.minigod.zero.data.mapper.CustomerHldAssetsHistoryMapper;
import com.minigod.zero.data.mapper.CustomerInfoMapper;
import com.minigod.zero.data.task.service.CustomerStatementAccountService;
import com.minigod.zero.data.vo.CustAssetsQuarterVO;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.customer.fegin.CustomerBrokerClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/24 17:53
 * @Version: 1.0
 */
@RestController
public class DataBrokerClient implements IDataBrokerClient{

	@Resource
	private CustomerInfoMapper customerInfoMapper;
	@Resource
	private CustomerStatementAccountService customerStatementAccountService;
	@Resource
	private CustomerFundAssetsHistoryMapper customerFundAssetsHistoryMapper;
	@Resource
	private CustomerHldAssetsHistoryMapper customerHldAssetsHistoryMapper;


	@Override
	public R<List<BrokerOrderInfoDTO>> selHldOrderInfoList(OrderInfoDTO orderInfoDTO) {
		List<BrokerOrderInfoDTO>  list = customerInfoMapper.selHldOrderInfoList(orderInfoDTO);
		return R.data(list);
	}

	@Override
	public R<List<BrokerOrderInfoDTO>> selBondOrderInfoList(OrderInfoDTO orderInfoDTO) {
		List<BrokerOrderInfoDTO>  list = customerInfoMapper.selBondOrderInfoList(orderInfoDTO);
		return R.data(list);
	}

	@Override
	public R<String> getLatestTradeDate(Date date) {
		String latestTradeDate = customerStatementAccountService.getLatestTradeDate(date);
		if (latestTradeDate != null) {
			return R.data(latestTradeDate);
		}
		return R.fail("获取该月最后交易日失败");
	}

	@Override
	public R<String> getBaseTradeDate(Date date) {
		String baseTradeDate = customerStatementAccountService.getBaseTradeDate(date);
		if (baseTradeDate != null) {
			return R.data(baseTradeDate);
		}
		return R.fail("获取最近交易日失败");
	}

	@Override
	public R<List<CustAssetsQuarterVO>> selHldSumQuarter(Date startTime, Date endTime) {
		List<CustAssetsQuarterVO> custAssetsQuarterVOS = customerHldAssetsHistoryMapper.selSumQuarter(startTime, endTime);
		return R.data(custAssetsQuarterVOS);
	}
	@Override
	public R<List<CustAssetsQuarterVO>> selBondSumQuarter(Date startTime, Date endTime) {
		List<CustAssetsQuarterVO> custAssetsQuarterVOS = customerFundAssetsHistoryMapper.selSumQuarter(startTime, endTime);
		return R.data(custAssetsQuarterVOS);
	}

}

