package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.AdInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 广告信息表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdInfoVO extends AdInfoEntity {
	private static final long serialVersionUID = 1L;
	private Integer[] userIds;
	private String[] channelIds;

	private String createUserName;
	private String updateUserName;
}
