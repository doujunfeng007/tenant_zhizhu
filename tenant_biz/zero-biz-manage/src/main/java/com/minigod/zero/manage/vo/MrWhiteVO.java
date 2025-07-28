package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.MrWhiteEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MR白名单 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MrWhiteVO extends MrWhiteEntity {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "客户ID(多个用','隔开)")
	private String userIds;

	private Long custId;

	private Integer status;

	private String createUserName;
	private String updateUserName;
}
