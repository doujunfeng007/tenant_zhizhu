package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100802Request;
import com.minigod.zero.trade.hs.req.EF01100823Request;
import com.minigod.zero.trade.hs.resp.BankInfo;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.resp.EF01100823VO;
import com.minigod.zero.trade.hs.resp.FundAccountBankInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 获取客户银行信息
 * Created by sunline on 2016/4/11 13:16.
 * inline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100823Request.class,responseVo = ClientCashSumInfo.class)
public class EF01100823<T extends GrmRequestVO>  extends T2sdkBiz<T> {
	@Resource
	private ZeroRedis zeroRedis;
    @Resource
    protected EF01100802 ef01100802;
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100823Request){
            EF01100823Request req = (EF01100823Request) request;
            Map<String,String> reqMap = new HashMap();
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
            List<EF01100823VO> listVo = new ArrayList<>();
            EF01100823VO vo;
            String bankNo;
            BankInfo bankInfo;
            EF01100823Request req = (EF01100823Request) request;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100823VO();
                bankNo = rowMap.get(HsConstants.Fields.BANK_NO);
                vo.setBankNo(bankNo);
                bankInfo = zeroRedis.findObject(BankInfo.class,bankNo);
                if(null == bankInfo){
                    EF01100802Request ef01100802Request = new EF01100802Request();
                    ef01100802Request.copyCommonParams(request);
                    ef01100802Request.setFunctionId("EF01100802");
                    ef01100802Request.setBankType("2");
                    GrmResponseVO bankInfoVO  = ef01100802.requestData(ef01100802Request);
                    if(bankInfoVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
                        bankInfo = zeroRedis.findObject(BankInfo.class, bankNo);
                    }else{
                        return responseVO.setErrorId(bankInfoVO.getErrorId()).setErrorMsg(bankInfoVO.getErrorMsg());
                    }
                }
                //过滤非银证帐号
                if(null != bankInfo){
                    vo.setFundAccount(rowMap.get(HsConstants.Fields.FUND_ACCOUNT));
                    vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                    vo.setBankAccount(rowMap.get(HsConstants.Fields.BANK_ACCOUNT));
                    vo.setClientAccount(rowMap.get(HsConstants.Fields.CLIENT_ACCOUNT));
                    vo.setBkaccountStatus(rowMap.get(HsConstants.Fields.BKACCOUNT_STATUS));
                    vo.setHolderName(rowMap.get(HsConstants.Fields.HOLDER_NAME));
                    vo.setForeignFlag(rowMap.get(HsConstants.Fields.FOREIGN_FLAG));
                    vo.setIdNo(rowMap.get(HsConstants.Fields.ID_NO));
                    vo.setIdKind(rowMap.get(HsConstants.Fields.ID_KIND));
                    vo.setBkaccountKind(rowMap.get(HsConstants.Fields.BKACCOUNT_KIND));
                    if(vo.getFundAccount().equals(req.getFundAccount())){
                        listVO.add(vo);
                    }
                }
                //存cache
                FundAccountBankInfo fundAccountBankInfo = new FundAccountBankInfo();
                fundAccountBankInfo.setBankNo(vo.getBankNo());
                fundAccountBankInfo.setBankAccount(vo.getBankAccount());
                fundAccountBankInfo.setClientAccount(vo.getClientAccount());
                fundAccountBankInfo.setBankType(null == bankInfo?"1":"2");
                fundAccountBankInfo.setHolderName(vo.getHolderName());
                fundAccountBankInfo.setIdKind(vo.getIdKind());
                fundAccountBankInfo.setIdNo(vo.getIdNo());
                fundAccountBankInfo.setMoneyType(vo.getMoneyType());
                T2sdkCacher.addBankInfoData(req.getSessionId(),req.getFundAccount(),fundAccountBankInfo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
