package com.minigod.zero.cust.apivo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.UserUnLock
 * @Date: 2023年02月17日 10:24
 * @Description:
 */
@Data
public class UserUnLock implements Serializable {

	private String sessionId;

	//解锁用户号/手机号字段
	private String account; //登入账号
	private String certType; //登入类型
	private String captcha; //验证码
	private String phone;//手机号
	private Integer eventId;//验证码ID

	//解锁交易类型字段
	private String userName; //用户姓名
	private String credentialsNum;//证件号码
	private String email;
	private Integer unlockType;//解锁类型 1表示 开户姓名证件 2表示 开户手机号和邮箱

	private Integer unLockLoginType;//1、表示用户号解锁,2表示交易账号解锁

	//检查设备信息
	private String deviceCode;
	//前端传入的错误ID
	private String grmErrorId;
	private String urlPrefix; //发送解锁URL前缀
	private String selectType;//1、表示交易解锁，2、表示重置密码
}
