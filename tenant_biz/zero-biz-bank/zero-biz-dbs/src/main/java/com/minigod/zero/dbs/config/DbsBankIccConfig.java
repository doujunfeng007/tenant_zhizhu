package com.minigod.zero.dbs.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Dbs 银行相关配置
 *
 * @author chenyu
 * @title DbsConfig
 * @date 2023-04-12 7:33
 * @description
 */
@Data
@Component
@ConditionalOnProperty(prefix = "bank.icc", name = "enabled", havingValue = "true")
@ConfigurationProperties(prefix = "bank.icc")
public class DbsBankIccConfig {

    /** 请求域名 */
    private String url;
    /** 主机名 */
    private String hostName;
    /** 机构ID*/
    private String orgId;
    /** 公钥 */
    private String pubKey;
    /** 私钥 */
    private String secKey;
	/** 私钥密码 */
    private String privayeKeyPassword;
    /** 秘钥标识 */
    private String keyId;
    /** 连接超时 */
    private Integer connectionTimeout;
    /** 公钥内容 */
    @Deprecated
    private String publicKeyText;
    /** 私钥内容 */
    @Deprecated
    private String privateKeyText;
    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 租户回调 URL
     */
    private String callbackUrl;

    @PostConstruct
    public void init(){
        // 加载公私钥到内存  需要改造PGP加密算法 从内存中读取公私钥

    }

}
