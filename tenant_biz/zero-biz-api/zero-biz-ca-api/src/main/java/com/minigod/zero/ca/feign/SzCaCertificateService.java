package com.minigod.zero.ca.feign;

import com.minigod.zero.ca.bo.ReqGatewaytokenCertApplyP10ServiceBo;
import com.minigod.zero.ca.bo.ReqGetCertDNBo;
import com.minigod.zero.ca.bo.ReqGetPDFInfoForSignJavaBo;
import com.minigod.zero.ca.bo.ReqSignP7ForPdfJavaBo;
import com.minigod.zero.ca.bo.sz.response.ResultSzCa;
import com.minigod.zero.ca.bo.*;
import com.minigod.zero.ca.bo.sz.response.*;
import com.minigod.zero.ca.bo.sz.request.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName: CaService
 * @Description: CA接口
 * @Author chenyu
 * @Date 2022/7/28
 * @Version 1.0
 */
@FeignClient(name = "szCaCertificateService",
    url = "${ca.sz.certificateUrl}"
)
public interface SzCaCertificateService {

    /**
     * 获取主题
     * 通过用户的传入的身份证信息生成用户主题并返回，主用于生成P10
     * @param bo
     * @return
     */
    @RequestMapping(
        value = "/SecuritiesAccount/service/reqGetCertDN",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = "application/json;charset=utf-8"
    )
    ResultSzCa<String> reqGetCertDN(ReqGetCertDNBo bo);

    /**
     * 上传pdf文件，生成对应的签名域文件以及文件待签名原文（文件HASH）
     * @param bo
     * @return
     */
    @RequestMapping(
        value = "/SecuritiesAccount/service/reqGetPDFInfoForSignJava",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = "application/json;charset=utf-8"
    )
    ResultSzCa<String> reqGetPDFInfoForSignJava(ReqGetPDFInfoForSignJavaBo bo);


    @RequestMapping(
        value = "/SecuritiesAccount/service/reqGatewaytokenCertApplyP10Service",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = "application/json;charset=utf-8"
    )
    ResultSzCa<String> reqGatewaytokenCertApplyP10Service(ReqGatewaytokenCertApplyP10ServiceBo bo);

    /**
     * 签署合成
     *
     * @param bo
     * @return
     */
    @RequestMapping(
        value = "/SecuritiesAccount/service/reqSignP7ForPdfJava",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = "application/json;charset=utf-8"
    )
    ResultSzCa<String> reqSignP7ForPdfJava(ReqSignP7ForPdfJavaBo bo);
}
