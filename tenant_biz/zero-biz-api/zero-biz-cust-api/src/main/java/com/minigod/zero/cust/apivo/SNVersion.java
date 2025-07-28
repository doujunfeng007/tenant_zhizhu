package com.minigod.zero.cust.apivo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName SNVersion.java
 * @Description TODO
 * @createTime 2023年02月14日 10:36:00
 */
@Data
public class SNVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 前端请求接口的版本号
	 */
	private String version;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 	由请求调用者生成的一个请求ID,后端可用于做并发控制，对于同一session是唯一的
	 *  APP端生成规则：app当前时间的timestamp+6位自增长数字，例如：1425908755444000001，改时间可能重复但风险不大
	 */
	private String id;

	/**
	 * 请求来源,sunline證券國際PC版调用src=PC，APP调用为空（默认APP），WEB调用src=WEB(后续可扩展)
	 */
	private String src;
	/**
	 * 官网用户登录凭证
	 */
	private String userToken;

	/**
	 * 客户端请求ip
	 */
	private String ip;
	/**
	 * 客户端mac
	 */
	private String mac;

	/**
	 * 请求来源
	 */
	private String requestSrc;

	/**
	 * 语言
	 */
	private String lang;
}
