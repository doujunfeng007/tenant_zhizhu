package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 银行卡信息比对
 *
 * @author eric
 * @since 2024-05-12 21:04:09
 */
@Data
public class ReqUnionPayVerifyBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    @FormProperty("name")
    private String name;
    /**
     * 身份证号码
     */
    @FormProperty("idCode")
    private String idCode;
    /**
     * 银行卡号
     */
    @FormProperty("bankNo")
    private String bankNo;
    /**
     * 银行卡预留的手机号
     */
    @FormProperty("bankMobile")
    private String bankMobile;

}
