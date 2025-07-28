package com.minigod.zero.bpm.vo;

import com.minigod.zero.bpm.entity.BpmSystemConfigEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置信息表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BpmSystemConfigVO extends BpmSystemConfigEntity {
	private static final long serialVersionUID = 1L;

}
