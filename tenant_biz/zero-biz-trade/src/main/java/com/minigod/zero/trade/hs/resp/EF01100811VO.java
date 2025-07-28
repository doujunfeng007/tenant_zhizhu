package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 811 服务_周边_密码有效期校验
 * @author sunline
 *
 */
public class EF01100811VO implements Serializable {
	private static final long serialVersionUID = -9157528673168386550L;
	//
	private String errorId;
	private String errorInfo;
	private String warningDays;
	private String expiryDays;
	private String expiryDate;
	private String warningFlag;
	//
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getWarningDays() {
		return warningDays;
	}
	public void setWarningDays(String warningDays) {
		this.warningDays = warningDays;
	}
	public String getExpiryDays() {
		return expiryDays;
	}
	public void setExpiryDays(String expiryDays) {
		this.expiryDays = expiryDays;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getWarningFlag() {
		return warningFlag;
	}
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}

}
