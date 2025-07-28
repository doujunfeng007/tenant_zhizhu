package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * PDF验签
 *
 * @author eric
 * @since 2024-05-12 22:19:10
 */
@Data
public class ReqSignatureBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * pdf文件
     */
    @FormProperty("file")
    private File file;
}
