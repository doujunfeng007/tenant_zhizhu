package com.minigod.zero.trade.hs.service;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.ErrorMsgHandler;
import com.minigod.zero.trade.hs.constants.StaticCode;
import com.minigod.zero.trade.hs.resp.*;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunline on 2016/5/21 16:45.
 * sunline
 */
@Service
public class GrmCacheMgrImpl implements GrmCacheMgr {
    private final Logger logger = LoggerFactory.getLogger(GrmCacheMgrImpl.class);
	@Resource
	private ZeroRedis zeroRedis;
//    @Resource
//    private DefaultConfig defaultConfig;
////    @Resource
////    private GrmCommonDao grmCommonDao;
//    @Resource
//    private UserDao userDao;
//    @Resource
//    private SysFareDictDao sysFareDictDao;
    //@Resource
   // private MktInfoService mktInfoService;

	// 获取汇率接口地址
    private String exchangeRateUrl;

	private String tradeApiUrl = "http://10.9.68.158:8512/trade/"; // TODO 配置

    @PostConstruct
    public void init() {
        exchangeRateUrl = tradeApiUrl + "fund/getExchangeRateList";
    }

    @Override
    public StkInfo getAssetInfo(String assetId) {
        // TODO return mktInfoService.findAssetInfo(assetId);

        return zeroRedis.protoGet(assetId, StkInfo.class);

    }

    /**
     * 根据目标币种查询汇率
     */
    @Override
    public HsExchangeRateMapVO getHsExchangeRateMapVO(String toMoneyType) {
        Map rateMapString = new HashMap();

        String json0 = "{\n" +
                "    \"exchangeRateMap\": {\n" +
                "      \"1\": {\n" +
                "        \"exchangeRate\": 6.4283,\n" +
                "        \"initDate\": 20210629,\n" +
                "        \"validDate\": 20991231,\n" +
                "        \"toMoneyType\": \"0\",\n" +
                "        \"fromMoneyType\": \"1\",\n" +
                "        \"reverseRate\": 1\n" +
                "      },\n" +
                "      \"2\": {\n" +
                "        \"exchangeRate\": 0.8581,\n" +
                "        \"initDate\": 20220510,\n" +
                "        \"validDate\": 20991231,\n" +
                "        \"toMoneyType\": \"0\",\n" +
                "        \"fromMoneyType\": \"2\",\n" +
                "        \"reverseRate\": 1.1654\n" +
                "      }\n" +
                "    },\n" +
                "    \"updateTime\": 1685588047998\n" +
                "  }";
        String json1 = "{\n" +
                "    \"exchangeRateMap\": {\n" +
                "      \"0\": {\n" +
                "        \"exchangeRate\": 0.155,\n" +
                "        \"initDate\": 20210629,\n" +
                "        \"validDate\": 20991231,\n" +
                "        \"toMoneyType\": \"1\",\n" +
                "        \"fromMoneyType\": \"0\",\n" +
                "        \"reverseRate\": 6.45\n" +
                "      },\n" +
                "      \"2\": {\n" +
                "        \"exchangeRate\": 0.1282,\n" +
                "        \"initDate\": 20210629,\n" +
                "        \"validDate\": 20991231,\n" +
                "        \"toMoneyType\": \"1\",\n" +
                "        \"fromMoneyType\": \"2\",\n" +
                "        \"reverseRate\": 7.8\n" +
                "      }\n" +
                "    },\n" +
                "    \"updateTime\": 1685588048266\n" +
                "  }";
        String json2 = "{\n" +
                "    \"exchangeRateMap\": {\n" +
                "      \"0\": {\n" +
                "        \"exchangeRate\": 1.1638,\n" +
                "        \"initDate\": 20220510,\n" +
                "        \"validDate\": 20991231,\n" +
                "        \"toMoneyType\": \"2\",\n" +
                "        \"fromMoneyType\": \"0\",\n" +
                "        \"reverseRate\": 1\n" +
                "      },\n" +
                "      \"1\": {\n" +
                "        \"exchangeRate\": 7.7534,\n" +
                "        \"initDate\": 20210629,\n" +
                "        \"validDate\": 20991231,\n" +
                "        \"toMoneyType\": \"2\",\n" +
                "        \"fromMoneyType\": \"1\",\n" +
                "        \"reverseRate\": 1\n" +
                "      }\n" +
                "    },\n" +
                "    \"updateTime\": 1685588048339\n" +
                "  }";

        rateMapString.put("0", json0);
        rateMapString.put("1", json1);
        rateMapString.put("2", json2);





        HsExchangeRateMapVO hsExchangeRateMapVO = zeroRedis.get(toMoneyType);
        boolean doUpdate = false;
        Date now = new Date();
        if (hsExchangeRateMapVO != null) {
            long diffHours = (now.getTime() - hsExchangeRateMapVO.getUpdateTime().getTime()) / 1000 / 60 / 60;
            if (diffHours > 3L) {//缓存超过3小时，触发更新操作
                doUpdate = true;
            }
        } else {
            doUpdate = true;
            hsExchangeRateMapVO = new HsExchangeRateMapVO();
        }
        if (doUpdate) {
            try {
                JSONObject request = new JSONObject();
                JSONObject params = new JSONObject();
                params.put("toMoneyType", toMoneyType);
                request.put("params", params);
                /*String jsonStr = HttpClientUtils.postJson(exchangeRateUrl, request.toJSONString(), StandardCharsets.UTF_8, true);*/

                /*if (StringUtils.isNotBlank(jsonStr)) {
                    JSONObject jsonObject = JSON.parseObject(jsonStr);
                    if (jsonObject != null && jsonObject.getInteger("code") == 0) {
                        List<HsExchangeRateVO> list = jsonObject.getObject("result", new TypeReference<List<HsExchangeRateVO>>() {
                        });
                        if (CollectionUtils.isNotEmpty(list)) {
                            Map<String, HsExchangeRateVO> rateVOHashMap = new HashMap<>(list.size());
                            for (HsExchangeRateVO rateVO : list) {
                                rateVOHashMap.put(rateVO.getFromMoneyType(), rateVO);
                            }
                            hsExchangeRateMapVO.setExchangeRateMap(rateVOHashMap);
                            hsExchangeRateMapVO.setUpdateTime(now);
							zeroRedis.set(toMoneyType,hsExchangeRateMapVO);
                        }
                    } else {
                        logger.warn("getHsExchangeRateMapVO failed, resp={}", jsonStr);
                    }
                }*/

                String jsonStr = (String) rateMapString.get(toMoneyType);

                hsExchangeRateMapVO = JSONUtil.toBean(jsonStr,HsExchangeRateMapVO.class);

            } catch (Exception e) {
                logger.error("getHsExchangeRateMapVO error", e);
            }
        }
        return hsExchangeRateMapVO;
    }

