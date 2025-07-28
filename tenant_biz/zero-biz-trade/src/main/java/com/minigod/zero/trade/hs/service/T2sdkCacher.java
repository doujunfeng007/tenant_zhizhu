package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.resp.FundAccountBankInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunline on 2016/11/21 18:32.
 * sunline
 */
public class T2sdkCacher {
    //key:sessionId_fundaccount_bankno
    private static Map<String, FundAccountBankInfo> clientBankInfoMap = new HashMap<>();

    public static FundAccountBankInfo getBankInfo(String sessionId,String fundAccount,String bankNo){
        return clientBankInfoMap.get(sessionId+fundAccount+bankNo);
    }

    public static void addBankInfoData(String sessionId,String fundAccount,FundAccountBankInfo fundAccountBankInfo){
        //todo 删除过期数据
        clientBankInfoMap.put(sessionId+fundAccount+fundAccountBankInfo.getBankNo(),fundAccountBankInfo);
    }
}
