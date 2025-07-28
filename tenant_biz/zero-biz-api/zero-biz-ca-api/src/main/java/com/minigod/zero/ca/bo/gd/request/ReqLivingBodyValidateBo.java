package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * 活体公安比对
 *
 * @author eric
 * @since 2024-05-12 17:34:10
 */
@Data
public class ReqLivingBodyValidateBo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @FormProperty("name")
    private String name;
    /**
     * 身份证号
     */
    @FormProperty("idCode")
    private String idCode;
    /**
     * 认证视频，建议大小不超过 600k，时长
     * 需要大于 2-3 秒
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
     * 事务 ID，用于异常时的查询，长度小于
     * 100 大于 20，只能包含数字和字母
     */
    @FormProperty("transactionId")
    private String transactionId;
}
