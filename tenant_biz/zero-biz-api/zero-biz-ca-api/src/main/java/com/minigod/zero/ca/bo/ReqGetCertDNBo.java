package com.minigod.zero.ca.bo;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.ca.bo.sz.request.CertDN;
import com.minigod.zero.ca.bo.sz.request.Request;
import com.minigod.zero.ca.bo.sz.request.RequestBo;
import feign.form.FormProperty;
import lombok.Data;


/**
 * @ClassName: ReqGetCertDNBo
 * @Description: 获取主题入参
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class ReqGetCertDNBo {
    @FormProperty("getCertDN")
    private String certDN;

    public ReqGetCertDNBo(String userName, String idNo) {
        RequestBo requestBo = new RequestBo();
        Request request = new Request();
        CertDN certDN = new CertDN();
        certDN.setIdNo(idNo);
        certDN.setUserName(userName);
        request.setCertDN(certDN);
        requestBo.setRequest(request);
        this.certDN = JSON.toJSONString(requestBo);
    }
}
