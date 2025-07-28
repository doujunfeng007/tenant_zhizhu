package com.minigod.zero.bpm.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 暗盘协议记录表
 * </p>
 *
 * @author zxw
 * @since 2023-10-07
 */
@Data
@ApiModel(value = "PhillipProtocolLog对象", description = "暗盘协议记录表")
public class PhillipProtocolLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**
     * 客户id
     */
    private Long custId;

    /**
     * 是否同意1、同意 0、不同意
     */
    private String isAgree;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否已删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
