package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.enums.Hs2BsFlag;
import com.minigod.zero.trade.enums.HsFundOrderStatus;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110148Request;
import com.minigod.zero.trade.hs.resp.EF01110112VO;
import com.minigod.zero.trade.hs.resp.EF01110148VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 历史委托 --免密接口
 * @author sunline
 * @date 2020.6.23
 * @param <T>
 */

@Component
@GrmFunctionEntity(requestVo = EF01110148Request.class,responseVo = EF01110148VO.class)
public class EF01110148<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110148Request) {
            EF01110148Request req = (EF01110148Request) request;
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            if(StringUtils.isNotEmpty(req.getExchangeType())){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
			if(StringUtils.isNotEmpty(req.getStockCode())){
				reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
			}
            reqMap.put(HsConstants.Fields.START_DATE, req.getStartDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            List<EF01110112VO> listVO = new ArrayList<>();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01110112VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110112VO.class);
                String assetId = DataFormatUtil.stkCodeToAssetId(vo.getStockCode(),vo.getExchangeType());
                vo.setAssetId(assetId);
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if (null != assetInfo) {
                    vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                    vo.setStockNameZhCN(assetInfo.getStkNameLong());
                    vo.setStockNameZhHK(assetInfo.getTraditionalName());
                    //添加每股手数,和股票子类型
                    vo.setLotSize(assetInfo.getLotSize());
                    vo.setSecSType(assetInfo.getSecSType());
                }
                vo.setBsFlag(Hs2BsFlag.getByPortfolio(vo.getBsFlag()));
                vo.setOrderType(StringUtils.upperCase(vo.getOrderType()));

                //调整订单状态的显示（用于app端）
                String orderStatus = HsFundOrderStatus.getZszzByHs(vo.getOrderStatus());

                boolean isEditable = HsFundOrderStatus.isEditable(vo.getOrderStatus());

                String yn = isEditable ? "1" : "0";
                vo.setCancelable(yn);
                vo.setModifiable(yn);
                vo.setOrderStatus(orderStatus);
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
