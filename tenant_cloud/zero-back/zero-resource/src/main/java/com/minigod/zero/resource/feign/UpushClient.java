package com.minigod.zero.resource.feign;

import cn.hutool.core.collection.CollectionUtil;
import com.minigod.zero.resource.dto.UpushDTO;
import com.minigod.zero.resource.service.IUpushService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.utils.upush.UpushUtil;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zhe.Xiao
 */
@RestController
public class UpushClient implements IUpushClient {

	@Resource
	private IUpushService uPushService;

	@Override
	public R sendByDevice(UpushDTO upushDTO) {
		R validParams = UpushUtil.validCommonParams(upushDTO);
		if (!validParams.isSuccess()) return validParams;
		if (CollectionUtil.isEmpty(upushDTO.getDeviceTokenList())) {
			return R.fail("deviceTokenList is empty");
		}
		return uPushService.sendByDevice(upushDTO);
	}

	@Override
	public R sendByApp(UpushDTO upushDTO) {
		R validParams = UpushUtil.validCommonParams(upushDTO);
		if (!validParams.isSuccess()) return validParams;
		return uPushService.sendByApp(upushDTO);
	}

	@Override
	public R sendByTags(UpushDTO upushDTO) {
		R validParams = UpushUtil.validCommonParams(upushDTO);
		if (!validParams.isSuccess()) return validParams;
		if (CollectionUtil.isEmpty(upushDTO.getTagList())) {
			return R.fail("tagList is empty");
		}
		return uPushService.sendByTags(upushDTO);
	}
}
