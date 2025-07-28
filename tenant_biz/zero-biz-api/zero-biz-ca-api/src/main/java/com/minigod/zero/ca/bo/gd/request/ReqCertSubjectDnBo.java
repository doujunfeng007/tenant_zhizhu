package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取证书主题参数
 *
 * @author eric
 * @since 2024-05-12 16:44:10
 */
@Data
public class ReqCertSubjectDnBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    @FormProperty("name")
    private String name;
    /**
     * 身份证号码
     */
    @FormProperty("idCode")
    private String idCode;
    /**
     * 所在省份
     */
    @FormProperty("province")
    private String province;
    /**
     * 所在城市
     */
    @FormProperty("city")
    private String city;
}
