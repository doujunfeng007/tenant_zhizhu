package com.minigod.zero.trade.afe.service;

import com.minigod.zero.trade.afe.resp.back.BackCommonResponse;

/**
 * @ClassName: com.minigod.zero.trade.afe.service.SendApiService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/25 13:34
 * @Version: 1.0
 */
public interface SendApiService {
	/**
	 * 发送请求
	 * @param bean
	 * @param apiName
	 * @param b
	 * @return
	 */
	String postGeneralServiceSend(Object bean, String apiName,  boolean b);

	/**
	 * 返回javaBean对象
	 * @param req
	 * @param apiName
	 * @param b
	 * @return
	 */
	BackCommonResponse sendMessage(Object req, String apiName, boolean b);
}
