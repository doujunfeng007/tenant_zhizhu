package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 【请填写功能名称】对象 mkt_user
 *
 * @author bpmx
 * @date 2021-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("mkt_user")
@Alias("cmsMktUser")
public class MktUser extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "客户名称", example = "客户昵称")
	@ExcelProperty("客户名称")
	private String custName;

	/**
	 * 租户号
	 */
	@ApiModelProperty(value = "租户ID", example = "租户ID")
	@ExcelProperty("租户ID")
	private String tenantId;

	/**
	 * 用户userId
	 */
	@ApiModelProperty(value = "客户Id,三方系统中用户唯一ID", example = "用户userId,三方系统中用户唯一ID")
	@ExcelProperty("客户ID")
	private Long custId;

	/**
	 * 0 非专业用户  1 专业用户
	 */
	@ApiModelProperty(value = "是否专业用户(0 非专业用户  1 专业用户) mkt_professiona_user", example = "是否专业用户字典 mkt_professiona_user")
	@ExcelProperty("0 非专业用户  1 专业用户")
	private Integer isProfessionalUser;

	/**
	 * 内部员工开始时间
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("内部员工开始时间")
	@ExcelProperty("内部员工开始时间")
	private Date beginDate;

	/**
	 * 内部员工结束时间
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("内部员工结束时间")
	@ExcelProperty("内部员工结束时间")
	private Date endDate;
}
