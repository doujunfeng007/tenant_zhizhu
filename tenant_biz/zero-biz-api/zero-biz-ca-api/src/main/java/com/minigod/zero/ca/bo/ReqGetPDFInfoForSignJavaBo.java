package com.minigod.zero.ca.bo;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.ca.bo.sz.GetPDF;
import com.minigod.zero.ca.bo.sz.request.Request;
import com.minigod.zero.ca.bo.sz.request.RequestBo;
import feign.form.FormProperty;
import lombok.Data;

import java.io.File;

/**
 * @ClassName: ReqGetPDFInfoForSignJavaBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/1
 * @Version 1.0
 */
@Data
public class ReqGetPDFInfoForSignJavaBo {
    @FormProperty(value = "getPdf")
    private String getPdf;
    @FormProperty(value = "file")
    private File file;

    public ReqGetPDFInfoForSignJavaBo(File file, GetPDF getPDF){
        this.file = file;
        Request request = new Request();
        request.setGetPDF(getPDF);
        RequestBo requestBo = new RequestBo();
        requestBo.setRequest(request);
        this.getPdf = JSON.toJSONString(requestBo);
    }
}
