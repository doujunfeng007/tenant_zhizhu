package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 14:13
 * @Description: 用户信息
 */
@Data
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 工银ci_no
	 */
	@ApiModelProperty(value = "工银ci_no")
	private String cino;

	/**
	 * 有无投资账户 0:无投资账户,1:有投资账户 bpm_account_info （有无数据）
	 */
	@ApiModelProperty(value = "有无投资账户 0:无投资账户,1:有投资账户")
	private Integer accountstatus;

	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机")
	private String mobile;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;

	/**
	 * 港股行情权限 0:无，1:串流，2:计数
	 */
	@ApiModelProperty(value = "港股行情权限 0:无，1:串流，2:计数")
	private Integer hklev;

	/**
	 * A股行情权限 0:延时，1:点击报价
	 */
	@ApiModelProperty(value = "用户ID")
	private Integer alev;

	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String nickname;

	/**
	 * 头像url
	 */
	@ApiModelProperty(value = "头像url")
	private String avatar;

	/**
	 * 头像上传时间戳
	 */
	@ApiModelProperty(value = "头像上传时间戳")
	private String avatartimestamp;

	/**
	 * 是否需要上报美股行情订阅者身份 0:否, 没有弹窗提示，1:是, 弹窗提示
	 */
	@ApiModelProperty(value = "用户ID")
	private String nsqreportstatus;

	/**
	 * 0:未绑定，1:已绑定touchid，2:已绑定faceid  (生物识别)
	 */
	@ApiModelProperty(value = "0:未绑定，1:已绑定touchid，2:已绑定faceid")
	private Integer biostatus;

	/**
	 * 是否需要重置 0 : 否，1 : 是
	 */
	@ApiModelProperty(value = "是否需要重置 0 : 否，1 : 是")
	private String bioreset;

	/**
	 * 申请串流信息
	 */
	@ApiModelProperty(value = "申请串流信息")
	private Stream stream;
}
