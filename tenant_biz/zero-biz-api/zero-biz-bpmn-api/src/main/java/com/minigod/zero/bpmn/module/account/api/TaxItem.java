package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: TaxItem
 * @Description:
 * @Author lixuan
 * @Date 2022/9/23
 * @Version 1.0
 */
@Data
public class TaxItem implements Serializable {
    private static final long serialVersionUID = 5648761124345124102L;

    private Integer hasTaxCode;

    private String reasonDesc;

    private String taxCode;

    private String taxFeasonType;

    private String taxJurisdiction;
}
