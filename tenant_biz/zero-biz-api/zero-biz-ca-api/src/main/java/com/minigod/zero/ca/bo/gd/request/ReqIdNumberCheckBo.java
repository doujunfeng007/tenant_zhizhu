package com.minigod.zero.ca.bo.gd.request;

import feign.form.FormProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 公安两要素验证
 *
 * @since 2024-05-12 17:53:00
 */
@Data
public class ReqIdNumberCheckBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 身份证号
     */
    @FormProperty("idCode")
    private String idCode;
    /**
     * 姓名
     */
    @FormProperty("name")
    private String name;
    /**
     * 事务 ID，用于异常时的查询，长度小于
     * 100 大于 20，只能包含数字和字母
     */
    @FormProperty("transactionId")
    private String transactionId;

}
