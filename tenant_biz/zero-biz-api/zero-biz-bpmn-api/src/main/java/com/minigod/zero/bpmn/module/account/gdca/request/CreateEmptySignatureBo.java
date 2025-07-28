package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.io.File;

/**
 * 推送pdf
 *
 * @author eric
 * @since 2024-05-12 19:23:09
 */
@Data
public class CreateEmptySignatureBo {
    /**
     * 待签名的 PDF
     */
    private File pdf;
    /**
     * 签章图片
     */
    private File signatureImage;
    /**
     * 签名地理位置
     */
    private String location;
    /**
     * 签名理由
     */
    private String reason;
    /**
     * 签章图片的宽度
     */
    private float width;
    /**
     * 签章图片的高度
     */
    private float height;
    /**
     * 签署位置集合 pageNo：签名的页数
     * x：左下角 x 坐标 y：左下角 y 坐标
     * [{"pageNo":1,"x":227.0,"y":206.0}, {"pageNo":10,"x":300.0,"y":150.0}]
     */
    private String signatureLocations;

    public void checkValidate() {
        Validate.notNull(pdf, "待签名的pdf不能为空");
        Validate.notNull(signatureImage, "签章图片不能为空");
        Validate.notNaN(width, "签章图片宽度不能为空");
        Validate.notNaN(height, "签章图片高度不能为空");
        if (width < 0) {
            throw new RuntimeException("签章图片宽度不能小于0");
        }
        if (height < 0) {
            throw new RuntimeException("签章图片高度不能小于0");
        }
    }
}
