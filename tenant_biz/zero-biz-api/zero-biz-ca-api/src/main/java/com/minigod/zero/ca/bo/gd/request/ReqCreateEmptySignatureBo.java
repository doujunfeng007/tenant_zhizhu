package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * 推送 PDF请求参数
 *
 * @author eric
 * @since 2024-05-12 17:34:10
 */
@Data
public class ReqCreateEmptySignatureBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 待签名的 PDF
     */
    @FormProperty("pdf")
    private File pdf;
    /**
     * 签章图片
     */
    @FormProperty("signatureImage")
    private File signatureImage;
    /**
     * 签名地理位置
     */
    @FormProperty("location")
    private String location;
    /**
     * 签名理由
     */
    @FormProperty("reason")
    private String reason;
    /**
     * 签章图片的宽度
     */
    @FormProperty("width")
    private float width;
    /**
     * 签章图片的高度
     */
    @FormProperty("height")
    private float height;
    /**
     * 签署位置集合 pageNo：签名的页数
     * x：左下角 x 坐标 y：左下角 y 坐标
     * [{"pageNo":1,"x":227.0,"y":206.0}, {"pageNo":10,"x":300.0,"y":150.0}]
     */
    @FormProperty("signatureLocations")
    private String signatureLocations;

}
