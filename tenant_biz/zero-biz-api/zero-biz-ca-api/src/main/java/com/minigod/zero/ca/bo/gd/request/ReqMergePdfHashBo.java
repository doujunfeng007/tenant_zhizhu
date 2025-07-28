package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 签署
 * PKCS#7 合并到空签名域 PDF 中，生成签名完成的 PDF
 */
@Data
public class ReqMergePdfHashBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 私钥签名数据
     */
    @FormProperty("p1Data")
    private String p1Data;
    /**
     * 创建空签名域返回的 pdfId
     */
    @FormProperty("pdfId")
    private String pdfId;
    /**
     * 证书序列号
     */
    @FormProperty("signCertSn")
    private String signCertSn;

}
