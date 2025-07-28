package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.trade.hs.constants.Constants;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100200Request;
import com.minigod.zero.trade.hs.resp.ClientSession;
import com.minigod.zero.trade.hs.resp.EF01100200VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 恒生客户登录
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01100200Request.class, responseVo = EF01100200VO.class)
public class EF01100200<T extends GrmRequestVO> extends T2sdkBiz<T> {
	@Resource
	private ZeroRedis zeroRedis;
//    @Resource
//    protected UserOldPwdsDao userOldPwdsDao;
	@Lazy
    @Resource
    private EF01100201 ef01100201;
//    @Resource
//    private SecuritiesUserInfoService securitiesUserInfoService;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100200Request) {
            EF01100200Request req = (EF01100200Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.OP_STATION, req.getOpStation());
            reqMap.put(HsConstants.Fields.OP_BRANCH_NO, req.getBranchNo());
            reqMap.put(HsConstants.Fields.PASSWORD, req.getPassword());
            reqMap.put(HsConstants.Fields.BRANCH_NO, req.getBranchNo());
            reqMap.put(HsConstants.Fields.CONTENT_TYPE, "0");

//            String loginType = defaultConfig.getVal("grm.login.type");
//            // 帐号类型，'6’表示account_content代表客户账号，‘1’表示account_content代表资金账号
//            if("6".equals(loginType)){
//            	logger.info("客户号登陆");
            	reqMap.put(HsConstants.Fields.INPUT_CONTENT, "6");
            	reqMap.put(HsConstants.Fields.ACCOUNT_CONTENT, req.getClientId());
//            }else if("1".equals(loginType)){
//            	reqMap.put(HsConstants.Fields.INPUT_CONTENT, "1");
//            	SecuritiesUserInfoReqVo condition = new SecuritiesUserInfoReqVo();
//            	condition.setUserId(req.getUserId());
//            	SecuritiesUserInfoRespVo securitiesUserInfo = securitiesUserInfoService.selectSecuritiesUserInfo(condition);
//            	reqMap.put(HsConstants.Fields.ACCOUNT_CONTENT, securitiesUserInfo.getFundAccount());
//            	logger.info("资金账户登陆");
//            }else{
//            	logger.error("不支持的登陆类型：" + loginType);
//            }

            String ipAddr = WebUtil.getIP();
            try {
//                if (ContextHolder.getRequest() != null) {
//                    ipAddr = ContextHolder.getRequest().getIp();
//                }
            } catch (Exception ex) {
                log.error("getIpAddress error", ex);
            }
            if (StringUtils.isNotBlank(ipAddr)) {
                int commaIndex = ipAddr.indexOf(',');
                if (commaIndex > 0) {
                    ipAddr = ipAddr.substring(0, commaIndex);
                }
                final int MAX_LEN = 64;
                if (ipAddr.length() > MAX_LEN) {
                    ipAddr = ipAddr.substring(0, MAX_LEN + 1);
                }
                reqMap.put(HsConstants.Fields.LOGIN_IP, ipAddr);
            }

