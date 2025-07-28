package com.minigod.zero.biz.common.mkt.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * 基金子类型
 */
@Data
public class FundSubClass implements Serializable {
	/**
     * 类别ID
	 */
	private Long subAssetClassId;
	/**
     * 英文
	 */
	private String nameEn;
	/**
	 * 简体
	 */
	private String nameCn;
	/**
	 * 繁体
	 */
	private String nameTw;
}
