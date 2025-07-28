package com.minigod.zero.bpmn.module.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.ca.bo.OcrIdCardBo;
import com.minigod.zero.ca.bo.ReqGetCertDNBo;
import com.minigod.zero.ca.bo.UnionpayVerifyFourfactorBo;
import com.minigod.zero.ca.bo.ValidateStillBo;
import com.minigod.zero.bpmn.module.account.ca.request.VerifyFourBo;
import com.minigod.zero.bpmn.module.account.ca.request.VerifyLivingBo;
import com.minigod.zero.bpmn.module.account.ca.response.ValidateStill;
import com.minigod.zero.bpmn.module.account.ca.response.VerifyFourFactor;
import com.minigod.zero.bpmn.module.account.service.CaService;
import com.minigod.zero.ca.feign.SzCaCertificateService;
import com.minigod.zero.ca.feign.SzCaService;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.ca.bo.sz.response.LivingResponse;
import com.minigod.zero.ca.bo.sz.response.ResultSzCa;
import com.minigod.zero.core.log.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * @ClassName: CaServiceImpl
 * @Description:
 * @Author chenyu
 * @Date 2022/8/12
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class SzCaServiceImpl implements CaService {
    private final SzCaService szCaService;
    private final SzCaCertificateService szCaCertificateService;

    @Override
    public VerifyFourFactor verifyFourFactor(VerifyFourBo verifyFourBo) {
        VerifyFourFactor verifyFourFactor = new VerifyFourFactor();
        Boolean pass = false;
        String orderno = "";
        UnionpayVerifyFourfactorBo bo = new UnionpayVerifyFourfactorBo();
        bo.setPhoneNumber(verifyFourBo.getPhoneNumber());
        bo.setIdcardName(verifyFourBo.getName());
        bo.setUnionpayCardnumber(verifyFourBo.getBankNo());
        bo.setIdcardNumber(verifyFourBo.getIdNo());
        ResultSzCa result = szCaService.unionpayVerifyFourfactor(token(), bo);
        if (result.getCode() == -1001) {
            resetToken();
            log.error("token失效:" + JSON.toJSONString(result));
            throw new ServiceException("请稍后重试");
        } else {
            if (result.getCode() != 0) {
                log.error("token失效:" + JSON.toJSONString(result));
                throw new ServiceException("请稍后重试");
            }
            verifyFourFactor.setSuccess(result.getSuccess());
            verifyFourFactor.setOrderNo(result.getOrderno());
        }
        return verifyFourFactor;
    }

    @Override
    public ValidateStill validateStill(VerifyLivingBo verifyLivingBo) {
        ValidateStill validateStill = new ValidateStill();
        ValidateStillBo bo = new ValidateStillBo();
        bo.setIdCardNumber(verifyLivingBo.getIdNo());
        bo.setIdCardName(verifyLivingBo.getName());
        try {
            bo.setVideo(ImageUtils.urlToFile(verifyLivingBo.getUrl()));
        } catch (MalformedURLException e) {
            log.error("静默活体读取");
            throw new ServiceException("请稍后重试");
        }
        ResultSzCa<LivingResponse> livingResponseResultSzCa = szCaService.validateStill(token(), bo);
        if (livingResponseResultSzCa.getCode() != 0) {
            log.error("活体识别错误:" + JSON.toJSONString(livingResponseResultSzCa));
            throw new ServiceException("请稍后重试");
        } else {
            validateStill.setOrderNo(livingResponseResultSzCa.getOrderno());
            validateStill.setImage(livingResponseResultSzCa.getObj().getImageBest());
        }

        return validateStill;
    }

    @Override
    public String token() {
        String token = "";
        ResultSzCa<String> stringResultSzCa = szCaService.getToken();
        if (stringResultSzCa.getCode() == 0) {
            token = stringResultSzCa.getObj();
            return token;
        } else {
            log.error("获取SZCA token 异常:" + JSON.toJSONString(stringResultSzCa));
            throw new ServiceException("请稍后重试");
        }
    }

    @Override
    public String resetToken() {
        ResultSzCa<String> stringResultSzCa = szCaService.getToken();
        if (stringResultSzCa.getCode() == 0) {
            String token = stringResultSzCa.getObj();
            return token;
        } else {
            log.error("获取SZCA token 异常:" + JSON.toJSONString(stringResultSzCa));
            throw new ServiceException("请稍后重试");
        }
    }

    @Override
    public String reqGetCertDN(VerifyFourBo verifyFourBo) {
        ReqGetCertDNBo reqGetCertDNBo = new ReqGetCertDNBo(verifyFourBo.getName(), verifyFourBo.getIdNo());
        ResultSzCa<String> resultSzCa = szCaCertificateService.reqGetCertDN(reqGetCertDNBo);
        if (resultSzCa.getCode() == 0) {
            return resultSzCa.getObj();
        }
        return null;
    }

    @Override
    public Map<String, Object> ocrIdCard(OcrIdCardBo ocrIdCardBo) {
        ResultSzCa<Map<String, Object>> resultSzCa = szCaService.ocrIdCard(token(), ocrIdCardBo);
        if (resultSzCa.getCode() == 0) {
            return resultSzCa.getObj();
        } else {
            throw new ServiceException("请稍后重试");
        }
    }
}
