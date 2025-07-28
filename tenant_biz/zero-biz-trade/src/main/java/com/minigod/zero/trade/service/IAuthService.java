package com.minigod.zero.trade.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import com.minigod.zero.trade.vo.request.BaseRequest;

/**
 * 登录权限服务
 */
public interface IAuthService {
    /**
     * 交易柜台类型
     */
	R getServerType(String request);

    /**
     * 客户登录
     */
	R<Object> login(BaseRequest request);

    /**
     * 开启关闭指纹解锁token
     */
	R switchToken(String request);

    /**
     * 校验密码
     */
	R validPwd(ValidPwdVO request);

    /**
     * 修改密码
     */
	R modifyPwd(ModifyPwdVO request);

    /**
     * 重置密码
     */
	R resetPwd(ResetTradePwdVO request);

    /**
     * 查询风险等级
     */
	R getRiskLevel(String request);

}
