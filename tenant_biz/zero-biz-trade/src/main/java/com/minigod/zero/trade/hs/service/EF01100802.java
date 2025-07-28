package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.redis.cache.Pair;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100802Request;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.resp.EF01100802VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 获取客户银行信息
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100802Request.class,responseVo = ClientCashSumInfo.class)
public class EF01100802<T extends GrmRequestVO>  extends T2sdkBiz<T> {
	@Resource
	private ZeroRedis zeroRedis;
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100802Request){
            EF01100802Request req = (EF01100802Request) request;
            Map<String,String> reqMap = new HashMap();
            if(StringUtils.isNotEmpty(req.getBankNo())){
                reqMap.put(HsConstants.Fields.BANK_NO,req.getBankNo());
            }
            if(StringUtils.isNotEmpty(req.getBankType())){
                reqMap.put(HsConstants.Fields.BANK_TYPE,req.getBankType());
            }
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
            List<EF01100802VO> listVo = new ArrayList<>();
            EF01100802VO vo;
            EF01100802Request req = (EF01100802Request) request;
            List<Pair> pairs = new ArrayList<>();
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100802VO();
                vo.setBankNo(rowMap.get(HsConstants.Fields.BANK_NO));
                vo.setBankType(rowMap.get(HsConstants.Fields.BANK_TYPE));
                vo.setBankName(rowMap.get(HsConstants.Fields.BANK_NAME));
                vo.setBranchNo(rowMap.get(HsConstants.Fields.BRANCH_NO));
                vo.setAmOpen(rowMap.get(HsConstants.Fields.AM_OPEN));
                vo.setAmClose(rowMap.get(HsConstants.Fields.AM_CLOSE));
                vo.setPmOpen(rowMap.get(HsConstants.Fields.PM_OPEN));
                vo.setPmClose(rowMap.get(HsConstants.Fields.PM_CLOSE));
                vo.setInitDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                vo.setBankStatus(rowMap.get(HsConstants.Fields.BANK_STATUS));
                vo.setBusinessDate(rowMap.get(HsConstants.Fields.BUSINESS_DATE));
                vo.setBankargFlag(rowMap.get(HsConstants.Fields.BANKARG_FLAG));
                pairs.add(new Pair(vo.getBankNo(),vo));
                listVO.add(vo);
            }
			// TODO zeroRedis.saveUpdateBatch(BankInfo.class,pairs);
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
