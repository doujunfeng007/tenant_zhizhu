package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 认证并申请证书请求参数
 */
@Data
public class VerifyAndApplyCertBo {
    /**
     * 证书主题
     */
    private String subjectDn;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    private String idCode;
    /**
     * 认证视频，建议大小不超过 600k，时
     * 长需要大于 2-3 秒
     */
    private String videoUrl;
    /**
     * 民族
     */
    private String nationality;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生年月
     */
    private String birth;
    /**
     * 所在省份，长度不能超过 16
     */
    private String province;
    /**
     * 所在市，长度不能超过 16
     */
    private String city;
    /**
     * 地址
     */
    private String address;
    /**
     * 身份证人像面，大小不超过 1.5M
     */
    private String frontUrl;
    /**
     * 身份证国徽面，大小不超过 1.5M
     */
    private String backUrl;
    /**
     * 签证机关
     */
    private String issueAuthority;
    /**
     * 银行卡号
     */
    private String bankNo;
    /**
     * 银行卡预留的手机号
     */
    private String bankMobile;
    /**
     * 证书申请 CSR
     */
    private String p10;
    public void checkValidate() {
        Validate.notBlank(this.subjectDn, "证书主题不能为空");
        Validate.notBlank(this.name, "姓名不能为空");
        Validate.notBlank(this.idCode, "身份证号码不能为空");
        Validate.notBlank(this.videoUrl, "认证视频不能为空");
        Validate.notBlank(this.nationality, "民族不能为空");
        Validate.notBlank(this.sex, "性别不能为空");
        Validate.notBlank(this.birth, "出生年月不能为空");
        Validate.notBlank(this.province, "所在省份不能为空");
        Validate.notBlank(this.city, "所在市不能为空");
        Validate.notBlank(this.address, "地址不能为空");
        Validate.notBlank(this.frontUrl, "身份证人像面不能为空");
        Validate.notBlank(this.backUrl, "身份证国徽面不能为空");
        Validate.notBlank(this.issueAuthority, "签证机关不能为空");
        Validate.notBlank(this.bankNo, "银行卡号不能为空");
        Validate.notBlank(this.bankMobile, "银行卡预留的手机号不能为空");
        Validate.notBlank(this.p10, "证书申请 CSR 不能为空");
    }
}
