package com.minigod.zero.bpm.vo.request;

import lombok.Data;

@Data
public class BsUserCheckReqVo {
	/**
	 * 证券账号
	 */
    private String clientId;
	/**
	 * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	 */
	private Integer idKind;
	/**
	 * 香港证件号码
	 */
    private String idNo;
	/**
	 * 内地证件号码
	 */
	private String hidNo;
	/**
	 * 香港证件类型
	 */
    private Integer idType;
	/**
	 * 内地证件类型
	 */
	private Integer hidType;
	/**
	 * 用户id
	 */
	private Integer userId;
}
