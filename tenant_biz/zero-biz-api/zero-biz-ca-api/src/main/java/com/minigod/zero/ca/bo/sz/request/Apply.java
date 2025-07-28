package com.minigod.zero.ca.bo.sz.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: Apply
 * @Description:
 * @Author chenyu
 * @Date 2022/7/30
 * @Version 1.0
 */
@Data
public class Apply {
    //操作令牌
    @NotBlank(message = "操作令牌（必填）")
    private String token;
    //用户名称（必填）
    @NotBlank(message = "用户名称（必填）")
    private String userName;
    //身份证号码（必填）
    @NotBlank(message = "身份证号码（必填）")
    private String idNo;
    //性别（必填）
    @NotBlank(message = "性别（必填）")
    private String sex;
    //联系手机（必填）
    @NotBlank(message = "联系手机（必填）")
    private String mobileNo;
    //身份证正面（必填） Base64 国徽面
    @NotBlank(message = "身份证正面（必填）")
    private String identityImgOne;
    //身份证反面（必填) Base64 人像面
    @NotBlank(message = "身份证反面（必填）")
    private String identityImgTwo;
    //人体照片（必填)
    @NotBlank(message = "人体照片（必填)")
    private String humanBodyImg;
    @NotBlank(message = "省份（必填）")
    private String province;
    @NotBlank(message = "市区（必填）")
    private String city;
    @NotBlank(message = "联系地址（必填）")
    private String contactAddr;
    @NotBlank(message = "签发机关（必填）")
    private String cardedPlace;
    private String card;
    //20180910-长期
    @NotBlank(message = "有效期（必填）")
    private String cardedExpiryDate;
    //证书载体
    private String carrier = "1";
    //证书主题
    private String certDn;
    //证书p10数据
    private String certP10;
    //活体认证id
    private String livingOrderNo;
    //银联四要素认证id
    private String fourFactorNo;
    private String appType = "apply";
}
