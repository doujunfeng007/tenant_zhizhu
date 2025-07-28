package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110119Request;
import com.minigod.zero.trade.hs.resp.EF01110119VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 资金开户
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110119Request.class,responseVo = EF01110119VO.class)
public class EF01110119<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110119Request){
            EF01110119Request req = (EF01110119Request) request;
            oParamMap.put("op_branch_no","100");
            oParamMap.put("operator_no","100");
            oParamMap.put("operator_no","110");
            oParamMap.put("op_password","1");
            oParamMap.put("user_type","3");
            oParamMap.put("op_station","121.35.249.10_68F728EE9B64");
            oParamMap.put("op_entrust_way","7");
            oParamMap.put("menu_id"," ");
            oParamMap.put("function_id","10119");
            oParamMap.put("branch_no","100");
            oParamMap.put("audit_action"," ");
            oParamMap.put("bank_no"," ");
            oParamMap.put("fund_card"," ");
            oParamMap.put("main_flag"," ");
            oParamMap.put("client_group"," ");
            oParamMap.put("room_code"," ");
            oParamMap.put("asset_prop"," ");
            oParamMap.put("organ_flag"," ");
            oParamMap.put("organ_audit_date"," ");
            oParamMap.put("fare_kind_str"," ");
            oParamMap.put("en_entrust_way"," ");
            oParamMap.put("client_rights"," ");
            oParamMap.put("restriction"," ");
            oParamMap.put("open_trades"," ");
            oParamMap.put("profit_flag"," ");
            oParamMap.put("discount_rate"," ");
            oParamMap.put("product_flag"," ");
            oParamMap.put("square_flag"," ");
            oParamMap.put("remark"," ");
            oParamMap.put("bank_batch"," ");
            oParamMap.put("bank_AC"," ");
            oParamMap.put("max_exposure"," ");
            oParamMap.put("expiry_date_exp"," ");
            oParamMap.put("credit_limit"," ");
            oParamMap.put("expiry_date"," ");
            oParamMap.put("holder_status_temp"," ");
            oParamMap.put("save_keeping"," ");
            oParamMap.put("contract_date"," ");
            oParamMap.put("client_marked"," ");
            oParamMap.put("credit_ratio"," ");
            oParamMap.put("day_trade_limit"," ");
            oParamMap.put("op_remark"," ");
            oParamMap.put("fund_account"," ");
            oParamMap.put("client_id",req.getClient_id());
        }
        return oParamMap;
    }



    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
           List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            EF01110119VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110119VO();
                vo.setFund_account(rowMap.get(HsConstants.Fields.CLIENT_ID));
                vo.setSerial_no(rowMap.get(HsConstants.Fields.SERIAL_NO));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
