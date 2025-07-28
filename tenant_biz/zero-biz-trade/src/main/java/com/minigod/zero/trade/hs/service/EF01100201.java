package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100201Request;
import com.minigod.zero.trade.hs.req.EF01180200Request;
import com.minigod.zero.trade.hs.resp.EF01100201VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改交易密码
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100201Request.class,responseVo = EF01100201VO.class)
public class EF01100201<T extends GrmRequestVO>  extends T2sdkBiz<T> {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Lazy
	@Resource
    private EF01180200 ef01180200;

//	@Resource
//	protected UserOldPwdsDao userOldPwdsDao;

	@Override
	public <R extends GrmRequestVO> GrmResponseVO requestData(R requestVo) {
		EF01100201Request chgPwdReq = (EF01100201Request) requestVo;

        // 是否存量用户密码表能校验通过 户迁移临时过渡
//        if (null != chgPwdReq.getUserId() && StringUtils.isNotBlank(chgPwdReq.getClientId()) && StringUtils.isNotBlank(chgPwdReq.getPassword())
//				&& userOldPwdsDao.checkOldTradePwd(chgPwdReq.getUserId(),chgPwdReq.getClientId(), chgPwdReq.getPassword())) {
//            logger.info("EF01100201存量用户校验交易密码通过,将访问恒生的密码设置为默认值，ClientId: {},userid:{}", chgPwdReq.getClientId(),chgPwdReq.getUserId());
//            chgPwdReq.setPassword("12345678");
//        }

		if (chgPwdReq.getNeedLogin()) {
			// 先登录
			EF01180200Request loginReq = new EF01180200Request();
			loginReq.copyCommonParams(requestVo);
			loginReq.setFunctionId(GrmFunctions.CLIENT_LOGIN_ALL_IN_ONE);
			loginReq.setClientId(chgPwdReq.getClientId());
			loginReq.setOpStation(chgPwdReq.getOpStation());
			loginReq.setPassword(chgPwdReq.getPassword());
			loginReq.setUserId(chgPwdReq.getUserId());
			GrmResponseVO vo200 = ef01180200.requestData(loginReq);
	        if(!vo200.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
	        	logger.debug("ChangeTradePwd, auto-login failed, clientId: {}, error: {}", loginReq.getClientId(), vo200.getErrorMsg());
	            return vo200;
	        } else {
	        	logger.debug("ChangeTradePwd, auto-login success, clientId: {}", loginReq.getClientId());
	        }
		}
		GrmResponseVO responseVO = super.requestData(requestVo);
//		if (responseVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//			// 如果是存量用户需重新激活交易 用户迁移临时过渡
//			userOldPwdsDao.activeTradePwd(chgPwdReq.getUserId(),chgPwdReq.getClientId());
//		}
		return responseVO;
	}

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100201Request){
            EF01100201Request req = (EF01100201Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
			reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
//			if (null != req.getUserId() && StringUtils.isNotBlank(req.getClientId()) && StringUtils.isNotBlank(req.getPassword())
//					&& userOldPwdsDao.checkOldTradePwd(req.getUserId(),req.getFundAccount(), req.getPassword())) {
//				// 存量用户迁移临时过渡
//				reqMap.put(HsConstants.Fields.PASSWORD, "12345678");
//			}
            reqMap.put(HsConstants.Fields.NEW_PASSWORD,req.getNewPassword());
            reqMap.put(HsConstants.Fields.PASSWORD_TYPE,"2");
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
