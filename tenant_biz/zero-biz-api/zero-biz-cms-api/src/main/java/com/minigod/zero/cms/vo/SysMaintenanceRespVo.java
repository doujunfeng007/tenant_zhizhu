package com.minigod.zero.cms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 掌上智珠
 * @since 2023/9/25 14:00
 */
@Data
public class SysMaintenanceRespVo implements Serializable {
	@ApiModelProperty(value = "id")
	private String id;
	/**
	 * 提示内容(简)
	 */
	@ApiModelProperty(value = "提示内容")
	private String tipsContent;


	private Date forceTime;

	private Date deadTime;

}
