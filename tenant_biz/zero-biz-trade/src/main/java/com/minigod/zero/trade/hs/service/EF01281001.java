package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.filter.GrmServerHolder;
import com.minigod.zero.trade.hs.req.*;
import com.minigod.zero.trade.hs.resp.*;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 根据客户号获取客户资产信息，AE模式
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01281001Request.class, responseVo = EF01281001VO.class)
public class EF01281001 extends Abstractbiz {
    @Resource
    private EF01110003 ef01110003;
    @Resource
    private EF01110004 ef01110004;
    @Resource
    private EF01110014 ef01110014;
    @Resource
    private EF01110015 ef01110015;
	@Resource
	private ZeroRedis zeroRedis;
    @Resource
    private GrmServerHolder grmServerHolder;
    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        ClientSession clientSession = new ClientSession();
        EF01281001VO vo = new EF01281001VO();
        ClientInfo clientInfo = new ClientInfo();
        EF01281001Request req = (EF01281001Request) request;
        String lang = req.getLang();
        Map<String, String> oParam = new HashMap();
        if (StringUtils.isEmpty(lang)) {
            lang = Constants.LANG_DEFAULT;
        }
		GrmServerEntity grmServer = grmServerHolder.getServerBySid(req.getSid());

        //操作员登录
        String operatorSessionId;
        if(grmServerHolder.isInnerClient(req.getSid())){
            operatorSessionId = Constants.innerClientSideSid;
        } else {
            return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.INVALID_REQUEST).setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.INVALID_REQUEST,req.getLang()));
        }

        //获取营业部
        String branchNo = HsConstants.InnerBrokerConfig.INNER_OP_BRANCHNO;
        //获取帐号列表
        EF01110014Request ef01110014Request = new EF01110014Request();
        ef01110014Request.copyCommonParams(request);
        ef01110014Request.setFunctionId(GrmFunctions.BROKER_GET_CLIENT_FUND_ACCT_INFO);
        ef01110014Request.setBranchNo(branchNo);
        ef01110014Request.setClientId(req.getClientId());
        ef01110014Request.setSessionId(operatorSessionId);
        GrmResponseVO fundsVo = ef01110014.requestData(ef01110014Request);
        if (!fundsVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            return fundsVo;
        }
        List fundAccts = fundsVo.resultData();
        List<FundAccountInfoWithCash> fundAccountInfoWithCashs = new ArrayList<>();
        List<FundAccountInfo> fundAccountInfos = new ArrayList<>();
        ClientCashSumInfo clientCashSumInfo;
        FundAccountInfo fundAccountInfo ;
        for (Object fo : fundAccts) {
            FundAccountInfoWithCash fundAccountInfoWithCash = new FundAccountInfoWithCash();
            fundAccountInfo = new FundAccountInfo();
            FundAccountInfo fundAcct = (FundAccountInfo) fo;
            String fundAccount = fundAcct.getFundAccount();

            //获取帐号类型
            EF01110015Request ef01110015Request = new EF01110015Request();
            ef01110015Request.copyCommonParams(request);
            ef01110015Request.setClientId(req.getClientId());
            ef01110015Request.setBranchNo(branchNo);
            ef01110015Request.setFundAccount(fundAccount);
            ef01110015Request.setFunctionId(GrmFunctions.BROKER_GET_FUND_ACCT_TYPE);
            ef01110015Request.setSessionId(operatorSessionId);
            GrmResponseVO ftVo = ef01110015.requestData(ef01110015Request);
            if (!ftVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                return ftVo;
            }
            fundAcct = (FundAccountInfo) (ftVo.resultData()).get(0);
            fundAccountInfoWithCash.setFundAccount(fundAcct.getFundAccount());
            fundAccountInfoWithCash.setAssetProp(fundAcct.getAssetProp());
            fundAccountInfoWithCash.setRestriction(fundAcct.getRestriction());
            fundAccountInfoWithCash.setMainFlag(fundAcct.getMainFlag());

            fundAccountInfo.setFundAccount(fundAcct.getFundAccount());
            fundAccountInfo.setAssetProp(fundAcct.getAssetProp());
            fundAccountInfo.setRestriction(fundAcct.getRestriction());
            fundAccountInfo.setMainFlag(fundAcct.getMainFlag());
            fundAccountInfos.add(fundAccountInfo);
            //fundAccountInfoWithCash.setStkAccts((List)stksVo.resultData());
            //查单币种资金
            EF01110003Request ef01110003Request = new EF01110003Request();
            ef01110003Request.copyCommonParams(request);
            ef01110003Request.setFunctionId(GrmFunctions.BROKER_GET_CACHE_INFO);
            ef01110003Request.setFundAccount(fundAccount);
            ef01110003Request.setSessionId(operatorSessionId);
            GrmResponseVO cashVo = ef01110003.requestData(ef01110003Request);
            if (!cashVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                return cashVo;
            }
            fundAccountInfoWithCash.setCashInfos((List) cashVo.resultData());
            //查汇总资金
            EF01110004Request ef01110004Request = new EF01110004Request();
            ef01110004Request.copyCommonParams(request);
            ef01110004Request.setFunctionId(GrmFunctions.BROKER_GET_CACHE_SUM_INFO);
            ef01110004Request.setFundAccount(fundAccount);
            ef01110004Request.setToMoneyType("2");  //默认港股汇总
            ef01110004Request.setSessionId(operatorSessionId);
            GrmResponseVO sumVo = ef01110004.requestData(ef01110004Request);
            if (!sumVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                return sumVo;
            }
            fundAccountInfoWithCash.setCashSumInfo((ClientCashSumInfo) (sumVo.resultData()).get(0));
            fundAccountInfoWithCashs.add(fundAccountInfoWithCash);
        }
        clientInfo.setClientId(req.getClientId());
        clientInfo.setFundAccts(fundAccountInfoWithCashs);
        vo.setClientInfo(clientInfo);
        //存入客户非登录session
        clientSession.setClientId(req.getClientId());
        clientSession.setBranchNo(branchNo);
        clientSession.setFundAccts(fundAccountInfos);
        clientSession.setIpAddress(request.getIpAddress());
        clientSession.setSid(request.getSid());
        clientSession.setUserType(Constants.USER_TYPE_CLIENT);
        clientSession.setSessionId(request.getSessionId());
        clientSession.setLoginTime(new Date());
        clientSession.setLastOperateTime(clientSession.getLoginTime());
        clientSession.setOpStation(req.getOpStation());
        clientSession.setEntrustWay(StringUtils.isEmpty(req.getEntrustWay())?grmServer.getEntrustWay():req.getEntrustWay());
		zeroRedis.saveUpdate(clientSession, clientSession.getSessionId());
        return responseVO.addResultData(vo);
    }
}
