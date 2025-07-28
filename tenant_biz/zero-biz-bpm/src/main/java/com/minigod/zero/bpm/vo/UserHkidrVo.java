package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserHkidrVo implements Serializable {

	/**
	 * 主键ID
	 */
	private Integer  id;

	/**
	 * 用户号
	 */
	private Integer  userId;

	/**
	 *  授权状态 0	未收集意向、1	已授权、2	未授权、3	机构户，无需收集
	 */
	private Integer hkidrStatus;

	/**
	 *  授权方式 0	App、1	H5、2	线下
	 */
	private Integer hkidrStatusApproach;

	/**
	 *  同步到恒生状态 0 未同步、1	已同步
	 */
	private Integer syncHsStatus;

	private Integer finiStatus;

	private Date finiTime;

	private Date hkidrTime;
}
