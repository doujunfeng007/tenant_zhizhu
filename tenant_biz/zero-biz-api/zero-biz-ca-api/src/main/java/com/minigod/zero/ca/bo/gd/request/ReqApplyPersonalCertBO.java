package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 申请数字证书请求参数
 *
 * @author eric
 * @since 2024-05-16 14:12:09
 */
@Data
public class ReqApplyPersonalCertBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 活体检测ID
     */
    @FormProperty("authenticationId")
    private String authenticationId;
    /**
     * 银行卡关联对比
     */
    @FormProperty("bankauthId")
    private String bankAuthId;
    /**
     * 证书申请 CSR
     */
    @FormProperty("p10")
    private String p10;
}
