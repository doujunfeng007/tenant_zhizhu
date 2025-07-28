package com.minigod.zero.trade.hs.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统维护白名单
 */
@Data
public class MaintainWhiteList implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;//主键
	private Integer userId;//用户号
	private Date createTime;//创建时间
	private Date modifyTime;//修改时间
}
