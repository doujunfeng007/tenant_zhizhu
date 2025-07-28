package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.io.File;

/**
 * PDF验签
 *
 * @author eric
 * @since 2024-05-12 22:21:00
 */
@Data
public class SignatureBo {
    /**
     * pdf文件
     */
    private File file;

    public void checkValidate() {
        Validate.notNull(file, "pdf文件不能为空");
    }
}
