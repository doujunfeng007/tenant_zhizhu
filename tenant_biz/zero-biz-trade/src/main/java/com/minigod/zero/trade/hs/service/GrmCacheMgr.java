package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.resp.HsExchangeRateMapVO;
import com.minigod.zero.trade.hs.resp.SystemMaintainVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;

import java.util.Map;

/**
 * Created by sunline on 2016/5/21 16:45.
 * sunline
 */
public interface GrmCacheMgr {
    /**
     * 获取行情码表数据
     * @param assetId
     * @return
     */
    StkInfo getAssetInfo(String assetId);

    /**
     * 根据目标币种查询汇率
     * @param toMoneyType
     * @return
     */
    HsExchangeRateMapVO getHsExchangeRateMapVO(String toMoneyType);

    /**
     * 查询错误码mapping
     * @param errorCode
     * @param module
     * @param lang
     * @return
     */
    String getErrorCodeMapping(String errorCode, String module, String lang);

    /**
     * 查询系统是否开启维护模式
     */
    SystemMaintainVO getSystemMaintainVO();

    /**
     * 查询系统是否开启维护模式，若开启，判断用户是否在白名单内
     * @param userId
     * @param lang
     * @return
     */
    GrmResponseVO checkSystemMaintain(Integer userId, String lang);

    /**
     * 获取费用名称配置
     * @param exchangeType 市场
     * @param lang 语言
     * @return
     */
    Map<String, String> getFareNames(String exchangeType, String lang);
}
