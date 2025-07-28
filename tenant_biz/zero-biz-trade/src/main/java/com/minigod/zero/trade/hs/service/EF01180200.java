package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.*;
import com.minigod.zero.trade.hs.resp.ClientSession;
import com.minigod.zero.trade.hs.resp.EF01100200VO;
import com.minigod.zero.trade.hs.resp.EF01180200VO;
import com.minigod.zero.trade.hs.resp.FundAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户登录，保存登录session
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01180200Request.class,responseVo = EF01180200VO.class)
public class EF01180200 extends Abstractbiz {
    @Resource
    private EF01100200 ef01100200;
    @Resource
    private EF01100808 ef01100808;
    @Resource
    private EF01100816 ef01100816;
    @Resource
    private EF01100811 ef811;
    //@Resource
    //private JmsTemplate pushMsg2UserJmsTemplate;
	@Resource
	private ZeroRedis zeroRedis;
//    @Resource
//    protected UserOldPwdsDao userOldPwdsDao;
//    @Resource
//    private SecuritiesUserInfoService securitiesUserInfoService;
    @Resource
    private EF01100201 ef01100201;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
    	GrmResponseVO responseVO = GrmResponseVO.getInstance();
        EF01180200Request req =(EF01180200Request) request;

        // 是否存量用户密码表能校验通过 户迁移临时过渡
//        if (null != req.getUserId() && StringUtils.isNotBlank(req.getClientId()) && StringUtils.isNotBlank(req.getPassword())
//        && userOldPwdsDao.checkOldTradePwd(req.getUserId(),req.getClientId(), req.getPassword())) {
//        	log.info("存量用户校验交易密码通过，交易与资金账户: {}, {}", req.getClientId(), req.getClientId());
//			log.info("调用user获取fundAccount,userId={}",req.getUserId());
//        	SecuritiesUserInfoReqVo condition = new SecuritiesUserInfoReqVo();
//        	condition.setUserId(req.getUserId());
//        	SecuritiesUserInfoRespVo securitiesUserInfo = securitiesUserInfoService.selectSecuritiesUserInfo(condition);
//        	if(securitiesUserInfo != null && securitiesUserInfo.getFundAccount() != null){
//        		// 调恒生接口修改交易密码
//        		EF01100201Request modifyReq = new EF01100201Request();
//        		modifyReq.copyCommonParams(request);
//        		modifyReq.setFunctionId(GrmFunctions.CLIENT_MODIFY_PASSWORD);
//        		modifyReq.setClientId(req.getClientId());
//        		modifyReq.setUserId(request.getUserId());
//        		modifyReq.setFundAccount(securitiesUserInfo.getFundAccount());
//        		modifyReq.setOpStation(request.getOpStation());
//        		modifyReq.setPassword("12345678");
//        		modifyReq.setNewPassword(req.getPassword());
//        		modifyReq.setNeedLogin(false); //存量用户不用登录
//				log.info("修改密码参数：{}",modifyReq);
//        		GrmResponseVO vo201 = ef01100201.requestData(modifyReq);
//        		if (vo201.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//        			// 激活存量用户交易密码
//        			userOldPwdsDao.activeTradePwd(request.getUserId(),req.getClientId());
//        			responseVO.setErrorId(GrmConstants.GRM_RESPONSE_OK);
//        			responseVO.setErrorMsg(GrmConstants.GRM_RESPONSE_OK_MSG);
//					log.info("存量用户更新交易密码到恒生成功，并激活交易: {}", req.getClientId());
//        		} else {
//					log.error("存量用户更新交易密码到恒生失败: {}", req.getClientId());
//					log.info("EF01180200存量用户校验交易密码通过,将访问恒生的密码设置为默认值，ClientId: {},userid:{}", req.getClientId(),req.getUserId());
//        			req.setPassword("12345678");
//        		}
//
//            }
//        }

        String lang = req.getLang();
        Map<String,String> oParam = new HashMap();
        if(StringUtils.isEmpty(lang)){
            lang = Constants.LANG_DEFAULT;
        }

