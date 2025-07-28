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
public class OpenImgBo {
    private Integer openType; // 开户类型
    private String imgType; // 图片信息
    private String imgBase64; // 图片信息
    private String imgUrl; // 图片信息
    private String location; // 图片类型坐标
    private String type; // 图片类型
}
