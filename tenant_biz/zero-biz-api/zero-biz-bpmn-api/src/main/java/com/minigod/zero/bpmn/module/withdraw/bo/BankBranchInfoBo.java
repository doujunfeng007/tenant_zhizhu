package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分行信息业务对象 bank_branch_info
 *
 * @author chenyu
 * @date 2023-04-21
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("分行信息业务对象")
public class BankBranchInfoBo extends BaseEntity {

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 分行编码
     */
    @ApiModelProperty(value = "分行编码")
    private String branchCode;

    /**
     * 分行名称
     */
    @ApiModelProperty(value = "分行名称")
    private String branchName;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 状态(1可用 2不可用)
     */
    @ApiModelProperty(value = "状态(1可用 2不可用)")
    private Integer status;

    /** 分行名称模糊查询条件 */
    @ApiModelProperty(value = "银行名称多条件模糊查询")
    private List<String> branchNameList;

    /**
     * #######################################################################
     * ################################扩展字段################################
     * #######################################################################
     */

    /**
     * 分行名称搜索
     */
    @ApiModelProperty(value = "分行名称搜索")
    private String branchNameSerach;


}
