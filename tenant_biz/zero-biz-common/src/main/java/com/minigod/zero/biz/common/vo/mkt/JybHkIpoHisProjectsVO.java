package com.minigod.zero.biz.common.vo.mkt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IPO保荐人等信息 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JybHkIpoHisProjectsVO extends JybHkIpoHisProjectsEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 投资人类别名称
	 */
	@ApiModelProperty(value = "投资人类别名称")
	private String invertorTypeName;
}
