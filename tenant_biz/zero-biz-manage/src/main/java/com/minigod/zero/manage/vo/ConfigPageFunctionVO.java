package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.ConfigPageFunctionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置页面组件 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigPageFunctionVO extends ConfigPageFunctionEntity {
	private static final long serialVersionUID = 1L;
	private String createUserName;
	private String updateUserName;
}
