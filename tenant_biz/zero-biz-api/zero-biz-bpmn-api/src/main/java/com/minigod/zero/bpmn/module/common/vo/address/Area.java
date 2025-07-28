package com.minigod.zero.bpmn.module.common.vo.address;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Area
 * @Description:
 * @Author chenyu
 * @Date 2022/9/6
 * @Version 1.0
 */
@Data
public class Area implements Serializable {
    private static final long serialVersionUID = -7334532010294906269L;
    private String label;
    private String code;
    private Long value;
}
