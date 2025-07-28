package com.minigod.zero.trade.afe.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @ClassName AfeConstants.java
 * @Description TODO
 * @createTime 2024年04月19日 18:59:00
 */
public class AfeConstants {

	public static final int SYNC_FUTURE_TIMEOUT = 30;       //发送同步请求的超时时间（单位：秒）
	//消息缓存
	public static final int CACHE_INITIAL_CAPACITY = 300; //缓存容器的初始容量
	public static final long CACHE_MAX_SIZE = 10000;  //缓存大小
	public static final int CACHE_LEVEL = 5000; //并发级别（可同时写缓存的线程数）
	public static final long CACHE_EXPIRE_TIME = SYNC_FUTURE_TIMEOUT; //缓存有效期（一个请求对应一个返回消息）
	public static final long CACHE_EXPIRE_TIME_MULTI = SYNC_FUTURE_TIMEOUT + 5L;  //缓存有效期（一个请求对应多个返回消息）

	/**
	 * web service 接口地址集合
	 */
	public static Map<String,String> INTERFACE_MAP = new HashMap<>();
	/**
	 * 柜台接口返回key
	 */
	public static String RETURN_CODE = "RETURNCODE";
	public static String GENERATE_KEY_DETAIL = "GENERATEKEYDETAIL";
	public static String MOVEMENT_ID = "MOVEMENTID";
	/**
	 * 柜台成功返回key
	 */
	public static String SUCCESS = "SUCCESS";

	/**
	 * 成交推送的msgId
	 */
	public static String ORDER_MSG_ID = "ORDER";

	/**
	 * 连接会话信息
	 */
	public static String WEB_SOCKET_SESSION = "WEB_SOCKET_SESSION:";

	/**
	 * AFE代理人渠道
	 */
	public static String AGENT_CHANNEL ="WU";


}
