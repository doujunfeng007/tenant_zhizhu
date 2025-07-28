package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100823Request;
import com.minigod.zero.trade.hs.req.EF01100824Request;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.resp.EF01100824VO;
import com.minigod.zero.trade.hs.resp.FundAccountBankInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 银证证转银
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100824Request.class,responseVo = ClientCashSumInfo.class)
public class EF01100824<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Resource
    protected HsFieldFormater fieldFormater;
    @Resource
    private EF01100823 ef01100823;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100824Request){
            EF01100824Request req = (EF01100824Request) request;
            Map<String,String> reqMap = new HashMap();
            FundAccountBankInfo fundAccountBankInfo = T2sdkCacher.getBankInfo(request.getSessionId(),req.getFundAccount(),req.getBankNo());
            if(fundAccountBankInfo == null){
                EF01100823Request ef01100823Request = new EF01100823Request();
                ef01100823Request.copyCommonParams(request);
                ef01100823Request.setFunctionId(GrmFunctions.CLIENT_SEC_CUSQRY);
                ef01100823Request.setFundAccount(((EF01100824Request) request).getFundAccount());
                ef01100823.requestData(ef01100823Request);
                fundAccountBankInfo = T2sdkCacher.getBankInfo(request.getSessionId(),req.getFundAccount(),req.getBankNo());
            }
            reqMap.put(HsConstants.Fields.ID_KIND,fundAccountBankInfo.getIdKind());
            reqMap.put(HsConstants.Fields.ID_NO,fundAccountBankInfo.getIdNo());
            reqMap.put(HsConstants.Fields.BANK_ACCOUNT,fundAccountBankInfo.getBankAccount());
            reqMap.put(HsConstants.Fields.HOLDER_NAME,fundAccountBankInfo.getHolderName());
            reqMap.put(HsConstants.Fields.CLIENT_ACCOUNT,fundAccountBankInfo.getClientAccount());

            reqMap.put(HsConstants.Fields.TRADE_SIGN,"D");
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.BANK_NO,req.getBankNo());
            reqMap.put(HsConstants.Fields.MONEY_TYPE,req.getMoneyType());
            reqMap.put(HsConstants.Fields.OCCUR_BALANCE,req.getOccurBalance());
            reqMap.put(HsConstants.Fields.REMARK,req.getRemark());
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
            List<EF01100824VO> listVo = new ArrayList<>();
            EF01100824VO vo;
            EF01100824Request req = (EF01100824Request) request;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100824VO();
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
