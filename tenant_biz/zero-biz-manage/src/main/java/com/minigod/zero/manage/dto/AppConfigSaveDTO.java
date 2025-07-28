package com.minigod.zero.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Xiaowei.Zhu
 * @Date 2023-07-28 17:48:21
 * @Description
 **/
@Data
public class AppConfigSaveDTO implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "适用人群类别 0:全部 1:指定用户")
    private Long id;
    /**
     * 适用人群类别 0:全部 1:指定用户
     */
    @ApiModelProperty(value = "适用人群类别 0:全部 1:指定用户 2:导入用户")
    @NotNull(message = "适用人群类别不能为空")
    private Integer groupType;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @NotEmpty(message = "描述不能为空")
    private String configDesc;
    /**
     * key
     */
    @ApiModelProperty(value = "key")
    @NotEmpty(message = "key不能为空")
    private String itemKey;
    /**
     * 指定用户
     */
    @ApiModelProperty(value = "指定用户")
    private String userIds;
    /**
     * 配置内容项
     */
    @ApiModelProperty(value = "配置内容项")
    @NotEmpty(message = "配置内容项不能为空")
    private String configContent;

	@ApiModelProperty(value = "状态")
	private Integer status ;
}
