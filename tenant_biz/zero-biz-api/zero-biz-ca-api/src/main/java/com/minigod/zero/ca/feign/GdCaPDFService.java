package com.minigod.zero.ca.feign;

import com.minigod.zero.ca.bo.gd.request.ReqCreateEmptySignatureBo;
import com.minigod.zero.ca.bo.gd.request.ReqMergePdfHashBo;
import com.minigod.zero.ca.bo.gd.request.ReqSignatureBo;
import com.minigod.zero.ca.bo.gd.response.CreateEmptySignatureObject;
import com.minigod.zero.ca.bo.gd.response.MergePdfHashObject;
import com.minigod.zero.ca.bo.gd.response.ResultGdCa;
import com.minigod.zero.ca.bo.gd.response.SignatureObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 广东CA服务证书接口
 * @Author eric
 * @Date 2024-05-12 14:44:22
 */
@FeignClient(name = "GdCaCertificateService", url = "${ca.gd.pdfUrl}")
public interface GdCaPDFService {
    /**
     * 推送PDF
     *
     * @param bo
     * @return
     */
    @RequestMapping(
            value = "/api/pdf/createEmptySignature",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json;charset=utf-8"
    )
    ResultGdCa<CreateEmptySignatureObject> createEmptySignature(@RequestBody ReqCreateEmptySignatureBo bo,
                                                                @RequestParam("appId") String appId,
                                                                @RequestParam("secretKey") String secretKey);

    /**
     * 签署合成
     *
     * @param bo
     * @return
     */
    @RequestMapping(
            value = "/api/pdf/mergePdfHash",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json;charset=utf-8"
    )
    ResultGdCa<MergePdfHashObject> mergePdfHash(@RequestBody ReqMergePdfHashBo bo,
                                                @RequestParam("appId") String appId,
                                                @RequestParam("secretKey") String secretKey);

    /**
     * PDF验签
     *
     * @param bo
     * @return
     */
    @RequestMapping(
            value = "/api/pdf/signature",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json;charset=utf-8"
    )
    ResultGdCa<SignatureObject> signature(@RequestBody ReqSignatureBo bo,
                                          @RequestParam("appId") String appId,
                                          @RequestParam("secretKey") String secretKey);
}
