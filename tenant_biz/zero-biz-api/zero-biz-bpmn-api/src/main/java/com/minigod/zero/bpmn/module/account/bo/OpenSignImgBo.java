package com.minigod.zero.bpmn.module.account.bo;

import lombok.Data;

/**
 * @ClassName: OpenImgBo
 * @Description:
 * @Author chenyu
 * @Date 2024/2/1
 * @Version 1.0
 */
@Data
public class OpenSignImgBo {

    private String imgBase64; // 图片信息
    private String imgUrl; // 图片信息
	/**
	 *
	 * {@link com.minigod.zero.bpmn.module.account.enums.ImageDescEnum}
	 * 4:w8协议_签名
	 */
    private String location;
	/**
	 * 图片类型 302:w8协议_签名
	 */
    private String type; // 图片类型
}
