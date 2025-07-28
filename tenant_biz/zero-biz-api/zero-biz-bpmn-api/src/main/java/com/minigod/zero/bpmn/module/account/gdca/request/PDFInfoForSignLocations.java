package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 签署位置集合 pageNo：签名的页数
 * x：左下角 x 坐标 y：左下角 y 坐标
 */
@Data
public class PDFInfoForSignLocations implements Serializable {
    private static final long serialVersionUID = -8898050388107272713L;
    /**
     * 签名的页数
     */
    private Integer pageNo;
    /**
     * x：左下角
     */
    private Integer x;
    /**
     * y：左下角
     */
    private Integer y;
}
