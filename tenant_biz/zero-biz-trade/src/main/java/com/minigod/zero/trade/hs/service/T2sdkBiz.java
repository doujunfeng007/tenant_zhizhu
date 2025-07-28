package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.filter.GrmServerHolder;
import com.minigod.zero.trade.hs.req.EF01110000Request;
import com.minigod.zero.trade.hs.resp.ClientSession;
import com.minigod.zero.trade.hs.resp.FunctionEntity;
import com.minigod.zero.trade.hs.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunline on 2016/4/20 10:21.
 * sunline
 * 对T2SDKBiz进行Spring单例改造
 *
 * 需要设置参数，在sdkBiz
 * function_id -- functionId
 *
 * 无须设置参数，默认值
 * license_str
 * branch_no
 * entrust_way
 * op_entrust_way
 * op_station
 * user_type - 互联网接口
 * operator_no - 互联网接口
 * op_branch_no - 互联网接口
 *
 * 其他必须参数或非必须参数,在接口实现中赋值
 *
 */
@Slf4j
@Component
public abstract class T2sdkBiz<R extends GrmRequestVO> implements GrmBiz, ApplicationContextAware {
	@Resource
	private GrmServerHolder grmServerHolder;
	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private RedisLockClient redisLockClient;
    @Resource
    protected HsFieldFormater fieldFormater;
	@Resource
	protected GrmCacheMgr grmCacheMgr;
	@Lazy
	@Resource
	private EF01110000 ef01110000;
	//@Resource
	//protected TtionalNameService ttionalNameService;

    private static T2Services sdkServices = null;
    protected GrmDataVO responseVO;
	private static ApplicationContext context;
    private static final List<GrmEventListener> listeners = new ArrayList();

    /**柜台响应超时时间 20秒*/
    private int readTimeout = 20;
    /**单个客户下单频率限制开关*/
    private boolean placeOrderLimitOpen = false;
    /**单个客户下单频率限制1秒*/
    private int placeOrderInternalSecond = 1;
	/**恒生客户端名称，需要和 t2sdk-config.xml 配置的一致*/
	private static final String clientName = "sunline_T2_AR";
	private static final String T2ADK_PATH = "hs/t2sdk-config.xml";
	private static final String T2ADK_PATH_DAT = "hs/client_licence.dat";

	@PostConstruct
    public static void getSdkService() {
//        try {
//            if (sdkServices == null) {
//				InputStream stream = ResourceUtil.getStream("hs/client_licence.dat");
//				File file = new File(T2ADK_PATH_DAT);
//				FileUtils.copyInputStreamToFile(stream,file);
//				InputStream stream2 = ResourceUtil.getStream("hs/t2sdk-config.xml");
//				File file2 = new File(T2ADK_PATH);
//				FileUtils.copyInputStreamToFile(stream2,file2);
//				try{
//					stream.close();
//					stream2.close();
//				}catch (Exception e) {
//					log.warn("stream.close error");
//				}
//                sdkServices = T2Services.getInstance();
//                sdkServices.setT2sdkConfigString(T2ADK_PATH);
//                sdkServices.init();
//                sdkServices.start();
//            }
//        } catch (T2SDKException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}

	/**
	 * 请求参数组装
	 */
	protected abstract Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap);

