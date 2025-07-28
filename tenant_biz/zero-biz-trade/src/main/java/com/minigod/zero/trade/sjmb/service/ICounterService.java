package com.minigod.zero.trade.sjmb.service;

import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import org.springframework.http.HttpMethod;

/**
 * @author chen
 * @ClassName ICounterService.java
 * @Description TODO
 * @createTime 2024年01月10日 19:12:00
 */
public interface ICounterService {


    /**
     * openapi
     * @param req
     * @param urlEnum
     * @return
     */
    SjmbResponse openApiSend(Object req, SjmbFunctionsUrlEnum urlEnum);

    /**
     * brokerapi
     * @param req
     * @param urlEnum
     * @return
     */
    SjmbResponse brokerApiSend(Object req, SjmbFunctionsUrlEnum urlEnum, HttpMethod method);

	/**
	 * 获取柜台租户信息
	 * @param tenantId
	 * @return
	 */
	TradeKey getTradeKey(String tenantId);
}
