package com.minigod.auth.granter.captcha;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 *
 * @author: guangjie.liao
 * @Date: 2024/4/18 10:05
 * @Description: 对手机号，验证码，认证结果进行包装
 */
public class CaptchaAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;
    private Object credentials;
	private String areaCode;
	private String tenantId;
	private String captchaKey;


	private String packageNum;

	/**
	 * 渠道id
	 */
	private Long channelId;
	/**
	 * 经理人id
	 */
	private Long brokerId;//渠道



	/**
     * 需要认证
     * @param principal
     * @param credentials
     */
    public CaptchaAuthenticationToken(Object principal, Object credentials,String areaCode,String tenantId,String captchaKey,String packageNum,Long channelId,Long brokerId) {
        super((Collection) null);
		this.tenantId = tenantId;
        this.principal = principal;
        this.credentials = credentials;
		this.areaCode = areaCode;
		this.captchaKey = captchaKey;
        this.setAuthenticated(false);
		this.packageNum = packageNum;
		if (!ObjectUtils.isEmpty(channelId)){
			this.channelId = channelId;
		}
		if (!ObjectUtils.isEmpty(brokerId)){
			this.brokerId = brokerId;
		}
    }
    /**
     * 不需要认证
     * @param principal
     * @param credentials
     * @param authorities
     */
    public CaptchaAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

	public String getAreaCode(){
		return this.areaCode;
	}

	public String getCaptchaKey() {
		return captchaKey;
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}


	public String getTenantId(){
		return this.tenantId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public Long getBrokerId() {
		return brokerId;
	}


	public String getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(String packageNum) {
		this.packageNum = packageNum;
	}

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
