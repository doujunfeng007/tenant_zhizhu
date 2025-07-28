package com.minigod.common.constant;

public interface ServiceConstant {

	/**
	 * 行情服务名前缀
	 */
	String MKTQUOT_NAME_PREFIX = "mktquot-";

	/**
	 * 资讯服务名前缀
	 */
	String MKTINFO_NAME_PREFIX = "mktinfo-";

	/**
	 * 转码服务名前缀
	 */
	String MKTTSC_NAME_PREFIX = "mkttsc-";

	/**
	 * 行情管理服务名前缀
	 */
	String MKTMGR_NAME_PREFIX = "mktmgr-";


	/**
	 * 行情PC和App统一接口服务名
	 */
	String SERVICE_MKTQUOT_OPENAPI_NAME = MKTQUOT_NAME_PREFIX + "openapi";

	/**
	 * 行情分钟线计算服务名
	 */
	String SERVICE_MKTQUOT_CORE_NAME = MKTQUOT_NAME_PREFIX + "core";

	/**
	 * 行情分钟线计算服务名
	 */
	String SERVICE_MKTQUOT_MINUTE_NAME = MKTQUOT_NAME_PREFIX + "minute";

	/**
	 * 行情推送服务名
	 */
	String SERVICE_MKTQUOT_PUSH_NAME = MKTQUOT_NAME_PREFIX + "push";

	/**
	 * 行情定时任务执行器
	 */
	String SERVICE_MKTQUOT_TASK_NAME = MKTQUOT_NAME_PREFIX + "task";


	/**
	 * 资讯服务名（F10/新闻/公告）
	 */
	String SERVICE_MKTINFO_SERVICE_NAME = MKTINFO_NAME_PREFIX + "service";

	/**
	 * 资讯定时任务执行器
	 */
	String SERVICE_MKTINFO_TASK_NAME = MKTINFO_NAME_PREFIX + "task";


	/**
	 * A股行情转码服务名
	 */
	String SERVICE_MKTTSC_A_NAME = MKTTSC_NAME_PREFIX + "a";

	/**
	 * 美股行情转码服务名
	 */
	String SERVICE_MKTTSC_US_NAME = MKTTSC_NAME_PREFIX + "us";

	/**
	 * 港股行情转码服务名
	 */
	String SERVICE_MKTTSC_HK_NAME = MKTTSC_NAME_PREFIX + "hk";

	/**
	 * 港股延迟行情转码服务名
	 */
	String SERVICE_MKTTSC_DELAY_NAME = MKTTSC_NAME_PREFIX + "delay";

	/**
	 * 港股暗盘转码服务名
	 */
	String SERVICE_MKTTSC_SPARROW_NAME = MKTTSC_NAME_PREFIX + "sparrow";


	/**
	 * 行情管理服务名（SaaS）
	 */
	String SERVICE_MKTMGR_SERVICE_NAME = MKTMGR_NAME_PREFIX + "service";

	/**
	 * 行情管理Client服务名（租户）
	 */
	String SERVICE_MKTMGR_CLIENT_NAME = MKTMGR_NAME_PREFIX + "client";

	/**
	 * 行情数据修复工具
	 */
	String SERVICE_MKTMGR_TOOLS_NAME = MKTMGR_NAME_PREFIX + "tools";

	/**
	 * 行情管理定时任务执行器
	 */
	String SERVICE_MKTMGR_TASK_NAME = MKTMGR_NAME_PREFIX + "task";


}
