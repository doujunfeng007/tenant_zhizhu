package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 获取证书主题请求参数
 *
 * @author eric
 * @since 2024-05-12 18:36:09
 */
@Data
public class CertDnByGdCABo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    private String idCode;
    /**
     * 所在省份
     */
    private String province;
    /**
     * 所在城市
     */
    private String city;

    /**
     * 校验参数
     *
     * @return
     */
    public void checkValidate() {
        Validate.notBlank(name, "姓名不能为空！");
        Validate.notBlank(idCode, "身份证号码不能为空！");
        Validate.notBlank(province, "所在省份不能为空！");
        Validate.notBlank(city, "所在城市不能为空！");
    }
}
