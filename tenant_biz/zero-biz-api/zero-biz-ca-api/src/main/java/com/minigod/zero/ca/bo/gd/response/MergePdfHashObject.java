package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * 签署合并返回对象
 *
 * @author eric
 * @since 2024-05-12 17:50:09
 */
@Data
public class MergePdfHashObject {
    /**
     * 签名完成的 PDF
     */
    private String signPdf;

    /**
     * 签名 ID
     */
    private String signatureId;
}
