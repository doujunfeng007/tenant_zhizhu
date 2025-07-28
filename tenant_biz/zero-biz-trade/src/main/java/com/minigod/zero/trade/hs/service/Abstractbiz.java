package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.Constants;
import com.minigod.zero.trade.hs.constants.ErrorMsgHandler;
import com.minigod.zero.trade.hs.constants.StaticCode;
import com.minigod.zero.trade.hs.vo.GrmBiz;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by sunline on 2016/4/22 15:59.
 * sunline
 */
@Component
@Slf4j
public abstract class Abstractbiz implements GrmBiz {
    @Resource
    protected GrmCacheMgr grmCacheMgr;
    protected abstract  <T extends GrmRequestVO> GrmResponseVO request(T request) throws Exception;

    public <T extends GrmRequestVO> GrmResponseVO requestData(T request){
            if(isLegalRequest(request)) {
                if(StringUtils.isNotEmpty(request.getFunctionId())){
                    try {
                        GrmResponseVO vo =  request(request);
                        return vo;
                    } catch (Exception e) {
                        log.error("功能请求失败!",e);
                    }
                }else{
                    return GrmResponseVO.getInstance().setErrorId("EM0512000001")
                            .setErrorMsg(ErrorMsgHandler.getErrorMsg("EM0512000001", Constants.LANG_DEFAULT));
                }
            }else {
                return GrmResponseVO.getInstance().setErrorId("EM0512000002")
                        .setErrorMsg(ErrorMsgHandler.getErrorMsg("EM0512000002", Constants.LANG_DEFAULT));
            }
        return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.FUNCTION_INVOKE_FAILED)
                .setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.FUNCTION_INVOKE_FAILED,Constants.LANG_DEFAULT));
    }

    /**
     * 验证请求是否合法
     * @param request
     * @return
     */
    private <T extends GrmRequestVO> boolean isLegalRequest(T request){
        return true;
    }

    /**
     * 拼凑通知消息
     */
    protected String getNotifyMsg(String errorId,String lang,String[] params){
        if(StringUtils.isEmpty(lang)){
            lang = Constants.LANG_DEFAULT;
        }
        String errorMsg = "";
        if(StringUtils.isNotEmpty(errorMsg) &&
                null != params && params.length > 0){
            errorMsg = ErrorMsgHandler.getErrorMsg( errorId,lang);
        }else{
            errorMsg = ErrorMsgHandler.getErrorMsg(errorId,lang);
        }
        return errorMsg;
    }
}
