package com.minigod.zero.manage.vo.request;

import com.minigod.zero.manage.entity.AppVersionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APP版本信息表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppVersionVO extends AppVersionEntity {
	private static final long serialVersionUID = 1L;

	private Integer toAll;// 1是，0 否

	private String startTime;

	private String endTime;
}
