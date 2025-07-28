package com.minigod.zero.data.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户实名认证表
 * customer_realname_verify表
 *
 * @author eric
 * @date 2024-10-26 10:28:18
 */
@Data
public class CustomerRealNameVerify implements Serializable {

    /** 客户ID */
    private Long custId;

    /** 真实姓名 */
    private String realName;

    /** 序列 */
    private Integer sequence;

    /** 实名失败认证次数 */
    private Byte verifyErrCount;

    /** 正面照 */
    private String frontPhoto;

    /** 反面照 */
    private String reversePhoto;

    /** 证件类型 [0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证] */
    private String idKind;

    /** 证件号码 */
    private String idCard;

    /** 性别 [0=男 1=女] */
    private Integer gender;

    /** 生日日期 */
    private String birthday;

    /** 证件地址 */
    private String idCardAddress;

    /** 身份证生效日期 */
    private Date idCardValidDateStart;

    /** 身份证失效日期 */
    private Date idCardValidDateEnd;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 乐观锁版本号 */
    private Integer lockVersion;

    /** 证件签发地 */
    private String issueCountry;

    /** 是否删除 */
    private Byte isDeleted;

    /** 租户id */
    private String tenantId;
}
