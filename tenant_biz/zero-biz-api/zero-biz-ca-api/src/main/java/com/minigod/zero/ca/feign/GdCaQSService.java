package com.minigod.zero.ca.feign;

import com.minigod.zero.ca.bo.gd.request.*;
import com.minigod.zero.ca.bo.gd.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 广东CA接口
 * @Author eric
 * @Date 2024-05-12 14:36:59
 */
@FeignClient(name = "gdCaService", url = "${ca.gd.qsUrl}")
public interface GdCaQSService {
    /**
     * 获取证书主题
     *
     * @param bo
     * @return
     */
    @RequestMapping(
            value = "/qs/api/v1/getCertSubjectDn",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResultGdCa<CertSubjectDnObject> getCertSubjectDn(@RequestBody ReqCertSubjectDnBo bo,
                                                     @RequestParam("appId") String appId,
                                                     @RequestParam("secretKey") String secretKey);

    /**
     * 申请数字证书
     *
     * @param bo
     * @return
     */
    @RequestMapping(
            value = "/qs/api/v1/applyPersonalCert",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResultGdCa<ApplyPersonalCertObject> applyPersonalCert(@RequestBody ReqApplyPersonalCertBO bo,
                                                          @RequestParam("appId") String appId,
                                                          @RequestParam("secretKey") String secretKey);

    /**
     * 认证并申请证书
     *
     * @param bo
     * @return
     */
    @RequestMapping(value = "/qs/api/v1/verifyAndApplyCert",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResultGdCa<VerifyAndApplyCertObject> verifyAndApplyCert(@RequestBody ReqVerifyAndApplyCertBo bo,
                                                            @RequestParam("appId") String appId,
                                                            @RequestParam("secretKey") String secretKey);

    /**
     * 活体公安比对(人脸视频识别)
     *
     * @param bo
     * @return
     */
    @RequestMapping(value = "/qs/api/v1/livingbodyvalidate",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResultGdCa<LivingBodyValidateObject> livingBodyValidate(@RequestBody ReqLivingBodyValidateBo bo,
                                                            @RequestParam("appId") String appId,
                                                            @RequestParam("secretKey") String secretKey);

    /**
     * 公安两要素验证(姓名+身份证号验证)
     *
     * @param bo
     * @return
     */
    @RequestMapping(value = "/qs/api/v1/idnumberCheck",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResultGdCa<IdNumberCheckObject> idNumberCheck(@RequestBody ReqIdNumberCheckBo bo,
                                                  @RequestParam("appId") String appId,
                                                  @RequestParam("secretKey") String secretKey);

    /**
     * 银行卡信息比对
     *
     * @param bo
     * @return
     */
    @RequestMapping(value = "/qs/api/v1/unionPayVerify",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResultGdCa<UnionPayVerifyObject> unionPayVerify(ReqUnionPayVerifyBo bo,
                                                    @RequestParam("appId") String appId,
                                                    @RequestParam("secretKey") String secretKey);

    /**
     * 身份证OCR识别
     *
     * @param bo
     * @return
     */
    @RequestMapping(value = "/qs/api/v1/idCardOCR",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResultGdCa<IdCardOCRObject> idCardOCR(@RequestBody ReqIdCardOCRBo bo,
                                          @RequestParam("appId") String appId,
                                          @RequestParam("secretKey") String secretKey);
}
