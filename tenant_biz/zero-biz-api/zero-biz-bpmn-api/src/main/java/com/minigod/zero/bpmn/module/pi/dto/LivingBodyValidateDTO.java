package com.minigod.zero.bpmn.module.pi.dto;

import lombok.Data;

/**
 * @author eric
 * @description: 活体公安对比
 * @since 2025-06-22 14:56:25
 */
@Data
public class LivingBodyValidateDTO {
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
}
