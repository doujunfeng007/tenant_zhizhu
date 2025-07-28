package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;

/**
 * 区域对象 bank_area
 *
 * @author chenyu
 * @date 2023-04-20
 */
@Data
@TableName("bank_area")
public class BankArea extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 父级编号
     */
    private Long pid;
    /**
     * 区域等级(1-省 2-市 3-区县)
     */
    private Integer level;
    /**
     * 状态(1可用 2不可用)
     */
    private Integer status;

}
