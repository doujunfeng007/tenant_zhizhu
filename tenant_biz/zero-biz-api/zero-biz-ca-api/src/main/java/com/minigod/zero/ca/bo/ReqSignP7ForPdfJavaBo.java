package com.minigod.zero.ca.bo;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.ca.bo.sz.request.Request;
import com.minigod.zero.ca.bo.sz.request.RequestBo;
import com.minigod.zero.ca.bo.sz.request.Sign;
import feign.form.FormProperty;
import lombok.Data;

/**
 * @ClassName: ReqSignP7ForPdfJavaBo
 * @Description: 签署合成接口参数
 * @Author chenyu
 * @Date 2022/8/1
 * @Version 1.0
 */
@Data
public class ReqSignP7ForPdfJavaBo {
    @FormProperty("sign")
    private String sign;

    /**
     * 签署合成构造器
     * @param certDn
     * @param certSn
     * @param fileID
     * @param P1SignData
     */
    public ReqSignP7ForPdfJavaBo(String certDn, String certSn, String fileID, String P1SignData) {
        Sign sign = new Sign();
        sign.setP1SignData(P1SignData);
        sign.setCertSn(certSn);
        sign.setFileID(fileID);
        sign.setCertDn(certDn);
        Request request = new Request();
        request.setSign(sign);
        RequestBo requestBo = new RequestBo();
        requestBo.setRequest(request);
        this.sign = JSON.toJSONString(requestBo);
    }

}
