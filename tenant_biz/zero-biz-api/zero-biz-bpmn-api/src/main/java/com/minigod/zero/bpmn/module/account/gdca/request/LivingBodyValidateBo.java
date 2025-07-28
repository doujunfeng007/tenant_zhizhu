package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 活体公安比对请求
 */
@Data
public class LivingBodyValidateBo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCode;
    /**
     * 认证视频，建议大小不超过 600k，时长
     * 需要大于 2-3 秒
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
    public void checkValidate() {
        Validate.notBlank(name, "姓名不能为空");
        Validate.notBlank(idCode, "身份证号不能为空");
        Validate.notBlank(videoUrl, "认证视频不能为空");
        Validate.notBlank(nationality, "民族不能为空");
        Validate.notBlank(sex, "性别不能为空");
        Validate.notBlank(birth, "出生年月不能为空");
        Validate.notBlank(province, "所在省份不能为空");
        Validate.notBlank(city, "所在市不能为空");
        Validate.notBlank(address, "地址不能为空");
        Validate.notBlank(frontUrl, "身份证人像面不能为空");
		Validate.notBlank(backUrl, "身份证国徽面不能为空");
    }
}
