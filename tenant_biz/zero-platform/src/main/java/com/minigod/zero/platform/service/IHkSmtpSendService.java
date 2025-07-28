package com.minigod.zero.platform.service;

/**
 * @author Zhe.Xiao
 */
public interface IHkSmtpSendService {

	/**
	 * 结单邮件发送香港邮件服务器
	 * @param type daily/monthly
	 */
	void statementSendHkSmtp(String type) throws Exception;

}
