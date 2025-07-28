package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.BoardInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公告信息表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BoardInfoVO extends BoardInfoEntity {
	private static final long serialVersionUID = 1L;

	private String createUserName;
	private String updateUserName;

}
