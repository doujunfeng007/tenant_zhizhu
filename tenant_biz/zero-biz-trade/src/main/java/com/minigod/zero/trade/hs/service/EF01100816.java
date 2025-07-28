package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100816Request;
import com.minigod.zero.trade.hs.resp.FundAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 查询客户帐号类型
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100816Request.class,responseVo = FundAccountInfo.class)
public class EF01100816<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100816Request){
            EF01100816Request req = (EF01100816Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
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
                vo.setBranchNo(oParam.get(HsConstants.Fields.BRANCH_NO));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
