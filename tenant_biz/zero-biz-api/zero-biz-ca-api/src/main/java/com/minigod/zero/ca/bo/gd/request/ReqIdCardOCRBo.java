package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * 身份证 OCR请求参数
 *
 * @author eric
 * @since 2024-05-12 18:44:12
 */
@Data
public class ReqIdCardOCRBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 身份证照片
     */
    @FormProperty("image")
    private File image;
}
