package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110015Request;
import com.minigod.zero.trade.hs.resp.FundAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取营业部编号
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110015Request.class,responseVo = FundAccountInfo.class)
public class EF01110015<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110015Request){
            EF01110015Request req = (EF01110015Request) request;
            Map<String,String> reqMap = new HashMap<>(16);
            reqMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
            reqMap.put(HsConstants.Fields.BRANCH_NO, req.getBranchNo());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
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
            String tempFA ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                FundAccountInfo vo = new FundAccountInfo();
                tempFA = oParam.get(HsConstants.Fields.FUND_ACCOUNT);
                vo.setFundAccount(tempFA);
                vo.setRestriction(rowMap.get(HsConstants.Fields.RESTRICTION));
                vo.setAssetProp(rowMap.get(HsConstants.Fields.ASSET_PROP));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
