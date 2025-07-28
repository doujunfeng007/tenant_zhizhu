package com.minigod.zero.ca.bo;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;

/**
 * @ClassName: OcrIdCardBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/29
 * @Version 1.0
 */
@Data
public class OcrIdCardBo {
    @FormProperty("idcard_img_file")
    private File idCardImgFile;
    @FormProperty("idcard_img_base64")
    private String idCardImgBase64;
}
