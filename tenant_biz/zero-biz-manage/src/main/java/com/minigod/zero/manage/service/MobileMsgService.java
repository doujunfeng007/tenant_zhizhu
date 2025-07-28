package com.minigod.zero.manage.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.dto.EmailDTO;
import com.minigod.zero.manage.dto.PushDTO;
import com.minigod.zero.manage.dto.SmsDTO;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;

public interface MobileMsgService {
	R sendMessage(SmsDTO sendSmsVO);

	R sendMessage(EmailDTO emailDTO);

	R sendMessage(PushDTO pushDTO);
}
