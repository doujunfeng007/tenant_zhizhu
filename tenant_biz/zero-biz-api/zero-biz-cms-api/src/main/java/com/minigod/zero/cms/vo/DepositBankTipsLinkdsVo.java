package com.minigod.zero.cms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: sunline
 * @date: 2019/5/9 9:36
 * @version: v1.0
 */
@Data
public class DepositBankTipsLinkdsVo implements Serializable {
	private static final long serialVersionUID = -2673765160309518497L;

	private String appWyHkd;
	private String appWyUsd;
	private String pcWyHkd;
	private String pcWyUsd;
	private String gt;
}
