package com.minigod.zero.trade.hs.constants;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.utils.ReflectUtils;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by sunline on 2016/4/25 16:04.
 * sunline
 */
@Service
@Slf4j
public class GrmFunctionHolder {
    private static Map<String,GrmFunction> grmFunctionMap = new HashMap<>();


    public static void initFunctions(String functionPkg){
        try {
            Set<Class<?>> classese = null; // TODO 加载 AnnUtils.getAllClasses("com.sunline.core.grm");
            for(Class<?> c:classese){
                GrmFunctionEntity entity = c.getAnnotation(GrmFunctionEntity.class);
                if(entity != null ) {
                    GrmFunction function = new GrmFunction();
                    Class suppervo = entity.requestVo().getSuperclass();
                    if(null  != suppervo && suppervo.equals(GrmRequestVO.class)){
                        Map<String, Class> fm = new HashMap<String,Class>();
                        Map<String, Method> gm = new HashMap<>();
                        Map<String, Method> sm = new HashMap<>();
                        Field[] supReqFields = suppervo.getDeclaredFields();
                        supReqFields = excludeField(supReqFields,"serialVersionUID");
                        fm.putAll(initFieldMap(supReqFields));
                        gm.putAll(initMethodMap(suppervo, supReqFields, false));
                        sm.putAll(initMethodMap(suppervo,supReqFields,true));

                        function.setBizClass(c);
                        function.setFunId(c.getSimpleName());
                        function.setRequestVo(entity.requestVo());
                        function.setResponseVo(entity.responseVo());
                        Field[] reqFields = entity.requestVo().getDeclaredFields();
                        reqFields = excludeField(reqFields,"serialVersionUID");
                        fm.putAll(initFieldMap(reqFields));
                        function.setReqFields(fm);
                        gm.putAll(initMethodMap(entity.requestVo(),reqFields,false));
                        function.setgReqMethods(gm);
                        sm.putAll(initMethodMap(entity.requestVo(), reqFields, true));
                        function.setsReqMethods(sm);

                        Field[] resFields = entity.responseVo().getDeclaredFields();
                        resFields = excludeField(resFields,"serialVersionUID");
                        function.setResFields(initFieldMap(resFields));
                        function.setgResMethods(initMethodMap(entity.responseVo(), resFields, false));
                        function.setsResMethods(initMethodMap(entity.responseVo(), resFields, true));
                        grmFunctionMap.put(function.getFunId(),function);
                }
                }
            }
            //printFunctions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printFunctions(){
        if(null != grmFunctionMap){
            log.info("Grm目前支持以下功能调用:");
            Set keys = grmFunctionMap.keySet();
            Iterator<String> iterator = keys.iterator();
            while(iterator.hasNext()){
				log.info(JSONObject.toJSONString(iterator.next()));
            }
        }
    }

    private static Field[] excludeField(Field[] fields ,String excludeFieldName){
        List<Field> list = new ArrayList();
        for(Field field:fields){
            if(!field.getName().equals(excludeFieldName)){
                list.add(field);
            }
        }
        return list.toArray(new Field[]{});
    }

    private static Map<String,Class> initFieldMap(Field[] fields){
        Map<String ,Class> fieldMap = new HashMap();
        for(Field field :fields){
            fieldMap.put(field.getName(),field.getType());
        }
        return fieldMap;
    }

    private static Map<String,Method> initMethodMap(Class clazz,Field[] fields,boolean isSet){
        Map<String ,Method> methodMap = new HashMap();
        for(Field field:fields){
            if(isSet){
                methodMap.put(field.getName(), ReflectUtils.getSetMethod(clazz,field));
            }else{
                methodMap.put(field.getName(), ReflectUtils.getGetMethod(clazz, field));
            }
        }
        return methodMap;
    }

    public static GrmFunction getFunction(String funcId){
        return grmFunctionMap.get(funcId);
    }

    public static void addFunction(GrmFunction function){
        grmFunctionMap.put(function.getFunId(),function);
    }

    public static Map<String, GrmFunction> getGrmFunctionMap() {
        return grmFunctionMap;
    }

    public static void setGrmFunctionMap(Map<String, GrmFunction> grmFunctionMap) {
        GrmFunctionHolder.grmFunctionMap = grmFunctionMap;
    }
}
