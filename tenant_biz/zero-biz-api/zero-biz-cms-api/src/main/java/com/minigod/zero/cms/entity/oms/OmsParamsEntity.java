package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统参数配置表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-17
 */
@Data
@TableName("oms_params")
@ApiModel(value = "OmsParams对象", description = "系统参数配置表")
public class OmsParamsEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 模块
     */
    @ApiModelProperty(value = "模块")
    private String moduleName;
    /**
     * key
     */
    @ApiModelProperty(value = "键")
    private String keyName;
	/**
	 * 值
	 */
	@ApiModelProperty(value = "值")
	private String keyValue;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 更新版本
     */
    @ApiModelProperty(value = "更新版本")
    private Long updateVersion;

}
