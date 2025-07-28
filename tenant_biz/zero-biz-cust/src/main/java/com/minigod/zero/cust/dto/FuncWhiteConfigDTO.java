package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.FuncWhiteConfigEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能白名单启用配置 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FuncWhiteConfigDTO extends FuncWhiteConfigEntity {
	private static final long serialVersionUID = 1L;

}
