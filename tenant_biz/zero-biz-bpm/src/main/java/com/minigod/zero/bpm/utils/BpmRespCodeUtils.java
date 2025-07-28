package com.minigod.zero.bpm.utils;

import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.cust.enums.CustStaticType;

/**
 * @description:
 * @author: sunline
 * @date: 2019/1/7 10:39
 * @version: v1.0
 */

public class BpmRespCodeUtils {

    // 返回指定错误信息
    public static ResponseVO getErrorMsg(Integer code, String message) {
        ResponseVO rt = new ResponseVO();
        rt.setCode(code);
        rt.setMessage(message);
        return rt;
    }

    // 返回请求成功信息
    public static ResponseVO getSuccessMsg(ResponseVO rt) {
        rt.setCode(CustStaticType.CodeType.OK.getCode());
        rt.setMessage(CustStaticType.CodeType.OK.getMessage());
        return rt;
    }

    // 返回参数错误提示
    public static ResponseVO getErrorParameMsg(ResponseVO rt) {
        rt.setCode(CustStaticType.CodeType.PARAMETER_ERROR.getCode());
        rt.setMessage(CustStaticType.CodeType.PARAMETER_ERROR.getMessage());
        return rt;
    }


}
