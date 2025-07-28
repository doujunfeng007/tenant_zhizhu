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
 * @Pcakage: com.minigod.zero.cust.apivo.excel.CustInfoExcel
 * @Date: 2023年03月15日 15:10
 * @Description:
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class CustInfoExcel implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelProperty("用户ID")
	private Long id;
	/**
	 * 客户昵称
	 */
	@ExcelProperty("客户昵称")
	private String nickName;
	/**
	 * 客户签名
	 */
	@ExcelProperty("客户签名")
	private String signature;
	/**
	 * 客户性别(1男，0女)
	 */
	@ExcelProperty("客户性别")
	@ApiModelProperty(value = "客户性别(1男，0女)")
	private Integer gender;
	/**
	 * 客户图像
	 */
	@ExcelProperty("客户图像")
	@ApiModelProperty(value = "客户图像")
	private String custIcon;
	/**
	 * 客户渠道
	 */
	@ExcelProperty("客户渠道")
	@ApiModelProperty(value = "客户渠道")
	private String custChannel;
	/**
	 * 推荐人ID,邀请该客户的客户ID
	 */
	@ExcelProperty("推荐人ID")
	@ApiModelProperty(value = "推荐人ID,邀请该客户的客户ID")
	private Long invCustId;
	/**
	 * 客户职业
	 */
	@ApiModelProperty(value = "客户职业")
	private Integer vocationId;
	/**
	 * 客户类型(默认所有客户均为1类型;0：游客客户；1：普通客户；2：认证投顾，表示已经审核通过的投顾客户；3:官方账号；)
	 */
	@ExcelProperty(value = "客户类型")
	@ApiModelProperty(value = "客户类型(默认所有客户均为1类型;0：游客客户；1：普通客户；2：认证投顾，表示已经审核通过的投顾客户；3:官方账号；)")
	private String custType;

	/**
	 * 隐私开关设置 每一位都用Y/N表示是否开通：第1位：是否允许将我引荐给别人 第2位：是否允许通过手机号搜索到我（控制手机号搜索、通讯录匹配） 第3位：是否接收资讯推送 第4位：加我为联系人时需要验证 第5位:是否开启自选股排序 第6位:是否开启交易通知提醒; 第7位：新股认购弹窗；第8位：交易股票是否添加到自选；第9位：是否同意隐私协议
	 */
	@ApiModelProperty(value = "隐私开关设置 每一位都用Y/N表示是否开通：第1位：是否允许将我引荐给别人 第2位：是否允许通过手机号搜索到我（控制手机号搜索、通讯录匹配） 第3位：是否接收资讯推送 第4位：加我为联系人时需要验证 第5位:是否开启自选股排序 第6位:是否开启交易通知提醒; 第7位：新股认购弹窗；第8位：交易股票是否添加到自选；第9位：是否同意隐私协议")
	private String privacy;



	/**
	 * 最后登录时间
	 */
	@ExcelProperty(value = "最后登录时间")
	@ApiModelProperty(value = "最后登录时间")
	private Date lastLoginTime;
	/**
	 * 最后登录IP地址
	 */
	@ExcelProperty(value = "最后登录IP地址")
	@ApiModelProperty(value = "最后登录IP地址")
	private String lastLoginIp;


	/**
	 * 手机号
	 */
	@ExcelProperty(value = "手机号")
	@ApiModelProperty(value = "手机号")
	private String cellPhone;
	/**
	 * 国际区号
	 */
	@ExcelProperty(value = "国际区号")
	@ApiModelProperty(value = "国际区号")
	private String areaCode;
	/**
	 * 从后台切换到前台需要输入手势密码的时长限制
	 */
	@ApiModelProperty(value = "从后台切换到前台需要输入手势密码的时长限制")
	private Integer getstureShowTime;
	/**
	 * 注册来源：1-web 2-ios 3-aos 4-pc 5-h5
	 */
	@ExcelProperty(value = "注册来源")
	@ApiModelProperty(value = "注册来源")
	private Integer regSourceType;
	/**
	 * 注册渠道：0=未知 1=互联网 2=线下开户 3-公司授权
	 */
	@ExcelProperty(value = "注册渠道")
	@ApiModelProperty(value = "注册渠道")
	private Integer regChannel;
	/**
	 * 是否写入手机号
	 */
	@ApiModelProperty(value = "是否写入手机号")
	private Integer isWritePhone;

	/**
	 * 客户是否接受协议 0:不接受，1:接受
	 */
	@ApiModelProperty(value = "客户是否接受协议 0:不接受，1:接受")
	private Integer acceptAgreement;
	/**
	/**
	 * 注册邮箱
	 */
	@ApiModelProperty(value = "注册邮箱")
	private String cellEmail;
	/**
	 * 是否同意
	 */
	@ApiModelProperty(value = "是否同意")
	private Integer isAgree;
	/**
	 * facebook/intagram
	 */
	@ApiModelProperty(value = "facebook/intagram")
	private String facebookName;

	@ApiModelProperty(value = "语言")
	private String lang;
}
