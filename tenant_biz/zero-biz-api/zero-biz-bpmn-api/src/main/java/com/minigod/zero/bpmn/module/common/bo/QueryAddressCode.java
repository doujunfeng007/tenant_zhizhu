package com.minigod.zero.bpmn.module.common.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: QueryAddressCode
 * @Description:
 * @Author chenyu
 * @Date 2022/9/6
 * @Version 1.0
 */
@Data
public class QueryAddressCode {
    @ApiModelProperty(value = "国家代码",required = true)
    private String countryCode;
    @ApiModelProperty("省份代码")
    private String provinceCode;
    @ApiModelProperty("市级代码")
    private String cityCode;
}
