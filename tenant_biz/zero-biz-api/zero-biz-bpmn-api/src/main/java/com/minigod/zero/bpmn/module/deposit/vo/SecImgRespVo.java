package com.minigod.zero.bpmn.module.deposit.vo;

import lombok.Data;

/**
 * @ClassName: SecImgRespVo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/1
 * @Version 1.0
 */
@Data
public class SecImgRespVo {
    private Long imgId;
    private String imgPath;
    private Long transactId;
}
