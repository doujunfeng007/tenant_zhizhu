package com.minigod.zero.manage.dto;

import com.minigod.zero.manage.entity.HqPackageInfoEntity;
import com.minigod.zero.manage.vo.SnActiveConfigItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动奖品配置详情
 *
 * @author eric
 * @since 2024-12-24 16:01:15
 */
@Data
@ApiModel(value = "活动奖品配置详情")
public class SnActiveConfigItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 奖品配置
	 */
	@ApiModelProperty(value = "奖品配置")
	private SnActiveConfigItemVO snActiveConfigItem;
	/**
	 * 奖品包
	 */
	@ApiModelProperty(value = "奖品包")
	private HqPackageInfoEntity hqPackageInfo;
}
