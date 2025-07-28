package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 投资者声明信息（美股） 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@TableName("cust_investor_stmt")
@ApiModel(value = "CustInvestorStmt对象", description = "投资者声明信息（美股）")
@EqualsAndHashCode(callSuper = true)
public class CustInvestorStmtEntity extends BaseEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 职位类型
     */
    @ApiModelProperty(value = "职位类型")
    private Integer postType;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    /**
     * 所属行业
     */
    @ApiModelProperty(value = "所属行业")
    private Integer vocation;
    /**
     * 职位级别
     */
    @ApiModelProperty(value = "职位级别")
    private Integer postLevel;
    /**
     * 投资者类型 0 专业投资者 1非专业投资者
     */
    @ApiModelProperty(value = "投资者类型 0 专业投资者 1非专业投资者")
    private Integer investorType;

}
