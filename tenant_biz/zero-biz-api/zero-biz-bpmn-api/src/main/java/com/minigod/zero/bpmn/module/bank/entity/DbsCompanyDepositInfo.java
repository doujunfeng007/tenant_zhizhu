package com.minigod.zero.bpmn.module.bank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
*@ClassName: DbsCompanyDepositInfo
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/7
*@Version 1.0
*
*/
/**
 * dbs公司入金信息
 */
@ApiModel(description="dbs公司入金信息")
@Data
@TableName(value = "dbs_company_deposit_info")
public class DbsCompanyDepositInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    @NotNull(message = "主键不能为null")
    private Long id;

    /**
     *  公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value=" 公司名称")
    @Size(max = 255,message = " 公司名称最大长度要小于 255")
    @NotBlank(message = " 公司名称不能为空")
    private String companyName;

    /**
     *  公司入金账户
     */
    @TableField(value = "company_account")
    @ApiModelProperty(value=" 公司入金账户")
    @Size(max = 255,message = " 公司入金账户最大长度要小于 255")
    @NotBlank(message = " 公司入金账户不能为空")
    private String companyAccount;

    /**
     *  租户 ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value=" 租户 ID")
    @Size(max = 20,message = " 租户 ID最大长度要小于 20")
    @NotBlank(message = " 租户 ID不能为空")
    private String tenantId;

    private static final long serialVersionUID = 1L;
}
