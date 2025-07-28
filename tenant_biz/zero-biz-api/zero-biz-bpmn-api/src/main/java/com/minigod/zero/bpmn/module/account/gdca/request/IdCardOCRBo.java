package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.io.File;

/**
 * 身份证 OCR
 */
@Data
public class IdCardOCRBo {
    /**
     * 身份证照片
     */
    private File image;

    public void checkValidate() {
        Validate.notNull(image, "身份证照片不能为空");
    }
}