	/**
	 * 返回结果组装
	 */
	protected abstract <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request);

    @Override
    public <R extends GrmRequestVO> GrmResponseVO requestData(R requestVo) {
        String grmFunNo = requestVo.getFunctionId();
        if (StringUtils.isNotEmpty(grmFunNo)) {
            Integer miFuncNo = HsFunctions.getNativeFuncId(grmFunNo);
            if (placeOrderLimitOpen) {
                // 下单频率控制校验
                GrmResponseVO duplicationCommitResponseVO = checkDuplicationCommit(miFuncNo, requestVo);
                if (!GrmConstants.GRM_RESPONSE_OK.equals(duplicationCommitResponseVO.getErrorId())) {
                    return duplicationCommitResponseVO;
                }
            }
            FunctionEntity entity = FunctionFactory.getFuncById(miFuncNo);
            if (null != entity) {
                //送参
                Map<String, String> oParam = new HashMap<>();
                oParam.putAll(getCommonParamsFromSession(requestVo));
                oParam = parseParamsFromRequestObject(requestVo, oParam);
                oParam.put(HsConstants.Fields.FUNCTION_ID, String.valueOf(miFuncNo));
                String lang = requestVo.getLang();
                if (StringUtils.isEmpty(lang)) {
                    lang = Constants.LANG_DEFAULT;
                }
                //编码转换,oParam中的参数，和contents.txt中配置的参数一一对应并转码
                oParam = setMsgParams(oParam, entity, lang);
                // 每次请求产生一个唯一请求id
                long senderId = T2sdkAsyncCache.autoIncrement.incrementAndGet();
                IEvent event = doRequest(senderId, oParam, miFuncNo, lang, requestVo);
                log.info("doRequest response senderId={} event={}  ", senderId,JSONObject.toJSONString(event));
                if (null != event) {
                    String sErrorNo;
                    String sErrorInfo;
                    String sT2ErrorInfo;
                    if (event.getReturnCode() != 0) {
                        sT2ErrorInfo = event.getEventDatas().getDataset(0).getString(HsConstants.Fields.ERROR_INFO).replaceAll("\\s", "");
                        //sT2ErrorInfo = "[200526][日期参数错误][Parameters:func_no=1330202,p_strategy_enddate=20191116p_init_date=20191118]";
                        if (sT2ErrorInfo.contains("[")) {
                            sErrorNo = sT2ErrorInfo.substring(1, sT2ErrorInfo.indexOf(']'));
                        } else {
                            sErrorNo = String.valueOf(event.getReturnCode());
                        }
                        sErrorInfo = ErrorMsgHandler.getErrorMsg(sErrorNo, lang);
                        if (StringUtils.isBlank(sErrorInfo)) {
                            //没有在Glossary_Error*.xml中找到对应的优化提示，从柜台的提示语中截取
                            if (sT2ErrorInfo.contains("[")) {
                                int fistEndIndex = sT2ErrorInfo.indexOf("]");
                                int tipsBegin = StringUtils.indexOf(sT2ErrorInfo, "[", fistEndIndex);
                                int tipsEnd = StringUtils.indexOf(sT2ErrorInfo, "]", fistEndIndex + 1);
                                if (tipsBegin > 0 && tipsEnd > 0) {
                                    sErrorInfo = sT2ErrorInfo.substring(tipsBegin + 1, tipsEnd);
                                } else {
                                    sErrorInfo = sT2ErrorInfo;
                                }
                            } else {
                                sErrorInfo = sT2ErrorInfo;
                            }
                        }
                        String operatorNo = null;
                        String opPassWord = null;
                        if(StringUtils.isBlank(operatorNo)){
							operatorNo = HsConstants.InnerBrokerConfig.INNER_OPERATOR_NO;
						}
                        if(StringUtils.isBlank(opPassWord)){
							opPassWord = HsConstants.InnerBrokerConfig.INNER_OP_PASSWORD;
						}

                        if (operatorNo.equals(oParam.get(HsConstants.Fields.OPERATOR_NO)) && !GrmFunctions.BROKE_LOGIN.equals(requestVo.getFunctionId()) &&
                                (sErrorNo.trim().equals("254079") || sErrorNo.trim().equals("130000")) || sErrorNo.equals("112027")) {
                            EF01110000Request ef01110000Request = new EF01110000Request();
                            ef01110000Request.setFunctionId(GrmFunctions.BROKE_LOGIN);
                            ef01110000Request.setOpStation(HsConstants.InnerBrokerConfig.INNER_OP_STATION);
                            ef01110000Request.setOpEntrustWay(HsConstants.InnerBrokerConfig.INNER_OP_ENTRUST_WAY);
                            ef01110000Request.setOperatorNo(operatorNo);
                            ef01110000Request.setOpPassword(opPassWord);
                            ef01110000Request.setUserType(Constants.USER_TYPE_OPERATOR);
                            ef01110000Request.setOpBranchNo(HsConstants.InnerBrokerConfig.INNER_OP_BRANCHNO);
                            ef01110000Request.setBranchNo(HsConstants.InnerBrokerConfig.INNER_OP_BRANCHNO);
                            //对于互联网接口，只保存一条操作员登录的clientSession
                            ef01110000Request.setSessionId(Constants.innerClientSideSid);
                            ef01110000Request.setSid(Constants.innerClientSideSid);
                            ef01110000Request.setSessionId(requestVo.getSessionId());
                            GrmResponseVO vo = ef01110000.requestData(ef01110000Request);
                            if (null != vo && vo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                                return requestData(requestVo);
                            } else {
                                return GrmResponseVO.getInstance().setErrorId(sErrorNo).setErrorMsg(sErrorInfo);
                            }
                        }
                        log.info("Response:[senderId={}]{}", senderId, sT2ErrorInfo);
                        GrmResponseVO responseVO = GrmResponseVO.getInstance().setErrorId(sErrorNo).setErrorMsg(sErrorInfo);
                        return responseVO;
                    } else {
                        //有些时候，getReturnCode() == 0，但getErrorInfo()确不为空，比如：
                        //报“超出许可证的发送包数限制”时，其getReturnCode() == 0
                        String error_info = null;
                        try {
                            error_info = event.getEventDatas().getDataset(0).getString(HsConstants.Fields.ERROR_INFO);
                        } catch (Exception e) {
                            log.error("parse errorInfo error", e);
                        }
                        log.info("Response:[senderId={}]{}", senderId, error_info);
                    }
                    GrmResponseVO vo = parseResponse(onMessageReceived(event, oParam, entity, lang), oParam, requestVo);
                    log.info("Response:[senderId={}]{}", senderId, JSONObject.toJSONString(vo));
                    return vo;
                } else {
                    return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.CONNECT_TO_T2_FAILED)
                            .setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.CONNECT_TO_T2_FAILED, lang));
                }
            } else {
                return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.FUNCTON_NOT_EXIT)
                        .setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.FUNCTON_NOT_EXIT, Constants.LANG_DEFAULT));
            }
        } else {
            //todo
            return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.NULL_PARAMETER)
                    .setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.NULL_PARAMETER, Constants.LANG_DEFAULT));
        }
    }


	/**
	 * 从ClientSession中获取公共参数
	 */
	private <R extends GrmRequestVO> Map<String, String> getCommonParamsFromSession(R requestVo) {
		Map<String, String> commonParams = new HashMap<>();
		if (HsFunctions.brokersidFuncs.contains(requestVo.getFunctionId())) {
			//互联网接口
			commonParams.put(HsConstants.Fields.LICENSE_STR, HsConstants.BROKER_LICENSE_STR);
			ClientSession brokerSession = zeroRedis.get(Constants.innerClientSideSid);
			if (null != brokerSession) {
				commonParams.put(HsConstants.Fields.BRANCH_NO, brokerSession.getBranchNo());
				commonParams.put(HsConstants.Fields.ENTRUST_WAY, brokerSession.getEntrustWay());
				commonParams.put(HsConstants.Fields.OP_ENTRUST_WAY, brokerSession.getEntrustWay());
				commonParams.put(HsConstants.Fields.OP_STATION, brokerSession.getOpStation());
				commonParams.put(HsConstants.Fields.USER_TYPE, Constants.USER_TYPE_OPERATOR);
				commonParams.put(HsConstants.Fields.OPERATOR_NO, HsConstants.InnerBrokerConfig.INNER_OPERATOR_NO);
				commonParams.put(HsConstants.Fields.OP_BRANCH_NO, HsConstants.InnerBrokerConfig.INNER_OP_BRANCHNO);
				brokerSession.setLastOperateTime(new Date());
				//更新最后操作时间
				zeroRedis.saveUpdate(brokerSession, Constants.innerClientSideSid);
			} else {
				log.warn("brokerSession is null, sessionId={}", Constants.innerClientSideSid);
			}
		} else {
			//周边接口
			commonParams.put(HsConstants.Fields.LICENSE_STR, HsConstants.CLIENT_LICENSE_STR);
			if(!GrmFunctions.SV_EXT_SYS_LOGIN.equals(requestVo.getFunctionId())){
				//非周边系统登录接口
				if (StringUtils.isNotEmpty(requestVo.getSessionId())) {
					ClientSession clientSession = zeroRedis.get(requestVo.getSessionId());
					if (clientSession != null) {
						commonParams.put(HsConstants.Fields.BRANCH_NO, clientSession.getBranchNo());
						commonParams.put(HsConstants.Fields.ENTRUST_WAY, clientSession.getEntrustWay());
						commonParams.put(HsConstants.Fields.OP_ENTRUST_WAY, clientSession.getEntrustWay());
						commonParams.put(HsConstants.Fields.OP_STATION, clientSession.getOpStation());
						clientSession.setLastOperateTime(new Date());
						//更新最后操作时间
						zeroRedis.saveUpdate(clientSession, requestVo.getSessionId());
					} else {
						log.warn("clientSession is null, sessionId={}", requestVo.getSessionId());
					}
				} else {
					log.warn("getCommonParamsFromSession.sessionId is null, functionId={}", requestVo.getFunctionId());
				}
			}
		}
		//设置委托渠道，保证委托渠道被填充
		if (!commonParams.containsKey(HsConstants.Fields.ENTRUST_WAY)) {
			String entrustWay = "Z";// TODO 表 sunline.grm_server grmServerHolder.getServerBySid(requestVo.getSid())
			// .getEntrustWay();
			commonParams.put(HsConstants.Fields.ENTRUST_WAY, entrustWay);
			commonParams.put(HsConstants.Fields.OP_ENTRUST_WAY, entrustWay);
		}
		return commonParams;
	}

	/**
	 * 调用恒生接口前编码转换,oParam中的参数，和contents.txt中配置的参数一一对应并转码
	 */
	private Map<String, String> setMsgParams(Map<String, String> oParam, FunctionEntity entity, String lang) {
		Set<String> asChtField = entity.getAsChtField();
		String[] asRequestField = entity.getAsRequestField();
		for (int i = 0; i < entity.asRequestField.length; i++) {
			String fieldName = asRequestField[i];
			if (asChtField.contains(asRequestField[i])) {
				String chtVal = oParam.get(fieldName);
				if ("sc".equals(lang)) {
					chtVal = new String(chtVal.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
					try {
						chtVal = new String(ZHConverter.convert(chtVal, 0).getBytes("big5"), "big5");
					} catch (UnsupportedEncodingException e) {
						log.error("参数处理异常1" + lang, e);
					}
				} else if (lang.equals(Constants.LANG_TRAD_CHINESE)) {
					try {
						chtVal = new String(chtVal.getBytes("big5"), "big5");
					} catch (UnsupportedEncodingException e) {
						log.error("参数处理异常" + lang, e);
					}
				}
				oParam.put(fieldName, chtVal);
			}
		}
		return oParam;
	}

	/**
	 * 恒生响应结果处理
	 */
	public GrmDataVO onMessageReceived(IEvent event, Map<String, String> oParam, FunctionEntity entity, String lang) {
		return setResult(receiveMsg(event, entity, lang), oParam, lang);
	}

	/**
	 * 生成恒生调用结果对象 GrmDataVO
	 */
	public GrmDataVO setResult(List<Map<String, String>> listData, Map<String, String> oParamMap, String lang) {
		GrmDataVO dataVO = GrmDataVO.getInstance().setError(GrmConstants.GRM_RESPONSE_OK, lang).setResult(listData);
		notifyListener(GrmEvent.getInstance().setFuncitonId(oParamMap.get(HsConstants.Fields.FUNCTION_ID)).setDateTime(new Date())
			.setEventType(GrmEvent.EType.SUCESSE_RETURN).setExtra(dataVO));
		return dataVO;
	}

	/**
	 * 返回接口只需要contents.txt配置的字段
	 */
	private List<Map<String, String>> receiveMsg(IEvent event, FunctionEntity entity, String lang) {
		List<Map<String, String>> listMap = new ArrayList<>();
		IDatasets result = event.getEventDatas();
		int iCount = result.getDatasetCount();
		for (int i = 0; i < iCount; i++) {
			IDataset ds = result.getDataset(i);
			listMap.addAll(formatMultiResult(ds, entity.getAsResponseField(), entity, lang));
		}
		return listMap;
	}

	/**
	 * 返回接口只需要contents.txt配置的字段
	 */
	private List<Map<String, String>> formatMultiResult(IDataset dataSet, String[] sField, FunctionEntity entity, String lang) {
		List<Map<String, String>> listMap = new ArrayList<>();
		Set<String> asChtField = entity.getAsChtField();
		dataSet.beforeFirst();
		while (dataSet.hasNext()) {
			dataSet.next();
			Map<String, String> dataMap = new HashMap<>();
			for (int i = 0; i < sField.length; i++) {
				if (!asChtField.contains(sField[i])) {
					dataMap.put(sField[i], fieldFormater.format(sField[i], dataSet.getString(sField[i])));
				} else {
					String fieldStr = dataSet.getString(sField[i]);
					try {
						fieldStr = new String(fieldStr.getBytes("gbk"), "big5");
						fieldStr = except(sField[i], fieldStr, entity);
						if (lang == "sc") {
							fieldStr = ZHConverter.convert(fieldStr, 1);
						}
					} catch (UnsupportedEncodingException e) {
						log.error("formatMultiResult exception", e);
					}
					dataMap.put(sField[i], fieldFormater.format(sField[i], fieldStr));
				}
			}
			listMap.add(dataMap);
		}
		return listMap;
	}

	/**
	 * 调用恒生服务
	 */
	private <R extends GrmRequestVO> IEvent doRequest(long senderId, Map oParam, int miFuncNo, String lang, R requestVo) {
		if (sdkServices == null) {
			getSdkService();
		}
		IEvent event = null;
		try {
			Map<String, Object> eventTag = new HashMap<>();
			eventTag.put(EventTagdef.TAG_FUNCTION_ID, String.valueOf(miFuncNo));// 设置功能号
			logRequest(senderId, oParam, requestVo);
			long beginTime = System.currentTimeMillis();
			if (miFuncNo >= 341 && miFuncNo <= 345) {
				//策略单接口使用同步请求
				event = sdkServices.getClient(clientName).sendReceive(eventTag, oParam);// 同步请求
			} else {
				// 注意，在配置中配置的回调函数<method id="3" className="com.sunline.core.grm.t2sdk.callback.BizCallBack"/>的id号必须填入event,否则回调不回应答
				eventTag.put(EventTagdef.TAG_SENDERID, 3);// 设置异步回调method id
				eventTag.put(EventTagdef.TAG_EVENT_ID, senderId);// 设置每次请求id，每次调用要保证不一样，在整个调用体系中要保持唯一
				try {
					final CountDownLatch countDownLatch = new CountDownLatch(1);
					T2sdkAsyncCache.requestMap.put(senderId, countDownLatch);
					// 异步发送请求
					sdkServices.getClient(clientName).send(eventTag, oParam);
					//异步请求等待响应结果
					countDownLatch.await(readTimeout, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					log.error("t2sdk.response wait timeout");
				}
				event = T2sdkAsyncCache.responseMap.get(senderId);
			}
			long endTime = System.currentTimeMillis();
			log.info("senderId={}, t2sdk.response spend {} ms", senderId, endTime - beginTime);
		} catch (T2SDKException e) {
			log.error("doRequest exception", e);
		} finally {
			//无论请求是正常返回，还是等待超时，都会清理senderId
			T2sdkAsyncCache.requestMap.remove(senderId);
			T2sdkAsyncCache.responseMap.remove(senderId);
		}
		return event;
	}

    /**
     * 校验是否重复提交请求
     */
    private GrmResponseVO checkDuplicationCommit(Integer miFuncNo, GrmRequestVO requestVO) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (miFuncNo == 302 || miFuncNo == 341) {
            // 针对下买卖单，下条件单的功能号做校验
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(requestVO));
            String tradeAccount = jsonObject.getString("clientId");
            String fundAccount = jsonObject.getString("fundAccount");
            String lockKey;
            String lockValue = "1";
            if (StringUtils.isNotBlank(fundAccount)) {
                lockKey = StaticCode.TRADE_DISTRIBUTED_LOCK_KEY + fundAccount;
            } else {
                lockKey = StaticCode.TRADE_DISTRIBUTED_LOCK_KEY + tradeAccount;
            }
            try {
                // 获取分布式锁
                int lockMills = placeOrderInternalSecond * 1000;//锁定1秒
                boolean lock = redisLockClient.tryLock(lockKey, LockType.REENTRANT,lockMills,lockMills,TimeUnit.MILLISECONDS);
                if (lock) {
                    OrderLimitCache limitCache = zeroRedis.findObject(OrderLimitCache.class, lockKey);
                    if (limitCache != null) {
                        // 用户1秒内提交过委托，则提示不允许重复提交
                        responseVO.setErrorId(StaticCode.ErrorCode.DUPLICATION_COMMIT)
                                .setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.DUPLICATION_COMMIT, requestVO.getLang()));
                        log.warn("duplication commit,  requestVO={}", JSON.toJSONString(requestVO));
                    } else {
                        limitCache = new OrderLimitCache();
                        limitCache.setTradeAccount(tradeAccount);
                        limitCache.setFundAccount(fundAccount);
						zeroRedis.saveUpdate(limitCache, lockKey, placeOrderInternalSecond);
                    }
                } else {
                    // 获取分布式锁失败，则提示服务器正忙，稍后再试
                    responseVO.setErrorId(StaticCode.ErrorCode.SERVER_BUSY)
                            .setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.SERVER_BUSY, requestVO.getLang()));
                }
            } catch (Exception e) {
                log.error("checkDuplicationCommit error", e);
            } finally {
                //释放分布式锁
				redisLockClient.unLock(lockKey,LockType.REENTRANT);
            }
        }
        return responseVO;
    }

	/**
	 * 打印请求信息对密码进行屏蔽
	 */
    private <R extends GrmRequestVO> void logRequest(long senderId, Map<String, String> oParam, R requestVo) {
        StringBuffer oBuffer = new StringBuffer();
        oBuffer.append("Request: senderId=").append(senderId);
        oBuffer.append(HsConstants.RESPONSE_FIELD_SEPARATOR);
        Set<Map.Entry<String, String>> entries = oParam.entrySet();
        Map.Entry<String, String> entry;
        String key;
        for (Iterator<Map.Entry<String, String>> iterator = entries.iterator(); iterator.hasNext(); ) {
            entry = iterator.next();
            key = entry.getKey();
            if (StringUtils.isNotEmpty(key) && key.toLowerCase().contains(Constants.Fields.PASSWORD)) {
                oBuffer.append(key + "=")
                        .append("********")
                        .append(HsConstants.RESPONSE_FIELD_SEPARATOR);
            } else {
                oBuffer.append(key + "=")
                        .append(entry.getValue())
                        .append(HsConstants.RESPONSE_FIELD_SEPARATOR);
            }
        }
        oBuffer.deleteCharAt(oBuffer.length() - 1).append(
                HsConstants.RESPONSE_END_OF_LINE_SEPARATOR);
        log.info(oBuffer.toString());
    }

    private String except(String fieldName, String fieldValue, FunctionEntity entity)
            throws UnsupportedEncodingException {
        String result = fieldValue;
        if ((entity.getMiFuncNo() == 335036) && (fieldName.equals("dict_prompt_ch"))) {
            byte[] bt = fieldValue.getBytes("big5");
            if ((bt[0] == 63) && (bt[1] == 32)) {
                result = ZHConverter.convert("恒生銀行", 0);
            }
        }
        return result;
    }

    private void notifyListener(GrmEvent event) {
        Iterator<GrmEventListener> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onEventReceived(event);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        T2sdkBiz.context = applicationContext;
    }

    private void registerBean(String beanName, BeanDefinition beanDefinition) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext.getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
    }

    protected String getLang() {
		// TODO 从请求中获取语言
//        String lang = null;
//        SNRequest sNRequest = ContextHolder.getRequest();
//        if (sNRequest != null) {
//            lang = sNRequest.getLang();
//        }
//        return lang;
		return "sc";
    }

    protected String getStkNameOrTtional(String sessionId, String stkName, String stkTtionalName, String lang) {
		// TODO
//        if (StringUtils.isBlank(lang) && StringUtils.isNotBlank(sessionId)) {
//            lang = ttionalNameService.findIsTtionalById(sessionId);
//        }
//        return ttionalNameService.getStkNameOrTtional(stkName, stkTtionalName, lang);
		return null;
    }
}
