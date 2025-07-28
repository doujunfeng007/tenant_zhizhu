package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 公安两要素对比
 *
 * @author eric
 * @since 2025-05-12 22:33:00
 */
@Data
public class IdNumberCheckBo {
    /**
     * 身份证号
     */
    private String idCode;
    /**
     * 姓名
     */
    private String name;
    public void checkValidate() {
        Validate.notBlank(idCode, "身份证号不能为空");
        Validate.notBlank(name, "姓名不能为空");
    }
}
