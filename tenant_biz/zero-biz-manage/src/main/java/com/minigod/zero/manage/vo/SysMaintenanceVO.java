package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.SysMaintenanceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统维护 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMaintenanceVO extends SysMaintenanceEntity {
	private static final long serialVersionUID = 1L;
	private String createUserName;
	private String updateUserName;
}
