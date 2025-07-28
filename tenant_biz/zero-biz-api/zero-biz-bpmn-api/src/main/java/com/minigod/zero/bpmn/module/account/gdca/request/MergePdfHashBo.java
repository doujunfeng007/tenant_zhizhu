package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 合并签署
 *
 * @author eric
 * @since 2024-05-12 19:38:05
 */
@Data
public class MergePdfHashBo {
    /**
     * 私钥签名数据
     */
    private String p1Data;
    /**
     * 创建空签名域返回的 pdfId
     */
    private String pdfId;
    /**
     * 证书序列号
     */
    private String signCertSn;
    public void checkValidate() {
        Validate.notBlank(p1Data, "私钥签名数据(p1Data)不能为空");
        Validate.notBlank(pdfId, "创建空签名域返回的 pdfId(pdfId)不能为空");
        Validate.notBlank(signCertSn, "证书序列号(signCertSn)不能为空");
    }
}
