package com.minigod.zero.trade.hs.vo;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:30 PM
 */
@Data
public abstract class GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 功能号
     */
	@NotBlank
    protected String functionId;

    /**
     * 语言种类
     */
    protected String lang;

    /**
     * sid 渠道编号
     */
    protected String sid = "0232d1566c0638a0a7583c967254c717";

    /**
     * sessionId
     */
    protected String sessionId = "0232d1566c0638a0a7583c967254c717";

	/**
	 * 站点/电话
	 */
	private String opStation;
    protected Long userId;
    protected String ipAddress;
    protected String pkgId;
    protected long recvTime;
    private Integer deviceId;

    public GrmRequestVO copyCommonParams(GrmRequestVO grmRequestVO){
        this.setIpAddress(grmRequestVO.getIpAddress());
        this.setSessionId(grmRequestVO.getSessionId());
        this.setSid(grmRequestVO.getSid());
        String sessionId = grmRequestVO.getSessionId();
        if(StringUtils.isNotEmpty(sessionId)){
            this.setSessionId(sessionId);
        }
        this.setLang(grmRequestVO.lang);
        return this;
    }
}
