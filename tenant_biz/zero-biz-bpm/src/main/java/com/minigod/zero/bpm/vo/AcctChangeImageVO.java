package com.minigod.zero.bpm.vo;

import com.minigod.zero.bpm.entity.AcctChangeImageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户修改资料图片表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AcctChangeImageVO extends AcctChangeImageEntity {
	private static final long serialVersionUID = 1L;

	private boolean changeTag;
}
