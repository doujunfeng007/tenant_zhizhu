package com.minigod.zero.cust.apivo;

import com.minigod.zero.cust.entity.CustLoginLogEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 登陆日志表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustLoginLogVO extends CustLoginLogEntity {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "创建开始时间")
	private String createTimeStart;

	@ApiModelProperty(value = "创建结束时间")
	private String createTimeEnd;

	@ApiModelProperty(value = "类型集合")
	private List<Integer> typeList;
}
