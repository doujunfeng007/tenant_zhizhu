package com.minigod.zero.bpmn.module.common.vo.address;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: City
 * @Description:
 * @Author chenyu
 * @Date 2022/9/6
 * @Version 1.0
 */
@Data
public class City {
    private static final long serialVersionUID = 7624715622333348332L;
    private String label;
    private String code;
    private Long value;
    private List<Area> children;

}
