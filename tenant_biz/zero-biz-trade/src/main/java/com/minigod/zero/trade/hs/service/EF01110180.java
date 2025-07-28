/**
 * @Title: EF01110180.java
 * @Description: 获取保证金比例
 * @author yanghu.luo
 * @date 2022年8月11日
 */

package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110180Request;
import com.minigod.zero.trade.hs.resp.EF01110180VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName: EF01110180
 * @Description: 获取保证金比例
 * @author yanghu.luo
 * @date 2022年8月11日
*/

@Component
@GrmFunctionEntity(requestVo = EF01110180Request.class, responseVo = EF01110180VO.class)
public class EF01110180<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110180Request) {
        	EF01110180Request req = (EF01110180Request) request;
            Map<String, String> reqMap = new HashMap<String, String>();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            if (StringUtils.isNotEmpty(req.getMoneyType())) {
                reqMap.put(HsConstants.Fields.MONEY_TYPE, req.getMoneyType());
            }

            // 接口默认值
            reqMap.put(HsConstants.Fields.MONITOR_TYPE, "MV");
            reqMap.put(HsConstants.Fields.MONITOR_VALUE_BEGIN, "0");
            reqMap.put(HsConstants.Fields.MONITOR_VALUE_END, "-1");
            reqMap.put(HsConstants.Fields.PAGE_NUM, "1");
            reqMap.put(HsConstants.Fields.PAGE_SIZE, "-1");
            reqMap.put(HsConstants.Fields.ORDER_TYPE, "0");

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
            List<Object> listVO = new ArrayList<>();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01110180VO eF01110180VO = JSON.parseObject(JSON.toJSONString(rowMap),EF01110180VO.class);

                listVO.add(eF01110180VO);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
