package com.minigod.zero.bpm.dto;

import lombok.Data;

/**
 * @description: 客户信用额度申请图片上传
 * @author: zejie.weng
 * @date: 2023-5-27 11:33:11
 */
@Data
public class MarginCreditAdjustImgDTO {

	private static final long serialVersionUID = 5609442749565658343L;

	/**
	 * base64
	 */
	private String imgBase64;

	/**
	 * 文件类型
	 */
	private String type;

}
