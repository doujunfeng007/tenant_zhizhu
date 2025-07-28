package com.minigod.zero.trade.afe.service;

import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import org.springframework.http.HttpMethod;

/**
 * @ClassName: com.minigod.zero.trade.afe.service.afeCreateAccount
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/11 18:04
 * @Version: 1.0
 */
public interface AfeCounterService {

	/**
	 * afe 开户接口
	 * @param req
	 * @return
	 */
	Object createAccount(Object req);

	/**
	 * afe  入金  客戶資金存入
	 * @param req
	 * @return
	 */
	Object depositAmount(Object req);

	/**
	 * afe  出金 客戶資金提取
	 * @param req
	 * @return
	 */
	Object withdrawAmount(Object req);




}