        String clientId = req.getClientId();
        String opStation = req.getOpStation();
        String password = req.getPassword();
        //获取营业部
        String branchNo = HsConstants.InnerBrokerConfig.INNER_OP_BRANCHNO;
        //登录接口
        EF01100200Request req200 = new EF01100200Request();
        req200.copyCommonParams(request);
        req200.setFunctionId(GrmFunctions.CLIENT_LOGIN);
        req200.setClientId(clientId);
        req200.setOpStation(opStation);
        req200.setBranchNo(branchNo);
        req200.setPassword(password);
        req200.setUserId(req.getUserId());
        GrmResponseVO vo200 = ef01100200.requestData(req200);
        if(!vo200.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
            return vo200;
        }
        String sessionId = ((EF01100200VO)vo200.resultData().get(0)).getSessionId();

        //获取帐号列表
        EF01100808Request req808 = new EF01100808Request();
        req808.setFunctionId(GrmFunctions.CLIENT_GET_FUND_ACCT_INFO);
        req808.copyCommonParams(request);
        req808.setClientId(req.getClientId());
        req808.setSessionId(sessionId);
        GrmResponseVO fundsVo = ef01100808.requestData(req808);
        if(!fundsVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
            return fundsVo;
        }
        List<Object> fundAccts = (List<Object>) fundsVo.resultData();
        List<FundAccountInfo> fundAccountInfos = new ArrayList<>();
        String fundAccount;
        String mainFlag;
        for(Object fo :fundAccts){
            FundAccountInfo fundAcct = (FundAccountInfo) fo;
            fundAccount = fundAcct.getFundAccount();
            mainFlag = fundAcct.getMainFlag();
                //获取帐号类型
                EF01100816Request req816 = new EF01100816Request();
                req816.setClientId(req.getClientId());
                req816.setFundAccount(fundAccount);
                req816.copyCommonParams(request);
                req816.setSessionId(sessionId);
                req816.setFunctionId(GrmFunctions.CLIENT_GET_FUND_ACCT_TYPE);
                GrmResponseVO ftVo= ef01100816.requestData(req816);
                if(!ftVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
                    return ftVo;
                }
                fundAcct = (FundAccountInfo)ftVo.resultData().get(0);
                fundAccountInfos.add(fundAcct);
        }
        ClientSession clientSession = zeroRedis.findObject(ClientSession.class,sessionId);
        clientSession.setFundAccts(fundAccountInfos);

        // 检查密码是否过期 811
        EF01100811Request req811 = new EF01100811Request();
        req811.copyCommonParams(request);
        req811.setFunctionId(GrmFunctions.CLIENT_CHECK_PASSWORD_EXPIRED);
        req811.setClientId(clientId);
        req811.setPasswordType("2");
        req811.setPassword(clientId);
        try {
	        GrmResponseVO resp811 = ef811.requestData(req811);
	        final String PWD_EXPIRED_ERRORCODE = "610556";
	        if (PWD_EXPIRED_ERRORCODE.equals(resp811.getErrorId())) {
	        	String pwdChangeTips = ErrorMsgHandler.getErrorMsg(PWD_EXPIRED_ERRORCODE, Constants.LANG_DEFAULT);
	        	//  TODO pushMsg2User(request.getSessionId(), pwdChangeTips);
	        }
        } catch (Exception ex) {
			log.error("EF01100811", ex);
        }

        return responseVO.setResult(fundAccountInfos);
    }

	/** TODO
	private void pushMsg2User(String sessionId, String pwdChangeTips) {
        try{
            MessageCreator messageCreator = session -> {
                Map<String,Object> map = new HashMap<>();
                map.put("sessionId",sessionId);
                map.put("pwdChangeTips",pwdChangeTips);
                TextMessage message = session.createTextMessage(JSONUtil.toJson(map));
                message.setJMSType(MqBizTypeConstant.PushMsg2User);
                return message;
            };
            pushMsg2UserJmsTemplate.send(messageCreator);
        }catch (Exception e){
            log.error("Invoke pushMsg2User " +  " Failed!", e);
        }
	}
	*/
}
