package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * 认证并申请证书返回值
 *
 * @author eric
 * @since 2024-05-12 16:53:09
 */
@Data
public class VerifyAndApplyCertObject {

    /**
     * 签名证书序列号
     */
    private String signCertSn;
    /**
     * 签名证书主题
     */
    private String signCertDn;
    /**
     * 签名证书过期时间
     */
    private String expireDate;
}
