package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

/**
 * @ClassName: OpenImgVo
 * @Description:
 * @Author chenyu
 * @Date 2024/1/31
 * @Version 1.0
 */
@Data
public class OpenImgVo {
    private Long imgId;
    private String imgUrl;
    private String location; // 图片类型坐标
    private String type; // 图片类型
}
