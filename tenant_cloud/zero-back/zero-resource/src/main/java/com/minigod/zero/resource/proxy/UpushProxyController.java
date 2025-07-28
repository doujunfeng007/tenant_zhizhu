package com.minigod.zero.resource.proxy;

import cn.hutool.core.collection.CollectionUtil;
import com.minigod.zero.resource.dto.UpushDTO;
import com.minigod.zero.resource.service.IUpushService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.utils.upush.UpushUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 友盟推送
 *
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX + "/upush")
@Slf4j
public class UpushProxyController {

	@Resource
	private IUpushService uPushService;

	@SneakyThrows
	@PostMapping(value = "/sendByDevice")
	public R sendByDevice(@RequestBody UpushDTO upushDTO) {
		R validParams = UpushUtil.validCommonParams(upushDTO);
		if (!validParams.isSuccess()) return validParams;
		if (CollectionUtil.isEmpty(upushDTO.getDeviceTokenList())) {
			return R.fail("deviceTokenList is empty");
		}
		return uPushService.sendByDevice(upushDTO);
	}

	@SneakyThrows
	@PostMapping(value = "/sendByApp")
	public R sendByApp(@RequestBody UpushDTO upushDTO) {
		R validParams = UpushUtil.validCommonParams(upushDTO);
		if (!validParams.isSuccess()) return validParams;
		return uPushService.sendByApp(upushDTO);
	}

	@SneakyThrows
	@PostMapping(value = "/sendByTags")
	public R sendByTags(@RequestBody UpushDTO upushDTO) {
		R validParams = UpushUtil.validCommonParams(upushDTO);
		if (!validParams.isSuccess()) return validParams;
		if (CollectionUtil.isEmpty(upushDTO.getTagList())) {
			return R.fail("tagList is empty");
		}
		return uPushService.sendByTags(upushDTO);
	}

}
