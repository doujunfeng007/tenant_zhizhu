package com.minigod.zero.trade.afe.service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.trade.afe.common.AfeInterfaceTypeEnum;
import com.minigod.zero.trade.afe.config.WebSocketClientService;
import com.minigod.zero.trade.afe.req.ChangePasswordRequest;
import com.minigod.zero.trade.afe.req.LoginRequest;
import com.minigod.zero.trade.afe.req.ResetPasswordRequest;
import com.minigod.zero.trade.afe.resp.CommonResponse;
import com.minigod.zero.trade.service.IAuthService;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import com.minigod.zero.trade.vo.request.BaseRequest;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName AfeAuthServiceImpl.java
 * @Description TODO
 * @createTime 2024年04月13日 17:41:00
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_AFE)
public class AfeAuthServiceImpl implements IAuthService {

    @Autowired
    private WebSocketClientService webSocketClientService;

    @Resource
    private ZeroRedis zeroRedis;

	@Value("${zero.trade.password}")
	private String password;

	@Value("${afe.agent.userId}")
	private String agentTradeAccount;

	@Value("${afe.agent.password}")
	private String agentPassword;

	@Value("${afe.agent.channel}")
	private String agentChannel;

    @Override
    public R getServerType(String request) {
        return null;
    }

    @Override
    public R<Object> login(BaseRequest request) {
		request.setPassword(password);
        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setPassword(RSANewUtil.decrypt(request.getPassword()));
		loginRequest.setPassword(request.getPassword());
		loginRequest.setLoginId(request.getTradeAccount());
        /**
         * 01 先连接
         */
        boolean connectStatus = webSocketClientService.connect(request.getTradeAccount(),1);
        if (connectStatus) {
            R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.LOGIN.getRequestType(), loginRequest,
                request.getTradeAccount());
            log.info("登录返回==={}", JSONUtil.toJsonStr(result));
            if (result.isSuccess()) {
                /**
                 * 存储会话
                 */
                CommonResponse response = (CommonResponse)result.getData();
                zeroRedis.hSet(CacheNames.TRADE_TOKEN, request.getTradeAccount(), response.getSessionKey());
                return result;
            } else {
                return R.fail(result.getMsg());
            }
        } else {
            return R.fail("连接失败");
        }
    }

    @Override
    public R switchToken(String request) {
        return null;
    }

    @Override
    public R validPwd(ValidPwdVO request) {
        return null;
    }

    @Override
    public R modifyPwd(ModifyPwdVO modifyPwdVO) {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setClientId(modifyPwdVO.getTradeAccount());
        request.setOldPassword(RSANewUtil.decrypt(modifyPwdVO.getPassword()));
        request.setNewPassword(RSANewUtil.decrypt(modifyPwdVO.getNewPassword()));
        request.setReconfirmPassword(modifyPwdVO.getNewPassword());
        R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.CHANGE_PASSWORD.getRequestType(), request,
            modifyPwdVO.getTradeAccount());
        return result;
    }

    @Override
    public R resetPwd(ResetTradePwdVO validatePwdVO) {
        /**
         * 先连接  重置密码会通过邮件发送客户
         */
        boolean connectStatus = webSocketClientService.connect(validatePwdVO.getTradeAccount(),1);
        if (connectStatus) {
            ResetPasswordRequest request = new ResetPasswordRequest();
            request.setClientId(validatePwdVO.getTradeAccount());
            request.setEmail(validatePwdVO.getEmail());
            request.setHkid(validatePwdVO.getIdNo());
            R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.RESET_PASSWORD.getRequestType(), request,
                validatePwdVO.getTradeAccount());
            return result;
        } else {
            return R.fail("连接失败");
        }
    }

    @Override
    public R getRiskLevel(String request) {
        return null;
    }

}
