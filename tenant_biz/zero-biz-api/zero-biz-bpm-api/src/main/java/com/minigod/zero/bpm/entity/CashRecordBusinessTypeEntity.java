package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.Boolean;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 业务查询归属 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_record_business_type")
@ApiModel(value = "CashRecordBusinessType对象", description = "业务查询归属")
public class CashRecordBusinessTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
    private String businessType;
    /**
     * 业务key
     */
    @ApiModelProperty(value = "业务key")
    private String businessKey;
    /**
     * 业务key描述
     */
    @ApiModelProperty(value = "业务key描述")
    private String remark;
    /**
     * key值
     */
    @ApiModelProperty(value = "key值")
    private String keyValue;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Boolean status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
