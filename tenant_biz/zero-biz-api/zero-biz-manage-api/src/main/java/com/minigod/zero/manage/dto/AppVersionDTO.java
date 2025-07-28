package com.minigod.zero.manage.dto;

import com.minigod.zero.manage.entity.AppVersionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APP版本信息表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppVersionDTO extends AppVersionEntity {
	private static final long serialVersionUID = 1L;

}
