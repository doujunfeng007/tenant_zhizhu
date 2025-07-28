package com.minigod.zero.trade.hs.service;

import com.minigod.zero.biz.common.utils.ArithmeticUtil;
import com.minigod.zero.trade.hs.resp.GrmField;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunline on 2016/6/13 17:24.
 * sunline
 */
@Component
public class HsFieldFormater implements FieldFormater{
//    @Resource
//    private GrmFieldDao grmFieldDao;
    private static final Map<String, GrmField> fieldMap= new HashMap<>();

    @PostConstruct
    private void loadField(){
		// TODO 字段类型转换表  sunline.grm_field
//        List<GrmField> fieldList = grmFieldDao.getAllField();
//        if(CollectionUtils.isNotEmpty(fieldList)){
//            for(GrmField grmField:fieldList){
//                fieldMap.put(grmField.getExtFieldCode(),grmField);
//            }
//        }
    }
    @Override
    public String format(String extFieldCode,String value){
        if(StringUtils.isNotEmpty(extFieldCode) && StringUtils.isNotEmpty(value)){
            GrmField grmField = fieldMap.get(extFieldCode);
            if(null != grmField){
                String type = grmField.getFieldType();
                if(type.equalsIgnoreCase("String")){
                    return value;
                }else if(type.equalsIgnoreCase("Integer")){
                    return String.valueOf(Double.valueOf(value).intValue());
                }else if(type.equalsIgnoreCase("Double")){
                    return ArithmeticUtil.strRound(value, grmField.getFieldDeci())/*.replaceAll("0+?$", "").replaceAll("[.]$", "")*/;
                }
            }
        }
        return value;
    }
}
