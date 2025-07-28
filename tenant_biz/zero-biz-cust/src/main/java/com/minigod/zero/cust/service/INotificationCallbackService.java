package com.minigod.zero.cust.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.vo.PriceReminderCallbackVO;
import com.minigod.zero.cust.vo.SendNotifyVO;

public interface INotificationCallbackService {
	R<Object> priceReminder(PriceReminderCallbackVO reqVo);

	R<Object> sendNotify(SendNotifyVO sendNotifyVO);
}
