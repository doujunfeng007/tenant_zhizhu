package com.minigod.zero.system.dto;

import com.minigod.zero.system.entity.FuncConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能配置模块 数据传输对象实体类
 *
 * @author ZSDP
 * @since 2024-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FuncConfigDTO extends FuncConfig {
	private static final long serialVersionUID = 1L;

}
