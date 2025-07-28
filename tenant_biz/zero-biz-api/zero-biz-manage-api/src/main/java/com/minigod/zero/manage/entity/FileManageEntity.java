package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件管理 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
@Data
@TableName("oms_file_manage")
@ApiModel(value = "FileManage对象", description = "文件管理")
@EqualsAndHashCode(callSuper = true)
public class FileManageEntity extends BaseEntity {

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    /**
     * 文件连接
     */
    @ApiModelProperty(value = "文件连接")
    private String filePath;
    /**
     * 修改人名
     */
    @ApiModelProperty(value = "修改人名")
    private String updateUserName;
	/**
	 * 租户 ID
	 */
	@ApiModelProperty(value = "租户 ID")
	private String tenantId;

}
