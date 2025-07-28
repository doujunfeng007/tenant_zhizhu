package com.minigod.zero.cust.vo;

import com.minigod.zero.platform.dto.SendNotifyDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SendNotifyVO extends SendNotifyDTO {

	/**
	 * 带参数的消息模板中的参数列表-繁体
	 */
	private List<String> paramsHant = new ArrayList<>();

	/**
	 * 带参数的消息模板中的参数列表-英文
	 */
	private List<String> paramsEn = new ArrayList<>();
}
