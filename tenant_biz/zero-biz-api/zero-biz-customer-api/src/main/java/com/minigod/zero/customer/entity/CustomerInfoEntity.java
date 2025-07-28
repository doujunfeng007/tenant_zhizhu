package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 客户信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_info")
public class CustomerInfoEntity extends TenantEntity {
	@ApiModelProperty("主键id")
	@TableId(
		value = "id",
		type = IdType.AUTO
	)
	private Long id;

	/**
	 * 租户id
	 */
	private String tenantId;

	/**
	 * 客户昵称
	 */
	private String nickName;

	/**
	 * 客户签名
	 */
	private String signature;

	/**
	 * 客户性别(0男，1女)
	 */
	private Integer gender;

	/**
	 * 客户图像
	 */
	private String custIcon;

	/**
	 * 渠道id
	 */
	private String custChannel;

	/**
	 * 上级经理人id
	 */
	//private Long brokerId;

	/**
	 * 客户密码
	 */
	private String password;

	/**
	 * 推荐人ID,邀请该客户的客户ID
	 */
	private Long invCustId;

	/**
	 * 客户类型：0-游客 1-普通个户 2-认证投顾 3-官方账号 4-公司授权人 5-存量ESOP客户
	 */
	private Integer custType;

	/**
	 * 乐观锁版本号
	 */
	private Integer lockVersion;

	/**
	 * 手机号
	 */
	private String cellPhone;

	/**
	 * 国际区号
	 */
	private String areaCode;

	/**
	 * 注册来源：1-web 2-ios 3-aos 4-pc 5-h5
	 */
	private Integer regSourceType;

	/**
	 * 注册渠道：0=未知 1=互联网 2=线下开户 3-公司授权 4-ESOP开户
	 */
	private Integer regChannel;

	/**
	 * 注册邮箱
	 */
	private String cellEmail;

	/**
	 * 创建人
	 */
	private Long createUser;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人
	 */
	private Long updateUser;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 客户状态 0-停用,1-正常,2-锁定,3-注销
	 */
	private Integer status;

	/**
	 * 注销时间
	 */
	private Date cancelTime;

	/**
	 * 关联客户ID
	 */
	private Long bindCust;

	/**
	 * 是否已删除：1-已删除
	 */
	private Integer isDeleted;

	/**
	 *
	 */
	private Long createDept;

	/**
	 * 用户pi等级
	 * 1-Common 2-Important 3-VIP 4-PI 5-Capital]
	 */
	private Integer piLevel;

	private Integer riskLevel;

	private Integer piRiskLevel;
	/**
	 * 是否做过衍生品调查 0未做，1做了
	 */
	private Integer derivative;

	private Date riskExpiryDate;

	/**
	 * 密码修改时间
	 */
	private Date pwdUpdTime;
	/**
	 * 密码提醒状态
	 */
	private Integer pwdWarnStatus;

	private static final long serialVersionUID = 1L;

}
