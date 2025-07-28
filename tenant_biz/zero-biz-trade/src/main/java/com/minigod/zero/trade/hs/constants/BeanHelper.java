package com.minigod.zero.trade.hs.constants;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;

import java.util.Iterator;
import java.util.Map;

public class BeanHelper {

    /**
     * 下划线key的map转驼峰属性的object
     * @param rowMap
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Map<String, String> rowMap, Class<T> clazz){
        JSONObject jsonObject = new JSONObject();
        for(Iterator<String> ite = rowMap.keySet().iterator(); ite.hasNext();){
            String key = ite.next();
            String prop = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
            jsonObject.put(prop, rowMap.get(key));
        }
        return JSON.parseObject(jsonObject.toJSONString(), clazz);
    }
}
