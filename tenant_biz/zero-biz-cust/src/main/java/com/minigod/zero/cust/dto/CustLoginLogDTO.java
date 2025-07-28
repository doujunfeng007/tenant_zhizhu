package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustLoginLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登陆日志表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustLoginLogDTO extends CustLoginLogEntity {
	private static final long serialVersionUID = 1L;

}
