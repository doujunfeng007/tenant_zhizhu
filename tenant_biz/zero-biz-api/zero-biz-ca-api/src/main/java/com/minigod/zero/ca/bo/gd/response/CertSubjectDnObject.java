package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * 证书主题返回值
 *
 * @author eric
 * @since 2024-05-12 16:53:09
 */
@Data
public class CertSubjectDnObject {
    /**
     * 证书主题
     */
    private String subjectDn;
}
