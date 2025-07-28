package com.minigod.zero.trade.hs.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易费用对照表
 */
@Data
public class SysFareDict implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String exchangeType;//市场（HS同步）
	private String fareDict;//费用代号（HS同步）
	private String fareType;//费用类型（HS同步）
	private String fareName;//费用名称（HS同步）
	private String fareNameCn;//费用简体名称
	private String fareNameHk;//费用繁体名称
	private String fareNameEn;//费用英文名称
	private Integer fareStatus = 1;//登录已激活：0-否 1-是
	private String remark;//备注
	private String updateUser;//修改人
	private Date updateTime;//修改时间
}
