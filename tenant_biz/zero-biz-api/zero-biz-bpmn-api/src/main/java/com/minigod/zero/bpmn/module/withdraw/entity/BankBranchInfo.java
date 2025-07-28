package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;

/**
 * 分行信息对象 bank_branch_info
 *
 * @author chenyu
 * @date 2023-04-21
 */
@Data
@TableName("bank_branch_info")
public class BankBranchInfo extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 分行编码
     */
    private String branchCode;
    /**
     * 分行名称
     */
    private String branchName;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态(1可用 2不可用)
     */
    private Integer status;

}
