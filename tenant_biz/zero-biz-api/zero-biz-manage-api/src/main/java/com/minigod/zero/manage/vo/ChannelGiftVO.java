package com.minigod.zero.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 渠道礼包
 *
 * @author eric
 * @since 2024-12-24 09:31:08
 */
@Data
public class ChannelGiftVO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 渠道ID
	 */
	private String channelId;
	/**
	 * 礼包名称
	 */
	private String giftName;
	/**
	 * 渠道密码
	 */
	private String channelPwd;
	/**
	 * 库存数量
	 */
	private Integer remainCount;
	/**
	 * 操作人员ID
	 */
	private Integer oprId;
	/**
	 * 操作人员名称
	 */
	private String oprName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 礼包奖品方案
	 */
	private Integer planId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 公司名称
	 */
	private String companyName;
}
