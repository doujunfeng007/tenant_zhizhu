package com.minigod.zero.bpmn.module.account.service;


import com.minigod.zero.ca.bo.OcrIdCardBo;
import com.minigod.zero.bpmn.module.account.ca.request.VerifyFourBo;
import com.minigod.zero.bpmn.module.account.ca.request.VerifyLivingBo;
import com.minigod.zero.bpmn.module.account.ca.response.ValidateStill;
import com.minigod.zero.bpmn.module.account.ca.response.VerifyFourFactor;

import java.util.Map;

/**
 * @ClassName: CaService
 * @Description:
 * @Author chenyu
 * @Date 2022/8/12
 * @Version 1.0
 */
public interface CaService {
    /**
     * 银行四要素认证, 默认通过，不通过抛异常
     *
     * @return
     */
    VerifyFourFactor verifyFourFactor(VerifyFourBo verifyFourBo);

    /**
     * 获取证书主题
     *
     * @param verifyFourBo
     * @return
     */
    String reqGetCertDN(VerifyFourBo verifyFourBo);

    /**
     * 验证静默活体
     *
     * @param verifyLivingBo
     * @return
     */
    ValidateStill validateStill(VerifyLivingBo verifyLivingBo);

    /**
     * ORD IdCard信息识别
     *
     * @param ocrIdCardBo
     * @return
     */
    Map<String, Object> ocrIdCard(OcrIdCardBo ocrIdCardBo);

    /**
     * 获取token
     *
     * @return
     */
    String token();

    /**
     * 请CA服务时,如果token失效重置token
     *
     * @return
     */
    String resetToken();
}
