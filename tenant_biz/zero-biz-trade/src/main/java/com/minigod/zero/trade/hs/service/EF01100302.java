package com.minigod.zero.trade.hs.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.constant.MktConstants;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01100100Request;
import com.minigod.zero.trade.hs.req.EF01100302Request;
import com.minigod.zero.trade.hs.resp.EF01100100VO;
import com.minigod.zero.trade.hs.resp.EF01100302VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


/**
 * 服务_周边_委托确认
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01100302Request.class, responseVo = EF01100302VO.class)
public class EF01100302<T extends GrmRequestVO> extends T2sdkBiz<T> {
	//@Resource
	//private RestTemplateUtil restTemplateUtil;
	@Resource
	private TradeEventNotifyService tradeEventNotifyService;
	@Resource
	private EF01100100 eF01100100;
	@Resource
	private ZeroRedis zeroRedis;
	@Value("${trade.check.ipo:'false'}")
	private Boolean checkIpo;

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if (request instanceof EF01100302Request) {
			EF01100302Request req = (EF01100302Request) request;
			Map<String, String> reqMap = new HashMap();
			reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
			reqMap.put(HsConstants.Fields.STOCK_ACCOUNT, req.getFundAccount());
			reqMap.put(HsConstants.Fields.PASSWORD, req.getPassword());
			reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
			reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
			reqMap.put(HsConstants.Fields.ENTRUST_AMOUNT, req.getEntrustAmount());
			reqMap.put(HsConstants.Fields.ENTRUST_PRICE, req.getEntrustPrice());
			reqMap.put(HsConstants.Fields.ENTRUST_BS, req.getEntrustBs());
			reqMap.put(HsConstants.Fields.ENTRUST_TYPE, "0");
			reqMap.put(HsConstants.Fields.ENTRUST_PROP, req.getEntrustProp());
			reqMap.put(HsConstants.Fields.SHORTSELL_TYPE, "N");
			reqMap.put(HsConstants.Fields.HEDGE_FLAG, "B");
			// A股委托交易时传入
			if (StringUtils.isNotBlank(req.getBcan())) {
				reqMap.put(HsConstants.Fields.BCAN, req.getBcan());
			}
			if (StringUtils.isNotBlank(req.getSessionType())) {
				reqMap.put(HsConstants.Fields.SESSION_TYPE, req.getSessionType());
			} else {
				// 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易）
				reqMap.put(HsConstants.Fields.SESSION_TYPE, "0");
			}
			try{
//            	SecuritiesUserInfoReqVo condition = new SecuritiesUserInfoReqVo();
//            	condition.setUserId(req.getUserId());
//            	SecuritiesUserInfoRespVo securitiesUserInfo = securitiesUserInfoService.selectSecuritiesUserInfo(condition);
//            	logger.info("调用user获取fundAccount,userId={}",req.getUserId());
//            	if(securitiesUserInfo != null && securitiesUserInfo.getTradeAccount() != null){
//            		// 是否存量用户密码表能校验通过 户迁移临时过渡
//            		if (userOldPwdsDao.checkOldTradePwd(req.getUserId(),securitiesUserInfo.getTradeAccount(), req.getPassword())) {
//            			logger.info("存量用户校验交易密码通过，交易与资金账户: {}, {}", securitiesUserInfo.getTradeAccount(), req.getFundAccount());
//            			logger.info("存量用户校验交易密码通过,将访问恒生的密码设置为默认值，ClientId: {},userid:{}", securitiesUserInfo.getTradeAccount(),req.getUserId());
//            			req.setPassword("12345678");
//            			reqMap.put(HsConstants.Fields.PASSWORD, "12345678");
//            		}
//            	}
			}catch(Exception e){
				log.info("调用user获取fundAccount失败,userId={}",req.getUserId());
			}
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
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				EF01100302VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01100302VO.class);
				responseVO.addResultData(vo);
			}
			// TODO 买卖交易提醒
			tradeEventNotifyService.dispatchBuySellEvent(request, oParam, grmDataVO);
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}

	@Override
	public <R extends GrmRequestVO> GrmResponseVO requestData(R request) {
		if (request instanceof EF01100302Request) {
			EF01100302Request req = (EF01100302Request) request;
			String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(req.getStockCode(), req.getExchangeType()), req.getExchangeType());
			// 覆盖父类方法，先检查买卖股票是否还在IPO阶段
			Boolean confidentialMark = true;//restTemplateUtil.postSend(OpenApiConstant.US_CONFIDENTIAL_MARK, Boolean.class, assetId);
			if (GrmHsConstants.ExchangeType.HK.getExchangeType().equals(req.getExchangeType())
				&& checkIpo && !confidentialMark) {
				if (inIpo(req.getStockCode())) {
					return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.NOT_SUPPORT_IPO_TRADE)
						.setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.NOT_SUPPORT_IPO_TRADE, request.getLang()));
				}
			} else if (GrmHsConstants.ExchangeType.US.getExchangeType().equals(req.getExchangeType())) {
				//判断美股盘前盘后交易是否开启
				if (!tradeSwitch()) {
					//不在美股交易段，不允许买卖
					return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.NOT_IN_US_TRADE_TIME)
						.setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.NOT_IN_US_TRADE_TIME, request.getLang()));
				}
				//恒生 0-盘中交易，1-盘前交易，2-暗盘交易，3-盘后交易
				String sessionType = "0";
				if(req.getDiscType() == GrmHsConstants.DiscTypeEnum.TYPE_1){
					int periodType = 2;//restTemplateUtil.postSend(OpenApiConstant.US_PERIOD_TYPE, Integer.class, null);
					if (MktConstants.PeriodType.PRE == periodType) {
						sessionType = "1";
					} else if (MktConstants.PeriodType.POST == periodType) {
						sessionType = "3";
					}
				}
				req.setSessionType(sessionType);
			}
		}
		return super.requestData(request);
	}

    /**
     * 是否还在IPO阶段，即当前时间是否小于等于中签结果公布日
     *
     * @return false 若查不到相关信息或当前日期大于中签结果公布日，true 当前日期小于等于中签结果公布日
     */
    private boolean inIpo(String stockCode) {
        try {
            stockCode = StringUtils.leftPad(stockCode, 5, '0');
            Date resultDate = null;//restTemplateUtil.postSend(OpenApiConstant.HK_IPO_INFO, Date.class, stockCode);
			if (resultDate == null) {
				return false;
			}
            if (resultDate.getTime() > 0L && DateUtil.compare(new Date(), resultDate) <= 0) {
                // 当前日期小于等于中签结果公布日
                return true;
            }
        } catch (Exception ex) {
            log.error("inIpo error", ex);
        }
        return false;
    }

	/**
	 * 查询交易状态
	 */
	@SneakyThrows
	private boolean tradeSwitch(){
		if (zeroRedis.get("Trade:Switch:".concat(GrmFunctions.SV_EXT_SYS_LOGIN)) != null) {
			//已经开启美股盘前盘后交易，不再调用柜台接口判断
			return true;
		}
		EF01100100Request request = new EF01100100Request();
		request.setFunctionId(GrmFunctions.SV_EXT_SYS_LOGIN);
		request.setSid("0232d1566c0638a0a7583c967254c717");
		GrmResponseVO responseVO = eF01100100.requestData(request);
		if (GrmConstants.GRM_RESPONSE_OK.equals(responseVO.getErrorId())) {
			EF01100100VO ef01100100VO = (EF01100100VO) responseVO.resultData().get(0);
			Date now = new Date();
			String todayStr = DateUtil.format(now,"yyyyMMdd");
			if (ef01100100VO.getInitDate().compareTo(todayStr) > 0) {
				//柜台时间大于当前时间，结算完成
				log.info("hs clear done, initDate={}", ef01100100VO.getInitDate());
				String switchEndTimeStr = ef01100100VO.getInitDate() + " 09:00:00";
				Date switchEndTime = DateUtil.parse(switchEndTimeStr, "yyyyMMdd HH:mm:ss");
				long timeoutSeconds = (switchEndTime.getTime() - now.getTime()) / 1000;
				zeroRedis.setEx("Trade:Switch:".concat(GrmFunctions.SV_EXT_SYS_LOGIN),GrmFunctions.SV_EXT_SYS_LOGIN,timeoutSeconds);
				return true;
			}
		}
		return false;
	}
}