            // 是否存量用户密码表能校验通过 户迁移临时过渡
//            if (null != req.getUserId() && StringUtils.isNotBlank(req.getClientId()) && StringUtils.isNotBlank(req.getPassword())
//                    && userOldPwdsDao.checkOldTradePwd(req.getUserId(),req.getClientId(), req.getPassword())) {
//                logger.info("存量用户校验交易密码通过，交易与资金账户: {}, {}", req.getClientId(), req.getClientId());
//                logger.info("调用user获取fundAccount,userId={}",req.getUserId());
//            	SecuritiesUserInfoReqVo condition = new SecuritiesUserInfoReqVo();
//            	condition.setUserId(req.getUserId());
//            	SecuritiesUserInfoRespVo securitiesUserInfo = securitiesUserInfoService.selectSecuritiesUserInfo(condition);
//            	if(securitiesUserInfo != null && securitiesUserInfo.getFundAccount() != null){
//            		// 调恒生接口修改交易密码
//            		EF01100201Request modifyReq = new EF01100201Request();
//            		modifyReq.copyCommonParams(request);
//            		modifyReq.setFunctionId(GrmFunctions.CLIENT_MODIFY_PASSWORD);
//            		modifyReq.setClientId(req.getClientId());
//            		modifyReq.setUserId(request.getUserId());
//            		modifyReq.setFundAccount(securitiesUserInfo.getFundAccount());
//            		modifyReq.setOpStation(request.getOpStation());
//            		modifyReq.setPassword("12345678");
//            		modifyReq.setNewPassword(req.getPassword());
//            		modifyReq.setNeedLogin(false); //存量用户不用登录
//            		logger.info("修改密码参数：{}",modifyReq);
//            		GrmResponseVO vo201 = ef01100201.requestData(modifyReq);
//            		if (vo201.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//            			// 激活存量用户交易密码
//            			userOldPwdsDao.activeTradePwd(request.getUserId(),req.getClientId());
//            			//responseVO.setErrorId(GrmConstants.GRM_RESPONSE_OK);
//            			//responseVO.setErrorMsg(GrmConstants.GRM_RESPONSE_OK_MSG);
//            			logger.info("存量用户更新交易密码到恒生成功，并激活交易: {}", req.getClientId());
//            		} else {
//            			logger.error("存量用户更新交易密码到恒生失败: {}", req.getClientId());
//            			logger.info("EF01100200存量用户校验交易密码通过,更新交易密码到恒生失败,将访问恒生的密码设置为默认值，ClientId: {},userid:{}", req.getClientId(),req.getUserId());
//            			req.setPassword("12345678");
//            			reqMap.put(HsConstants.Fields.PASSWORD, "12345678");
//            		}
//            	}
//            }

            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (!grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
        	EF01100200Request req = (EF01100200Request) request;
            // 是否存量用户密码表能校验通过 户迁移临时过渡
//            if (null != req.getUserId() && StringUtils.isNotBlank(req.getClientId()) && StringUtils.isNotBlank(req.getPassword())
//                    && userOldPwdsDao.checkOldTradePwd(req.getUserId(),oParam.get(HsConstants.Fields.ACCOUNT_CONTENT), oParam.get(HsConstants.Fields.PASSWORD))) {
//                grmDataVO.setErrorId(GrmConstants.GRM_RESPONSE_OK);
//                grmDataVO.setErrorMsg(GrmConstants.GRM_RESPONSE_OK_MSG);
//                logger.info("存量用户交易账号登录校验通过，此处不更新不激活密码: {}", oParam.get(HsConstants.Fields.ACCOUNT_CONTENT));
//            }
        }
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            EF01100200Request req = (EF01100200Request) request;
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            List<Object> listVO = new ArrayList<>();
            String sessionId = request.getSessionId();
            if (StringUtils.isEmpty(sessionId)) {
                //线下开户用户首次登录，sessionId空
                sessionId = req.getClientId();
            }
            String clientName = null;
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01100200VO vo = new EF01100200VO();
                vo.setBranchNo(rowMap.get(HsConstants.Fields.BRANCH_NO));
                vo.setClientId(rowMap.get(HsConstants.Fields.CLIENT_ID));
                vo.setClientName(rowMap.get(HsConstants.Fields.CLIENT_NAME));
                vo.setFundAccount(rowMap.get(HsConstants.Fields.FUND_ACCOUNT));
                vo.setSysStatus(rowMap.get(HsConstants.Fields.SYS_STATUS));
                vo.setSessionId(sessionId);
                listVO.add(vo);
                clientName = vo.getClientName();
            }
            responseVO.addResultData(listVO);
            //保存session
            ClientSession clientSession = zeroRedis.get(sessionId);
            if (clientSession == null) {
                clientSession = new ClientSession();
            }
            clientSession.setUserType(Constants.USER_TYPE_CLIENT);
            clientSession.setIpAddress(oParam.get(HsConstants.Fields.LOGIN_IP));
            clientSession.setSid(request.getSid());
            clientSession.setClientId(req.getClientId());
            clientSession.setBranchNo(req.getBranchNo());
            clientSession.setClientName(clientName);
            clientSession.setSessionId(sessionId);
            clientSession.setLoginTime(new Date());
            clientSession.setHasLogin(true);
            clientSession.setEntrustWay(oParam.get(HsConstants.Fields.ENTRUST_WAY));
            clientSession.setLastOperateTime(clientSession.getLoginTime());
            clientSession.setOpStation(req.getOpStation());
			zeroRedis.set(sessionId, clientSession);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