    /**
     * 查询错误码mapping
     */
    @Override
    public String getErrorCodeMapping(String errorCode, String module, String lang) {

		String errorMessage = null;

// TODO
//		ErrorCodeMapping errorCodeMapping = grmCommonDao.getErrorCodeMapping(errorCode, module);
//        if (errorCodeMapping != null) {
//            if (CommonConstant.LANGUAGE_TRADITIONAL.equals(lang)) {
//                errorMessage = errorCodeMapping.getCantonese();
//            } else if (CommonConstant.LANGUAGE_ENGLISH.equals(lang)) {
//                errorMessage = errorCodeMapping.getEn();
//            } else {
//                //语言不匹配默认返回简体
//                errorMessage = errorCodeMapping.getSc();
//            }
//        } else {
////            errorMessage = "网络出错或系统繁忙，请稍后再试。";
//            logger.warn("getErrorCodeMapping null, errorCode={}, module={}", errorCode, module);
//        }
        return errorMessage;
    }


    /**
     * 查询系统是否开启维护模式
     */
    @Override
    public SystemMaintainVO getSystemMaintainVO() {
        SystemMaintainVO maintainVO = null;
		// TODO
//        try {
//            maintainVO = redisMapService.findObject(SystemMaintainVO.class, SystemMaintainVO.class.getSimpleName());
//            if (maintainVO == null) {
//                //查询db配置，加载到redis
//                SysConfig sysConfig = grmCommonDao.getSystemMaintenanceConfig();
//                if (sysConfig != null) {
//                    maintainVO = JSON.parseObject(sysConfig.getKeyValue(), SystemMaintainVO.class);
//                } else {
//                    maintainVO = new SystemMaintainVO();
//                    maintainVO.setMaintainFlag(0);
//                }
//                redisMapService.saveUpdate(maintainVO, SystemMaintainVO.class.getSimpleName());
//                //清空之前的白名单数据
//                redisMapService.delete(MaintainWhiteList.class);
//                //查询白名单db配置，加载到redis
//                List<MaintainWhiteList> list = grmCommonDao.getAllMaintainWhiteList();
//                if (CollectionUtils.isNotEmpty(list)) {
//                    List<Pair> pairList = new ArrayList<>(list.size());
//                    for (MaintainWhiteList whiteList : list) {
//                        Pair pair = new Pair();
//                        pair.setKey(whiteList.getUserId());
//                        pair.setValue(whiteList);
//                        pairList.add(pair);
//                    }
//                    redisMapService.saveUpdateBatch(MaintainWhiteList.class, pairList);
//                }
//            }
//        } catch (Exception e) {
//            //查询配置异常，默认为非维护模式
//            logger.error("getSystemMaintainVO error", e);
//            maintainVO = new SystemMaintainVO();
//            maintainVO.setMaintainFlag(0);
//        }
//        maintainVO.setUserInWhiteList(false);
        return maintainVO;
    }

