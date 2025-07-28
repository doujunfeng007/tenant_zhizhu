package com.minigod.zero.bpmn.module.common.vo.address;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Province
 * @Description:
 * @Author chenyu
 * @Date 2022/9/6
 * @Version 1.0
 */
@Data
public class Province implements Serializable {
    private static final long serialVersionUID = 7624715622333348332L;
    private String label;
    private String code;
    private Long value;
    private List<City> children;
}
