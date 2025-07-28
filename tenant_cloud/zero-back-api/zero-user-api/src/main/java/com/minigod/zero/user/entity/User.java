
package com.minigod.zero.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.tenant.mp.TenantEntity;

import java.util.Date;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("zero_user")
@EqualsAndHashCode(callSuper = true)
public class User extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	private String code;
	/**
	 * 用户类型：1-证券户 2-基金户 3-ESOP人事 4-ESOP员工
	 */
	private Integer userType;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 真名
	 */
	private String realName;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 区号
	 */
	private String area;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 部门id
	 */
	private String deptId;
	/**
	 * 岗位id
	 */
	private String postId;

	/**
	 * 证件类型   1:身份证号码
	 */
	private Integer idcardType;

	/**
	 * 证件号码
	 */
	private String idcardNo;

	/**
	 * 智珠custId
	 */
	private Long custId;
	/**
	 * 是否需要重置密码
	 */
	private Integer updatePwd;

}
