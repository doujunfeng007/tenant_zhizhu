package com.minigod.zero.cust.apivo;

import com.minigod.zero.cust.apivo.req.UserLoginReqVO;
import com.minigod.zero.cust.entity.CustInfoEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.LockVO
 * @Date: 2023年02月17日 10:20
 * @Description:
 */
@Data
public class LockVO implements Serializable {

	private Long custId;
	private String account; //登入账号
	private String pwd; //登入密码
	private String captcha; //验证码
	private String key;
	private String src;//来源
	private String phone;//手机号
	private Integer eventId;//验证码ID
	private Integer operationStep;
	private boolean isPlainText = false; //密码是否明文

	public void initParam(CustInfoEntity custInfo, UserLoginReqVO userLoginVO, UserUnLock userUnLock) {

		if (custInfo != null) {
			//用户基础信息
			setCustId(custInfo.getId());
		}
		if (userLoginVO != null && userLoginVO.getParams() != null) {
			UserLoginReqVO.UserLoginVO reqUser = userLoginVO.getParams();
			setAccount(reqUser.getCertCode());
			setPwd(reqUser.getPwd());
			setKey(reqUser.getKey());
			setSrc(userLoginVO.getSrc());
		}
		//用户号解锁相关
		if (userUnLock != null) {
			setCaptcha(userUnLock.getCaptcha());
			setPhone(userUnLock.getPhone());
		}
	}
}
