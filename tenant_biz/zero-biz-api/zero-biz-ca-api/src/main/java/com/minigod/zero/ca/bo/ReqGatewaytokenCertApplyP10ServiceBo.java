package com.minigod.zero.ca.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.ca.bo.sz.request.Request;
import com.minigod.zero.ca.bo.sz.request.Apply;
import com.minigod.zero.ca.bo.sz.request.RequestBo;
import lombok.Data;

/**
 * @ClassName: ReqGatewaytokenCertApplyP10ServiceBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/1
 * @Version 1.0
 */
@Data
public class ReqGatewaytokenCertApplyP10ServiceBo {
    @JSONField(name = "apply")
    private String apply;

    public ReqGatewaytokenCertApplyP10ServiceBo(Apply apply) {
        Request request = new Request();
        request.setApply(apply);
        RequestBo requestBo = new RequestBo();
        requestBo.setRequest(request);
        this.apply = JSON.toJSONString(requestBo);
    }
}
