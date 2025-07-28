package com.minigod.zero.resource.feign;

import com.minigod.zero.resource.dto.UpushDTO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 友盟消息推送
 *
 * @author Zhe.Xiao
 */
@FeignClient(
	value = AppConstant.APPLICATION_RESOURCE_NAME,
	fallback = IUpushClientFallback.class
)
public interface IUpushClient {

	String SEND_BY_DEVICE = AppConstant.FEIGN_API_PREFIX + "/sendByDevice";
	String SEND_BY_APP = AppConstant.FEIGN_API_PREFIX + "/sendByApp";
	String SEND_BY_TAGS = AppConstant.FEIGN_API_PREFIX + "/sendByTags";

	/**
	 * 指定设备发送
	 *
	 * @param upushDTO
	 * @return
	 */
	@PostMapping(SEND_BY_DEVICE)
	R sendByDevice(@RequestBody UpushDTO upushDTO);

	/**
	 * 广播
	 *
	 * @param upushDTO
	 * @return
	 */
	@PostMapping(SEND_BY_APP)
	R sendByApp(@RequestBody UpushDTO upushDTO);

	/**
	 * 指定标签发送
	 *
	 * @param upushDTO
	 * @return
	 */
	@PostMapping(SEND_BY_TAGS)
	R sendByTags(@RequestBody UpushDTO upushDTO);

}
