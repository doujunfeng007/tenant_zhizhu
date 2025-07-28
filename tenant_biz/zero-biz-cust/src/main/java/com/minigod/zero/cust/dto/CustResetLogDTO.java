package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustResetLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户密码重置日志 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustResetLogDTO extends CustResetLogEntity {
	private static final long serialVersionUID = 1L;

}
