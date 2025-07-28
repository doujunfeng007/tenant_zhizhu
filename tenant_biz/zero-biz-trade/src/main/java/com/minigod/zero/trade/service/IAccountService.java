package com.minigod.zero.trade.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;

/**
 * 帐户服务
 */
public interface IAccountService {
    /**
     * 开户
     */
    R<OpenAccountVO> openAccount(OpenAccountReq openAccountReq,boolean isRepeat);

	/**
	 * 账户资料设置
	 * @param modifyAccountReq
	 * @return
	 */
	R updateAccount(ModifyAccountReq modifyAccountReq);

}