    /**
     * 查询系统是否开启维护模式，若开启，判断用户是否在白名单内
     */
    @Override
    public GrmResponseVO checkSystemMaintain(Integer userId, String lang) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        try {
            SystemMaintainVO maintainVO = getSystemMaintainVO();
            if (maintainVO.getMaintainFlag() == 1) {
                //维护模式开启
                if (userId != null) {
                    //且userId不为空的情况，判断是否在白名单
                    MaintainWhiteList whiteList = zeroRedis.get(userId.toString());
                    if (whiteList != null) {
                        maintainVO.setUserInWhiteList(true);
                    }
                } else {
                    //userId为空的情况，默认在白名单内
                    maintainVO.setUserInWhiteList(true);
                }
            }
            if (maintainVO.getMaintainFlag() == 1 && !maintainVO.getUserInWhiteList()) {
                responseVO.setErrorId(StaticCode.ErrorCode.TRADE_TEST_WITH_TIME);
                String templateStr = ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.TRADE_TEST_WITH_TIME, lang);
                String message = MessageFormat.format(templateStr, maintainVO.getEndTime());
                responseVO.setErrorMsg(message);
            }
        } catch (Exception e) {
            logger.error("checkSystemMaintain error", e);
        }
        return responseVO;
    }

    @Override
    public Map<String, String> getFareNames(String exchangeType, String lang) {
        if (StringUtils.isBlank(lang) || StringUtils.isBlank(exchangeType)) {
            logger.error("getFaresName lang or exchangeType can not be null: " + lang + exchangeType);
            return null;
        }
        CacheFareName cacheFareName = zeroRedis.get(CacheFareName.class.getSimpleName() + exchangeType + lang);
        if (cacheFareName != null) {
            return cacheFareName.getFareNames();
        }
        cacheFareName = new CacheFareName();
		// TODO
//        try {
//            DataSourceContextHolder.setCurrentLookupKey(Constants.sunline_DS);
//            List<SysFareDict> fareDictList = sysFareDictDao.findFaresInfo(exchangeType);
//            Map<String, String> fareNames = new HashMap<>();
//            for (SysFareDict fareDict : fareDictList) {
//                // 遍历筛选出需要获取的语言
//                if (Constants.LANG_ENGLISH.equals(lang.toLowerCase())) {
//                    fareNames.put(fareDict.getFareDict(), fareDict.getFareNameEn());
//                } else if (Constants.LANG_TRADITIONAL.equals(lang.toLowerCase())) {
//                    fareNames.put(fareDict.getFareDict(), fareDict.getFareNameHk());
//                } else {
//                    fareNames.put(fareDict.getFareDict(), fareDict.getFareNameCn());
//                }
//            }
//            cacheFareName.setFareNames(fareNames);
//			zeroRedis.saveUpdate(cacheFareName, CacheFareName.class.getSimpleName() + exchangeType + lang);
//        } catch (Exception e) {
//            logger.error("getFaresName error", e);
//        }
        return cacheFareName.getFareNames();
    }

}
