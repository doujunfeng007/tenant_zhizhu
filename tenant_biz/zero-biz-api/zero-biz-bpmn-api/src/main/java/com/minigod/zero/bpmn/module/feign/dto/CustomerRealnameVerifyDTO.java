package com.minigod.zero.bpmn.module.feign.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/10 15:31
 * @description：
 */
@Data
public class CustomerRealnameVerifyDTO {
	/**
	 * 客户ID
	 */
	private Long custId;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 绑定序号 0表示当前使用记录(有效状态) 其余数字为历史记录
	 */
	private Integer sequence;

	/**
	 * 实名失败认证次数
	 */
	private Integer verifieErrCount;

	/**
	 * 正面照
	 */
	private String frontPhoto;

	/**
	 * 反面照
	 */
	private String reversePhoto;

	/**
	 * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	 */
	private String idKind;

	/**
	 * 证件号码
	 */
	private String idCard;

	/**
	 * 性别[0=男 1=女 2=其他]
	 */
	private Integer gender;

	/**
	 * 生日日期
	 */
	private String birthday;

	/**
	 * 证件地址
	 */
	private String idCardAddress;

	/**
	 * 身份证生效日期
	 */
	private String idCardValidDateStart;

	/**
	 * 身份证失效日期
	 */
	private String idCardValidDateEnd;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 乐观锁版本号
	 */
	private Integer lockVersion;

	/**
	 * 证件签发地
	 */
	private String issueCountry;
}
