package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100808Request;
import com.minigod.zero.trade.hs.resp.FundAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取帐号列表
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100808Request.class,responseVo = FundAccountInfo.class)
public class EF01100808<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100808Request){
            EF01100808Request req = (EF01100808Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
           List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            List<Object>  listVO = new ArrayList<>();
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                FundAccountInfo vo = new FundAccountInfo();
                vo.setFundAccount(rowMap.get(HsConstants.Fields.FUND_ACCOUNT));
                vo.setBranchNo(oParam.get(HsConstants.Fields.BRANCH_NO));
                vo.setMainFlag(rowMap.get(HsConstants.Fields.MAIN_FLAG));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
