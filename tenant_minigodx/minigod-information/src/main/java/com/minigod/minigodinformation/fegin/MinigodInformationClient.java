package com.minigod.minigodinformation.fegin;

import com.minigod.minigodinformation.mapper.ExchangeAnnouncementMapper;
import com.minigod.minigodinformation.service.MinigodInformationClientService;
import com.minigod.minigodinformationapi.fegin.IMinigodInformationClient;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.feign.IDictBizClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName: com.minigod.minigodinformation.fegin.MinigodInformationClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/11/7 13:29
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(AppConstant.FEIGN_API_PREFIX + "/disclosure_announcement")
public class MinigodInformationClient implements IMinigodInformationClient {


	@Autowired
	private MinigodInformationClientService minigodInformationClientService;

	@Override
	@GetMapping(SY_INFORMATION_DATA_URL)
	public R syInformationData(Date startTime, Date endTime) {
		//获取交易所通知
		minigodInformationClientService.syExchangeAnnouncement(startTime,endTime);

		//获取披露公告
		minigodInformationClientService.syExchangeDisclosureAnnouncement(startTime,endTime);


		return R.success();
	}
}
