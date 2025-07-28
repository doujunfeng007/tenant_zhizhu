package com.minigod.zero.cust.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.service.INotificationCallbackService;
import com.minigod.zero.cust.vo.PriceReminderCallbackVO;
import com.minigod.zero.cust.vo.SendNotifyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 通知回调控制器
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/wt/notify")
@Api(value = "客户中心通知回调接口", tags = "")
public class NotificationCallbackController {

	@Autowired
	private INotificationCallbackService notificationCallbackService;

	@PostMapping("/priceReminder")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "股价提醒回调", notes = "")
	public R<Object> priceReminder(@RequestBody PriceReminderCallbackVO reqVo) {
		return notificationCallbackService.priceReminder(reqVo);
	}

	@PostMapping("/sendNotify")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "模板通知回调", notes = "")
	public R<Object> sendNotify(@RequestBody SendNotifyVO sendNotifyVO) {
		return notificationCallbackService.sendNotify(sendNotifyVO);
	}
}
