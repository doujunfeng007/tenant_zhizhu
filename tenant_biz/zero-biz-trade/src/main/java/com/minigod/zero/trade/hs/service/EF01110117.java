package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110117Request;
import com.minigod.zero.trade.hs.resp.EF01110117VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 客户开户
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110117Request.class,responseVo = EF01110117VO.class)
public class EF01110117<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110117Request){
            EF01110117Request req = (EF01110117Request) request;
            oParamMap.put("operator_no","110");
            oParamMap.put("op_password","1");
            oParamMap.put("user_type","3");
            oParamMap.put("op_station","121.35.249.10_68F728EE9B64");
            oParamMap.put("op_entrust_way","7");
            oParamMap.put("menu_id"," ");
            oParamMap.put("op_branch_no","100");
            oParamMap.put("branch_no","100");
            oParamMap.put("audit_action","1");
            oParamMap.put("corp_client_group","0");
            oParamMap.put("client_name","中華人民共和國");
            oParamMap.put("client_sex","1");
            oParamMap.put("nationality"," ");
            oParamMap.put("asset_prop","0");
            oParamMap.put("foreign_flag","0");
            oParamMap.put("id_kind","1");
            oParamMap.put("id_no","42031029312534523");
            oParamMap.put("id_term"," ");
            oParamMap.put("risk_level"," ");
            oParamMap.put("birthday"," ");
            oParamMap.put("last_name"," ");
            oParamMap.put("zipcode"," ");
            oParamMap.put("city_no"," ");
            oParamMap.put("home_tel"," ");
            oParamMap.put("office_tel"," ");
            oParamMap.put("address","中華人民共和國中華人民共和國中華人民共和國");
            oParamMap.put("id_address"," ");
            oParamMap.put("phonecode"," ");
            oParamMap.put("contact_mobile"," ");
            oParamMap.put("e_mail"," ");
            oParamMap.put("fax"," ");
            oParamMap.put("mobile_tel"," ");
            oParamMap.put("beeppager"," ");
            oParamMap.put("mail_name"," ");
            oParamMap.put("relation_idtype"," ");
            oParamMap.put("relation_id"," ");
            oParamMap.put("instrepr_name"," ");
            oParamMap.put("instrepr_idtype"," ");
            oParamMap.put("instrepr_id"," ");
            oParamMap.put("degree_code"," ");
            oParamMap.put("profession_code"," ");
            oParamMap.put("corporate_kind"," ");
            oParamMap.put("income"," ");
            oParamMap.put("child_flag"," ");
            oParamMap.put("child_id"," ");
            oParamMap.put("statement_flag"," ");
            oParamMap.put("account_data"," ");
            oParamMap.put("risk_info"," ");
            oParamMap.put("officeaddress"," ");
            oParamMap.put("officezip"," ");
            oParamMap.put("nativeplace"," ");
            oParamMap.put("homeplace"," ");
            oParamMap.put("icqid"," ");
            oParamMap.put("roomhire"," ");
            oParamMap.put("sumhire"," ");
            oParamMap.put("specifycomputer"," ");
            oParamMap.put("developer"," ");
            oParamMap.put("primcustmanager"," ");
            oParamMap.put("priminvestor"," ");
            oParamMap.put("primsaleman"," ");
            oParamMap.put("primassetmanager"," ");
            oParamMap.put("remark"," ");
            oParamMap.put("organ_name"," ");
            oParamMap.put("company_name"," ");
            oParamMap.put("organ_code"," ");
            oParamMap.put("sale_licence"," ");
            oParamMap.put("tax_register"," ");
            oParamMap.put("company_kind"," ");
            oParamMap.put("work_range"," ");
            oParamMap.put("register_fund"," ");
            oParamMap.put("register_money_type"," ");
            oParamMap.put("contract_person"," ");
            oParamMap.put("contract_tel"," ");
            oParamMap.put("home_page"," ");
            oParamMap.put("organ_flag"," ");
            oParamMap.put("holder_status_temp"," ");
            oParamMap.put("benifical_owner"," ");
            oParamMap.put("local_no"," ");
            oParamMap.put("invest_info"," ");
            oParamMap.put("locale_name"," ");
            oParamMap.put("locale_address"," ");
            oParamMap.put("trading_system"," ");
            oParamMap.put("address_id"," ");
            oParamMap.put("ensure_pwd_valid"," ");
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
           List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            EF01110117VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110117VO();
                vo.setClient_id(rowMap.get(HsConstants.Fields.CLIENT_ID));
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
