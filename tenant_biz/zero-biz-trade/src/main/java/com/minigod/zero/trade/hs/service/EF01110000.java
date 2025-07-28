package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.hs.constants.Constants;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110000Request;
import com.minigod.zero.trade.hs.resp.ClientSession;
import com.minigod.zero.trade.hs.resp.EF01110000VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用于对操作员进行登录
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110000Request.class, responseVo = EF01110000VO.class)
public class EF01110000<T extends GrmRequestVO> extends T2sdkBiz<T> {
	@Resource
	private ZeroRedis zeroRedis;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110000Request) {
            EF01110000Request req = (EF01110000Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.OP_STATION, req.getOpStation());
            reqMap.put(HsConstants.Fields.OP_ENTRUST_WAY, req.getOpEntrustWay());
            reqMap.put(HsConstants.Fields.USER_TYPE, req.getUserType());
            reqMap.put(HsConstants.Fields.OPERATOR_NO, req.getOperatorNo());
            reqMap.put(HsConstants.Fields.OP_BRANCH_NO, req.getOpBranchNo());
            reqMap.put(HsConstants.Fields.OP_PASSWORD, req.getOpPassword());
            reqMap.put(HsConstants.Fields.FUNCTION_ID, "10000");
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            EF01110000VO vo;
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                vo = new EF01110000VO();
                String sessionId =  Constants.innerClientSideSid;
                vo.setSessionId(sessionId);
                vo.setOperatorName(rowMap.get(HsConstants.Fields.OPERATOR_NAME));
                vo.setCompanyName(rowMap.get(HsConstants.Fields.COMPANY_NAME));
                vo.setSysStatus(rowMap.get(HsConstants.Fields.SYS_STATUS));
                vo.setSysStatusName(rowMap.get(HsConstants.Fields.SYS_STATUS_NAME));
                vo.setMainBranchNo(rowMap.get(HsConstants.Fields.MAIN_BRANCH_NO));
                vo.setDefMoneyType(rowMap.get(HsConstants.Fields.DEF_MONEY_TYPE));
                vo.setInitDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                responseVO.addResultData(vo);
                //保存session
                Date now = new Date();
                ClientSession clientSession = new ClientSession();
                clientSession.setIpAddress(request.getIpAddress());
                clientSession.setSid(request.getSid());
                clientSession.setUserType(oParam.get(HsConstants.Fields.USER_TYPE));
                clientSession.setClientId(oParam.get(HsConstants.Fields.OPERATOR_NO));
                clientSession.setBranchNo(oParam.get(HsConstants.Fields.OP_BRANCH_NO));
                clientSession.setClientName(vo.getOperatorName());
                clientSession.setSessionId(sessionId);
                clientSession.setLoginTime(now);
                clientSession.setHasLogin(true);
                clientSession.setLastOperateTime(now);
                clientSession.setOpStation(oParam.get(HsConstants.Fields.OP_STATION));
                clientSession.setEntrustWay(oParam.get(HsConstants.Fields.OP_ENTRUST_WAY));
				zeroRedis.set(sessionId, clientSession);
            }
            return responseVO;
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
