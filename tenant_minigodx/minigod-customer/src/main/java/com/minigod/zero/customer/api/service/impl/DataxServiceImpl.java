package com.minigod.zero.customer.api.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.DataxService;
import com.minigod.zero.customer.dto.JobDetailDTO;
import com.minigod.zero.customer.vo.TradingRecordsJobListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: com.minigod.zero.customer.api.service.impl.DataxServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/6 20:30
 * @Version: 1.0
 */
@Slf4j
@Service
public class DataxServiceImpl implements DataxService {

	@Value("${datax.job.admin.addresses:203.86.123.84:9528}")
	private String adminAddresses;//datax 管理后台

	@Value("${datax.job.customer_fund_trading_records.id:9}")
	private String customerFundTradingRecordsId;//datax 客户基金交易流水 任务id


	@Override
	public R DataxDetail() {
		//获取运行时间和数据条数
		String handleTime = null;
		String num = null;
		try {
			//http://203.86.123.84:9528/api/log/pageList?current=1&size=10&jobGroup=0&jobId=0&logStatus=1&filterTime=
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username","admin");
			jsonObject.put("password","123456");
			jsonObject.put("rememberMe","1");

			String result = HttpRequest.get("http://"+adminAddresses+"/api/auth/login")
				.body(jsonObject.toJSONString())
				.timeout(3000)//超时，毫秒
				.execute().body();
			log.info("datax data:{}",result);
			String data = JSONObject.parseObject(result).getJSONObject("content").getString("data");

			String url = adminAddresses + "/api/log/pageList?current=1&size=10&jobGroup=0&logStatus=1&filterTime=&jobId=" + customerFundTradingRecordsId ;
			result = HttpRequest.get(url)
				.header("Authorization",data)
				.timeout(3000)//超时，毫秒
				.execute().body();
			log.info("datax result2:{}",result);
			List<TradingRecordsJobListVO> list = JSONObject.parseArray(JSONObject.parseObject(result).getJSONObject("content").getString("data"), TradingRecordsJobListVO.class);
			TradingRecordsJobListVO tradingRecordsJobListVO = list.get(0);
			handleTime = tradingRecordsJobListVO.getHandleTime();
			//LogStatistics{taskStartTime=2024-05-06 22:55:50, taskEndTime=2024-05-06 22:56:00, taskTotalTime=10s, taskAverageFlow=1.49KB/s, taskRecordWritingSpeed=12rec/s, taskRecordReaderNum=129, taskRecordWriteFailNum=0}
			String handleMsg = tradingRecordsJobListVO.getHandleMsg();
			num = StrUtil.subBetween(handleMsg,"taskRecordReaderNum=", ",");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取datax数据失败 msg: {}",e.getMessage());
			return R.fail(e.getMessage());
		}

		JobDetailDTO jobDetailDTO = new JobDetailDTO();
		jobDetailDTO.setDataNum(num);
		jobDetailDTO.setHandleTime(handleTime);
		return  R.data(jobDetailDTO);

	}


}
