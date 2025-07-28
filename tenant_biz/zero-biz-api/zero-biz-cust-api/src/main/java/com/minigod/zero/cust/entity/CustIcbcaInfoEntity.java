package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工银客户信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Data
@TableName("cust_icbca_info")
@ApiModel(value = "CustIcbcaInfo对象", description = "工银客户信息表")
@EqualsAndHashCode(callSuper = true)
public class CustIcbcaInfoEntity extends AppEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 工银账号ci_no
     */
    @ApiModelProperty(value = "工银账号ci_no")
    private String ciNo;
    /**
     * 工银账号medium_no
     */
    @ApiModelProperty(value = "工银账号medium_no")
    private String mediumNo;
    /**
     * 工银账号medium_id
     */
    @ApiModelProperty(value = "工银账号medium_id")
    private String mediumId;

}
