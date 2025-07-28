package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110018Request;
import com.minigod.zero.trade.hs.resp.EF01110018VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证交易密码
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110018Request.class,responseVo = EF01110018VO.class)
public class EF01110018<T extends GrmRequestVO>  extends T2sdkBiz<T> {

//    @Resource
//    protected UserOldPwdsDao userOldPwdsDao;

    @Override
    public <R extends GrmRequestVO> GrmResponseVO requestData(R requestVo) {
        // 校验交易密码
        GrmResponseVO responseVO = super.requestData(requestVo);
        if (!responseVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK) && requestVo instanceof EF01110018Request) {
            EF01110018Request request = (EF01110018Request) requestVo;
            // 是否存量用户密码表能校验通过 户迁移临时过渡
//            if (null != request.getUserId() && StringUtils.isNotBlank(request.getClientId()) && StringUtils.isNotBlank(request.getPassword())
//                    && userOldPwdsDao.checkOldTradePwd(request.getUserId(),request.getClientId(), request.getPassword())) {
//                logger.info("存量用户校验交易密码通过，交易与资金账户: {}, {}", request.getClientId(), request.getFundAccount());
//                // 调恒生接口修改交易密码
//                EF01100201Request modifyReq = new EF01100201Request();
//                modifyReq.copyCommonParams(requestVo);
//                modifyReq.setFunctionId(GrmFunctions.CLIENT_MODIFY_PASSWORD);
//                modifyReq.setClientId(request.getClientId());
//                modifyReq.setUserId(request.getUserId());
//                modifyReq.setFundAccount(request.getFundAccount());
//                modifyReq.setOpStation(request.getOpStation());
//                modifyReq.setPassword("12345678");
//                modifyReq.setNewPassword(request.getPassword());
//                modifyReq.setNeedLogin(false); //存量用户不用登录
//                logger.info("修改密码参数：{}",modifyReq);
//                GrmResponseVO vo201 = ef01100201.requestData(modifyReq);
//
//                if (vo201.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//                    // 激活存量用户交易密码
//                    userOldPwdsDao.activeTradePwd(request.getUserId(),request.getClientId());
//                    responseVO.setErrorId(GrmConstants.GRM_RESPONSE_OK);
//                    responseVO.setErrorMsg(GrmConstants.GRM_RESPONSE_OK_MSG);
//                    logger.info("存量用户更新交易密码到恒生成功，并激活交易: {}", request.getClientId());
//                } else {
//                    logger.error("存量用户更新交易密码到恒生失败: {}", request.getClientId());
//                }
//            }
        }
        return responseVO;
    }

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110018Request){
            EF01110018Request req = (EF01110018Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
            reqMap.put(HsConstants.Fields.INPUT_CONTENT,"6");
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
