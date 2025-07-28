package com.minigod.zero.biz.common.constant;

import java.util.HashMap;
import java.util.Map;

public class InfotreeUrlConstant {

    private static final  Map<String, Integer> infotreeUrlMap= new HashMap();
    static {
        infotreeUrlMap.put(SpiderUrlConstants.QQ.HK_DHYB,1);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.HK_ZB  ,2);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.HK_JGYB,3);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.HK_GGZL,4);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.HK_LZZX,5);

        infotreeUrlMap.put(SpiderUrlConstants.QQ.US_MGXW,101);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.US_GNG ,102);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.US_FXPL,103);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.US_PMBD,104);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.US_GDXW,105);
        infotreeUrlMap.put(SpiderUrlConstants.QQ.US_MGKT,106);
    }

    public static Integer getInfotreeId(String url){
        return infotreeUrlMap.get(url);
    }
}
