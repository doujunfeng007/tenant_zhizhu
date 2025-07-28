package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MR白名单 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@TableName("oms_mr_white")
@ApiModel(value = "MrWhite对象", description = "MR白名单")
@EqualsAndHashCode(callSuper = true)
public class MrWhiteEntity extends BaseEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Integer userId;
    /**
     * 修改人名
     */
    @ApiModelProperty(value = "修改人名")
    private String updateUserName;

}
