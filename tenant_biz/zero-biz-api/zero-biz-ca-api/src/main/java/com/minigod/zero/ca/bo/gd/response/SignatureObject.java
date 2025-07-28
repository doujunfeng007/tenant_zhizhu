package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

import java.util.List;

/**
 * PDF 验签
 *
 * @author eric
 * @since 2024-05-12 17:50:09
 */
@Data
public class SignatureObject {
    /**
     * 验签结果
     */
    private Signature data;

    /**
     * 验签结果
     */
    @Data
    class Signature {
        /**
         * 文档是否包含签名 true:有 false:无
         */
        private String haveSignature;
        /**
         * 验签小结，例如：自应用本
         * 签名以来，文档未被修改
         */
        private String message;
        /**
         * 签名集合
         */
        private List<stamp> stamps;

    }

    /**
     * 签名集合
     */
    @Data
    class stamp {
        /**
         *
         */
        private String signName;
        /**
         * 证书颁发者
         */
        private String issuer;
        /**
         * 证书生效时间
         */
        private String notBefore;
        /**
         * 证书过期时间
         */
        private String notAfter;
        /**
         * 签名时间
         */
        private String signDate;
        /**
         * 时间戳时间
         */
        private String timeStampDate;
        /**
         * 是 否 使 用 时 间 戳 true: 有 false:无
         */
        private String useTimestamp;
        /**
         * 时 间 戳 是 否 有 效 true: 有  false:无
         */
        private String validTimestamp;
        /**
         * 证书序列号
         */
        private String signCertSn;
        /**
         * 签名算法
         */
        private String signatureAlgorithm;
        /**
         * 证书用法
         */
        private String keyUsage;
        /**
         * 粤港互认证书策略 OID
         */
        private String certificatePolicy;
    }
}
