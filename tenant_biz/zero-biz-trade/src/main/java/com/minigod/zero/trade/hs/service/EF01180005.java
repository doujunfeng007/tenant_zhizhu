package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100345Request;
import com.minigod.zero.trade.hs.req.EF01100806Request;
import com.minigod.zero.trade.hs.req.EF01110112Request;
import com.minigod.zero.trade.hs.req.EF01180005Request;
import com.minigod.zero.trade.hs.resp.EF01100345VO;
import com.minigod.zero.trade.hs.resp.EF01100806VO;
import com.minigod.zero.trade.hs.resp.EF01110112VO;
import com.minigod.zero.trade.hs.resp.EF01180005VO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询条件单
 *
 * @author sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01180005Request.class, responseVo = EF01180005VO.class)
public class EF01180005 extends Abstractbiz {
    @Resource
    private EF01100806 ef01100806;
    @Resource
    private EF01100345 ef01100345;
    @Resource
    private EF01110112 ef01110112;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        List<EF01180005VO> returnList = new ArrayList<>();
        EF01180005Request ef01180005Request = (EF01180005Request) request;

        String password = ef01180005Request.getPassword();

        List<T> currentOrderList = null;

        if(ef01180005Request.getFunctionId().equals(password)) {
            //当日委托-免密
            EF01110112Request eF01110112Request = new EF01110112Request();
            BeanUtils.copyProperties(ef01180005Request, eF01110112Request);
            eF01110112Request.setFunctionId(GrmFunctions.BROKER_GET_BUSINESS);
            eF01110112Request.setFilterType(HsConstants.EntrustFilterType.STRATEGY.getCode());
            GrmResponseVO currentOrderResp = ef01110112.requestData(eF01110112Request);
            if (!currentOrderResp.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                return currentOrderResp;
            }
            currentOrderList = (List<T>) currentOrderResp.getResult().get("data");
            if(CollectionUtils.isEmpty(currentOrderList)){
                return responseVO.addResultData(returnList);
            }
        } else {
            //当日委托
            EF01100806Request ef01100806Request = new EF01100806Request();
            BeanUtils.copyProperties(ef01180005Request, ef01100806Request);
            ef01100806Request.setFunctionId(GrmFunctions.CLIENT_GET_BUSINESS);
            ef01100806Request.setFilterType(HsConstants.EntrustFilterType.STRATEGY.getCode());
            GrmResponseVO currentOrderResp = ef01100806.requestData(ef01100806Request);
            if (!currentOrderResp.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                return currentOrderResp;
            }
            currentOrderList = (List<T>) currentOrderResp.getResult().get("data");
            if(CollectionUtils.isEmpty(currentOrderList)){
                return responseVO.addResultData(returnList);
            }
        }

        //查当日策略单
        EF01100345Request ef01100345Request = new EF01100345Request();
        BeanUtils.copyProperties(ef01180005Request, ef01100345Request);
        ef01100345Request.setFunctionId(GrmFunctions.STRATEGY_GET_ORDERBOOK);
        GrmResponseVO conditionOrderResp = ef01100345.requestData(ef01100345Request);
        if (!conditionOrderResp.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            return conditionOrderResp;
        }
        Map<String, String> touchPriceMap = new HashMap<>();
        List<EF01100345VO> conditionOrderList = (List<EF01100345VO>) conditionOrderResp.getResult().get("data");
        if (CollectionUtils.isNotEmpty(conditionOrderList)) {
            for (EF01100345VO vo : conditionOrderList) {
                //key=委托单号
                touchPriceMap.put(vo.getEntrustNo(), vo.getTouchPrice());
            }
        }

        if(null != currentOrderList && currentOrderList.size() > 0 ){
            for(int i=0; i<currentOrderList.size();i++) {
                Object obj = currentOrderList.get(i);
                EF01180005VO ef01180005VO = new EF01180005VO();
                if(obj instanceof EF01100806VO){
                    EF01100806VO ef01100806VO = (EF01100806VO) obj;
                    BeanUtils.copyProperties(ef01100806VO, ef01180005VO);
                } else {
                    EF01110112VO ef01110112VO = (EF01110112VO)obj;
                    BeanUtils.copyProperties(ef01110112VO, ef01180005VO);
                }

                String touchPrice = touchPriceMap.get(ef01180005VO.getEntrustNo());
                if(StringUtils.isNotBlank(touchPrice)){
                    ef01180005VO.setTouchPrice(touchPrice);
                }else{
                    ef01180005VO.setTouchPrice("--");
                }
                if ("1".equals(ef01180005VO.getStrategyStatus()) || "2".equals(ef01180005VO.getStrategyStatus())) {
                    //改单、撤单设置，strategy_status策略状态（1-未执行;2-执行中;3-执行完成;4-暂停;5-强制释放）
                    ef01180005VO.setModifiable("1");
                    ef01180005VO.setCancelable("1");
                }else {
                    ef01180005VO.setModifiable("0");
                    ef01180005VO.setCancelable("0");
                }
                returnList.add(ef01180005VO);
            }
        }
        return responseVO.addResultData(returnList);
    }
}

