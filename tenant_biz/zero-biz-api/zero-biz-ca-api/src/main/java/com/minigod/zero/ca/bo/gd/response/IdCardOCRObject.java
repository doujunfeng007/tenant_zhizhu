package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * 身份证 OCR识别结果
 *
 * @author eric
 * @since 2024-05-12 18:47:12
 */
@Data
public class IdCardOCRObject {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String number;
    /**
     * front 代表身份证正面，back 代表身份证反面（即国徽面）
     */
    private String side;
    /**
     * 地址
     */
    private String address;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private String birthDay;
    /**
     * 民族
     */
    private String nation;
    /**
     * 扩展信息
     * BorderCodeValue，身份证边框不完整告警阈值分数，（取值
     * 范围：0~100，分数越低边框遮挡可能性越低，建议阈值≥50）;
     * Quality，图片质量分数，（取值范围：0~100，分数越低越模
     * 糊，建议阈值≥50）;
     * WarnInfos，告警信息，Code 告警码列表和释义：
     * -9100 身份证有效期不合法告警，
     * -9101 身份证边框不完整告警，
     * -9102 身份证复印件告警，
     * -9103 身份证翻拍告警，
     * -9104 临时身份证告警，
     * -9105 身份证框内遮挡告警，
     * -9106 身份证 PS 告警，
     * -9107 身份证反光告警。
     */
    private AdvancedInfo advancedInfo;
    /**
     * 人像面身份证照片裁剪（去掉证件外多余的边缘、自
     * 动矫正拍摄角度），以 URL 的方式返回，有效期 30 分钟
     */
    private String cropImage;
    /**
     * 签发机关
     */
    private String issuer;
    /**
     * 过期日期
     */
    private String expiredDate;
    /**
     * 签发日期
     */
    private String issuerDate;

}

/**
 * 扩展信息
 */
@Data
class AdvancedInfo {
    /**
     * 身份证边框不完整告警阈值分数
     */
    String BorderCodeValue;
    /**
     * 图片质量分数
     */
    String Quality;
    /**
     * 告警信息
     */
    String WarnInfos;
}
