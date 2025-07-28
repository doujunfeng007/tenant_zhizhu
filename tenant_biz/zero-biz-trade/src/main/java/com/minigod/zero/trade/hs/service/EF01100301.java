package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01100301Request;
import com.minigod.zero.trade.hs.req.EF01110004Request;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.resp.EF01100301VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 获取营业部编号
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01100301Request.class, responseVo = EF01100301VO.class)
public class EF01100301<T extends GrmRequestVO> extends T2sdkBiz<T> {
    @Resource
    private EF01110004 ef01110004;
//    @Resource
//    protected UserOldPwdsDao userOldPwdsDao;
    @Resource
    private EF01100201 ef01100201;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100301Request) {
            EF01100301Request req = (EF01100301Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.STOCK_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.PASSWORD, req.getPassword());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            reqMap.put(HsConstants.Fields.ENTRUST_PRICE, req.getEntrustPrice());
            if (StringUtils.isNotBlank(req.getEntrustProp()) &&
                    GrmHsConstants.EntrustProp.OLI.getCode().equals(req.getEntrustProp())) {
                // 碎股单单独处理
                reqMap.put(HsConstants.Fields.ENTRUST_PROP, req.getEntrustProp());
            } else {
                reqMap.put(HsConstants.Fields.ENTRUST_PROP, "0");
            }

            // 是否存量用户密码表能校验通过 户迁移临时过渡
//            if (null != req.getUserId() && StringUtils.isNotBlank(req.getClientId()) && StringUtils.isNotBlank(req.getPassword())
//                    && userOldPwdsDao.checkOldTradePwd(req.getUserId(),req.getClientId(), req.getPassword())) {
//            	logger.info("存量用户校验交易密码通过，交易与资金账户: {}, {}", req.getClientId(), req.getFundAccount());
//                // 调恒生接口修改交易密码
//                EF01100201Request modifyReq = new EF01100201Request();
//                modifyReq.copyCommonParams(request);
//                modifyReq.setFunctionId(GrmFunctions.CLIENT_MODIFY_PASSWORD);
//                modifyReq.setClientId(req.getClientId());
//                modifyReq.setUserId(request.getUserId());
//                modifyReq.setFundAccount(req.getFundAccount());
//                modifyReq.setOpStation(request.getOpStation());
//                modifyReq.setPassword("12345678");
//                modifyReq.setNewPassword(req.getPassword());
//                modifyReq.setNeedLogin(false); //存量用户不用登录
//                logger.info("修改密码参数：{}",modifyReq);
//                GrmResponseVO vo201 = ef01100201.requestData(modifyReq);
//                if (vo201.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//                    // 激活存量用户交易密码
//                    userOldPwdsDao.activeTradePwd(request.getUserId(),req.getClientId());
//                    //responseVO.setErrorId(GrmConstants.GRM_RESPONSE_OK);
//                    //responseVO.setErrorMsg(GrmConstants.GRM_RESPONSE_OK_MSG);
//                    logger.info("存量用户更新交易密码到恒生成功，并激活交易: {}", req.getClientId());
//                } else {
//                    logger.error("存量用户更新交易密码到恒生失败: {}", req.getClientId());
//                    logger.info("EF01100301存量用户校验交易密码通过,更新交易密码到恒生失败,将访问恒生的密码设置为默认值，ClientId: {},userid:{}", req.getClientId(),req.getUserId());
//                    req.setPassword("12345678");
//                    reqMap.put(HsConstants.Fields.PASSWORD, "12345678");
//                }
//            }

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
            EF01100301VO vo;
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                vo = new EF01100301VO();
                String enableAmount = rowMap.get(HsConstants.Fields.ENABLE_AMOUNT);
                if (StringUtils.isNotBlank(enableAmount)){
					vo.setEnableAmount(new BigDecimal(enableAmount).intValue() + "");
				}

                String cashAmount = rowMap.get(HsConstants.Fields.CASH_AMOUNT);
                if (StringUtils.isNotBlank(enableAmount)){
					vo.setCashAmount(new BigDecimal(cashAmount).intValue() + "");
				}
                responseVO.addResultData(vo);
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());

        try {
            // 碎股交易单独处理
            checkOLIResponse(request, responseVO);
        } catch (Exception e) {
            log.error("处理碎股最大可买股票数量异常, request:{}, response:{}", JSON.toJSONString(request),
                    JSON.toJSONString(responseVO), e);
        }

        return responseVO;
    }

    private <R extends GrmRequestVO> void checkOLIResponse(R request, GrmResponseVO responseVO) {
        if (request instanceof EF01100301Request) {
            EF01100301Request req = (EF01100301Request) request;
            if (StringUtils.isNotBlank(req.getEntrustProp()) &&
                    GrmHsConstants.EntrustProp.OLI.getCode().equals(req.getEntrustProp())) {
                log.info("request:{}", JSON.toJSONString(request));
                log.info("responseVO:{}", JSON.toJSONString(responseVO));

                // 碎股单单独处理最大可买, 超过一手
                String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(req.getStockCode(), req.getExchangeType()), req.getExchangeType());
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);

                log.info("assetInfo:{}", JSON.toJSONString(assetInfo));
                if (assetInfo != null) {
                    Integer lotSize = assetInfo.getLotSize();
                    List<?> objects = responseVO.resultData();
                    if (!CollectionUtils.isEmpty(objects)) {
                        EF01100301VO ef01100301VO = (EF01100301VO) objects.get(0);
                        EF01110004Request ef01110004Request = buildEF01110004Request(req);
                        GrmResponseVO grmResponseVO = null;
                        // 最大可买
                        if (Integer.valueOf(ef01100301VO.getEnableAmount()) > 0) {
                            ef01100301VO.setEnableAmount(String.valueOf(lotSize - 1));
                        } else {
                            grmResponseVO = ef01110004.requestData(ef01110004Request);
                            log.info("grmResponseVO:{}", JSON.toJSONString(grmResponseVO));
                            //购买力/entrustPrice
                            if (grmResponseVO != null) {
                                List<?> objects1 = grmResponseVO.resultData();
                                if (!CollectionUtils.isEmpty(objects1)) {
                                    ClientCashSumInfo clientCashSumInfo = (ClientCashSumInfo) objects1.get(0);
                                    BigDecimal enableBalance = new BigDecimal(clientCashSumInfo.getEnableBalance());
                                    BigDecimal entrustPrice = new BigDecimal(req.getEntrustPrice());
                                    Integer enableAmount = enableBalance.divide(entrustPrice, 1, BigDecimal.ROUND_DOWN).intValue();
                                    ef01100301VO.setEnableAmount(enableAmount >= lotSize ?
                                            String.valueOf(lotSize - 1) : String.valueOf(enableAmount));
                                }
                            }
                        }

                        // 现金可买
                        if (Integer.valueOf(ef01100301VO.getCashAmount()) > 0) {
                            ef01100301VO.setCashAmount(String.valueOf(lotSize - 1));
                        } else {
                            if (grmResponseVO == null) {
                                grmResponseVO = ef01110004.requestData(ef01110004Request);
                                log.info("grmResponseVO:{}", JSON.toJSONString(grmResponseVO));
                            }

                            //现金/entrustPrice
                            if (grmResponseVO != null) {
                                List<?> objects1 = grmResponseVO.resultData();
                                if (!CollectionUtils.isEmpty(objects1)) {
                                    ClientCashSumInfo clientCashSumInfo = (ClientCashSumInfo) objects1.get(0);
                                    BigDecimal fetchBalance = new BigDecimal(clientCashSumInfo.getFetchBalance());
                                    BigDecimal entrustPrice = new BigDecimal(req.getEntrustPrice());
                                    Integer cashAmount = fetchBalance.divide(entrustPrice, 1, BigDecimal.ROUND_DOWN).intValue();
                                    if (cashAmount < 0) {
                                        ef01100301VO.setCashAmount(String.valueOf(0));
                                    } else {
                                        ef01100301VO.setCashAmount(cashAmount >= lotSize ?
                                                String.valueOf(lotSize - 1) : String.valueOf(cashAmount));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private EF01110004Request buildEF01110004Request(EF01100301Request req) {
        EF01110004Request ef01110004Request = new EF01110004Request();
        ef01110004Request.setOpStation(req.getOpStation());
        ef01110004Request.setFunctionId(GrmFunctions.BROKER_GET_CACHE_SUM_INFO);
        ef01110004Request.setFundAccount(req.getFundAccount());
        ef01110004Request.setToMoneyType(Constants.MoneyType.HKD);
        ef01110004Request.setSessionId(req.getSessionId());
        ef01110004Request.setSid(req.getSid());
        ef01110004Request.setLang(req.getLang());

        return ef01110004Request;
    }
}
