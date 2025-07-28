package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * 认证并申请证书请求参数
 *
 * @author eric
 * @since 2024-05-12 17:01:01
 */
@Data
public class ReqVerifyAndApplyCertBo implements Serializable {
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
     * 认证视频，建议大小不超过 600k，时
     * 长需要大于 2-3 秒
     */
    @FormProperty("video")
    private File video;
    /**
     * 民族
     */
    @FormProperty("nationality")
    private String nationality;
    /**
     * 性别
     */
    @FormProperty("sex")
    private String sex;

    /**
     * 出生年月
     */
    @FormProperty("birth")
    private String birth;

    /**
     * 所在省份，长度不能超过 16
     */
    @FormProperty("province")
    private String province;

    /**
     * 所在市，长度不能超过 16
     */
    @FormProperty("city")
    private String city;

    /**
     * 地址
     */
    @FormProperty("address")
    private String address;
    /**
     * 身份证人像面，大小不超过 1.5M
     */
    @FormProperty("front")
    private File front;
    /**
     * 身份证国徽面，大小不超过 1.5M
     */
    @FormProperty("back")
    private File back;
    /**
     * 签证机关
     */
    @FormProperty("issueAuthority")
    private String issueAuthority;

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

    /**
     * 证书申请 CSR
     */
    @FormProperty("p10")
    private String p10;
}
