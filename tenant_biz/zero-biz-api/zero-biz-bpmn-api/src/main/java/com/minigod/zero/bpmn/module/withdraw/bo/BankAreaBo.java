package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 区域业务对象 bank_area
 *
 * @author chenyu
 * @date 2023-04-20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("区域业务对象")
public class BankAreaBo extends BaseEntity {

    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    private Long id;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字", required = true)
    private String name;

    /**
     * 父级编号
     */
    @ApiModelProperty(value = "父级编号", required = true)
    private Long pid;

    /**
     * 区域等级(1-省 2-市 3-区县)
     */
    @ApiModelProperty(value = "区域等级(1-省 2-市 3-区县)", required = true)
    private Integer level;

    /**
     * 状态(1可用 2不可用)
     */
    @ApiModelProperty(value = "状态(1可用 2不可用)", required = true)
    private Integer status;


}
