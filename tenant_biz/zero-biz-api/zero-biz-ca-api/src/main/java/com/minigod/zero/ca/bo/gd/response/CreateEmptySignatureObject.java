package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * 推送 PDF返回结果
 *
 * @author eric
 * @since 2024-05-12 17:21:09
 */
@Data
public class CreateEmptySignatureObject {
    /**
     * 待签名的 hash
     */
    private String signHash;
    /**
     * pdf id
     */
    private String pdfId;
}
