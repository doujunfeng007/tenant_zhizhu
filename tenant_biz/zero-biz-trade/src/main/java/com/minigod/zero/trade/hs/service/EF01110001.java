package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110001Request;
import com.minigod.zero.trade.hs.resp.EF01110001VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 经纪人信息查询
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110001Request.class,responseVo = EF01110001VO.class)
public class EF01110001<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
           List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            EF01110001VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110001VO();
                vo.setBrokerAccount(rowMap.get(HsConstants.Fields.BROKER_ACCOUNT));
                vo.setBranchNo(rowMap.get(HsConstants.Fields.BRANCH_NO));
                vo.setBrokerName(rowMap.get(HsConstants.Fields.BROKER_NAME));
                vo.setBrokerKind(rowMap.get(HsConstants.Fields.BROKER_KIND));
                vo.setIdKind(rowMap.get(HsConstants.Fields.ID_KIND));
                vo.setIdNo(rowMap.get(HsConstants.Fields.ID_NO));
                vo.setBrokerSex(rowMap.get(HsConstants.Fields.BROKER_SEX));
                vo.setAddress(rowMap.get(HsConstants.Fields.ADDRESS));
                vo.setPhonecode(rowMap.get(HsConstants.Fields.PHONECODE));
                vo.setMobileTel(rowMap.get(HsConstants.Fields.MOBILE_TEL));
                vo.setEMail(rowMap.get(HsConstants.Fields.E_MAIL));
                vo.setBrokerStatus(rowMap.get(HsConstants.Fields.BROKER_STATUS));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
