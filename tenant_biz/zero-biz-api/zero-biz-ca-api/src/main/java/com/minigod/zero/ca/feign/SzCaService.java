package com.minigod.zero.ca.feign;


import com.minigod.zero.ca.bo.LivingBodyVerifyBo;
import com.minigod.zero.ca.bo.OcrIdCardBo;
import com.minigod.zero.ca.bo.UnionpayVerifyFourfactorBo;
import com.minigod.zero.ca.bo.ValidateStillBo;
import com.minigod.zero.ca.bo.sz.response.LivingResponse;
import com.minigod.zero.ca.bo.sz.response.RandomNumberResponse;
import com.minigod.zero.ca.bo.sz.response.ResultSzCa;
import com.minigod.zero.ca.bo.sz.response.*;
import com.minigod.zero.ca.bo.sz.request.*;
import com.minigod.zero.ca.bo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


/**
 * @ClassName: CaService
 * @Description: CA接口
 * @Author chenyu
 * @Date 2022/7/28
 * @Version 1.0
 */
@FeignClient(
    name = "szCaService",
    url = "${ca.sz.url}"
)
public interface SzCaService {
    /**
     * 获取token
     *
     * @return
     */
    @GetMapping(
        value = "/authServer/auth/token/${ca.sz.clientId}/${ca.sz.clientKey}",
        consumes = "application/json;charset=utf-8",
        produces = "application/json;charset=utf-8")
    ResultSzCa<String> getToken();

    /**
     * 银联认证 四要素
     *
     * @param token
     * @param bo
     * @return
     */
    @RequestMapping(
        value = "/core/api/unionpay_verify_fourfactor?token={token}",
        method = RequestMethod.POST,
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResultSzCa<Object> unionpayVerifyFourfactor(@PathVariable("token") String token,
                                                UnionpayVerifyFourfactorBo bo);

    /**
     * 获取活体认证随机字符
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/core/api/get_random_number?token={token}",
        method = RequestMethod.GET,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResultSzCa<RandomNumberResponse> getRandomNumber(@PathVariable("token") String token);

    /**
     * 活体检查
     *
     * @param token
     * @param bo
     * @return
     */
    @RequestMapping(value = "/core/api/livingbody_verify?token={token}",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResultSzCa<LivingResponse> livingbodyVerify(@PathVariable("token") String token,
                                                LivingBodyVerifyBo bo);

    /**
     * 静默活体
     *
     * @param token
     * @param bo
     * @return
     */
    @RequestMapping(value = "/core/api/validate_still?token={token}",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResultSzCa<LivingResponse> validateStill(@PathVariable("token") String token,
                                             ValidateStillBo bo);


    @RequestMapping(value = "/core/api/ocr_idcard?token={token}",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResultSzCa<Map<String,Object>> ocrIdCard(@PathVariable("token") String token,
                                             OcrIdCardBo bo);

}
