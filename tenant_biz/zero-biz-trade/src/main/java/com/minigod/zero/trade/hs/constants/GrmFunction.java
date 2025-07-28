package com.minigod.zero.trade.hs.constants;

import com.minigod.zero.trade.hs.vo.GrmBiz;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 恒生调用接口各类参数
 */
public class GrmFunction {

    private String funId;
    private Class<? extends GrmRequestVO> requestVo;
    private Class responseVo;
    private Class<? extends GrmBiz> bizClass;
    private Map<String,Method> gReqMethods = new HashMap();
    private Map<String,Method> sReqMethods = new HashMap();
    private Map<String,Class> reqFields = new HashMap();
    private Map<String,Method> gResMethods = new HashMap();
    private Map<String,Method> sResMethods = new HashMap();
    private Map<String,Class> resFields = new HashMap();

    public Method getReqGetMethodByField(String field){
        return gReqMethods.get(field);
    }

    public void addReqGetMethod(String field,Method getMethod){
        gReqMethods.put(field, getMethod);
    }

    public Method getReqSetMethodByField(String field){
        return sReqMethods.get(field);
    }

    public void addReqSetMethod(String field,Method setMethod){
        sReqMethods.put(field, setMethod);
    }

    public Class getReqFieldType(String field){
        return reqFields.get(field);
    }

    public void addReqFieldType(String field,Class fieldClass){
        reqFields.put(field, fieldClass);
    }

    public Method getResGetMethodByField(String field){
        return gResMethods.get(field);
    }

    public void addResGetMethod(String field,Method getMethod){
        gResMethods.put(field, getMethod);
    }

    public Method getResSetMethodByField(String field){
        return sResMethods.get(field);
    }

    public void addResSetMethod(String field,Method setMethod){
        sResMethods.put(field, setMethod);
    }

    public Class getResFieldType(String field){
        return resFields.get(field);
    }

    public void addResFieldType(String field,Class fieldClass){
        resFields.put(field, fieldClass);
    }



    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public Class<? extends GrmRequestVO> getRequestVo() {
        return requestVo;
    }

    public void setRequestVo(Class<? extends GrmRequestVO> requestVo) {
        this.requestVo = requestVo;
    }

    public Class getResponseVo() {
        return responseVo;
    }

    public void setResponseVo(Class responseVo) {
        this.responseVo = responseVo;
    }

    public Map<String, Method> getgReqMethods() {
        return gReqMethods;
    }

    public void setgReqMethods(Map<String, Method> gReqMethods) {
        this.gReqMethods = gReqMethods;
    }

    public Map<String, Method> getsReqMethods() {
        return sReqMethods;
    }

    public void setsReqMethods(Map<String, Method> sReqMethods) {
        this.sReqMethods = sReqMethods;
    }

    public Map<String, Class> getReqFields() {
        return reqFields;
    }

    public void setReqFields(Map<String, Class> reqFields) {
        this.reqFields = reqFields;
    }

    public Map<String, Method> getgResMethods() {
        return gResMethods;
    }

    public void setgResMethods(Map<String, Method> gResMethods) {
        this.gResMethods = gResMethods;
    }

    public Map<String, Method> getsResMethods() {
        return sResMethods;
    }

    public void setsResMethods(Map<String, Method> sResMethods) {
        this.sResMethods = sResMethods;
    }

    public Map<String, Class> getResFields() {
        return resFields;
    }

    public void setResFields(Map<String, Class> resFields) {
        this.resFields = resFields;
    }

    public Class<? extends GrmBiz> getBizClass() {
        return bizClass;
    }

    public void setBizClass(Class bizClass) {
        this.bizClass = bizClass;
    }
}
