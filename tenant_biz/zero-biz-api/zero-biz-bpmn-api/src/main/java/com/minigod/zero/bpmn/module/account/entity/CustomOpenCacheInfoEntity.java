package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.minigod.zero.core.mp.base.AppEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.time.LocalDateTime;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("custom_open_cache_info")
@ApiModel(value = "CustomOpenCacheInfo对象", description = "")
public class CustomOpenCacheInfoEntity extends AppEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户 ID
	 */
	@ApiModelProperty(value = "用户 ID")
	private Long userId;
	/**
	 * 步骤
	 */
	@ApiModelProperty(value = "步骤")
	private Integer step;
	/**
	 * 缓存内容
	 */
	@ApiModelProperty(value = "缓存内容")
	private String jsonInfo;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

}
