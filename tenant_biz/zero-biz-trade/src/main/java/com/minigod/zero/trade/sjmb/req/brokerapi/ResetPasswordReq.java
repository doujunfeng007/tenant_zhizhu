package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName ResetPasswordReq.java
 * @Description TODO
 * @createTime 2024年03月04日 15:34:00
 */
@Data
public class ResetPasswordReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountId;

	private String newPassword;
}
