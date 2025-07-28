package com.minigod.zero.cust.apivo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.excel.FeedbackExcel
 * @Date: 2023年03月17日 11:48
 * @Description:
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class FeedbackExcel implements Serializable {


	private static final long serialVersionUID = 1L;


	@ExcelProperty("id")
	private Long id;


	@ExcelProperty("用户号")
	private Long custId;

	@ExcelProperty("昵称")
	private String nickName;
	/**
	 * 联系方式
	 */
	@ExcelProperty("联系方式")
	private String contact;
	/**
	 * ip地址
	 */
	@ExcelProperty("ip地址")
	private String ip;
	/**
	 * 反馈内容
	 */
	@ExcelProperty("反馈内容")
	private String content;
	/**
	 * 图片url
	 */
	@ExcelProperty("图片url")
	private String imageUrl;
	/**
	 * 来源
	 */
	@ExcelProperty("来源")
	private String source;
	/**
	 * 回复状态(N-未回复,Y-已回复)
	 */
	@ExcelProperty("回复状态(N-未回复,Y-已回复)")
	private String replyStatus;
	/**
	 * 最近回复时间
	 */
	@ExcelProperty("最近回复时间")
	private Date replyTime;
	/**
	 * 是否已经处理(N-待处理,Y-已处理)
	 */
	@ExcelProperty("是否已经处理")
	private String handleStatus;
	/**
	 * 处理意见
	 */
	@ExcelProperty("处理意见")
	private String handleResult;
	/**
	 * 处理人
	 */
	@ApiModelProperty(value = "处理人")
	private Long handleUser;
	/**
	 * 处理时间
	 */
	@ApiModelProperty(value = "处理时间")
	private Date handleTime;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private Long createUser;
	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	private Long createDept;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	private Long updateUser;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**
	 * 状态：0-无效/禁用 1-有效/启用
	 */
	@ApiModelProperty(value = "状态：0-无效/禁用 1-有效/启用")
	private Integer status;



}
