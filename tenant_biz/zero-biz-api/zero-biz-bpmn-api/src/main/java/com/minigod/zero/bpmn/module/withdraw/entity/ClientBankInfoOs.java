package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @ClassName: ClientBankInfoOs
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/28
 * @Version 1.0
 */
@ApiModel(description = "client_bank_info_os")
@Data
@TableName(value = "client_bank_info_os")
public class ClientBankInfoOs implements Serializable {
    @TableField(value = "id")
    @ApiModelProperty(value = "")
    private Long id;

    @TableField(value = "bank_code")
    @ApiModelProperty(value = "")
    @Size(max = 64, message = "最大长度要小于 64")
    private String bankCode;

    @TableField(value = "bank_name")
    @ApiModelProperty(value = "")
    @Size(max = 256, message = "最大长度要小于 256")
    private String bankName;

    @TableField(value = "bank_name_en")
    @ApiModelProperty(value = "")
    @Size(max = 256, message = "最大长度要小于 256")
    private String bankNameEn;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "")
    private Byte status;

    @TableField(value = "deposit")
    @ApiModelProperty(value = "")
    private Short deposit;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "")
    private Date createTime;

    @TableField(value = "create_user")
    @ApiModelProperty(value = "")
    private Long createUser;

    @TableField(value = "update_time")
    @ApiModelProperty(value = "")
    private Date updateTime;

    @TableField(value = "update_user")
    @ApiModelProperty(value = "")
    private Long updateUser;

    private static final long serialVersionUID = 1L;
}
